package com.yolo.xxljob.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class XxlJobResponseInfo {

    private Integer code;
    private String msg;
    private String content;
}
