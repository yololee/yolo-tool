
package com.yolo.oss.rule;

import com.yolo.common.support.utils.date.DateUtil;
import com.yolo.common.support.utils.file.FileUtil;
import com.yolo.common.support.utils.string.StringPool;
import com.yolo.common.support.utils.string.StringUtil;
import lombok.AllArgsConstructor;

/**
 * 默认存储桶生成规则
 */
@AllArgsConstructor
public class BladeOssRule implements OssRule {

	@Override
	public String bucketName(String bucketName) {
		return bucketName;
	}

	@Override
	public String fileName(String originalFilename) {
		return "upload" + StringPool.SLASH + DateUtil.today() + StringPool.SLASH + StringUtil.randomUUID() + StringPool.DOT + FileUtil.getFileExtension(originalFilename);
	}

}
