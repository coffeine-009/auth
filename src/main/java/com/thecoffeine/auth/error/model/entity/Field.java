/**
 * Copyright (c) 2014-2016 by Coffeine Inc
 *
 * @author <a href = "mailto:vitaliy.tsutsman@musician-virtuoso.com>Vitaliy Tsutsman</a>
 *
 * @date 12/13/15 2:22 PM
 */

package com.thecoffeine.auth.error.model.entity;

/**
 * Describe fild with validation error.
 *
 * @version 1.0.
 */
public class Field {

    /// *** Properties  *** ///
    /**
     * Field, which contain error.
     */
    protected String field;

    /**
     * Message of error.
     */
    protected String message;


    /// *** Methods     *** ///
    /**
     * Constructor, init error.
     *
     * @param field     Name of field.
     * @param message   Message about error for this field.
     */
    public Field(
        String field,
        String message
    ) {
        this.field = field;
        this.message = message;
    }

    //- SECTION :: GET -//

    /**
     * Get field.
     *
     * @return String.
     */
    public String getField() {
        return field;
    }

    /**
     * Get message.
     *
     * @return String.
     */
    public String getMessage() {
        return message;
    }


    //- SECTION :: SET -//
    /**
     * Set field.
     *
     * @param field Name of field.
     */
    public void setField( String field ) {
        this.field = field;
    }

    /**
     * Set message.
     *
     * @param message Message about error for this field.
     */
    public void setMessage( String message ) {
        this.message = message;
    }
}
