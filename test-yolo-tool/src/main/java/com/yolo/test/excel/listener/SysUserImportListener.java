package com.yolo.test.excel.listener;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.yolo.test.excel.SysUser;
import com.yolo.test.excel.SysUserImportVo;
import com.yolo.common.exception.ServiceException;
import com.yolo.common.support.utils.validator.ValidatorUtil;
import com.yolo.excel.ExcelListener;
import com.yolo.excel.ExcelResult;
import java.util.Collections;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 系统用户自定义导入
 */
@Slf4j
public class SysUserImportListener extends AnalysisEventListener<SysUserImportVo> implements ExcelListener<SysUserImportVo> {

    private final Boolean isUpdateSupport;

//    private final ISysUserService userService;
    private int successNum = 0;
    private int failureNum = 0;
    private final StringBuilder successMsg = new StringBuilder();
    private final StringBuilder failureMsg = new StringBuilder();

    public SysUserImportListener(Boolean isUpdateSupport) {
        this.isUpdateSupport = isUpdateSupport;
//        this.userService = SpringUtil.getBean(ISysUserService.class);
    }

    @Override
    public void invoke(SysUserImportVo userVo, AnalysisContext context) {
//        SysUser user = userService.selectUserByUserName(userVo.getUserName());
        // TODO 根据名称获取用户
        SysUser user = new SysUser();
        try {
            // 验证是否存在这个用户
            if (ObjectUtil.isNull(user)) {
                user = BeanUtil.toBean(userVo, SysUser.class);
                ValidatorUtil.validate(user);
                //TODO 插入数据
//                userService.insertUser(user);
                successNum++;
                successMsg.append("<br/>").append(successNum).append("、账号 ").append(user.getUserName()).append(" 导入成功");
            } else if (isUpdateSupport) {
                Long userId = user.getUserId();
                user = BeanUtil.toBean(userVo, SysUser.class);
                user.setUserId(userId);
                ValidatorUtil.validate(user);
                //TODO 校验参数  并且进行更新
//                userService.checkUserAllowed(user);
//                userService.checkUserDataScope(user.getUserId());
//                userService.updateUser(user);
                successNum++;
                successMsg.append("<br/>").append(successNum).append("、账号 ").append(user.getUserName()).append(" 更新成功");
            } else {
                failureNum++;
                failureMsg.append("<br/>").append(failureNum).append("、账号 ").append(user.getUserName()).append(" 已存在");
            }
        } catch (Exception e) {
            failureNum++;
            String msg = "<br/>" + failureNum + "、账号 " + user.getUserName() + " 导入失败：";
            failureMsg.append(msg).append(e.getMessage());
            log.error(msg, e);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }

    @Override
    public ExcelResult<SysUserImportVo> getExcelResult() {
        return new ExcelResult<SysUserImportVo>() {

            @Override
            public String getAnalysis() {
                if (failureNum > 0) {
                    failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
                    throw new ServiceException(failureMsg.toString());
                } else {
                    successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
                }
                return successMsg.toString();
            }

            @Override
            public List<SysUserImportVo> getList() {
                return Collections.emptyList();
            }

            @Override
            public List<String> getErrorList() {
                return Collections.emptyList();
            }
        };
    }
}
