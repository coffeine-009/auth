/**
 * Copyright (c) 2014-2016 by Coffeine Inc
 *
 * @author <a href = "mailto:vitaliy.tsutsman@musician-virtuoso.com>Vitaliy Tsutsman</a>
 *
 * @date 11/25/15 10:40 PM
 */

package com.thecoffeine.auth.library.validator.anotation.implementation;


import com.thecoffeine.auth.library.validator.anotation.Event;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static org.springframework.util.Assert.isTrue;

/**
 * Implementation of Event validator.
 *
 * @version 1.0
 * @see Event
 */
public class EventValidatorImpl implements ConstraintValidator<Event, Object> {

    /**
     * Logger.
     */
    private static final Logger log = LogManager.getLogger( EventValidatorImpl.class );

    /**
     * Name of field which contains start date of event.
     */
    private String startDateFieldName;

    /**
     * Name of field which contains end date or null of event,
     * null means that event has not finished yet.
     */
    private String endDateFieldName;


    /**
     * Initializes the validator in preparation for
     * {@link #isValid(Object, ConstraintValidatorContext)} calls.
     * The constraint annotation for a given constraint declaration
     * is passed.
     *
     * <p>This method is guaranteed to be called before any use of this instance for
     * validation.
     *
     * @param constraintAnnotation annotation instance for a given constraint declaration
     */
    @Override
    public void initialize( Event constraintAnnotation ) {
        //- Initialization -//
        this.startDateFieldName = constraintAnnotation.start();
        this.endDateFieldName = constraintAnnotation.end();
    }

    /**
     * Implements the validation logic.
     * The state of {@code value} must not be altered.
     *
     * <p>This method can be accessed concurrently, thread-safety must be ensured
     * by the implementation.
     *
     * @param value   object to validate
     * @param context context in which the constraint is evaluated
     *
     * @return {@code false} if {@code value} does not pass the constraint
     */
    @Override
    public boolean isValid( Object value, ConstraintValidatorContext context ) {

        boolean valid = false;

        try {
            //- Get values for validation -//
            LocalDate startDate = LocalDate.parse( BeanUtils.getProperty(
                value,
                this.startDateFieldName
            ));
            String endDate =  BeanUtils.getProperty( value, this.endDateFieldName );

            //- Validate -//
            valid = (endDate == null || startDate.isBefore( LocalDate.parse( endDate ) ) )
                && startDate.isBefore( LocalDate.now() );

            //- Add error message to second field -//
            isTrue( valid );
        } catch (
            IllegalArgumentException
            | IllegalAccessException
            | InvocationTargetException
            | NoSuchMethodException
            | NullPointerException e
        ) {
            //- Log exception -//
            log.warn( "Validation error.", e );

            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                context.getDefaultConstraintMessageTemplate()
            )
                .addPropertyNode( this.endDateFieldName )
                .addConstraintViolation();
        }

        return valid;
    }
}
