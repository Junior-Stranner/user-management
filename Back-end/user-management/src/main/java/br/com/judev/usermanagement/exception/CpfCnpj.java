package br.com.judev.usermanagement.exception;


import br.com.judev.usermanagement.web.validators.CpfCnpjValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CpfCnpjValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface CpfCnpj {

    String message() default "Invalid CPF or CNPJ";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
