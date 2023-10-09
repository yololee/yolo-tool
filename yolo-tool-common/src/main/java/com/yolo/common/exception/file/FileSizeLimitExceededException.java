package com.yolo.common.exception.file;

import com.yolo.common.exception.BaseException;

/**
 * 文件名大小限制异常类
 */
public class FileSizeLimitExceededException extends BaseException {
    private static final long serialVersionUID = 1L;


    public FileSizeLimitExceededException(Integer code, String message) {
        super(null,code.toString(), message);
    }

    public FileSizeLimitExceededException(long defaultMaxSize) {
        super("upload.exceed.maxSize"+ ":" + defaultMaxSize);
    }
}
