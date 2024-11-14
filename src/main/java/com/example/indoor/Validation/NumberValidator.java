package com.example.indoor.Validation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static org.apache.logging.log4j.util.Strings.isBlank;

public class NumberValidator implements
        ConstraintValidator<Number, String> {

    @Override
    public void initialize(Number contactNumber) {
    }

    @Override
    public boolean isValid(String id,
                           ConstraintValidatorContext cxt) {
                if (isBlank(id)) {
                    return true;
                } else {
                    return id.matches("^[0-9]*$");
                }
    }
}
