package com.yolo.test.xxljob;

import com.yolo.common.api.R;
import com.yolo.xxljob.model.JobInfo;
import com.yolo.xxljob.utils.XxlJobUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/xxl-job")
@RequiredArgsConstructor
public class TestXxlJobController {

    private final XxlJobUtil xxlJobUtil;



    @GetMapping("/test")

    public R<String> test(){

//        JobInfo jobInfo = new JobInfo();
//        jobInfo.setCron("0 0 0 * * ? *");
//        jobInfo.setJobDesc("test");
//        jobInfo.setExecutorHandler("demo11");
//        jobInfo.setAuthor("yolo");
//        xxlJobUtil.addJobInfo(jobInfo);


        return R.success("OK");
    }


}
