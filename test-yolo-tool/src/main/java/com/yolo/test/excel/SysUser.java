package com.yolo.test.excel;

import lombok.Data;

@Data
public class SysUser {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户账号
     */
    private String userName;
    /**
     * 用户性别
     */
    private String sex;

    /**
     * 帐号状态（0正常 1停用）
     */
    private String status;

}