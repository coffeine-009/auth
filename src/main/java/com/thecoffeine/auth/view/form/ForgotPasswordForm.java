/**
 * Copyright (c) 2014-2016 by Coffeine Inc
 *
 * @author <a href = "mailto:vitaliy.tsutsman@musician-virtuoso.com>Vitaliy Tsutsman</a>
 *
 * @date 12/2/15 8:31 PM
 */

package com.thecoffeine.auth.view.form;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Form for forgot password requests.
 *
 * @version 1.0
 */
public class ForgotPasswordForm {

    /**
     * Email of account which password you forgot for.
     */
    @NotNull
    @NotEmpty
    @Email
    private String email;


    /**
     * Default constructor for create empty form.
     */
    public ForgotPasswordForm() {
        //- Initialization -//
    }

    /**
     * Create form with required fields.
     *
     * @param email    E-mail address of associated to account
     */
    public ForgotPasswordForm( String email ) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail( String email ) {
        this.email = email;
    }
}
