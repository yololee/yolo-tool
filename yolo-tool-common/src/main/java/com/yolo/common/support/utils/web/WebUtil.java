package com.yolo.common.support.utils.web;


import com.yolo.common.support.utils.convert.ConvertUtil;
import com.yolo.common.support.utils.jackson.JsonUtil;
import com.yolo.common.support.utils.string.Charsets;
import com.yolo.common.support.utils.string.StringPool;
import com.yolo.common.support.utils.string.StringUtil;
import com.yolo.common.support.utils.bean.ClassUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;


@Slf4j
public class WebUtil extends org.springframework.web.util.WebUtils {

	public static final String USER_AGENT_HEADER = "user-agent";

	public static final String UN_KNOWN = "unknown";


	/**
	 * 获取String参数
	 */
	public static String getParameter(String name) {
		return Objects.requireNonNull(getRequest()).getParameter(name);
	}

	/**
	 * 获取String参数
	 */
	public static String getParameter(String name, String defaultValue) {
		return ConvertUtil.toStr(Objects.requireNonNull(getRequest()).getParameter(name), defaultValue);
	}

	/**
	 * 获取Integer参数
	 */
	public static Integer getParameterToInt(String name) {
		return ConvertUtil.toInt(Objects.requireNonNull(getRequest()).getParameter(name));
	}

	/**
	 * 获取Integer参数
	 */
	public static Integer getParameterToInt(String name, Integer defaultValue) {
		return ConvertUtil.toInt(Objects.requireNonNull(getRequest()).getParameter(name), defaultValue);
	}

	/**
	 * 获取Boolean参数
	 */
	public static Boolean getParameterToBool(String name) {
		return ConvertUtil.toBool(Objects.requireNonNull(getRequest()).getParameter(name));
	}

	/**
	 * 获取Boolean参数
	 */
	public static Boolean getParameterToBool(String name, Boolean defaultValue) {
		return ConvertUtil.toBool(Objects.requireNonNull(getRequest()).getParameter(name), defaultValue);
	}

	/**
	 * 获得所有请求参数
	 *
	 * @param request 请求对象{@link ServletRequest}
	 * @return Map
	 */
	public static Map<String, String[]> getParams(ServletRequest request) {
		final Map<String, String[]> map = request.getParameterMap();
		return Collections.unmodifiableMap(map);
	}

	/**
	 * 获得所有请求参数
	 *
	 * @param request 请求对象{@link ServletRequest}
	 * @return Map
	 */
	public static Map<String, String> getParamMap(ServletRequest request) {
		Map<String, String> params = new HashMap<>();
		for (Map.Entry<String, String[]> entry : getParams(request).entrySet()) {
			params.put(entry.getKey(), StringUtil.join(entry.getValue(), StringPool.COMMA));
		}
		return params;
	}



	/**
	 * 获取response
	 */
	public static HttpServletResponse getResponse() {
		return getRequestAttributes().getResponse();
	}

	/**
	 * 获取session
	 */
	public static HttpSession getSession() {
		return getRequest().getSession();
	}

	public static ServletRequestAttributes getRequestAttributes() {
		RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
		return (ServletRequestAttributes) attributes;
	}


	/**
	 * 判断是否ajax请求
	 * spring ajax 返回含有 ResponseBody 或者 RestController注解
	 *
	 * @param handlerMethod HandlerMethod
	 * @return 是否ajax请求
	 */
	public static boolean isBody(HandlerMethod handlerMethod) {
		ResponseBody responseBody = ClassUtil.getAnnotation(handlerMethod, ResponseBody.class);
		return responseBody != null;
	}

	/**
	 * 读取cookie
	 *
	 * @param name cookie name
	 * @return cookie value
	 */
	@Nullable
	public static String getCookieVal(String name) {
		HttpServletRequest request = WebUtil.getRequest();
		Assert.notNull(request, "request from RequestContextHolder is null");
		return getCookieVal(request, name);
	}

	/**
	 * 读取cookie
	 *
	 * @param request HttpServletRequest
	 * @param name    cookie name
	 * @return cookie value
	 */
	@Nullable
	public static String getCookieVal(HttpServletRequest request, String name) {
		Cookie cookie = getCookie(request, name);
		return cookie != null ? cookie.getValue() : null;
	}

	/**
	 * 清除 某个指定的cookie
	 *
	 * @param response HttpServletResponse
	 * @param key      cookie key
	 */
	public static void removeCookie(HttpServletResponse response, String key) {
		setCookie(response, key, null, 0);
	}

