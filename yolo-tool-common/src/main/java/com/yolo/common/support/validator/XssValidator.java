package com.yolo.common.support.validator;


import com.yolo.common.support.utils.spring.RegexUtil;
import com.yolo.common.support.validator.anno.Xss;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 自定义xss校验注解实现
 */
public class XssValidator implements ConstraintValidator<Xss, String> {

    private static final String RE_HTML_MARK = "(<[^<]*?>)|(<[\\s]*?/[^<]*?>)|(<[^<]*?/[\\s]*?>)";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return !RegexUtil.find(RE_HTML_MARK, value);
    }

}
