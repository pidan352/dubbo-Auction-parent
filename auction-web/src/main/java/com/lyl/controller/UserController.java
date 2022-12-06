package com.lyl.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.lyl.pojo.Auctionuser;
import com.lyl.service.UserService;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * 功能：
 *
 * @author 林亦亮
 * @version 1.0
 * @date 2022/10/25
 */

@Controller
public class UserController {

	@Reference
	private UserService us;

	@Autowired
	private DefaultKaptcha captchaProducer;

	@RequestMapping("{url}.html")
	public String url(@PathVariable String url) {
		return url;
	}

	@RequestMapping("/doLogin")
	public String doLogin(HttpServletRequest req, Model model) {

		String exceptionError = (String) req.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);

		if (exceptionError != null) {
			if (UnknownAccountException.class.getName().equals(exceptionError)) {
				model.addAttribute("errorMsg", "账号错误");
			}
			if (IncorrectCredentialsException.class.getName().equals(exceptionError)) {
				model.addAttribute("errorMsg", "密码错误");
			}
			if ("valideCodeError".equals(exceptionError)) {
				model.addAttribute("errorMsg", "验证码错误");
			}
		}

		return "login";
	}

	///**
	// * @return
	// * @ModelAttribute 用于数据回显，指定返回时的名称
	// */
	//@RequestMapping("/doLogin")
	//public String doLogin(@ModelAttribute("username") String username,
	//					  @ModelAttribute("password") String password,
	//					  String inputCode, HttpSession session, Model model) {
	//
	//	String vrifyCode = (String) session.getAttribute("vrifyCode");
	//
	//	//1.先判断验证码是否正确
	//	if (!vrifyCode.equals(inputCode)) {
	//		model.addAttribute("errorMsg", "验证码错误");
	//		return "login";
	//	}
	//
	//	//2.业务判断
	//	Auctionuser user = us.findUserByUsername(username);
	//	if (user != null) {
	//		System.out.println(user);
	//		//存在这个用户，判断密码是否正确
	//		if (!user.getUserpassword().equals(password)) {
	//			model.addAttribute("errorMsg", "密码错误");
	//			return "login";
	//		}
	//		session.setAttribute("user", user);
	//		return "redirect:queryAuctions";
	//	}
	//	model.addAttribute("errorMsg", "没有这个用户");
	//	return "login";
	//}

	/**
	 * @Validated 标注要校验的数据，读取类中的注解
	 * BindingResult  存放错误信息的“容器”，必须紧跟被校验数据的后面
	 */
	@RequestMapping("/register")
	public String register(@Validated @ModelAttribute("registerUser") Auctionuser user, BindingResult bindingResult,
						   Model model) {
		//判断校验是否通过
		//有错误，将信息一同回到注册页面
		if (bindingResult.hasErrors()) {
			//获取所有错误信息
			List<FieldError> errorList = bindingResult.getFieldErrors();
			for (FieldError error : errorList) {
				//返回字段名对应的错误,方便于做错误信息的显示
				model.addAttribute(error.getField(), error.getDefaultMessage());
			}
			return "register";
		}

		//校验成功将用户添加到数据库
		us.addUser(user);
		return "login";
	}

	////注销
	//@RequestMapping("/cancellation")
	//public String cancellation(HttpSession session) {
	//	session.removeAttribute("user");
	//	return "login";
	//}


	@RequestMapping("/checkUsername")
	@ResponseBody
	public String checkUsername(@RequestBody String name) {
		System.err.println(name);
		boolean result = us.checkUsername(name);
		return result + "";
	}

	/**
	 * 获取验证码
	 *
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @throws Exception
	 */
	@RequestMapping("/defaultKaptcha")
	public void defaultKaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
			throws Exception {
		byte[] captchaChallengeAsJpeg = null;
		ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
		try {
			// 生产验证码字符串并保存到session中
			String createText = captchaProducer.createText();
			System.out.println(createText);
			httpServletRequest.getSession().setAttribute("vrifyCode", createText);
			// 使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
			BufferedImage challenge = captchaProducer.createImage(createText);
			ImageIO.write(challenge, "jpg", jpegOutputStream);
		} catch (IllegalArgumentException e) {
			httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		// 定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
		captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
		httpServletResponse.setHeader("Cache-Control", "no-store");
		httpServletResponse.setHeader("Pragma", "no-cache");
		httpServletResponse.setDateHeader("Expires", 0);
		httpServletResponse.setContentType("image/jpeg");
		ServletOutputStream responseOutputStream = httpServletResponse.getOutputStream();
		responseOutputStream.write(captchaChallengeAsJpeg);
		responseOutputStream.flush();
		responseOutputStream.close();
	}

}
