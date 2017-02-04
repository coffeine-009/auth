/**
 * Copyright (c) 2014-2016 by Coffeine Inc
 *
 * @author <a href = "mailto:vitaliy.tsutsman@musician-virtuoso.com>Vitaliy Tsutsman</a>
 *
 * @date 3/20/16 1:49 PM
 */

package com.thecoffeine.auth.view.form;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Form for recovering access.
 *
 * @version 1.0
 */
public class AccessRecoveryForm {

    /**
     * One time hash for recovering access.
     * Was sent in message.
     */
    @NotNull
    @NotEmpty
    private String hash;

    /**
     * New password.
     */
    @NotNull
    @NotEmpty
    private String password;


    public AccessRecoveryForm() {
    }

    public AccessRecoveryForm( String hash, String password ) {
        this.hash = hash;
        this.password = password;
    }

    //- SECTION :: GET -//
    public String getHash() {
        return hash;
    }

    public String getPassword() {
        return password;
    }


    //- SECTION :: SET -//
    public void setHash( String hash ) {
        this.hash = hash;
    }

    public void setPassword( String password ) {
        this.password = password;
    }
}
