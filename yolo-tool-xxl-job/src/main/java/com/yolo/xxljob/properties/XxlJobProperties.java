package com.yolo.xxljob.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * xxl-job 配置类
 */
@Getter
@Setter
@ConfigurationProperties(XxlJobProperties.PREFIX)
@Component
public class XxlJobProperties {
	public static final String PREFIX = "yolo.xxl.job";

	/**
	 * 是否启用分布式调度任务，默认：开启
	 */
	private boolean enabled = true;

	/**
	 * 调度中心配置
	 */
	private XxlJobAdminProps admin = new XxlJobAdminProps();

	/**
	 * 执行器配置
	 */
	private XxlJobExecutorProps executor = new XxlJobExecutorProps();

	@Getter
	@Setter
	public static class XxlJobAdminProps {
		/**
		 * 调度中心地址，如调度中心集群部署存在多个地址则用逗号分隔。执行器将会使用该地址进行"执行器心跳注册"和"任务结果回调"；为空则关闭自动注册；支持配置，{@code lb:// + ${service_name}} 从注册中心动态获取地址
		 */
		private String address;

		/**
		 * 与调度中心交互的accessToken，非空时启用
		 */
		private String accessToken;

		/**
		 * 用户名
		 */
		private String username;

		/**
		 * 密码
		 */
		private String password;
	}

	@Getter
	@Setter
	public static class XxlJobExecutorProps {
		/**
		 * 执行器AppName名称，长度最多为64
		 */
		private String appName;

		/**
		 * 执行器 IP，默认为空表示自动获取IP，多网卡时可手动设置指定IP，该IP不会绑定Host仅作为通讯实用；地址信息用于 "执行器注册" 和 "调度中心请求并触发任务"
		 */
		private String ip;

		/**
		 * 执行器端口，小于等于0则自动获取；默认端口为9999，单机部署多个执行器时，注意要配置不同执行器端口
		 */
		private int port = -1;

		/**
		 * 执行器日志位置
		 */
		private String logPath;

		/**
		 * 执行器日志保留天数，默认值：-1，值大于3时生效，启用执行器Log文件定期清理功能，否则不生效
		 */
		private int logRetentionDays = -1;

		/**
		 * 执行器名称 （必填） 新增配置 长度最多为12
		 */
		private String title;

		/**
		 * 执行器地址类型：0=自动注册、1=手动录入，默认为0 选填 新增配置
		 */
		private int addressType;

		/**
		 * 在上面为1的情况下，手动录入执行器地址列表，多地址逗号分隔  选填
		 */
		private String addressList;
	}
}
