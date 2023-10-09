package com.yolo.common.exception.file;

import com.yolo.common.exception.BaseException;

/**
 * 文件名称超长限制异常类
 */
public class FileNameLengthLimitExceededException extends BaseException {
    private static final long serialVersionUID = 1L;

    public FileNameLengthLimitExceededException(int defaultFileNameLength) {
        super("upload.filename.exceed.length" + ":" +defaultFileNameLength);
    }
}
