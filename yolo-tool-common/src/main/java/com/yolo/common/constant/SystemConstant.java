package com.yolo.common.constant;

/**
 * 系统常量
 * @author jujueaoye
 */
public interface SystemConstant {

	/**
	 * 编码
	 */
	String UTF_8 = "UTF-8";

	/**
	 * contentType
	 */
	String CONTENT_TYPE_NAME = "Content-type";

	/**
	 * JSON 资源
	 */
	String CONTENT_TYPE = "application/json;charset=utf-8";

	/**
	 * 角色前缀
	 */
	String SECURITY_ROLE_PREFIX = "ROLE_";

	/**
	 * 主键字段名
	 */
	String DB_PRIMARY_KEY = "id";

	/**
	 * 业务状态[1:正常]
	 */
	int DB_STATUS_NORMAL = 1;


	/**
	 * 删除状态[0:正常,1:删除]
	 */
	int DB_NOT_DELETED = 0;
	int DB_IS_DELETED = 1;

	/**
	 * 用户锁定状态
	 */
	int DB_ADMIN_NON_LOCKED = 0;
	int DB_ADMIN_LOCKED = 1;

	/**
	 * 顶级父节点id
	 */
	Long TOP_PARENT_ID = 0L;
	/**
	 * 顶级父节点名称
	 */
	String TOP_PARENT_NAME = "顶级";

	/**
	 * 日志默认状态
	 */
	String LOG_NORMAL_TYPE = "1";

	/**
	 * 资源映射路径 前缀
	 */
	String RESOURCE_PREFIX = "/profile";


	/**
	 * 防重提交 redis key
	 */
	String REPEAT_SUBMIT_KEY = "repeat_submit:";

	/**
	 * 默认为空消息
	 */
	String DEFAULT_NULL_MESSAGE = "暂无承载数据";
	/**
	 * 默认成功消息
	 */
	String DEFAULT_SUCCESS_MESSAGE = "操作成功";
	/**
	 * 默认失败消息
	 */
	String DEFAULT_FAILURE_MESSAGE = "操作失败";
	/**
	 * 默认未授权消息
	 */
	String DEFAULT_UNAUTHORIZED_MESSAGE = "签名认证失败";

}
