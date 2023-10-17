package com.yolo.http.utils;

import com.yolo.http.HttpRequest;
import com.yolo.http.ResponseSpec;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import java.util.Map;

@Slf4j
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpUtil {


    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final String COOKIE_KEY = "Cookie";

    /**
     * Get请求
     *
     * @param url     请求的url
     * @param queries 请求参数
     * @return {@link String}
     */
    public static String get(String url, Map<String, Object> queries) {
        return get(url, null, queries);
    }

    /**
     * Get 请求
     *
     * @param url     请求的url
     * @param header  请求头
     * @param queries 请求参数
     * @return {@link String}
     */
    public static String get(String url, Map<String, String> header, Map<String, Object> queries) {
        HttpRequest request = buildGet(url, header, queries);
        return getResponseBodyStr(request);
    }

    /**
     * 构造 Get 请求
     * 如果要添加 Cookie 把Cookie放到请求头中
     *
     * @param url     请求的url
     * @param header  请求头
     * @param queries 请求参数
     * @return {@link HttpRequest}
     */
    public static HttpRequest buildGet(String url, Map<String, String> header, Map<String, Object> queries) {
        StringBuilder sb = new StringBuilder(url);
        if (queries != null && !queries.keySet().isEmpty()) {
            sb.append("?");
            queries.forEach((k, v) -> sb.append("&").append(k).append("=").append(v));
        }

        HttpRequest request = HttpRequest.get(sb.toString());
        if (header != null && !header.keySet().isEmpty()) {
            request.addHeader(header);
        }
        return request;
    }

    /**
     * POST
     *
     * @param url    请求的url
     * @param params post form 提交的参数
     * @return String
     */
    public static String postForm(String url, Map<String, Object> params) {
        return postForm(url, null, params);
    }

    /**
     * POST
     *
     * @param url    请求的url
     * @param header 请求头
     * @param params post form 提交的参数
     * @return String
     */
    public static String postForm(String url, Map<String, String> header, Map<String, Object> params) {
        return getResponseBodyStr(buildPostForm(url, header, params));
    }

    /**
     * 构建 post form请求方式的request
     *
     * @param url    请求的url
     * @param header 请求头
     * @param params post form 提交的参数
     * @return {@link HttpRequest}
     */
    public static HttpRequest buildPostForm(String url, Map<String, String> header, Map<String, Object> params) {
        HttpRequest request = HttpRequest.post(url);
        //添加参数
        if (params != null && !params.keySet().isEmpty()) {
            request.queryMap(params);
        }
        if (header != null && !header.keySet().isEmpty()) {
            request.addHeader(header);
        }
        return request;
    }


    /**
     * POST请求发送JSON数据
     *
     * @param url  请求的url
     * @param json 请求的json串
     * @return String
     */
    public static String postJson(String url, String json) {
        return postJson(url, null, json);
    }

    /**
     * POST请求发送JSON数据
     *
     * @param url    请求的url
     * @param header 请求头
     * @param json   请求的json串
     * @return String
     */
    public static String postJson(String url, Map<String, String> header, String json) {
        return postContent(url, header, json, JSON);
    }


    /**
     * 发送POST请求
     *
     * @param url       请求的url
     * @param header    请求头
     * @param content   请求内容
     * @param mediaType 请求类型
     * @return String
     */
    public static String postContent(String url, Map<String, String> header, String content, MediaType mediaType) {
        return getResponseBodyStr(buildPostContent(url, header, content, mediaType));
    }

    /**
     * 根据请求类型构造 post 的request
     *
     * @param url       请求的url
     * @param header    请求头
     * @param content   请求内容
     * @param mediaType 请求类型
     * @return {@link HttpRequest}
     */
    public static HttpRequest buildPostContent(String url, Map<String, String> header, String content, MediaType mediaType) {
        HttpRequest request = HttpRequest.post(url).body(content, mediaType);
        if (header != null && !header.keySet().isEmpty()) {
            request.addHeader(header);
        }
        return request;
    }


    /**
     * 获取响应 body json字符串
     *
     * @param request 构造请求方法
     * @return {@link String}
     */
    public static String getResponseBodyStr(HttpRequest request) {
        return request.execute().onSuccessful(ResponseSpec::asString);
    }

    /**
     * 获取响应 Response
     *
     * @param request 构造请求方法
     * @return {@link String}
     */
    public static Response getResponse(HttpRequest request) {
        Response response = null;
        try {
            response = request.execute().onSuccess(ResponseSpec::rawResponse);
            if (response != null && response.isSuccessful()) {
                return response;
            }
        } catch (Exception e) {
            log.error("okhttp3  error >> ex = {}", e.getMessage());
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return response;
    }

}
