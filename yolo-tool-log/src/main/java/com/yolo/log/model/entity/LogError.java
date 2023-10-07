package com.yolo.log.model.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.yolo.log.model.LogAbstract;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 服务 异常
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("log_error")
public class LogError extends LogAbstract implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 堆栈信息
	 */
	private String stackTrace;
	/**
	 * 异常名
	 */
	private String exceptionName;
	/**
	 * 异常消息
	 */
	private String message;

	/**
	 * 文件名
	 */
	private String fileName;

	/**
	 * 代码行数
	 */
	private Integer lineNumber;


}
