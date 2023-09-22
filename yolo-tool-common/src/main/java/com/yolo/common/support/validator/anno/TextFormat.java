package com.yolo.common.support.validator.anno;

import com.yolo.common.support.validator.TextFormatValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 自定义参数校验注解： @TextFormat
 * @author jujueaoye
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TextFormatValidator.class)
@Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.TYPE})
public @interface TextFormat {

    /**
     * 是否必填 默认是必填的
     */
    boolean required() default true;

    /**
     * 判断字符串长度
     */
    int maxLength() default -1;


    /**
     * 判断字符串中是否包含中文 包含则抛出异常
     */
    boolean notChinese() default false;

    /**
     * 判断手机号码是否和否规范 包含则抛出异常
     */
    boolean phone() default false;

    /**
     * 判断邮箱是否和否规范 包含则抛出异常
     */
    boolean email() default false;


    /**
     * 是否包含,不包含抛出异常
     */
    String[] contains() default {};

    /**
     * 是否不包含,包含抛出异常
     */
    String[] notContains() default {};

    /**
     * 前缀以xx开始
     */
    String startWith() default "";

    /**
     * 后缀以xx结束
     */
    String endsWith() default "";

    /**
     * 默认错误提示信息
     */
    String message() default "参数校验失败!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
