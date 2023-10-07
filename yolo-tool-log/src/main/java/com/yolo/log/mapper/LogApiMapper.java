package com.yolo.log.mapper;

import com.yolo.log.model.entity.LogApi;
import com.yolo.mybatisplus.support.BaseMapperPlus;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogApiMapper extends BaseMapperPlus<LogApiMapper, LogApi, LogApi> {
}
