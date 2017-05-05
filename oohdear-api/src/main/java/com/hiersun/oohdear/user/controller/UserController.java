package com.hiersun.oohdear.user.controller;

/**
* Created by liubaocheng on 2017/2/28.
*/

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hiersun.oohdear.config.redis.RedisUtil;
import com.hiersun.oohdear.core.CacheKey;
import com.hiersun.oohdear.core.ResponseMessage;
import com.hiersun.oohdear.user.entity.UserMemberInfo;
import com.hiersun.oohdear.user.service.FileService;
import com.hiersun.oohdear.user.service.UserMemberInfoService;
import com.hiersun.oohdear.user.vo.UserInfo;
import com.hiersun.oohdear.util.StringUtil;
import com.hiersun.oohdear.util.ThirdPartUtil;

/**
 * Description:
 * Author: liubaocheng
 * Create: 2017-02-28 9:59
 **/
@RestController
public class UserController {
	private static final Long EXPIRE_CAPTCHA = 5 * 60 * 1000L;
	@Autowired
	private UserMemberInfoService userMemberInfoService;
	@Autowired
	private FileService fileService;
	@Autowired
	private RedisUtil redisUtil;
	@Value("${user.sms.url}")
	private String smsUrl;
	@Value("${user.switch.check}")
	private String switchCheck;
	/*文件上传路径*/
	@Value("${user.upload.path}")
	private String uploadPath;
	 private static final SimpleDateFormat SDF = new SimpleDateFormat("_yyyyMMddHHmmssSSS");
	@RequestMapping("index")
	public String index(Model model) {
		/*UserMemberInfo user = userMemberInfoService.queryUserInfo("1302545445");
		model.addAttribute("entity", user);*/
		return "index";
	}

	@RequestMapping("random")
	public ResponseEntity<Boolean> random(String random) {
		Boolean status = redisUtil.set(random, random);
		return new ResponseEntity<Boolean>(status, HttpStatus.OK);
	}

	/**
	 * 查询个人信息
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value="member",method=RequestMethod.GET)
	public ResponseEntity<Object> query(ResponseMessage responseMessage, Model model, String memberNo) {
		UserInfo user = userMemberInfoService.queryUserInfo(memberNo);
		user.setMobile(StringUtil.getStarMobile(user.getMobile()));
		responseMessage.getBody().put("user", user);
		return new ResponseEntity<Object>(responseMessage, HttpStatus.OK);
	}

	/**
	 * 编辑个人信息
	 * @param avatar
	 * @param nickname
	 * @param gender
	 * @param memberNo
	 * @return
	 */
	@RequestMapping(value="/member/edit", method=RequestMethod.POST)
	public ResponseEntity<Object> edit(String nickname,
			String gender, String memberNo,String avatar){//,@RequestParam(value="test",required=false) MultipartFile file) {
		System.out.println(nickname+gender+memberNo);
		ResponseMessage responseMessage = new ResponseMessage();
		if (!StringUtils.hasText(nickname)) {
			responseMessage.getHead().setCode(20003);
			responseMessage.getHead().setMessage("昵称必填");
			return new ResponseEntity<Object>(false, HttpStatus.OK);
		}
		nickname = nickname.trim();
		if (nickname.length() > 20) {
			responseMessage.getHead().setCode(20004);
			responseMessage.getHead().setMessage("昵称最多20个字符");
			return new ResponseEntity<Object>(false, HttpStatus.OK);
		}
		return new ResponseEntity<Object>(userMemberInfoService.editUserInfo(memberNo, avatar, nickname, gender),
				HttpStatus.OK);
	}

