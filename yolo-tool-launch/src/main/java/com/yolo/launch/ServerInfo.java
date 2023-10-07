package com.yolo.launch;

import com.yolo.common.support.utils.web.INetUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.stereotype.Component;

/**
 * 服务器信息
 * @author jujueaoye
 */
@Getter
@Slf4j
@Component
public class ServerInfo implements SmartInitializingSingleton {
	private final ServerProperties serverProperties;
	private String hostName;
	private String ip;
	private Integer port;
	private String ipWithPort;

	@Autowired(required = false)
	public ServerInfo(ServerProperties serverProperties) {
		this.serverProperties = serverProperties;
	}

	@Override
	public void afterSingletonsInstantiated() {
		this.hostName = INetUtil.getHostName();
		this.ip = INetUtil.getHostIp();
		this.port = serverProperties.getPort();
		this.ipWithPort = String.format("%s:%d", ip, port);
		log.info("---启动完成，，主机名:[{}],当前使用的ip端口:[{}]---", hostName,ipWithPort);
	}
}
