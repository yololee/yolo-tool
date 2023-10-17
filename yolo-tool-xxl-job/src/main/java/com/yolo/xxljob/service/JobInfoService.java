package com.yolo.xxljob.service;

import com.yolo.common.exception.ServiceException;
import com.yolo.common.support.Kv;
import com.yolo.common.support.utils.bean.BeanUtil;
import com.yolo.common.support.utils.jackson.JsonUtil;
import com.yolo.http.HttpRequest;
import com.yolo.http.ResponseSpec;
import com.yolo.http.utils.HttpUtil;
import com.yolo.xxljob.model.XxlJobInfo;
import com.yolo.xxljob.properties.XxlJobProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


@Component
@Slf4j
@AllArgsConstructor
public class JobInfoService{

    private final XxlJobProperties xxlJobProperties;

    private static final String JOB_INFO_URI = "/jobinfo";

    private final JobLoginService jobLoginService;

    public List<XxlJobInfo> getJobInfo(Integer jobGroupId, String executorHandler) {
        String url = xxlJobProperties.getAdmin().getAddress() + JOB_INFO_URI + "/pageList";

        Map<String, String> header = Kv.newMap();
        header.put(HttpUtil.COOKIE_KEY, jobLoginService.getCookie());
        Kv params = Kv.init().set("jobGroup",jobGroupId).set("executorHandler",executorHandler).set("triggerStatus",-1);
        HttpRequest request = HttpUtil.buildPostForm(url, header, params);
        return request.execute().onSuccessful(responseSpec -> responseSpec.atJsonPathList("/data", XxlJobInfo.class));
    }

    public void addJobInfo(XxlJobInfo xxlJobInfo) {

        Map<String, Object> paramMap = JsonUtil.beanToMap(xxlJobInfo);
        String url = xxlJobProperties.getAdmin().getAddress() + JOB_INFO_URI + "/add";

        Map<String, String> header = Kv.newMap();
        header.put(HttpUtil.COOKIE_KEY, jobLoginService.getCookie());
        HttpRequest request = HttpUtil.buildPostForm(url, header, paramMap);

        Integer code = request.execute().onSuccess(ResponseSpec::code);
        if (code == null || !code.equals(200)) {
            throw new ServiceException("add jobInfo error!");
        }
        log.info("新增任务成功");
    }

}