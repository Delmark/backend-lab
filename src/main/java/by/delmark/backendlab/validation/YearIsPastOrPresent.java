package by.delmark.backendlab.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = YearValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface YearIsPastOrPresent {
    String message() default "Указан некорректный год издания, должен быть указан прошедший или текущий год";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
