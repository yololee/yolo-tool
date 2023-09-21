package com.yolo.tool.support.validator;




import com.yolo.tool.support.utils.collection.CollectionUtil;
import com.yolo.tool.support.utils.string.StringUtil;
import com.yolo.tool.support.validator.anno.TextFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 参数校验验证器
 */
public class TextFormatValidator implements ConstraintValidator<TextFormat, String> {

    private Boolean  required;
    private int maxLength;
    private boolean notChinese;
    private Set<String> contains;
    private Set<String> notContains;
    private String startWith;
    private String endsWith;

    private boolean phone;
    private boolean email;

    // 注解初始化时执行
    @Override
    public void initialize(TextFormat textFormat) {
        this.required = textFormat.required();
        this.maxLength = textFormat.maxLength();
        this.notChinese = textFormat.notChinese();
        this.contains = Arrays.stream(textFormat.contains()).collect(Collectors.toSet());
        this.notContains = Arrays.stream(textFormat.notContains()).collect(Collectors.toSet());
        this.startWith = textFormat.startWith();
        this.endsWith = textFormat.endsWith();
        this.phone = textFormat.phone();
        this.email = textFormat.email();
    }

    @Override
    public boolean isValid(String target, ConstraintValidatorContext context) {
        if (StringUtil.isBlank(target)) {
            return !required;
        }

        if (Boolean.TRUE.equals(!required && StringUtil.isNotBlank(target) && maxLength != -1) && target.length() < maxLength){
            return Boolean.TRUE;
        }

        if (StringUtil.isNotBlank(target) && notChinese){
            return checkNoeChinese(target);
        }

        if (StringUtil.isNotBlank(target) && phone){
            return checkPhone(target);
        }

        if (StringUtil.isNotBlank(target) && email){
            return checkEmail(target);
        }

        if (CollectionUtil.isNotEmpty(contains) && contains.contains(target)){
           return Boolean.TRUE;
        }

        if (CollectionUtil.isNotEmpty(notContains) && !notContains.contains(target)){
            return Boolean.TRUE;
        }

        if (StringUtil.isNotBlank(startWith) && target.startsWith(startWith)) {
            return Boolean.TRUE;
        }

        if (StringUtil.isNotBlank(endsWith) && target.endsWith(startWith)) {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    private boolean checkEmail(String target) {
        String regEx = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(target);
        return  matcher.find();
    }

    private boolean checkPhone(String target) {
        String regEx = "^1[3456789]\\d{9}$";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(target);
        return  matcher.find();
    }

    private Boolean checkNoeChinese(String target) {
        String regEx = "[\\u4e00-\\u9fa5]";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(target);
        return  matcher.find();
    }

}
