package com.example.indoor.Validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = MaxPriceValidator.class)
@Target( { ElementType.PARAMETER, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface MaxPrice {
    String message() default "金額は999999以内で入力してください";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
