package com.yolo.xxljob.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class XxlJobGroup {

    private int id;
    private String appname;
    private String title;
    private int addressType;        // 执行器地址类型：0=自动注册、1=手动录入
    private String addressList;
    // 执行器地址列表，多地址逗号分隔(手动录入)

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    // registry list
    private List<String> registryList;  // 执行器地址列表(系统注册)

}