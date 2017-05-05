package com.hiersun.oohdear.service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.alipay.api.AlipayApiException;
import com.hiersun.oohdear.entity.OrderInfo;
import com.hiersun.oohdear.entity.SysUser;
import com.hiersun.oohdear.entity.vo.OrderDetailVo;
import com.hiersun.oohdear.entity.vo.OrderInfoVo;

/**
 * 订单服务
 * @author liuyang
 * @email y_liu@hiersun.com | 745089707@qq.com
 */
public interface OrderService {

	/**
	 * 根据订单号查询订单
	 * @param orderNo 订单编号
	 * @return
	 */
	OrderInfo queryByOrderNo(String orderNo);

	/**
	 * 根据id查询订单
	 * @param id
	 * @return
	 */
	OrderInfo queryById(Long id);

	/**
	 * 获取订单列表
	 * @param orderInfoVo 查询条件
	 * @return
	 */
	List<OrderInfoVo> orderList(OrderInfoVo orderInfoVo);

	/**
	 * 获取订单详情
	 * @param orderNo
	 * @return
	 */
	OrderDetailVo detail(String orderNo);

	/**
	 * 备注
	 * @param id
	 * @param orderNo
	 * @param commentType
	 * @param message
	 * @return
	 */
	boolean comment(Long id, String orderNo, Integer commentType, String message, SysUser user);

	/**
	 * 上传画作
	 * @param id 订单ID
	 * @param painting 画作
	 * @return
	 */
	String uploadPainting(Long id, MultipartFile painting);

	/**
	 * 修改画作
	 * @param id 订单ID
	 * @param comment 备注
	 * @return
	 */
	String updatePainting(Long id, String comment, String cover, SysUser user);

	/**
	 * 退款
	 * @param id 订单ID
	 * @return
	 */
	boolean refund(Long id, SysUser user) throws AlipayApiException;

	/**
	 * 评估
	 * @param id 订单ID
	 * @param orderStatus 订单状态
	 * @param comment 备注
	 */
	boolean assess(Long id, Integer orderStatus, String comment, SysUser user);

	/**
	 * 制作完成
	 * @param id 订单ID
	 */
	boolean finished(Long id, SysUser user);

	/**
	 * 发货
	 * @param id 订单ID
	 * @param code
	 * @param expressNo
	 */
	boolean shipped(Long id, String code, String expressNo, SysUser user);

	/**
	 * 上传设计图
	 * @param id 订单ID
	 * @param designDiagram 设计图
	 */
	String uploadDesignDiagram(Long id, MultipartFile designDiagram, SysUser user);

	/**
	 * 下载设计图，返回下载地址
	 * @param id 订单ID
	 * @return
	 */
	String downloadDesignDiagram(Long id, OutputStream out, SysUser user) throws IOException;
	/**
	 * 下载设计图，不改变状态
	 * @param id
	 * @param out
	 * @param user
	 * @return
	 * @throws IOException
	 */
	String downloadDesignDiagramOnly(Long id, OutputStream out, SysUser user) throws IOException;

	/**
	 * 订单日志操作类型
	 * 1-备注；2-修改画作；3-退款；4-设计评估；5-上传设计图；6-下载设计图；7-制作完成；8-发货
	 * @author liuyang
	 * @email y_liu@hiersun.com | 745089707@qq.com
	 */
	enum OperatorType {
		/** 备注 **/
		COMMENT(1, "备注"),
		/** 修改画作 **/
		UPDATE_PAINTING(2, "修改画作"),
		/** 退款 **/
		REFUND(3, "退款"),
		/** 设计评估 **/
		ASSESS(4, "设计评估"),
		/** 上传设计图 **/
		UPLOAD_DESIGN_DIAGRAM(5, "上传设计图"),
		/** 下载设计图 **/
		DOWNLOAD_DESIGN_DIAGRAM(6, "下载设计图"),
		/** 制作完成 **/
		FINISHED(7, "制作完成"),
		/** 发货 **/
		SHIPPED(8, "发货");

		private Integer code;
		private String name;

		private OperatorType(Integer code, String name) {
			this.code = code;
			this.name = name;
		}

		public Integer getCode() {
			return code;
		}

		public void setCode(Integer code) {
			this.code = code;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public static String forOrderCode(int code) {
			for (OperatorType status : OperatorType.values()) {
				if (status.getCode() == code)
					return status.getName();
			}
			return null;
		}
	}

	/**
	 * 订单日志操作类型
	 * 1-订单；2-用户
	 * @author liuyang
	 * @email y_liu@hiersun.com | 745089707@qq.com
	 */
	enum CommentType {
		/** 1-订单 **/
		ORDER(1, "订单"),
		/** 2-用户 **/
		USER(2, "用户");

		private Integer code;
		private String name;

		private CommentType(Integer code, String name) {
			this.code = code;
			this.name = name;
		}

		public Integer getCode() {
			return code;
		}

		public void setCode(Integer code) {
			this.code = code;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public static String forOrderCode(int code) {
			for (CommentType status : CommentType.values()) {
				if (status.getCode() == code)
					return status.getName();
			}
			return null;
		}
	}
}
