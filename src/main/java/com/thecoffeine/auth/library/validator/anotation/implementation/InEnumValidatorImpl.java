/**
 * Copyright (c) 2014-2016 by Coffeine Inc
 *
 * @author <a href = "mailto:vitaliy.tsutsman@musician-virtuoso.com>Vitaliy Tsutsman</a>
 *
 * @date 08/12/14 3:26 PM
 */

package com.thecoffeine.auth.library.validator.anotation.implementation;


import com.thecoffeine.auth.library.validator.anotation.InEnum;

import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validator for enumeration values.
 *
 * @version 1.0
 */
public class InEnumValidatorImpl implements ConstraintValidator<InEnum, List<String>> {

    /// *** Properties  *** ///
    /**
     * List of available values.
     */
    private List<String> valueList = null;


    /// *** Methods     *** ///
    /**
     * Validate input.
     *
     * @param values     List of values for checking.
     * @param context    Context.
     *
     * @return boolean true - input is valid, false - not valid.
     */
    @Override
    public boolean isValid(
        List<String> values,
        ConstraintValidatorContext context
    ) {
        for ( String value : values ) {
            if ( !valueList.contains( value.toUpperCase()) ) {
                return false;
            }
        }

        return true;
    }

    /**
     * Initialization.
     *
     * @param constraintAnnotation    Annotation.
     */
    @Override
    public void initialize( InEnum constraintAnnotation ) {

        valueList = new ArrayList<>();

        Class<? extends Enum<?>> enumClass = constraintAnnotation.enumClass();

        @SuppressWarnings( "rawtypes" )
        Enum[] enumValArr = enumClass.getEnumConstants();

        for ( Enum enumVal: enumValArr ) {
            valueList.add( enumVal.toString().toUpperCase() );
        }

    }
}
