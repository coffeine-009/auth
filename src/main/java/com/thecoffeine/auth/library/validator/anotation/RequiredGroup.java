/**
 * Copyright (c) 2014-2016 by Coffeine Inc
 *
 * @author <a href = "mailto:vitaliy.tsutsman@musician-virtuoso.com>Vitaliy Tsutsman</a>
 *
 * @date 8/9/16 8:53 PM
 */

package com.thecoffeine.auth.library.validator.anotation;


import com.thecoffeine.auth.library.validator.anotation.implementation.RequiredGroupValidatorImpl;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Validator for required group.
 *
 * @version 1.0
 */
@Documented
@Target( { TYPE, ANNOTATION_TYPE } )
@Retention( RUNTIME )
@Constraint( validatedBy = RequiredGroupValidatorImpl.class )
public @interface RequiredGroup {

    /**
     * List of fields that has to be required.
     *
     * @return Fields' names.
     */
    String[] fields() default {};

    /**
     * Message for invalid values.
     *
     * @return String Error message.
     */
    String message() default "{javax.validation.constraints.RequiredGroup.message}";

    /**
     * Validate group of events.
     *
     * @return Event.
     */
    Class<?>[] groups() default {};

    /**
     * FIXME: Investigate if this is needed.
     * @return TODO
     */
    Class<? extends Payload>[] payload() default {};

    /**
     * Defines several {@link Event} annotations on the same element.
     *
     * @see Event
     */
    @Target( { METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER } )
    @Retention( RUNTIME )
    @Documented
    @interface List {

        Event[] value();
    }
}
