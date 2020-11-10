package com.exam.BithumbTest.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 비밀번호 유효성 annotaion 구현
 */
@Documented
@Constraint(validatedBy = CheckPasswordValidatorImpl.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordValidation {
    String message() default "password is not validate";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
