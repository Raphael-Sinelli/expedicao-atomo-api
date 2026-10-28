package br.com.fiap.t2tdsps.expedicaoatomo.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UfValidaValidator.class)
public @interface UfValida {

    String message() default "UF deve ser uma das 27 siglas oficiais em maiúsculas, como SP";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
