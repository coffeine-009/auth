/**
 * Copyright (c) 2014-2016 by Coffeine Inc
 *
 * @author <a href = "mailto:vitaliy.tsutsman@musician-virtuoso.com>Vitaliy Tsutsman</a>
 *
 * @date 12/13/15 2:27 PM
 */

package com.thecoffeine.auth.library.validator.anotation;


import com.thecoffeine.auth.library.validator.anotation.implementation.InEnumValidatorImpl;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotNull;

/**
 * Validator for check if field contains only items from enum.
 *
 * @version 1.0
 */
@Documented
@Constraint( validatedBy = InEnumValidatorImpl.class )
@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.FIELD )
@NotNull( message = "Value can not be null" )
@ReportAsSingleViolation
public @interface InEnum {

    /**
     * Enum that contains values for checking.
     *
     * @return Enum.
     */
    Class<? extends Enum<?>> enumClass();

    /**
     * Default message for constraint violation.
     *
     * @return String.
     */
    String message() default "Value is not valid";

    /**
     * Grouping.
     *
     * @return Array of classes.
     */
    Class<?>[] groups() default {};

    /**
     * Payload.
     *
     * @return Class.
     */
    Class<? extends Payload>[] payload() default {};
}
