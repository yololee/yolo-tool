package com.yolo.xxljob.utils;

import com.yolo.common.exception.ServiceException;
import com.yolo.common.support.Kv;
import com.yolo.common.support.utils.collection.CollectionUtil;
import com.yolo.common.support.utils.string.StringUtil;
import com.yolo.xxljob.model.JobInfo;
import com.yolo.xxljob.model.XxlJobGroup;
import com.yolo.xxljob.model.XxlJobInfo;
import com.yolo.xxljob.service.JobGroupService;
import com.yolo.xxljob.service.JobInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class XxlJobUtil {

    private final JobInfoService jobInfoService;
    private final JobGroupService jobGroupService;

    public void addJobInfo(JobInfo jobInfo) {
        checkAddJobParam(jobInfo);
        Kv hashMap = Kv.init().set("cron", jobInfo.getCron())
                .set("executorHandler", jobInfo.getExecutorHandler())
                .set("jobDesc", jobInfo.getJobDesc())
                .set("author", jobInfo.getAuthor())
                .set("executorRouteStrategy", jobInfo.getExecutorRouteStrategy())
                .set("triggerStatus", jobInfo.getTriggerStatus());

        //获取执行器
        List<XxlJobGroup> jobGroups = jobGroupService.getJobGroup();
        XxlJobGroup xxlJobGroup = jobGroups.get(0);

        List<XxlJobInfo> xxlJobInfos = jobInfoService.getJobInfo(xxlJobGroup.getId(), hashMap.getStr("executorHandler"));
        if (CollectionUtil.isNotEmpty(xxlJobInfos)) {
            //因为是模糊查询，需要再判断一次
            Optional<XxlJobInfo> first = xxlJobInfos.stream()
                    .filter(xxlJobInfo -> xxlJobInfo.getExecutorHandler().equals(hashMap.get("executorHandler")))
                    .findFirst();
            if (first.isPresent()) {
                throw new ServiceException("xxl-job：执行器为" + xxlJobGroup.getAppname() + "JobHandle为" + hashMap.get("executorHandler") + "已经存在");
            }
        }
        XxlJobInfo xxlJobInfo = createXxlJobInfo(xxlJobGroup, hashMap);
        jobInfoService.addJobInfo(xxlJobInfo);
    }

    private void checkAddJobParam(JobInfo jobInfo) {
        if (StringUtil.isBlank(jobInfo.getCron())){
            throw new ServiceException("xxl-job的cron表达式不能为空");
        }
        if (StringUtil.isBlank(jobInfo.getAuthor())){
            throw new ServiceException("xxl-job的负责人不能为空");
        }
        if (StringUtil.isBlank(jobInfo.getJobDesc())){
            throw new ServiceException("xxl-job的描述不能为空");
        }
        if (StringUtil.isBlank(jobInfo.getExecutorHandler())){
            throw new ServiceException("xxl-job的执行器名称不能为空");
        }
    }

    private XxlJobInfo createXxlJobInfo(XxlJobGroup xxlJobGroup, Kv kv) {
        XxlJobInfo xxlJobInfo = new XxlJobInfo();
        xxlJobInfo.setJobGroup(xxlJobGroup.getId());
        xxlJobInfo.setJobDesc(kv.getStr("jobDesc"));
        xxlJobInfo.setAuthor(kv.getStr("author"));
        xxlJobInfo.setScheduleType("CRON");
        xxlJobInfo.setScheduleConf(kv.getStr("cron"));
        xxlJobInfo.setGlueType("BEAN");
        xxlJobInfo.setExecutorHandler(kv.getStr("executorHandler"));
        xxlJobInfo.setExecutorRouteStrategy(kv.getStr("executorRouteStrategy"));
        xxlJobInfo.setMisfireStrategy("DO_NOTHING");
        xxlJobInfo.setExecutorBlockStrategy("SERIAL_EXECUTION");
        xxlJobInfo.setExecutorTimeout(0);
        xxlJobInfo.setExecutorFailRetryCount(0);
        xxlJobInfo.setGlueRemark("GLUE代码初始化");
        xxlJobInfo.setTriggerStatus(Integer.parseInt(kv.getStr("triggerStatus")));
        return xxlJobInfo;
    }
}
