package com.yolo.test.http;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yolo.common.api.R;
import com.yolo.common.support.Kv;
import com.yolo.common.support.utils.jackson.JsonUtil;
import com.yolo.http.HttpRequest;
import com.yolo.http.ResponseSpec;
import com.yolo.http.utils.HttpUtil;
import com.yolo.xxljob.model.XxlJobActuatorInfo;
import lombok.RequiredArgsConstructor;
import okhttp3.Cookie;
import okhttp3.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/http")
@RequiredArgsConstructor
public class TestHttpController {

    private static final String XXL_JOB_LOGIN_COOKIE = "XXL_JOB_LOGIN_IDENTITY";


    @GetMapping("/login")
    public R<XxlJobActuatorInfo> postForm() throws IOException {

        String url = "http://localhost:9100/xxl-job-admin/jobgroup/pageList";

        Map<String, String> header = Kv.newMap();
        header.put(HttpUtil.COOKIE_KEY, "XXL_JOB_LOGIN_IDENTITY=7b226964223a312c22757365726e616d65223a2261646d696e222c2270617373776f7264223a226531306164633339343962613539616262653536653035376632306638383365222c22726f6c65223a312c227065726d697373696f6e223a6e756c6c7d");
        Kv params = Kv.init().set("appname","xxl-job-executor-test01").set("title","yolo-executor");

        HttpRequest request = HttpUtil.buildPostForm(url, header, params);
        XxlJobActuatorInfo xxlJobActuatorInfo1 = request.execute().asValue(XxlJobActuatorInfo.class);
        return R.data(xxlJobActuatorInfo1);
    }
}
