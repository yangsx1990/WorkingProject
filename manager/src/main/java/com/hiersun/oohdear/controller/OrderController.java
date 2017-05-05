package com.hiersun.oohdear.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alipay.api.AlipayApiException;
import com.github.pagehelper.Page;
import com.hiersun.oohdear.entity.BasePageModel;
import com.hiersun.oohdear.entity.OrderInfo;
import com.hiersun.oohdear.entity.SysRole;
import com.hiersun.oohdear.entity.SysUser;
import com.hiersun.oohdear.entity.vo.OrderDetailVo;
import com.hiersun.oohdear.entity.vo.OrderInfoVo;
import com.hiersun.oohdear.service.OrderService;
import com.hiersun.oohdear.service.UserService;
import com.hiersun.oohdear.util.ExcelUtils;
import com.hiersun.oohdear.util.ThirdPartUtil;

@Controller
@RequestMapping("/order")
public class OrderController extends BaseController {

	private final static Logger logger = LoggerFactory.getLogger(OrderController.class);
	@Autowired
	private OrderService orderService;
	@Autowired
	private UserService userService;

	/**
	 * 跳转到订单列表页
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@RequiresRoles(logical = Logical.OR, value = { "admin", "operation", "services", "designer", "production" })
	@RequiresPermissions("order:list")
	public String ordersView(Model model) {
		SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
		List<SysRole> roleList = userService.findRoles(user);
		StringBuffer sb = new StringBuffer();
		for (SysRole role : roleList) {
			sb.append(role.getRoleName()).append(",");
		}
		model.addAttribute("roles", sb.toString());
		return "order/list";
	}

	/**
	 * 获取订单列表
	 * @param orderInfoVo 查询条件
	 * @param pageParam 分页参数
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@RequiresRoles(logical = Logical.OR, value = { "admin", "operation", "services", "designer", "production" })
	@RequiresPermissions("order:list")
	public BasePageModel orders(OrderInfoVo orderInfoVo, BasePageModel basePageModel) {
		Page<?> page = pageWapper(orderInfoVo, proplist());
		orderService.orderList(orderInfoVo);
		return dataTableWapper(page, basePageModel);
	}

	/**
	 * 订单详情
	 * @return
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String detailView(String orderNo, Model model) {
		OrderDetailVo orderDetailVo = orderService.detail(orderNo);
		SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
		model.addAttribute("detail", orderDetailVo);
		model.addAttribute("role",user.getRole());
		return "order/detail";
	}

	/**
	 * 备注页面
	 * @param id
	 * @param orderNo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/comment", method = RequestMethod.GET)
	@RequiresRoles(logical = Logical.OR, value = { "admin", "services" })
	@RequiresPermissions("order:comment")
	public String commentView(Long id, String orderNo, Model model) {
		OrderInfo orderInfo = orderService.queryById(id);
		model.addAttribute("order", orderInfo);
		return "order/comment";
	}

	/**
	 * 备注
	 * @param orderInfo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/comment", method = RequestMethod.POST)
	@RequiresRoles(logical = Logical.OR, value = { "admin", "services" })
	@RequiresPermissions("order:comment")
	public boolean comment(OrderInfo orderInfo, Integer commentType) {
		SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
		return orderService.comment(orderInfo.getId(), orderInfo.getOrderNo(), commentType, orderInfo.getComment(),
				user);
	}

	/**
	 * 修改画作页面
	 * @param id
	 * @param orderNo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/painting", method = RequestMethod.GET)
	@RequiresRoles(logical = Logical.OR, value = { "admin", "services" })
	@RequiresPermissions("order:painting")
	public String paintingView(Long id, String orderNo, Model model) {
		OrderInfo orderInfo = orderService.queryById(id);
		model.addAttribute("order", orderInfo);
		return "order/painting";
	}

	/**
	 * 上传画作
	 * @param id 订单ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/upload/painting", method = RequestMethod.POST)
	@RequiresRoles(logical = Logical.OR, value = { "admin", "services" })
	@RequiresPermissions("order:painting")
	public String updatePainting(OrderInfo orderInfo, MultipartFile painting) {
		String result = orderService.uploadPainting(orderInfo.getId(), painting);
		return result;
	}

	/**
	 * 修改画作
	 * @param orderInfo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/painting", method = RequestMethod.POST)
	@RequiresRoles(logical = Logical.OR, value = { "admin", "services" })
	@RequiresPermissions("order:painting")
	public String updatePainting(OrderInfo orderInfo) {
		SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
		String result = orderService.updatePainting(orderInfo.getId(), orderInfo.getComment(), orderInfo.getCover(),
				user);
		return result;
	}

	/**
	 * 退款页面
	 * @param id
	 * @param orderNo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/refund", method = RequestMethod.GET)
	@RequiresRoles(logical = Logical.OR, value = { "admin", "operation" })
	@RequiresPermissions("order:refund")
	public String refundView(Long id, Model model) {
		OrderInfo orderInfo = orderService.queryById(id);
		model.addAttribute("order", orderInfo);
		return "order/refund";
	}

	/**
	 * 退款
	 * @param orderInfo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/refund", method = RequestMethod.POST)
	@RequiresRoles(logical = Logical.OR, value = { "admin", "operation" })
	@RequiresPermissions("order:refund")
	public String refund(OrderInfo orderInfo) {
		SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
		try {
			orderService.refund(orderInfo.getId(), user);
			return "success";
		} catch (AlipayApiException e) {
			logger.warn("退款失败", e);
			if (ThirdPartUtil.ALI_REFUND_FAILED_REASON.equals(e.getErrCode())) {
				return e.getErrMsg();
			}
		}
		return "failed";
	}

	/**
	 * 设计评估页面
	 * @param id
	 * @param orderNo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/assess", method = RequestMethod.GET)
	@RequiresRoles(logical = Logical.OR, value = { "admin", "designer" })
	@RequiresPermissions("order:assess")
	public String assessView(Long id, Model model) {
		OrderInfo orderInfo = orderService.queryById(id);
		model.addAttribute("order", orderInfo);
		return "order/assess";
	}

	/**
	 * 设计评估
	 * @param orderInfo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/assess", method = RequestMethod.POST)
	@RequiresRoles(logical = Logical.OR, value = { "admin", "designer" })
	@RequiresPermissions("order:assess")
	public boolean assess(OrderInfo orderInfo) {
		SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
		return orderService.assess(orderInfo.getId(), orderInfo.getOrderStatus(), orderInfo.getComment(), user);
	}

	/**
	 * 制作完成页面
	 * @param id
	 * @param orderNo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/finished", method = RequestMethod.GET)
	@RequiresRoles(logical = Logical.OR, value = { "admin", "production" })
	@RequiresPermissions("order:finished")
	public String finishedView(Long id, Model model) {
		OrderInfo orderInfo = orderService.queryById(id);
		model.addAttribute("order", orderInfo);
		return "order/finished";
	}

	/**
	 * 制作完成
	 * @param orderInfo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/finished", method = RequestMethod.POST)
	@RequiresRoles(logical = Logical.OR, value = { "admin", "production" })
	@RequiresPermissions("order:finished")
	public boolean finished(OrderInfo orderInfo) {
		SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
		return orderService.finished(orderInfo.getId(), user);
	}

	/**
	 * 发货页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/shipped", method = RequestMethod.GET)
	@RequiresRoles(logical = Logical.OR, value = { "admin", "production" })
	@RequiresPermissions("order:shipped")
	public String shippedView(Long id, Model model) {
		OrderInfo orderInfo = orderService.queryById(id);
		model.addAttribute("order", orderInfo);
		return "order/shipped";
	}

	/**
	 * 发货
	 * @param companyCode 物流公司编码
	 * @param expressNo 快递单号
	 */
	@ResponseBody
	@RequestMapping(value = "/shipped", method = RequestMethod.POST)
	@RequiresRoles(logical = Logical.OR, value = { "admin", "production" })
	@RequiresPermissions("order:shipped")
	public boolean shipped(OrderInfo orderInfo, String companyCode, String expressNo) {
		SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
		return orderService.shipped(orderInfo.getId(), companyCode, expressNo, user);
	}

