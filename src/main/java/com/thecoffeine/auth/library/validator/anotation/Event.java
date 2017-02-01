/**
 * Copyright (c) 2014-2016 by Coffeine Inc
 *
 * @author <a href = "mailto:vitaliy.tsutsman@musician-virtuoso.com>Vitaliy Tsutsman</a>
 *
 * @date 11/25/15 10:41 PM
 */

package com.thecoffeine.auth.library.validator.anotation;


import com.thecoffeine.auth.library.validator.anotation.implementation.EventValidatorImpl;

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
 * The annotated class must contain two LocalDate fields.
 *
 * <p>Annotation does validation of end date for event(s).
 * The end date must be later then start date or must be null.
 * endDate = null means that event has not finished.
 * </p>
 *
 * <p>Supported types are:
 * <ul>
 *     <li>{@code java.time.LocalDate}</li>
 * </ul>
 *
 * {@code null} end dates are considered valid.
 */
@Documented
@Target( { TYPE, ANNOTATION_TYPE } )
@Retention( RUNTIME )
@Constraint( validatedBy = EventValidatorImpl.class )
public @interface Event {

    /**
     * Date of start event.
     *
     * @return Field name of the start date.
     */
    String start();

    /**
     * Date of the end event.
     *
     * @return Field name of the end date.
     */
    String end();

    /**
     * Message for invalid values.
     *
     * @return String Error message.
     */
    String message() default "{javax.validation.constraints.Event.message}";

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
