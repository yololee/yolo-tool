package com.example.test.validator;


import com.yolo.common.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/validator")
@Api(value = "测试JSR 303校验",tags = "测试JSR 303校验")
public class TestValidatorController {

    @ApiOperation(value = "获取用户列表")
    @PostMapping("/add")
    public R<User> demo(@Validated @RequestBody User user){
        return R.data(user);
    }


    @ApiModel(value = "用户信息")
    @Data
    public static class User{
        @ApiModelProperty(value = "用户名称")
        @NotBlank(message = "用户名称不能为空")
        private String name;
        @ApiModelProperty(value = "用户年纪")
        @Range(min = 0,max = 100,message = "用户年纪不合法")
        private Integer age;
        @ApiModelProperty(value = "数量")
        @Range(min = 1,max = 10,message = "数量不合法")
        private Integer count;

    }
}
