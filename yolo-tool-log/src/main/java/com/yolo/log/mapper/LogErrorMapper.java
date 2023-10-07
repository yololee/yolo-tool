package com.yolo.log.mapper;

import com.yolo.log.model.entity.LogError;
import com.yolo.mybatisplus.support.BaseMapperPlus;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogErrorMapper extends BaseMapperPlus<LogErrorMapper, LogError,LogError> {
}
