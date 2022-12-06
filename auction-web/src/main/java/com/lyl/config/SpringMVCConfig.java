package com.lyl.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 功能：
 *
 * @author 林亦亮
 * @version 1.0
 * @date 2022/11/16
 */

@Component
public class SpringMVCConfig implements WebMvcConfigurer {

	@Autowired
	private FileProperties fileProperties;

	//使用http路径映射物理文件
	//addResourceHandler：指定一个http的虚拟路径，例如：	/upload/**
	//addResourceLocations:指定物理路径   e:/upload/

	//这两个方法的参数写在源码中不好，可以提取到配置文件中，方便配置
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		registry.addResourceHandler(fileProperties.getStaticAccessPath()).addResourceLocations(
				"file:" + fileProperties.getUploadFileFolder());
	}


	////添加SpringMVC的拦截器
	//@Override
	//public void addInterceptors(InterceptorRegistry registry) {
	//	ArrayList<String> paths = new ArrayList<>();
	//	paths.add("/css/**");
	//	paths.add("/js/**");
	//	paths.add("/images/**");
	//	paths.add("/doLogin");
	//	paths.add("/login.html");
	//	paths.add("/register.html");
	//	paths.add("/defaultKaptcha");
	//
	//	//注册拦截器
	//	registry.addInterceptor(new CheckUserInterceptor())
	//				.addPathPatterns("/**")		//添加需要拦截资源
	//				.excludePathPatterns(paths);     //添加放行的资源
	//}

}
