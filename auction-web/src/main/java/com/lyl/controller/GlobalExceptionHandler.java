package com.lyl.controller;


import com.lyl.exception.recordException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能：
 *
 * @author 林亦亮
 * @version 1.0
 * @date 2022/10/24
 */

@ControllerAdvice
public class GlobalExceptionHandler {
	/**
	 * @param request
	 * @param e
	 * @return 错误信息
	 * @ResponseBody 返回json数据
	 * @ExceptionHandler 要捕获的异常
	 */
	@ResponseBody
	@ExceptionHandler(value = Exception.class)
	public Map<String, Object> handleException(HttpServletRequest request, Exception e) {
		HashMap<String, Object> map = new HashMap<>();
		e.printStackTrace();
		map.put("code", 500);
		map.put("msg", "发生了异常");
		map.put("data", "");

		return map;
	}

	@ExceptionHandler(value = recordException.class)
	public ModelAndView handlerecordException(HttpServletRequest request, Exception e) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("errorMsg", e.getMessage());
		mv.setViewName("error");

		return mv;
	}
}
