package com.hiersun.oohdear.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hiersun.oohdear.core.ResponseMessage;
import com.hiersun.oohdear.user.service.AddressService;
import com.hiersun.oohdear.user.vo.UserAddress;

/**
 * @author saixing_yang@hiersun.com
 * @date 创建时间：2017年3月2日 下午6:16:24
 * @version 1.0
 */
@Controller
@RequestMapping("/address")
public class AddressController {

	@Autowired
	AddressService adddressService;
 
	/**
	 * 我的地址列表
	 * @param model
	 * @param memberNo
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Object> index(Model model,@RequestParam(required=true)String memberNo) {
		return new  ResponseEntity<Object>(adddressService.queryAddressList(memberNo),HttpStatus.OK);
	}
	
	/**
	 * 确认下单-我的地址
	 * @param model
	 * @param memberNo
	 * @return
	 */
	@RequestMapping(value="/detail/my",method=RequestMethod.GET)
	public ResponseEntity<ResponseMessage> address(ResponseMessage responseMessage,@RequestParam(required=true)String memberNo,@RequestParam(required=false)Long addressId) {
		UserAddress address=adddressService.queryAddressById(memberNo,addressId);
		responseMessage.getBody().put("address", address);
		return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
	}


	/**
	 * 新增地址
	 * @param model
	 * @param memberNo
	 * @return
	 */
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public ResponseEntity<Object> save(@RequestParam String consignee,@RequestParam String mobile,
			@RequestParam String zone,@RequestParam String detail,@RequestParam String memberNo){
		ResponseMessage responseMessage = new ResponseMessage();
		if (!StringUtils.hasText(consignee)) {
			responseMessage.getHead().setCode(30003);
			responseMessage.getHead().setMessage("收货人名称必填");
			return new ResponseEntity<Object>(false, HttpStatus.OK);
		}
		consignee = consignee.trim();
		if (consignee.length() > 20) {
			responseMessage.getHead().setCode(30004);
			responseMessage.getHead().setMessage("收货人名称最多20个字符");
			return new ResponseEntity<Object>(false, HttpStatus.OK);
		}
		if (!StringUtils.hasText(mobile)) {
			responseMessage.getHead().setCode(30005);
			responseMessage.getHead().setMessage("收货人手机号必填");
			return new ResponseEntity<Object>(false, HttpStatus.OK);
		}
		mobile = mobile.trim();
		if (!mobile.matches("1[0-9]{10}")) {
			responseMessage.getHead().setCode(30006);
			responseMessage.getHead().setMessage("收货人手机号为11位数字");
			return new ResponseEntity<Object>(false, HttpStatus.OK);
		}
		zone = zone == null ? "" : zone.trim();
		if (zone.length() == 0 || zone.length() > 50) {
			responseMessage.getHead().setCode(30007);
			responseMessage.getHead().setMessage("所在地区必填或不能超过50个字符");
			return new ResponseEntity<Object>(false, HttpStatus.OK);
		}
		detail = detail == null ? "" : detail.trim();
		if (detail.length() == 0 || detail.length() > 50) {
			responseMessage.getHead().setCode(30008);
			responseMessage.getHead().setMessage("详细地址必填或不能超过50个字符");
			return new ResponseEntity<Object>(false, HttpStatus.OK);
		}
		String address=adddressService.addAddress(consignee,mobile,zone,detail,memberNo);
		responseMessage.getBody().put("addressId", address);
		return new ResponseEntity<Object>(responseMessage,HttpStatus.OK);
		
	}
	
	/**
	 * 编辑地址
	 * @param model
	 * @param memberNo
	 * @return
	 */
	@RequestMapping(value="/edit",method=RequestMethod.POST)//,method = RequestMethod.POST)
	public ResponseEntity<Object> edit(@RequestParam String id,@RequestParam String consignee,@RequestParam String mobile,
			@RequestParam String zone,@RequestParam String detail,@RequestParam Boolean defaultAddr,@RequestParam String memberNo) {
		ResponseMessage responseMessage = new ResponseMessage();
		if (!StringUtils.hasText(consignee)) {
			responseMessage.getHead().setCode(30003);
			responseMessage.getHead().setMessage("收货人名称必填");
			return new ResponseEntity<Object>(false, HttpStatus.OK);
		}
		consignee = consignee.trim();
		if (consignee.length() > 20) {
			responseMessage.getHead().setCode(30004);
			responseMessage.getHead().setMessage("收货人名称最多20个字符");
			return new ResponseEntity<Object>(false, HttpStatus.OK);
		}
		if (!StringUtils.hasText(mobile)) {
			responseMessage.getHead().setCode(30005);
			responseMessage.getHead().setMessage("收货人手机号必填");
			return new ResponseEntity<Object>(false, HttpStatus.OK);
		}
		mobile = mobile.trim();
		if (!mobile.matches("1[0-9]{10}")) {
			responseMessage.getHead().setCode(30006);
			responseMessage.getHead().setMessage("收货人手机号为11位数字");
			return new ResponseEntity<Object>(false, HttpStatus.OK);
		}
		zone = zone == null ? "" : zone.trim();
		if (zone.length() == 0 || zone.length() > 50) {
			responseMessage.getHead().setCode(30007);
			responseMessage.getHead().setMessage("所在地区必填或不能超过50个字符");
			return new ResponseEntity<Object>(false, HttpStatus.OK);
		}
		detail = detail == null ? "" : detail.trim();
		if (detail.length() == 0 || detail.length() > 50) {
			responseMessage.getHead().setCode(30008);
			responseMessage.getHead().setMessage("详细地址必填或不能超过50个字符");
			return new ResponseEntity<Object>(false, HttpStatus.OK);
		}
		return new ResponseEntity<Object>(adddressService.editAddress(id,consignee,mobile, zone,detail,defaultAddr,memberNo),HttpStatus.OK);
		
	}
	/**
	 * 删除地址
	 * @param id
	 * @param consignee
	 * @param mobile
	 * @param detail
	 * @param defaultAddr
	 * @param memberNo
	 * @return
	 */
	@RequestMapping(value="/{id}/delete",method=RequestMethod.POST)
	public ResponseEntity<Object> delete(@PathVariable Long id) {
		return new ResponseEntity<Object>(adddressService.deleteAddress(id),HttpStatus.OK);
	}

	/**
	 * 设为默认地址
	 * @param model
	 * @param memberNo
	 * @return
	 */
	@RequestMapping(value="/default",method=RequestMethod.POST)
	public ResponseEntity<Object> setdefault(@RequestParam String id,@RequestParam String memberNo) {
		return new ResponseEntity<Object>(adddressService.setDefault(id, memberNo),HttpStatus.OK);
		
	}
}
