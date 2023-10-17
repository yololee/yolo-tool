package com.yolo.xxljob.runner;

import com.yolo.common.exception.ServiceException;
import com.yolo.common.support.utils.string.StringUtil;
import com.yolo.xxljob.properties.XxlJobProperties;
import com.yolo.xxljob.service.JobGroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class SystemApplicationRunner implements ApplicationRunner {

    private final JobGroupService jobGroupService;

    private final XxlJobProperties xxlJobProperties;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        if (xxlJobProperties.isEnabled()){
            //注册执行器
            addJobGroup();
        }
    }

    /**
     * 自动注册执行器
     * 配置文件中的appName和title精确匹配查看调度中心是否已有执行器被注册过了，如果存在则跳过，不存在则新注册一个
     */
    private void addJobGroup() {
        checkParam(xxlJobProperties);
        //存在直接返回
        if (jobGroupService.preciselyCheck()) {
            log.info("check xxl-job group is exist!");
            return;
        }

        //不存在新增执行器
        if (jobGroupService.autoRegisterGroup()) {
            log.info("auto register xxl-job group success!");
        }
    }

    private void checkParam(XxlJobProperties xxlJobProperties) {
        String appName = xxlJobProperties.getExecutor().getAppName();
        String title = xxlJobProperties.getExecutor().getTitle();
        if (StringUtil.isBlank(appName) || appName.length() > 64){
            throw new ServiceException("执行器AppName名称，长度最多为64");
        }
        if (StringUtil.isBlank(title) || title.length() > 12){
            throw new ServiceException("执行器名称,长度最多为12");
        }
    }

}