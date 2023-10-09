package com.yolo.oss.model;

import lombok.Data;

/**
 * BladeFile
 */
@Data
public class BladeFile {
	/**
	 * 文件地址
	 */
	private String link;
	/**
	 * 域名地址
	 */
	private String domain;
	/**
	 * 文件名
	 */
	private String name;
	/**
	 * 原始文件名
	 */
	private String originalName;
}
