package com.yolo.mybatisplus.support;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 分页工具
 */
@Data
@Accessors(chain = true)
@ApiModel(description = "查询条件")
public class PageQuery {

	/**
	 * 当前页
	 */
	@ApiModelProperty(value = "当前页")
	private Integer current;

	/**
	 * 每页的数量
	 */
	@ApiModelProperty(value = "每页的数量")
	private Integer size;

	/**
	 * 排序的字段名
	 */
	@ApiModelProperty(hidden = true)
	private String orderByColumn;

	/**
	 * 排序方式 排序的方向desc或者asc
	 */
	@ApiModelProperty(hidden = true)
	private String isAsc;

}