	/**
	 * 上传设计图
	 * @param file
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/upload")
	@RequiresRoles(logical = Logical.OR, value = { "admin", "designer" })
	@RequiresPermissions("order:upload")
	public String uploadDesignDiagram(Long id, MultipartFile designDiagram) {
		SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
		//判断文件名称
		String result = orderService.uploadDesignDiagram(id, designDiagram, user);
		return result;
	}

	/**
	 * 下载设计图
	 * @param id
	 */
	@RequestMapping(value = "/download")
	@RequiresRoles(logical = Logical.OR, value = { "admin", "production" })
	@RequiresPermissions("order:download")
	public void downloadDesignDiagram(Long id, HttpServletResponse response) {
		SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			OrderInfo order = orderService.queryById(id);
			if (order != null) {
				response.setContentType("application/x-zip-compressed");
				response.addHeader("Content-Disposition", "attachment; filename=" + order.getOrderNo() + ".zip");
				String filename = orderService.downloadDesignDiagram(order.getId(), out, user);
				if ("false".equals(filename)) {
					logger.warn("download orderId=[" + id + "]'s design diagram failed");
				}
			}
		} catch (IOException e) {
			logger.warn("download orderId=[" + id + "]'s design diagram failed", e);
		}
		return;
	}

	/**
	 * 下载设计图-不改变状态
	 * @param id
	 */
	@RequestMapping(value = "/downDesign")
	@RequiresRoles(logical = Logical.OR, value = { "admin", "production" })
	@RequiresPermissions("order:download")
	public void downloadDesign(Long id, HttpServletResponse response) {
		SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			OrderInfo order = orderService.queryById(id);
			if (order != null) {
				response.setContentType("application/x-zip-compressed");
				response.addHeader("Content-Disposition", "attachment; filename=" + order.getOrderNo() + ".zip");
				String filename = orderService.downloadDesignDiagramOnly(order.getId(), out, user);
				if ("false".equals(filename)) {
					logger.warn("download orderId=[" + id + "]'s design diagram failed");
				}
			}
		} catch (IOException e) {
			logger.warn("download orderId=[" + id + "]'s design diagram failed", e);
		}
		return;
	}
	@RequestMapping("excel")
	@RequiresRoles(logical = Logical.OR, value = { "admin", "operation" })
	@RequiresPermissions("order:excel")
	public void excel(HttpServletResponse response) {
		OrderInfoVo orderInfoVo = new OrderInfoVo();
		List<OrderInfoVo> orders = orderService.orderList(orderInfoVo);
		if (orders != null && orders.size() > 0) {
			for (OrderInfoVo order : orders) {
				order.setOrderStatusName(order.getOrderStatusName());
			}
			ExcelUtils.listToExcel(orders, getOrderExcelFiledMap(), "表一", response);
		}
	}

	/**
	 * 定义表头列表模板
	 * @return
	 */
	public LinkedHashMap<String, String> getOrderExcelFiledMap() {
		LinkedHashMap<String, String> fieldMap = new LinkedHashMap<String, String>();
		fieldMap.put("orderNo", "订单号");
		fieldMap.put("memberNo", "会员编号");
		fieldMap.put("mobile", "会员手机号");
		fieldMap.put("created", "下单时间");
		fieldMap.put("price", "商品金额");
		fieldMap.put("payAmount", "实际支付金额");
		fieldMap.put("orderStatusName", "订单状态");
		return fieldMap;
	}

	public String[] proplist() {
		return new String[] { "o.id", "", "o.order_no", "o.buyer_no", "u.mobile", "o.created", "o.order_price" };
	}
}
