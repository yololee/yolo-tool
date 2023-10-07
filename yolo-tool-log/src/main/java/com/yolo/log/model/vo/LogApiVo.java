package com.yolo.log.model.vo;

import com.yolo.log.model.entity.LogApi;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * LogApi视图实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LogApiVo extends LogApi {
	private static final long serialVersionUID = 1L;

	private String strId;

}
