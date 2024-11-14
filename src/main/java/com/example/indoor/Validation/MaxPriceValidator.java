package com.example.indoor.Validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static java.util.regex.Pattern.matches;
import static org.apache.logging.log4j.util.Strings.isBlank;

public class MaxPriceValidator implements
        ConstraintValidator<MaxPrice, String> {

    @Override
    public void initialize(MaxPrice contactNumber) {
    }

    @Override
    public boolean isValid(String maxPrice,
                           ConstraintValidatorContext cxt) {
        if (isBlank(maxPrice) || !maxPrice.matches("^[0-9]*$")) {
            return true;
        } else {
            return 1000000 >= Integer.parseInt(maxPrice);
        }
    }
}

