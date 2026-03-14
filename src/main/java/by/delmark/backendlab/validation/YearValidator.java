package by.delmark.backendlab.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;


public class YearValidator implements ConstraintValidator<YearIsPastOrPresent, Integer> {

    @Override
    public boolean isValid(Integer integer, ConstraintValidatorContext constraintValidatorContext) {
        return (integer != null && integer <= LocalDate.now().getYear());
    }
}