	/**
	 * 设置cookie
	 *
	 * @param response        HttpServletResponse
	 * @param name            cookie name
	 * @param value           cookie value
	 * @param maxAgeInSeconds maxage
	 */
	public static void setCookie(HttpServletResponse response, String name, @Nullable String value, int maxAgeInSeconds) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		cookie.setMaxAge(maxAgeInSeconds);
		cookie.setHttpOnly(true);
		response.addCookie(cookie);
	}

	/**
	 * 获取 HttpServletRequest
	 *
	 * @return {HttpServletRequest}
	 */
	public static HttpServletRequest getRequest() {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		return (requestAttributes == null) ? null : ((ServletRequestAttributes) requestAttributes).getRequest();
	}

	/**
	 * 返回json
	 *
	 * @param response HttpServletResponse
	 * @param result   结果对象
	 */
	public static void renderJson(HttpServletResponse response, Object result) {
		renderJson(response, result, MediaType.APPLICATION_JSON_VALUE);
	}

	/**
	 * 返回json
	 *
	 * @param response    HttpServletResponse
	 * @param result      结果对象
	 * @param contentType contentType
	 */
	public static void renderJson(HttpServletResponse response, Object result, String contentType) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType(contentType);
		try (PrintWriter out = response.getWriter()) {
			out.append(JsonUtil.toJson(result));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 获取ip
	 *
	 * @return {String}
	 */
	public static String getIp() {
		return getIp(WebUtil.getRequest());
	}

	/**
	 * 获取ip
	 *
	 * @param request HttpServletRequest
	 * @return {String}
	 */
	@Nullable
	public static String getIp(HttpServletRequest request) {
		Assert.notNull(request, "HttpServletRequest is null");
		String ip = request.getHeader("X-Requested-For");
		if (StringUtil.isBlank(ip) || UN_KNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Forwarded-For");
		}
		if (StringUtil.isBlank(ip) || UN_KNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtil.isBlank(ip) || UN_KNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtil.isBlank(ip) || UN_KNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (StringUtil.isBlank(ip) || UN_KNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (StringUtil.isBlank(ip) || UN_KNOWN.equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return StringUtil.isBlank(ip) ? null : ip.split(",")[0];
	}


	/***
	 * 获取 request 中 json 字符串的内容
	 *
	 * @param request request
	 * @return 字符串内容
	 */
	public static String getRequestParamString(HttpServletRequest request) {
		try {
			return getRequestStr(request);
		} catch (Exception ex) {
			return StringPool.EMPTY;
		}
	}

	/**
	 * 获取 request 请求内容
	 *
	 * @param request request
	 * @return String
	 * @throws IOException IOException
	 */
	public static String getRequestStr(HttpServletRequest request) throws IOException {
		String queryString = request.getQueryString();
		if (StringUtil.isNotBlank(queryString)) {
			return new String(queryString.getBytes(Charsets.ISO_8859_1), Charsets.UTF_8).replaceAll("&amp;", "&").replaceAll("%22", "\"");
		}
		return getRequestStr(request, getRequestBytes(request));
	}

	/**
	 * 获取 request 请求的 byte[] 数组
	 *
	 * @param request request
	 * @return byte[]
	 * @throws IOException IOException
	 */
	public static byte[] getRequestBytes(HttpServletRequest request) throws IOException {
		int contentLength = request.getContentLength();
		if (contentLength < 0) {
			return null;
		}
		byte[] buffer = new byte[contentLength];
		for (int i = 0; i < contentLength; ) {

			int readlen = request.getInputStream().read(buffer, i, contentLength - i);
			if (readlen == -1) {
				break;
			}
			i += readlen;
		}
		return buffer;
	}

	/**
	 * 获取 request 请求内容
	 *
	 * @param request request
	 * @param buffer buffer
	 * @return String
	 * @throws IOException IOException
	 */
	public static String getRequestStr(HttpServletRequest request, byte[] buffer) throws IOException {
		String charEncoding = request.getCharacterEncoding();
		if (charEncoding == null) {
			charEncoding = StringPool.UTF_8;
		}
		String str = new String(buffer, charEncoding).trim();
		if (StringUtil.isBlank(str)) {
			StringBuilder sb = new StringBuilder();
			Enumeration<String> parameterNames = request.getParameterNames();
			while (parameterNames.hasMoreElements()) {
				String key = parameterNames.nextElement();
				String value = request.getParameter(key);
				StringUtil.appendBuilder(sb, key, "=", value, "&");
			}
			str = StringUtil.removeSuffix(sb.toString(), "&");
		}
		return str.replaceAll("&amp;", "&");
	}

	/**
	 * 获取 request 请求体
	 *
	 * @param servletInputStream servletInputStream
	 * @return body
	 */
	public static String getRequestBody(ServletInputStream servletInputStream) {
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(servletInputStream, StandardCharsets.UTF_8));
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (servletInputStream != null) {
				try {
					servletInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 内容编码
	 *
	 * @param str 内容
	 * @return 编码后的内容
	 */
	public static String urlEncode(String str) {
		try {
			return URLEncoder.encode(str, StandardCharsets.UTF_8.name());
		} catch (UnsupportedEncodingException e) {
			return StringPool.EMPTY;
		}
	}

	/**
	 * 内容解码
	 *
	 * @param str 内容
	 * @return 解码后的内容
	 */
	public static String urlDecode(String str) {
		try {
			return URLDecoder.decode(str, StandardCharsets.UTF_8.name());
		} catch (UnsupportedEncodingException e) {
			return StringPool.EMPTY;
		}
	}

	/**
	 * 获取 request 请求内容
	 *
	 * @param request request
	 * @return {String}
	 */
	public static String getRequestContent(HttpServletRequest request) {
		try {
			String queryString = request.getQueryString();
			if (StringUtil.isNotBlank(queryString)) {
				return new String(queryString.getBytes(Charsets.ISO_8859_1), Charsets.UTF_8).replaceAll("&amp;", "&").replaceAll("%22", "\"");
			}
			String charEncoding = request.getCharacterEncoding();
			if (charEncoding == null) {
				charEncoding = StringPool.UTF_8;
			}
			byte[] buffer = getRequestBody(request.getInputStream()).getBytes();
			String str = new String(buffer, charEncoding).trim();
			if (StringUtil.isBlank(str)) {
				StringBuilder sb = new StringBuilder();
				Enumeration<String> parameterNames = request.getParameterNames();
				while (parameterNames.hasMoreElements()) {
					String key = parameterNames.nextElement();
					String value = request.getParameter(key);
					StringUtil.appendBuilder(sb, key, "=", value, "&");
				}
				str = StringUtil.removeSuffix(sb.toString(), "&");
			}
			return str.replaceAll("&amp;", "&");
		} catch (Exception ex) {
			ex.printStackTrace();
			return StringPool.EMPTY;
		}
	}


}

