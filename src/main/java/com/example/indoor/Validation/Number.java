package com.example.indoor.Validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = NumberValidator.class)
@Target( { ElementType.PARAMETER, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Number {
    String message() default "数字以外が入力されています";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
