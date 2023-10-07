package com.yolo.log.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yolo.log.model.LogAbstract;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 实体类
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("log_api")
public class LogApi extends LogAbstract implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 日志类型
	 */
	private String type;
	/**
	 * 日志标题
	 */
	private String title;

}
