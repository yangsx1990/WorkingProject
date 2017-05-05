package com.hiersun.oohdear.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import tk.mybatis.mapper.entity.Example;

import com.alipay.api.AlipayApiException;
import com.hiersun.oohdear.entity.Coupon;
import com.hiersun.oohdear.entity.OrderExpress;
import com.hiersun.oohdear.entity.OrderInfo;
import com.hiersun.oohdear.entity.OrderLog;
import com.hiersun.oohdear.entity.OrderPaymentInfo;
import com.hiersun.oohdear.entity.OrderRefund;
import com.hiersun.oohdear.entity.OrderStatus;
import com.hiersun.oohdear.entity.OrderWorkflow;
import com.hiersun.oohdear.entity.SysExpressDict;
import com.hiersun.oohdear.entity.SysUser;
import com.hiersun.oohdear.entity.TpExpressInfo;
import com.hiersun.oohdear.entity.UserMemberInfo;
import com.hiersun.oohdear.entity.vo.OrderDetailVo;
import com.hiersun.oohdear.entity.vo.OrderInfoVo;
import com.hiersun.oohdear.mapper.CouponMapper;
import com.hiersun.oohdear.mapper.OrderExpressMapper;
import com.hiersun.oohdear.mapper.OrderInfoMapper;
import com.hiersun.oohdear.mapper.OrderLogMapper;
import com.hiersun.oohdear.mapper.OrderPaymentInfoMapper;
import com.hiersun.oohdear.mapper.OrderRefundMapper;
import com.hiersun.oohdear.mapper.OrderWorkflowMapper;
import com.hiersun.oohdear.mapper.SysExpressDictMapper;
import com.hiersun.oohdear.mapper.TpExpressInfoMapper;
import com.hiersun.oohdear.mapper.UserMemberInfoMapper;
import com.hiersun.oohdear.service.FileService;
import com.hiersun.oohdear.service.OrderService;
import com.hiersun.oohdear.util.CompressUtils;
import com.hiersun.oohdear.util.OrderStatusAxis;
import com.hiersun.oohdear.util.ThirdPartUtil;

