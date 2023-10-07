package com.yolo.test.log;

import com.yolo.common.api.R;
import com.yolo.log.annotation.ApiLog;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/log")
public class TestLogController {


    @GetMapping("/request/log")
    @ApiLog
    public R<String> requestLog(String name){
        return R.data("OK");
    }
}
