package com.yolo.test.swagger;

import com.yolo.common.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/swagger")
@Api(value = "测试swagger",tags = "测试swagger")
public class TestSwaggerController {

    @ApiOperation(value = "获取用户列表")
    @GetMapping("/list")
    public R<User> demo(User user){
        return R.data(user);
    }


    @ApiModel(value = "用户信息")
    @Data
    public static class User{
        @ApiModelProperty(value = "用户名称")
        private String name;
        @ApiModelProperty(value = "用户年纪")
        private Integer age;

    }
}
