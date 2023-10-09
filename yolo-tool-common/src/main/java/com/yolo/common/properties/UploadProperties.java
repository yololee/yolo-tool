package com.yolo.common.properties;


import com.yolo.common.support.utils.file.PathUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * 文件上传配置
 */
@Getter
@Setter
@Component
@ConfigurationProperties(UploadProperties.PREFIX)
public class UploadProperties {
    public static final String PREFIX = "yolo.upload";

    /**
     * 是否启用本地的文件上传，默认：开启
     */
    private  boolean enable = true;

    /**
     * 默认图片大小50M
     */
    private  long defaultMaxSize = 52428800L;
    /**
     * 默认的文件名称最大长度100
     */
    private  int defaultFileNameLength = 100;

    /**
     * 文件保存目录，默认：jar 包同级目录
     */
    @Nullable
    private  String savePath = PathUtil.getJarPath();

    /**
     * 导入文件上传路径
     */
    private  String importPath = PathUtil.getJarPath();

    private static final UploadProperties ME = new UploadProperties();

    public static UploadProperties me() {
        return ME;
    }

    /**
     * 获取导入上传路径
     */
    public static String getImportPath() {
        return UploadProperties.ME.importPath + "/import";
    }


    /**
     * 获取文件上传路径
     */
    public static String getUploadPath() {
        return UploadProperties.ME.savePath + "/upload";
    }

}
