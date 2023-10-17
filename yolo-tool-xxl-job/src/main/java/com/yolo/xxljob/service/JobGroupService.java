package com.yolo.xxljob.service;

import cn.hutool.core.collection.CollUtil;
import com.yolo.common.exception.ServiceException;
import com.yolo.common.support.Kv;
import com.yolo.http.HttpRequest;
import com.yolo.http.ResponseSpec;
import com.yolo.http.utils.HttpUtil;
import com.yolo.xxljob.model.XxlJobActuatorInfo;
import com.yolo.xxljob.model.XxlJobGroup;
import com.yolo.xxljob.properties.XxlJobProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@AllArgsConstructor
@Slf4j
public class JobGroupService{

    private final XxlJobProperties xxlJobProperties;
    private final JobLoginService jobLoginService;

    private static final String JOB_GROUP_URI = "/jobgroup";


    /**
     * 根据appName和执行器名称title查询执行器列表
     *
     * @return {@link List}<{@link XxlJobGroup}>
     */
    public List<XxlJobGroup> getJobGroup() {
        String url = xxlJobProperties.getAdmin().getAddress() + JOB_GROUP_URI + "/pageList";

        Map<String, String> header = Kv.newMap();
        header.put(HttpUtil.COOKIE_KEY, jobLoginService.getCookie());
        Kv params = Kv.init().set("appname",xxlJobProperties.getExecutor().getAppName()).set("title",xxlJobProperties.getExecutor().getTitle());

        HttpRequest request = HttpUtil.buildPostForm(url, header, params);
        XxlJobActuatorInfo xxlJobActuatorInfo = request.execute().asValue(XxlJobActuatorInfo.class);
        return xxlJobActuatorInfo.getData();
    }

    /**
     * 注册新executor到调度中心
     *
     * @return boolean
     */
    public boolean autoRegisterGroup() {
        int addressType = xxlJobProperties.getExecutor().getAddressType();
        String addressList = xxlJobProperties.getExecutor().getAddressList();

        String url = xxlJobProperties.getAdmin().getAddress() + JOB_GROUP_URI + "/save";
        Kv params = Kv.init().set("appname",xxlJobProperties.getExecutor().getAppName()).set("title",xxlJobProperties.getExecutor().getTitle());

        //0=自动注册、1=手动录入
        if (addressType == 1) {
            if (Strings.isBlank(addressList)) {
                throw new ServiceException("手动录入模式下,执行器地址列表不能为空");
            }
            params.set("addressType",addressType);
        }

        Map<String, String> header = Kv.newMap();
        header.put(HttpUtil.COOKIE_KEY, jobLoginService.getCookie());
        HttpRequest request = HttpUtil.buildPostForm(url, header, params);
        Boolean aBoolean = request.execute().onSuccessful(ResponseSpec::isOk);
        if (Boolean.TRUE.equals(aBoolean)){
            log.info("添加执行器成功，执行器名称{}",xxlJobProperties.getExecutor().getTitle());
        }

        return Boolean.TRUE.equals(aBoolean);
    }


    /**
     * 精确检查
     * 根据 appName和title
     *
     * @return boolean
     */
    public boolean preciselyCheck() {
        //原生xxl-job-admin中分页模糊查询
        List<XxlJobGroup> jobGroup = getJobGroup();
        if (CollUtil.isEmpty(jobGroup)){
            return false;
        }
        Optional<XxlJobGroup> has = jobGroup.stream()
                .filter(xxlJobGroup -> xxlJobGroup.getAppname().equals(xxlJobProperties.getExecutor().getAppName())
                        && xxlJobGroup.getTitle().equals(xxlJobProperties.getExecutor().getTitle()))
                .findAny();
        //isPresent()方法用于判断value是否存在，不为NULL则返回true
        return has.isPresent();
    }

}