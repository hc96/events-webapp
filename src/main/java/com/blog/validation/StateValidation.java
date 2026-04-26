package com.blog.validation;

import com.blog.anno.State;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StateValidation implements ConstraintValidator<State, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context){
        //提供校验规则
        if (value == null){
            return false;
        }
        if (value.equals("published") || value.equals("draft")){
            return true;
        }
        return false;
    }
}
