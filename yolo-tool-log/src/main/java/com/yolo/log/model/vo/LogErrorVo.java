package com.yolo.log.model.vo;

import com.yolo.log.model.entity.LogError;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * LogError视图实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LogErrorVo extends LogError {
	private static final long serialVersionUID = 1L;

	private String strId;

}