	/**
	 * 上传画作
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/pic/upload", method = RequestMethod.POST)
	public ResponseEntity<Object> upload(@RequestParam("test") MultipartFile file) {//@RequestParam("test")
		ResponseMessage responseMessage = new ResponseMessage();
		String avatar = "";
		Boolean status = false;
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (file == null){
			responseMessage.getHead().setCode(20003);
			responseMessage.getHead().setMessage("上传内容不能为空");
			return new ResponseEntity<Object>(responseMessage, HttpStatus.OK);
		}
        //检验图片格式
		String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
		System.out.println("suffix:"+suffix);
		if(suffix.equals(".gif") || suffix.equals(".GIF")){
			responseMessage.getHead().setCode(20003);
			responseMessage.getHead().setMessage("上传文件格式不正确，请重新上传。");
			return new ResponseEntity<Object>(responseMessage, HttpStatus.OK);
		}
		System.out.println("通过检验");
		if ("false".equals(switchCheck)) {
			avatar="http://192.168.4.87/000.jpg";	
		}else{	
			String fileName =  SDF.format(new Date()) + suffix;
			avatar = fileService.upload(file,fileName,uploadPath);
		}
		if (StringUtils.hasText(avatar)) {
			status = true;
		}		
		responseMessage.getBody().put("status", status);
		responseMessage.getBody().put("avatar", avatar);
		return new ResponseEntity<Object>(responseMessage, HttpStatus.OK);
	}

	/**
	 * 获取验证码
	 * @param requestMessage
	 * @return
	 */
	@RequestMapping(value = "/user/captcha", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessage> getCaptcha(String mobile) {
		ResponseMessage responseMessage = new ResponseMessage();
		Boolean status = false;
		if (!StringUtils.hasText(mobile)) {
			responseMessage.getHead().setCode(20001);
			responseMessage.getHead().setMessage("手机号不能为空");
			return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
		}
		if (!StringUtil.isMobile(mobile)) {
			responseMessage.getHead().setCode(20002);
			responseMessage.getHead().setMessage("手机号格式不正确");
			return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
		}
		String captcha = RandomStringUtils.randomNumeric(6);
		boolean success = redisUtil.set(CacheKey.MEMBER_CAPTCHA + mobile, captcha, EXPIRE_CAPTCHA);
		if (success) {
			//发送短信
			status = ThirdPartUtil.sendSmsCaptcha(smsUrl, "login", mobile, captcha);
		} else {
			responseMessage.getHead().setCode(99999);
			responseMessage.getHead().setMessage("服务异常");
		}
		responseMessage.getBody().put("status", status);
		return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
	}

	/**
	 * 免注册登录
	 * @param requestMessage
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessage> login(String mobile, String captcha, String captchaNo, String random) {
		System.out.println(mobile + ":" + captcha + ":" + captchaNo + ":" + random);
		ResponseMessage responseMessage = new ResponseMessage();
		if (!StringUtils.hasText(random)) {
			responseMessage.getHead().setCode(20001);
			responseMessage.getHead().setMessage("系统繁忙，请联系客服");
			return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
		}
		if (!StringUtils.hasText(captcha)) {
			responseMessage.getHead().setCode(20001);
			responseMessage.getHead().setMessage("验证码不能为空");
			return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
		}
		if (!StringUtils.hasText(captchaNo)) {
			responseMessage.getHead().setCode(20001);
			responseMessage.getHead().setMessage("数字验证码不能为空");
			return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
		}
		if (!StringUtils.hasText(mobile)) {
			responseMessage.getHead().setCode(20001);
			responseMessage.getHead().setMessage("手机号不能为空");
			return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
		}
		if (!StringUtil.isMobile(mobile)) {
			responseMessage.getHead().setCode(20002);
			responseMessage.getHead().setMessage("手机号格式不正确");
			return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
		}

		String randomValue = (String) redisUtil.get(random);
		if (!captchaNo.equals(randomValue)) {
			responseMessage.getHead().setCode(20001);
			responseMessage.getHead().setMessage("数字验证码错误");
			return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
		}
		if("true".equals(switchCheck)){
			String validCode = (String) redisUtil.get(CacheKey.MEMBER_CAPTCHA + mobile);
			if (!captcha.equals(validCode)){
				responseMessage.getHead().setCode(20001);
				responseMessage.getHead().setMessage("短信验证码错误");
				return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
			}
		}
		//验证码相等说明验证通过，注册用户并生成token存储到redis，同时下发token和memberNo
		UserMemberInfo member = userMemberInfoService.register(mobile);
		String memberNo = member.getMemberNo();
		String token=RandomStringUtils.randomAlphanumeric(16);
		redisUtil.set(CacheKey.MEMBER_TOKEN + memberNo, token);
		responseMessage.getBody().put("memberNo", memberNo);
		responseMessage.getBody().put("nickName", member.getNickName());
		responseMessage.getBody().put("mobile", member.getMobile());
		responseMessage.getBody().put("token", token);
		responseMessage.getHead().setMessage("登录成功");
		
		return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
	}

	/**
	 * 个人中心
	 * @param requestMessage
	 * @return
	 */
	@RequestMapping(value = "/member/center", method = RequestMethod.GET)
	public ResponseEntity<ResponseMessage> memberCenter(@RequestParam String memberNo) {//@RequestHeader
		UserMemberInfo member = userMemberInfoService.queryUserInfoByMemberNo(memberNo);
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.getBody().put("avatar", member.getAvatar() == null ? "" : member.getAvatar());
		responseMessage.getBody().put("nickname", member.getNickName() == null ? "" : member.getNickName());
		responseMessage.getBody().put("mobile", StringUtil.getStarMobile(member.getMobile()));
		return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.OK);
	}

	@RequestMapping(value="/pic", method = RequestMethod.GET)
	public void getPic(HttpServletRequest request, String random, HttpServletResponse response) throws IOException {
		int w = 120;
		int h = 50;
		BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		//在图片上画一个矩形当背景
		Graphics g = img.getGraphics();
		g.setColor(new Color(245, 245, 245));
		g.fillRect(0, 0, w, h);

		String str = "0123456789";
		String resultStr = "";
		for (int i = 0; i < 4; i++) {
			g.setColor(new Color(r(50, 180), r(50, 180), r(50, 180)));
			g.setFont(new Font("黑体", Font.PLAIN, 40));
			char c = str.charAt(r(0, str.length()));
			resultStr += String.valueOf(c);
			g.drawString(String.valueOf(c), 10 + i * 30, r(h - 30, h));
		}

		Boolean status = redisUtil.set(random, resultStr, EXPIRE_CAPTCHA);
		System.out.println("图片验证码：" + status + ",验证码" + resultStr);
		//画随机线
		/* for(int i=0;i<25;i++){
		     g.setColor(new Color(r(50,180),r(50,180),r(50,180)));
		     g.drawLine(r(0,w), r(0,h),r(0,w), r(0,h));
		 }*/
		//把内存中创建的图像输出到文件中
		//File file = new File("vcode.png");
		ImageIO.write(img, "png", response.getOutputStream());
	}

	public static Random random = new Random();

	public static int r(int min, int max) {
		int num = 0;
		num = random.nextInt(max - min) + min;
		return num;
	}

}
