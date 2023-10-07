package com.yolo.test.mybatisplus;

import com.yolo.test.mybatisplus.model.User;
import com.yolo.mybatisplus.support.BaseMapperPlus;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapperPlus<UserMapper, User,User> {
}
