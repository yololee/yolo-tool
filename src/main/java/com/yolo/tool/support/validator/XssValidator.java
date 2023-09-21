package com.yolo.tool.support.validator;

import cn.hutool.core.util.ReUtil;
import cn.hutool.http.HtmlUtil;
import com.yolo.tool.support.validator.anno.Xss;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 自定义xss校验注解实现
 */
public class XssValidator implements ConstraintValidator<Xss, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return !ReUtil.contains(HtmlUtil.RE_HTML_MARK, value);
    }

}
