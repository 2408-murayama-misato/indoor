package com.example.indoor.Validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static org.apache.logging.log4j.util.Strings.isBlank;

public class MinPriceValidator implements
        ConstraintValidator<MaxPrice, String> {

    @Override
    public void initialize(MaxPrice contactNumber) {
    }

    @Override
    public boolean isValid(String minPrice,
                           ConstraintValidatorContext cxt) {
        if (isBlank(minPrice)) {
            return true;
        } else {
            return 1 <=Integer.parseInt(minPrice);
        }
    }
}
