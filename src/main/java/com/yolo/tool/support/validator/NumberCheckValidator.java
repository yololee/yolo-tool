package com.yolo.tool.support.validator;



import com.yolo.tool.support.utils.convert.NumberUtil;
import com.yolo.tool.support.validator.anno.NumberCheck;
import org.apache.commons.lang3.ObjectUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NumberCheckValidator implements ConstraintValidator<NumberCheck, Object> {

    private Boolean  required;
    private int min;
    private int max;
    private int maxScale;

    @Override
    public void initialize(NumberCheck numberCheck) {
        this.required = numberCheck.required();
        this.min = numberCheck.min();
        this.max = numberCheck.max();
        this.maxScale = numberCheck.maxScale();
    }

    @Override
    public boolean isValid(Object target, ConstraintValidatorContext constraintValidatorContext) {
        if (ObjectUtils.isEmpty(target) || NumberUtil.toInt(target.toString()) <= 0) {
            return !required;
        }

        if (target instanceof Integer){
            int tar = (Integer) target;

            if (!required && (tar > min || tar < max)){
                return Boolean.TRUE;
            }
        }

        if (target instanceof Long){
            long tar = (Long) target;


            if (!required && (tar > min || tar < max)){
                return Boolean.TRUE;
            }
        }

        if (target instanceof Double){
            double tar = (Double) target;

            if (!required && (tar < min || tar > max)){
                return Boolean.FALSE;
            }

            int length = String.valueOf(tar).split("\\.")[1].length();
            if (!required &&  length <= maxScale){
                return Boolean.TRUE;
            }
        }

        return false;
    }
}
