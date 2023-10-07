package com.yolo.log.model.vo;

import com.yolo.log.model.entity.LogUsual;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * LogUsual视图实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LogUsualVo extends LogUsual {
	private static final long serialVersionUID = 1L;

	private String strId;

}
