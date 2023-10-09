package com.yolo.test.oss;

import com.yolo.common.api.IController;
import com.yolo.common.api.R;
import com.yolo.oss.AliossTemplate;
import com.yolo.oss.model.BladeFile;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/oss/ali")
@RequiredArgsConstructor
public class TestAliController implements IController {

    private final AliossTemplate aliossTemplate;


    /**
     * 上传文件
     * 返回结果
     * "link": "http://mytanghua.oss-cn-beijing.aliyuncs.com/upload/20231009/8cf1072f1345a580705b4b7b39f4037e.jpeg",
     * "domain": "http://mytanghua.oss-cn-beijing.aliyuncs.com",
     * "name": "upload/20231009/8cf1072f1345a580705b4b7b39f4037e.jpeg",
     * "originalName": "111.jpeg"
     *
     * @param file 文件
     * @return {@link R}<{@link String}>
     */
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R<BladeFile> upload(@RequestPart("file") MultipartFile file) {
        BladeFile putFile = aliossTemplate.putFile("mytanghua", "111.jpeg", file);
        return R.success(putFile);
    }

    /**
     * 删除文件
     *
     * @param fileName "name": "upload/20231009/8cf1072f1345a580705b4b7b39f4037e.jpeg",
     * @return {@link R}<{@link String}>
     */
    @GetMapping("/remove")
    public R<String> remove(String fileName) {
        aliossTemplate.removeFile(fileName);
        return R.success("OK");
    }

}
