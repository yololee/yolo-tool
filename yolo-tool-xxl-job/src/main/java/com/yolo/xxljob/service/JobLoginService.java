package com.yolo.xxljob.service;

import com.yolo.common.exception.ServiceException;
import com.yolo.common.support.Kv;
import com.yolo.http.HttpRequest;
import com.yolo.http.utils.HttpUtil;
import com.yolo.xxljob.properties.XxlJobProperties;
import lombok.AllArgsConstructor;
import okhttp3.Cookie;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


/**
 * xxl-job 登录服务
 * @author jujueaoye
 * @date 2023/10/12
 */
@Component
@AllArgsConstructor
public class JobLoginService{

    private final XxlJobProperties xxlJobProperties;

    private final Map<String, String> loginCookie = new HashMap<>();

    private static final String XXL_JOB_LOGIN_COOKIE = "XXL_JOB_LOGIN_IDENTITY";

    public void login() {
        String url = xxlJobProperties.getAdmin().getAddress() + "/login";
        Kv params = Kv.init().set("userName", xxlJobProperties.getAdmin().getUsername()).set("password", xxlJobProperties.getAdmin().getPassword());
        HttpRequest request = HttpUtil.buildPostForm(url, null, params);
        Cookie cookie = request.execute().onSuccessful(responseSpec -> responseSpec.cookie(XXL_JOB_LOGIN_COOKIE));
        if (ObjectUtils.isEmpty(cookie)) {
            throw new ServiceException("get xxl-job cookie error");
        }
        loginCookie.put(XXL_JOB_LOGIN_COOKIE, cookie.value());
    }

    public String getCookie() {
        for (int i = 0; i < 3; i++) {
            String cookieStr = loginCookie.get(XXL_JOB_LOGIN_COOKIE);
            if (cookieStr != null) {
                return XXL_JOB_LOGIN_COOKIE + "=" + cookieStr;
            }
            login();
        }
        throw new ServiceException("get xxl-job cookie error!");
    }


}