/**
 * 订单服务
 * @author liuyang
 * @email y_liu@hiersun.com | 745089707@qq.com
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	private final static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Autowired
	private OrderInfoMapper orderInfoMapper;
	@Autowired
	private TpExpressInfoMapper tpExpressInfoMapper;
	@Autowired
	private SysExpressDictMapper sysExpressDictMapper;
	@Autowired
	private OrderWorkflowMapper orderWorkflowMapper;
	@Autowired
	private OrderExpressMapper orderExpressMapper;
	@Autowired
	private OrderPaymentInfoMapper orderPaymentInfoMapper;
	@Autowired
	private OrderLogMapper orderLogMapper;
	@Autowired
	private UserMemberInfoMapper userMemberInfoMapper;
	@Autowired
	private OrderRefundMapper orderRefundMapper;
	@Autowired
	private CouponMapper couponMapper;
	@Autowired
	private FileService fileService;
	@Value("${express.url}")
	private String url;
	@Value("${painting.upload.path}")
	private String paintingUploadPath;
	@Value("${diagram.upload.path}")
	private String diagramUploadPath;

	@Value("${user.sms.url}")
	private String smsUrl;
	private static final SimpleDateFormat SDF = new SimpleDateFormat("_yyyyMMddHHmmssSSS");

	public OrderInfo queryByOrderNo(String orderNo) {
		OrderInfo record = new OrderInfo();
		record.setOrderNo(orderNo);
		record = orderInfoMapper.selectOne(record);
		return record;
	}

	@Override
	public OrderInfo queryById(Long id) {
		return orderInfoMapper.selectByPrimaryKey(id);
	}

	public List<OrderInfoVo> orderList(OrderInfoVo orderInfoVo) {
		return orderInfoMapper.orderList(orderInfoVo);
	}

	@Override
	public OrderDetailVo detail(String orderNo) {
		OrderDetailVo orderDetailVo = new OrderDetailVo();
		//1、订单信息
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setOrderNo(orderNo);
		orderInfo = orderInfoMapper.selectOne(orderInfo);
		orderDetailVo.setOrderInfo(orderInfo);
		//2、工作流
		Example workflow = new Example(OrderWorkflow.class);
		workflow.setOrderByClause("created ASC");
		workflow.createCriteria().andEqualTo("orderNo", orderNo);
		List<OrderWorkflow> workflows = orderWorkflowMapper.selectByExample(workflow);
		//状态轴
		List<String[]> statusAxis = OrderStatusAxis.get(false, workflows, orderInfo.getOrderStatus());
		orderDetailVo.setStatusAxis(statusAxis);
		//3、支付信息
		OrderPaymentInfo orderPaymentInfo = new OrderPaymentInfo();
		orderPaymentInfo.setOrderNo(orderNo);
		orderPaymentInfo = orderPaymentInfoMapper.selectOne(orderPaymentInfo);
		orderDetailVo.setOrderPaymentInfo(orderPaymentInfo);
		//优惠券信息
		Integer couponId = orderInfo.getCouponId();
		if(couponId != null){
			Coupon coupon = couponMapper.selectByPrimaryKey(couponId);
			String couponTypeFullName = coupon.getTypeName()+"-"+coupon.getName()+"-"+coupon.getCouponCode();
			orderDetailVo.setCouponTypeFullName(couponTypeFullName);
		}else{
			orderDetailVo.setCouponTypeFullName("无");
		}
		//4、物流信息
		OrderExpress orderExpress = new OrderExpress();
		orderExpress.setOrderNo(orderNo);
		orderExpress = orderExpressMapper.selectOne(orderExpress);
		orderDetailVo.setOrderExpress(orderExpress);
		//5、操作记录
		Example orderLogExample = new Example(OrderLog.class);
		orderLogExample.createCriteria().andEqualTo("orderNo", orderNo);
		orderLogExample.setOrderByClause("created DESC");
		List<OrderLog> orderLogList = orderLogMapper.selectByExample(orderLogExample);
		orderDetailVo.setOrderLogList(orderLogList);
		//6、用户信息
		if (orderInfo != null) {
			UserMemberInfo userMemberInfo = new UserMemberInfo();
			userMemberInfo.setMemberNo(orderInfo.getBuyerNo());
			userMemberInfo = userMemberInfoMapper.selectOne(userMemberInfo);
			orderDetailVo.setMemberInfo(userMemberInfo);
		}
		if (orderExpress != null) {
			//7、物流追踪信息
			if (StringUtils.hasText(orderExpress.getExpressNo())) {
				TpExpressInfo tpExpressInfo = new TpExpressInfo();
				tpExpressInfo.setExpressNo(orderExpress.getExpressNo());
				List<TpExpressInfo> tpExpressInfoList = tpExpressInfoMapper.select(tpExpressInfo);
				orderDetailVo.setExpressInfoList(tpExpressInfoList);
			}
			//8、快递公司
			SysExpressDict dict = new SysExpressDict();
			if (StringUtils.hasText(orderExpress.getSysCompanyCode())) {
				dict.setCompanyCode(orderExpress.getSysCompanyCode());
				dict.setEnable(true);
				dict = sysExpressDictMapper.selectOne(dict);
			}
			orderDetailVo.setCompanyName(dict.getCompanyName());
		}
		//9、原画作和退款原因
		List<String> paintingUrls = new ArrayList<String>();
		String refundReason = null;
		for (int i = workflows.size() - 1; i >= 0; i--) {
			OrderWorkflow workflow2 = workflows.get(i);
			String url = workflow2.getContent();
			if (OrderStatus.WAIT_CONFIRM.getCode().equals(workflow2.getOrderStatus()) && StringUtils.hasText(url)
					&& url.startsWith("http://")) {
				paintingUrls.add(url);
			} else if (OrderStatus.WAIT_REFUND.getCode().equals(workflow2.getOrderStatus())) {
				refundReason = workflow2.getContent();
			}
		}
		if (paintingUrls.size() > 0) {
			//移除最新的原画作
			paintingUrls.remove(0);
		}
		orderDetailVo.setPaintingUrls(paintingUrls);
		orderDetailVo.setRefundReason(refundReason);
		//10、订单备注、用户备注，包含订单状态不可做的信息
		List<OrderLog> orderCommentList = new ArrayList<OrderLog>();
		List<OrderLog> userCommentList = new ArrayList<OrderLog>();
		for (OrderLog log : orderLogList) {
			if (log.getCommentType() != null && StringUtils.hasText(log.getComment())) {
				if (2 == log.getCommentType()) {
					userCommentList.add(log);
				} else if (1 == log.getCommentType() || 3 == log.getCommentType()) {
					orderCommentList.add(log);
				}
			}
		}
		orderDetailVo.setOrderCommentList(orderCommentList);
		orderDetailVo.setUserCommentList(userCommentList);
		return orderDetailVo;
	}

	@Override
	public boolean comment(Long id, String orderNo, Integer commentType, String message, SysUser user) {
		OrderInfo old = queryById(id);
		if ((OrderStatus.DONE.getCode()).equals(old.getOrderStatus())
				|| OrderStatus.SHIPPED.getCode().equals(old.getOrderStatus())) {
			return false;
		}
		//记录操作日志
		int count = doOrderLog(orderNo, commentType, message, OperatorType.COMMENT, user);
		return count == 1;
	}

	@Override
	public String uploadPainting(Long id, MultipartFile painting) {
		OrderInfo old = queryById(id);
		String suffix = painting.getOriginalFilename().substring(painting.getOriginalFilename().indexOf("."));
		String fileName = old.getOrderNo() + SDF.format(new Date()) + suffix;
		String cover = fileService.upload(painting, fileName, paintingUploadPath);
		return cover;
	}

	@Override
	public String updatePainting(Long id, String comment, String cover, SysUser user) {
		OrderInfo old = queryById(id);
		if (!OrderStatus.REFUSE.getCode().equals(old.getOrderStatus())
				&& !OrderStatus.WAIT_CONFIRM.getCode().equals(old.getOrderStatus())) {
			return null;
		}
		String orderNo = old.getOrderNo();
		//修改订单状态，修改首图
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setId(id);
		orderInfo.setOrderStatus(OrderStatus.WAIT_CONFIRM.getCode());
		orderInfo.setCover(cover);
		updateByPrimaryKeySelective(orderInfo);
		//记录工作流
		doOrderWorkflow(orderNo, cover, OrderStatus.WAIT_CONFIRM.getCode(), user);
		//记录操作日志
		doOrderLog(orderNo, null, comment, OperatorType.UPDATE_PAINTING, user);
		return cover;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = AlipayApiException.class)
	public boolean refund(Long id, SysUser user) throws AlipayApiException {
		OrderInfo old = queryById(id);
		if (!OrderStatus.WAIT_REFUND.getCode().equals(old.getOrderStatus())) {
			return false;
		}
		String orderNo = old.getOrderNo();
		//修改订单状态
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setId(id);
		orderInfo.setOrderStatus(OrderStatus.REFUNDED.getCode());
		//更新订单状态
		int count = updateByPrimaryKeySelective(orderInfo);
		//查询订单支付流水号
		//OrderPaymentInfo orderPaymentInfo = new OrderPaymentInfo();
		//orderPaymentInfo.setOrderNo(orderNo);
		//orderPaymentInfo = orderPaymentInfoMapper.selectOne(orderPaymentInfo);
		//String tradeNo = orderPaymentInfo == null ? null : orderPaymentInfo.getSerialNumbe();
		// 向第三方发起退款请求
		//第一期不做接口退款ThirdPartUtil.refund(orderNo, old.getOrderPrice().toString(), tradeNo);
		//记录退款记录
		OrderRefund orderRefund = new OrderRefund();
		orderRefund.setContent("退款完成");
		orderRefund.setCreated(new Date());
		orderRefund.setOperator(user.getUsername());
		orderRefund.setOrderNo(orderNo);
		orderRefund.setOrderStatus(orderInfo.getOrderStatus());
		orderRefundMapper.insert(orderRefund);
		//记录工作流
		doOrderWorkflow(orderNo, "退款完成", OrderStatus.REFUNDED.getCode(), user);
		//记录操作日志
		doOrderLog(orderNo, null, "-", OperatorType.REFUND, user);
		return count == 1;
	}

	@Override
	public boolean assess(Long id, Integer orderStatus, String comment, SysUser user) {
		OrderInfo old = queryById(id);
		if (!OrderStatus.WAIT_CONFIRM.getCode().equals(old.getOrderStatus())) {
			return false;
		}
		String orderNo = old.getOrderNo();
		//修改订单状态
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setId(id);
		orderInfo.setOrderStatus(orderStatus);
		orderInfo.setComment(comment);
		int count = updateByPrimaryKeySelective(orderInfo);
		//记录工作流
		doOrderWorkflow(orderNo, "评估", orderStatus, user);
		//记录操作日志
		Integer commentType = null;
		if (OrderStatus.DESIGNING.getCode().equals(orderStatus)) {
			comment = "-";
		} else {
			commentType = 3;
		}
		doOrderLog(orderNo, commentType, comment, OperatorType.ASSESS, user);
		return count == 1;
	}

	@Override
	public boolean finished(Long id, SysUser user) {
		OrderInfo old = queryById(id);
		if (!OrderStatus.MAKING.getCode().equals(old.getOrderStatus())) {
			return false;
		}
		String orderNo = old.getOrderNo();
		//修改订单状态
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setId(id);
		orderInfo.setOrderStatus(OrderStatus.WAIT_SHIPPING.getCode());
		int count = updateByPrimaryKeySelective(orderInfo);
		//记录工作流
		doOrderWorkflow(orderNo, "制作完成", OrderStatus.WAIT_SHIPPING.getCode(), user);
		//记录操作日志
		doOrderLog(orderNo, null, "-", OperatorType.FINISHED, user);
		return count == 1;
	}

	@Override
	public boolean shipped(Long id, String code, String expressNo, SysUser user) {
		OrderInfo old = queryById(id);
		if (!OrderStatus.WAIT_SHIPPING.getCode().equals(old.getOrderStatus())) {
			return false;
		}
		String orderNo = old.getOrderNo();
		//修改订单状态
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setId(id);
		orderInfo.setOrderStatus(OrderStatus.SHIPPED.getCode());
		int count = updateByPrimaryKeySelective(orderInfo);
		//记录物流信息
		OrderExpress orderExpress = new OrderExpress();
		orderExpress.setExpressNo(expressNo);
		orderExpress.setSysCompanyCode(code);
		orderExpress.setShippedTime(new Date());
		Example example = new Example(OrderExpress.class);
		example.createCriteria().andEqualTo("orderNo", orderNo);
		int row = orderExpressMapper.updateByExampleSelective(orderExpress, example);
		if (row == 0) {
			logger.warn("订单号={}，快递单号={}，快递公司编码={} 已发货，但物流信息丢失", orderNo, expressNo, code);
		}
		//订阅第三方物流
		ThirdPartUtil.expressSubscribe(url, code, expressNo);
		//发送短信
		OrderExpress express = new OrderExpress();
		express.setOrderNo(orderNo);
		express = orderExpressMapper.selectOne(express);
		if (!StringUtils.hasText(express.getConsigneeMobile())) {
			throw new RuntimeException("用户订单信息缺失，收货人手机号为空。orderNo=" + orderNo);
		}
		ThirdPartUtil.sendSms(smsUrl, "shipped", express.getConsigneeMobile());
		//记录工作流
		doOrderWorkflow(orderNo, "已发货", OrderStatus.SHIPPED.getCode(), user);
		//记录操作日志
		doOrderLog(orderNo, null, "-", OperatorType.SHIPPED, user);
		return count == 1;
	}

	@Override
	public String uploadDesignDiagram(Long id, MultipartFile designDiagram, SysUser user) {
		OrderInfo old = queryById(id);
		if (!OrderStatus.DESIGNING.getCode().equals(old.getOrderStatus())
				&& !OrderStatus.WAIT_MAKE.getCode().equals(old.getOrderStatus())) {
			return null;
		}
		String orderNo = old.getOrderNo();
		//上传文件
		String fileName = designDiagram.getOriginalFilename();
		if (StringUtils.hasText(fileName) && !orderNo.equals(fileName.split("\\.")[0])) {
			logger.warn("上传设计图名称[{}]和订单号[{}]不同，自动使用订单号作为设计图名称", fileName, orderNo);
		}
		int index = fileName.lastIndexOf(".");
		String suffix = index == -1 ? "" : fileName.substring(index);
		String url = fileService.upload(designDiagram, orderNo + suffix, diagramUploadPath);
		//修改订单状态
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setId(id);
		orderInfo.setOrderStatus(OrderStatus.WAIT_MAKE.getCode());
		updateByPrimaryKeySelective(orderInfo);
		//记录工作流
		doOrderWorkflow(orderNo, "上传设计图，待制作", OrderStatus.WAIT_MAKE.getCode(), user);
		//记录操作日志
		doOrderLog(orderNo, null, orderNo + suffix, OperatorType.UPLOAD_DESIGN_DIAGRAM, user);
		return url;
	}

	@Override
	public String downloadDesignDiagram(Long id, OutputStream out, SysUser user) throws IOException {
		OrderInfo old = queryById(id);
		if (!OrderStatus.WAIT_MAKE.getCode().equals(old.getOrderStatus())) {
			return "false";
		}
		String orderNo = old.getOrderNo();
		Example example = new Example(OrderLog.class);
		example.setOrderByClause("created desc limit 1");
		example.createCriteria().andEqualTo("orderNo", orderNo)
				.andEqualTo("operatorType", OperatorType.UPLOAD_DESIGN_DIAGRAM.getCode());
		List<OrderLog> orderLogs = orderLogMapper.selectByExample(example);
		if (orderLogs == null || orderLogs.size() != 1) {
			return "false";
		}
		//获取已经上传的设计图文件地址
		String source = diagramUploadPath + "/" + orderLogs.get(0).getComment();
		//把订单信息文本添加到已有压缩包
		String orderInfoTxt = generateOrderTxt(old);
		ByteArrayInputStream in = new ByteArrayInputStream(orderInfoTxt.getBytes());
		//压缩到servlet输出流，给用户下载
		CompressUtils.zip(source, in, out);
		//修改订单状态
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setId(id);
		orderInfo.setOrderStatus(OrderStatus.MAKING.getCode());
		updateByPrimaryKeySelective(orderInfo);
		//记录工作流
		doOrderWorkflow(orderNo, "下载设计图，制作中", OrderStatus.MAKING.getCode(), user);
		//记录操作日志
		doOrderLog(orderNo, null, "-", OperatorType.DOWNLOAD_DESIGN_DIAGRAM, user);
		//发送短信
		OrderExpress express = new OrderExpress();
		express.setOrderNo(orderNo);
		express = orderExpressMapper.selectOne(express);
		if (!StringUtils.hasText(express.getConsigneeMobile())) {
			throw new RuntimeException("用户订单信息缺失，收货人手机号为空。orderNo=" + orderNo);
		}
		if (OrderStatus.MAKING.getCode().equals(orderInfo.getOrderStatus())) {
			ThirdPartUtil.sendSms(smsUrl, "make", express.getConsigneeMobile());
		}
		return orderNo;
	}

	/**
	 * 根据id更新订单
	 * @param orderInfo 订单信息
	 * @return
	 */
	private int updateByPrimaryKeySelective(OrderInfo orderInfo) {
		int count = orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
		return count;
	}

	/**
	 * 做订单日志
	 * @param orderNo 订单号
	 * @param comment 备注
	 */
	private int doOrderLog(String orderNo, Integer commentType, String comment, OperatorType operatorType, SysUser user) {
		OrderLog orderLog = new OrderLog();
		orderLog.setCommentType(commentType);
		orderLog.setComment(comment);
		orderLog.setCreated(new Date());
		orderLog.setNickname(user.getUsername());//取当前操作用户名称 
		orderLog.setOperatorType(operatorType.getCode());
		orderLog.setOrderNo(orderNo);
		orderLog.setRoleId(user.getRole());//取当前角色
		return orderLogMapper.insert(orderLog);
	}

	/**
	 * 做工作流日志
	 * @param orderNo 订单号
	 * @param content 内容
	 * @param orderStatus 订单状态
	 */
	private int doOrderWorkflow(String orderNo, String content, Integer orderStatus, SysUser user) {
		OrderWorkflow orderWorkflow = new OrderWorkflow();
		orderWorkflow.setContent(content);
		orderWorkflow.setCreated(new Date());
		orderWorkflow.setOperator(user.getUsername());
		orderWorkflow.setOrderNo(orderNo);
		orderWorkflow.setOrderStatus(orderStatus);
		return orderWorkflowMapper.insert(orderWorkflow);
	}

	/**
	 * 生成订单文本信息
	 * @param orderLogs 订单操作日志
	 * @return
	 */
	private String generateOrderTxt(OrderInfo order) {
		StringBuffer orderInfoTxt = new StringBuffer();
		String tab = "\t", line = System.getProperties().getProperty("line.separator");
		orderInfoTxt.append("订单号：").append(order.getOrderNo()).append(tab);
		orderInfoTxt.append("订单状态：").append(OrderStatus.forOrderCode(order.getOrderStatus())).append(line);
		orderInfoTxt.append("商品金额：¥ ").append(order.getOrderPrice()).append(tab);
		orderInfoTxt.append("实际支付金额：¥ ").append(order.getPayAmount()).append(line);
		orderInfoTxt.append("款式：").append(order.getGoodsStyle()).append(tab);
		orderInfoTxt.append("材质：").append(order.getGoodsMaterial()).append(tab);
		orderInfoTxt.append("尺寸：").append(order.getGoodsSize()).append(line);
		orderInfoTxt.append("刻字：").append(order.getLettering()).append(tab);
		orderInfoTxt.append("下单时间：").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(order.getCreated()))
				.append(line);
		//查询订单备注
		Example example = new Example(OrderLog.class);
		example.setOrderByClause("created desc");
		example.createCriteria().andEqualTo("orderNo", order.getOrderNo())
				.andEqualTo("operatorType", OperatorType.COMMENT.getCode())
				.andEqualTo("commentType", CommentType.ORDER);
		List<OrderLog> orderLogs = orderLogMapper.selectByExample(example);
		for (OrderLog log : orderLogs) {
			orderInfoTxt.append("【 ").append(log.getRoleId()).append("】");
			orderInfoTxt.append(log.getComment());
			orderInfoTxt.append(System.getProperties().getProperty("line.separator"));
		}
		return orderInfoTxt.toString();
	}

	@Override
	public String downloadDesignDiagramOnly(Long id, OutputStream out,
			SysUser user) throws IOException {
		OrderInfo old = queryById(id);
		/*if (!OrderStatus.WAIT_MAKE.getCode().equals(old.getOrderStatus())) {
			return "false";
		}*/
		String orderNo = old.getOrderNo();
		Example example = new Example(OrderLog.class);
		example.setOrderByClause("created desc limit 1");
		example.createCriteria().andEqualTo("orderNo", orderNo)
				.andEqualTo("operatorType", OperatorType.UPLOAD_DESIGN_DIAGRAM.getCode());
		List<OrderLog> orderLogs = orderLogMapper.selectByExample(example);
		if (orderLogs == null || orderLogs.size() != 1) {
			return "false";
		}
		//获取已经上传的设计图文件地址
		String source = diagramUploadPath + "/" + orderLogs.get(0).getComment();
		//把订单信息文本添加到已有压缩包
		String orderInfoTxt = generateOrderTxt(old);
		ByteArrayInputStream in = new ByteArrayInputStream(orderInfoTxt.getBytes());
		//压缩到servlet输出流，给用户下载
		CompressUtils.zip(source, in, out);
		//记录工作流
		//doOrderWorkflow(orderNo, "再次下载设计图，制作中", OrderStatus.MAKING.getCode(), user);
		//记录操作日志
		doOrderLog(orderNo, null, "-", OperatorType.DOWNLOAD_DESIGN_DIAGRAM, user);		
		return orderNo;
	}

}
