package com.yolo.common.config;

import com.yolo.common.constant.YoloSystemConstant;
import com.yolo.common.properties.LaunchProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

/**
 * 配置类
 */
@Slf4j
@Component
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@AllArgsConstructor
public class BootAutoConfig {

	private LaunchProperties launchProperties;

	/**
	 * 全局变量定义
	 *
	 * @return SystemConstant
	 */
	@Bean
	public YoloSystemConstant fileConst() {
		YoloSystemConstant me = YoloSystemConstant.me();

		//设定开发模式
		me.setDevMode(("dev".equals(launchProperties.getEnv())));


        //设定文件上传是否为远程模式
        me.setRemoteMode(Boolean.TRUE.equals(launchProperties.getBoolean("remote-mode", true)));


		//设定文件上传远程地址
		me.setDomain(launchProperties.get("upload-domain", "http://localhost:8888"));



		//远程上传地址
		me.setRemotePath(launchProperties.get("remote-path", System.getProperty("user.dir") + "/work/yolo"));

		//设定文件上传头文件夹
		me.setUploadPath(launchProperties.get("upload-path", "/upload"));

		//设定文件下载头文件夹
		me.setDownloadPath(launchProperties.get("download-path", "/download"));

		//设定上传图片是否压缩
		me.setCompress(Boolean.TRUE.equals(launchProperties.getBoolean("compress", false)));

		//设定上传图片压缩比例
		me.setCompressScale(launchProperties.getDouble("compress-scale", 2.00));

		//设定上传图片缩放选择:true放大;false缩小
		me.setCompressFlag(Boolean.TRUE.equals(launchProperties.getBoolean("compress-flag", false)));

		return me;
	}

}
