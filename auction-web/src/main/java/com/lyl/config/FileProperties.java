package com.lyl.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 功能：
 *
 * @author 林亦亮
 * @version 1.0
 * @date 2022/11/16
 */

//通过借助自动配置中的属性类来读取配置文件中的信息
@ConfigurationProperties(prefix = "file")
@Component
public class FileProperties {

	//虚拟路径
	private String staticAccessPath;
	//物理路径
	private String uploadFileFolder;

	public String getStaticAccessPath() {
		return staticAccessPath;
	}

	public void setStaticAccessPath(String staticAccessPath) {
		this.staticAccessPath = staticAccessPath;
	}

	public String getUploadFileFolder() {
		return uploadFileFolder;
	}

	public void setUploadFileFolder(String uploadFileFolder) {
		this.uploadFileFolder = uploadFileFolder;
	}
}
