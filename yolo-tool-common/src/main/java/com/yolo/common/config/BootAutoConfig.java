package com.yolo.common.config;

import com.yolo.common.constant.YoloSystemConstant;
import com.yolo.launch.properties.LaunchProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

/**
 * 配置类
 *
 * @author Chill
 */
@Slf4j
@Component
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@AllArgsConstructor
public class BootAutoConfig {

	private LaunchProperties bladeProperties;

	/**
	 * 全局变量定义
	 *
	 * @return SystemConstant
	 */
	@Bean
	public YoloSystemConstant fileConst() {
		YoloSystemConstant me = YoloSystemConstant.me();

		//设定开发模式
		me.setDevMode(("dev".equals(bladeProperties.getEnv())));

		//设定文件上传远程地址
		me.setDomain(bladeProperties.get("upload-domain", "http://localhost:8888"));

		//设定文件上传是否为远程模式
		me.setRemoteMode(Boolean.TRUE.equals(bladeProperties.getBoolean("remote-mode", true)));

		//远程上传地址
		me.setRemotePath(bladeProperties.get("remote-path", System.getProperty("user.dir") + "/work/blade"));

		//设定文件上传头文件夹
		me.setUploadPath(bladeProperties.get("upload-path", "/upload"));

		//设定文件下载头文件夹
		me.setDownloadPath(bladeProperties.get("download-path", "/download"));

		//设定上传图片是否压缩
		me.setCompress(Boolean.TRUE.equals(bladeProperties.getBoolean("compress", false)));

		//设定上传图片压缩比例
		me.setCompressScale(bladeProperties.getDouble("compress-scale", 2.00));

		//设定上传图片缩放选择:true放大;false缩小
		me.setCompressFlag(Boolean.TRUE.equals(bladeProperties.getBoolean("compress-flag", false)));

		return me;
	}

}
