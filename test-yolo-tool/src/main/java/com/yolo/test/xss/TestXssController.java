package com.yolo.test.xss;

import com.yolo.common.api.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/xss")
public class TestXssController {


    /**
     * http://localhost:8081/xss/clear?name=<script>alert(111)</script>
     *  这里选择的清空模式
     * @param name
     * @return {@link R}<{@link String}>
     */
    @GetMapping("/clear")
    public R<String> clearXss(String name){
        return R.data(name);
    }
}
