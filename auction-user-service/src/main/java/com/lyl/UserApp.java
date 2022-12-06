package com.lyl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * 功能：
 *
 * @author 林亦亮
 * @version 1.0
 * @date 2022/12/6
 */

@SpringBootApplication
@MapperScan("com.lyl.mapper")
public class UserApp {
	public static void main(String[] args) {
		new SpringApplicationBuilder(UserApp.class).web(WebApplicationType.NONE).run(args);
	}
}
