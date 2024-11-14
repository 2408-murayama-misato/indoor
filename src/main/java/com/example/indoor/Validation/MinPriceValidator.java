package com.example.indoor.Validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static org.apache.logging.log4j.util.Strings.isBlank;

public class MinPriceValidator implements
        ConstraintValidator<MinPrice, String> {

    @Override
    public void initialize(MinPrice contactNumber) {
    }

    @Override
    public boolean isValid(String minPrice,
                           ConstraintValidatorContext cxt) {
        if (isBlank(minPrice) || !minPrice.matches("^[0-9]*$")) {
            return true;
        } else {
            return 1 <=Integer.parseInt(minPrice);
        }
    }
}
