package br.gov.pr.celepar.exemplo.util.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Interface Bean Validator de CPF.
 * @author GIC
 * @version 1.0 - 17/04/19
 */
@Constraint(validatedBy = CPFValidator.class)
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CPF {

	String message() default "{message.cpfinvalido}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}