package com.yolo.test.excel;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.ListUtil;
import com.yolo.test.excel.listener.SysUserImportListener;
import com.yolo.common.api.R;
import com.yolo.excel.ExcelResult;
import com.yolo.excel.utils.ExcelUtil;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("excel")
public class TestExcelController {

    /**
     * 导出用户列表
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) {
        List<SysUser> list = getUserList();
        List<SysUserExportVo> listVo = BeanUtil.copyToList(list, SysUserExportVo.class);
        ExcelUtil.exportExcel(listVo, "用户数据", SysUserExportVo.class, response);
    }


    /**
     * 获取导入模板
     */
    @GetMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil.exportExcel(new ArrayList<>(), "用户数据", SysUserImportVo.class, response);
    }

    /**
     * 导入数据
     *
     * @param file          导入文件
     * @param updateSupport 是否更新已存在数据
     */
    @PostMapping(value = "/importData", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R<String> importData(@RequestPart("file") MultipartFile file, boolean updateSupport) throws Exception {
        ExcelResult<SysUserImportVo> result = ExcelUtil.importExcel(file.getInputStream(), SysUserImportVo.class, new SysUserImportListener(updateSupport));
        String analysis = result.getAnalysis();
        return R.data("",analysis);
    }

    private List<SysUser> getUserList() {
        SysUser user01 = new SysUser();
        user01.setUserId(1L);
        user01.setUserName("test01");
        user01.setSex("0");
        user01.setStatus("0");

        SysUser user02 = new SysUser();
        user02.setUserId(2L);
        user02.setUserName("test02");
        user02.setSex("1");
        user02.setStatus("1");
        return ListUtil.of(user01,user02);
    }


}
