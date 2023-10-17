package com.yolo.xxljob.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobInfo {

    /**
     * cron表达式
     */
    private String cron;
    /**
     * 执行器，任务Handler名称
     */
    private String executorHandler;
    /**
     * 描述
     */
    private String jobDesc;
    /**
     * 负责人
     */
    private String author;
    /**
     * 默认为 ROUND 轮询方式
     */
    private String executorRouteStrategy = "ROUND";
    /**
     * 调度状态：0-停止，1-运行
     */
    private int triggerStatus = 0;

}
