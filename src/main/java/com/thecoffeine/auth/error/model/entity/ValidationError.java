/**
 * Copyright (c) 2014-2016 by Coffeine Inc
 *
 * @author <a href = "mailto:vitaliy.tsutsman@musician-virtuoso.com>Vitaliy Tsutsman</a>
 *
 * @date 12/13/15 2:24 PM
 */

package com.thecoffeine.auth.error.model.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Object for describe input error.
 *
 * @version 1.0
 */
public class ValidationError {

    /// *** Properties  *** ///
    /**
     * List of errors/field.
     */
    private List<Field> fieldErrors = new ArrayList<>();


    //***    Methods     *** ///
    /**
     * Add new fieldErrors.
     *
     * @param field     Name of field.
     * @param message   Message about error for this field.
     */
    public void addFieldError( String field, String message ) {
        fieldErrors.add(
            new Field(
                field,
                message
            )
        );
    }


    //-  SECTION :: GET -//
    /**
     * Get field errors.
     *
     * @return List of errors/fields.
     */
    public List<Field> getFieldErrors() {
        return fieldErrors;
    }
}
