package com.hiersun.oohdear.controller;

import static jxl.biff.FormatRecord.logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hiersun.oohdear.entity.SysUser;
import com.hiersun.oohdear.entity.vo.UserPermitVo;
import com.hiersun.oohdear.service.UserService;
import com.hiersun.oohdear.util.EncryptUtils;

/**
 * Description: 系统默认�?
 * Author: liubaocheng
 * Create: 2017-01-03 10:48
 **/
@Controller
public class IndexController {
	
	private String username;

	@RequestMapping(value = "/left", method = RequestMethod.GET)
	public ModelAndView left() {
		// 菜单处理
		ModelAndView view = new ModelAndView("/pages/left");
		return view;
	}

	@RequestMapping(value = "/top", method = RequestMethod.GET)
	public ModelAndView top(Model model) {
		model.addAttribute("username", username);
		// 菜单处理
		ModelAndView view = new ModelAndView("/pages/top");
		return view;
	}

	@RequestMapping(value = "/right", method = RequestMethod.GET)
	public ModelAndView main(Model model) {
		// 菜单处理
		ModelAndView view = new ModelAndView("/pages/index");
		return view;
	}

	/*@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index() {		
		// 菜单处理
		ModelAndView view = new ModelAndView("/index");
		return view;
	}*/
	
	@Autowired
	UserService userService;
	/**
	 * 跳转登录页
	 * @return
	 */
/*	@RequestMapping(value={"","/index"})
	public String query(){
		 return "user/login";
	}
	*/
	@RequestMapping(value="/loginUrl")
	public String test(){
		 return "index";
	}
	@RequestMapping(value={"","index","login"})
	public String login(){
		Subject currentUser = SecurityUtils.getSubject();
		if(currentUser.isAuthenticated()){
			return "index";
		}
		return "user/login";
	}
//	/**
//	 * 退出
//	 * @return
//	 */
//	@RequestMapping("/logout")
//	public String logout(HttpServletRequest request){
//		HttpSession session = request.getSession();
//		SysUser user=(SysUser) session.getAttribute("user");
//		if(user!=null){
//			session.setAttribute("user", null);
//		}
//		return "user/logout";
//	}
	/**
	 * 退出
	 * @return
	 */
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public String logout(RedirectAttributes redirectAttributes ){
		//使用权限管理工具进行用户的退出，跳出登录，给出提示信息
		SecurityUtils.getSubject().logout();
		redirectAttributes.addFlashAttribute("message", "您已安全退出");
		return "redirect:/login";
	}


	@RequestMapping("/403")
	public String unauthorizedRole(){
		logger.info("------没有权限-------");
		return "403";
	}

	/**
	 * 用户登录
	 * @param model
	 * @param username
	 * @param password
	 * @return
	 
	@RequestMapping("/login")
	public String login(HttpServletRequest request,Model model,String username,String password){
//		SysUser sysUser=userService.login(username, password);
//		if(sysUser==null){
//			model.addAttribute("message", "用户名或密码错误");
//			return "user/login";
//		}
//		HttpSession session = request.getSession();
//		session.setAttribute("user", sysUser);
//		session.setMaxInactiveInterval(1800);
		 return "index";
	}*/

	@RequestMapping(value="/login",method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> login(@Valid SysUser user, BindingResult bindingResult, RedirectAttributes redirectAttributes){
		if(bindingResult.hasErrors()){
			return new ResponseEntity<String>("failed",HttpStatus.OK);
		}
		Boolean loginStatus=false;
		String username = user.getUsername();
		String password="";
		try {
			password=EncryptUtils.aesDecrypt(user.getMd5Pwd());
			System.out.println("解密后的密码是："+password);
			logger.info("解密后的密码是："+password);
			password=EncryptUtils.aesEncrypt(username+password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(),password);
		//获取当前的Subject
		Subject currentUser = SecurityUtils.getSubject();
		try {
			//在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
			//每个Realm都能在必要时对提交的AuthenticationTokens作出反应
			//所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
			logger.info("对用户[" + username + "]进行登录验证..验证开始");
			currentUser.login(token);
			loginStatus=true;
			logger.info("对用户[" + username + "]进行登录验证..验证通过");
		}catch(UnknownAccountException uae){
			logger.info("对用户[" + username + "]进行登录验证..验证未通过,未知账户");
			redirectAttributes.addFlashAttribute("message", "未知账户");
		}catch(IncorrectCredentialsException ice){
			logger.info("对用户[" + username + "]进行登录验证..验证未通过,错误的凭证");
			redirectAttributes.addFlashAttribute("message", "密码不正确");
		}catch(LockedAccountException lae){
			logger.info("对用户[" + username + "]进行登录验证..验证未通过,账户已锁定");
			redirectAttributes.addFlashAttribute("message", "账户已锁定");
		}catch(ExcessiveAttemptsException eae){
			logger.info("对用户[" + username + "]进行登录验证..验证未通过,错误次数过多");
			redirectAttributes.addFlashAttribute("message", "用户名或密码错误次数过多");
		}catch(AuthenticationException ae){
			//通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
			logger.info("对用户[" + username + "]进行登录验证..验证未通过,堆栈轨迹如下");
			ae.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "用户名或密码不正确");
		}
		//验证是否登录成功
		if(loginStatus){
			logger.info("用户[" + username + "]登录认证通过(这里可以进行一些认证通过后的一些系统参数初始化操作)");
			return new ResponseEntity<String>("success",HttpStatus.OK);
		}else{
			token.clear();
			return  new ResponseEntity<String>("failed",HttpStatus.OK);
		}
	}


	@RequestMapping("/role")
	public ResponseEntity<UserPermitVo> getRole(HttpServletRequest request,Model model,String userId){
		HttpSession session = request.getSession();
		SysUser user=(SysUser) session.getAttribute("user");
		UserPermitVo userPermit=new UserPermitVo();
		if(user==null){
			return new ResponseEntity<UserPermitVo>(userPermit,HttpStatus.OK);
		}
		if(user.getRole()==3 || user.getRole()==4 ||user.getRole()==5){   
			userPermit.setId(user.getId());
			userPermit.setUserPermit(false);
			userPermit.setOrderPermit(true);
			userPermit.setOperationPermit(false);
		}else if(user.getRole()==1||user.getRole()==2){ //管理员
			userPermit.setId(user.getId());
			userPermit.setUserPermit(true);
			userPermit.setOrderPermit(true);
			userPermit.setOperationPermit(true);
		}
		
		return new ResponseEntity<UserPermitVo>(userPermit,HttpStatus.OK);
	}
	
}
