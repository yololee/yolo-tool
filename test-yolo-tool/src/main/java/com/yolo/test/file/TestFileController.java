package com.yolo.test.file;

import com.yolo.common.api.R;
import com.yolo.common.constant.SystemConstant;
import com.yolo.common.properties.UploadProperties;
import com.yolo.common.support.Kv;
import com.yolo.common.support.utils.file.FileHandleUtil;
import com.yolo.common.support.utils.file.FileUploadUtil;
import com.yolo.common.support.utils.string.StringUtil;
import com.yolo.common.support.utils.web.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/file")
@Slf4j
public class TestFileController {


    @Autowired
    private FileUploadUtil fileUploadUtil;


    /**
     * 通用上传请求（单个）
     */
    @PostMapping("/upload")
    @ResponseBody
    public R<Kv> uploadFile(@RequestPart MultipartFile file) {
        try {
            // 上传文件路径
            String filePath = UploadProperties.getUploadPath();
            // 上传并返回新文件名称
            String fileName = fileUploadUtil.upload(filePath, file);
            String url = WebUtil.getDomain() + fileName;
            Kv kv = Kv.init().set("url", url).set("fileName", fileName).set("newFileName", FileHandleUtil.getName(fileName));
            return R.success(kv);
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }


    /**
     * 通用上传请求（多个）
     */
    @PostMapping("/uploads")
    @ResponseBody
    public R<Kv> uploadFiles(@RequestPart List<MultipartFile> files) {
        try {
            // 上传文件路径
            String filePath = UploadProperties.getUploadPath();
            List<String> urls = new ArrayList<>();
            List<String> fileNames = new ArrayList<>();
            List<String> newFileNames = new ArrayList<>();
            for (MultipartFile file : files) {
                // 上传并返回新文件名称
                String fileName = fileUploadUtil.upload(filePath, file);
                String url = WebUtil.getDomain() + fileName;
                urls.add(url);
                fileNames.add(fileName);
                newFileNames.add( FileHandleUtil.getName(fileName));
            }
            Kv kv = Kv.init().set("url", StringUtil.join(urls, ","))
                    .set("fileName", StringUtil.join(fileNames, ","))
                    .set("newFileName", StringUtil.join(newFileNames, ","));
            return R.success(kv);
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }

    /**
     * 本地资源通用下载
     * http://localhost:8081/file/download/resource?resource=/profile//2023/10/09/程序员头像_20231009171548A001.jpeg&delete=false
     */
    @GetMapping("/download/resource")
    public void resourceDownload(String resource, Boolean delete, HttpServletResponse response) {
        try {
            if (!FileHandleUtil.checkAllowDownload(resource)) {
                throw new Exception(StringUtil.format("资源文件({})非法，不允许下载。 ", resource));
            }
            // 本地资源路径
            String localPath = UploadProperties.getUploadPath();
            // 数据库资源地址
            String downloadPath = localPath + StringUtils.substringAfter(resource, SystemConstant.RESOURCE_PREFIX);
            // 下载名称
            String downloadName = StringUtils.substringAfterLast(downloadPath, "/");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileHandleUtil.setAttachmentResponseHeader(response, downloadName);
            FileHandleUtil.writeBytes(downloadPath, response.getOutputStream());
            if (delete) {
                FileHandleUtil.deleteFile(downloadPath);
            }
        } catch (Exception e) {
            log.error("下载文件失败", e);
        }
    }

}
