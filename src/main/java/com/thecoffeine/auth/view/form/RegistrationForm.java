/**
 * Copyright (c) 2014-2016 by Coffeine Inc
 *
 * @author <a href = "mailto:vitaliy.tsutsman@musician-virtuoso.com>Vitaliy Tsutsman</a>
 *
 * @date 11/30/15 11:14 PM
 */

package com.thecoffeine.auth.view.form;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Form for registration of a new users.
 *
 * @version 1.0
 */
public class RegistrationForm implements Serializable {

    /// *** Properties  *** ///
    @NotNull
    @NotEmpty
    @Email
    protected String username;

    @NotEmpty
    protected String password;

    @NotNull
    @NotEmpty
    @Size( max = 33 )
    protected String fullName;


    /// *** Methods     *** ///
    /**
     * Default constructor.
     */
    public RegistrationForm() {
    }

    /**
     * Create form with required fields.
     *
     * @param username      Unique username of user.
     * @param password      Password of user.
     * @param fullName      First & last names of user.
     */
    @JsonCreator
    public RegistrationForm(
        @JsonProperty("username") String username,
        @JsonProperty("password") String password,
        @JsonProperty("fullName") String fullName
    ) {
        //- Initialization -//
        this.username = username;
        this.password = password;
        this.fullName = fullName;
    }

    //- SECTION :: GET -//
    /**
     * Get username.
     *
     * @return String   Username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get password.
     *
     * @return String Password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Get first name.
     *
     * @return String First name.
     */
    public String getFullName() {
        return this.fullName;
    }


    //- SECTION :: SET -//
    /**
     * Set username.
     *
     * @param username    Username.
     */
    public void setUsername( String username ) {
        this.username = username;
    }

    /**
     * Set password.
     *
     * @param password    Password.
     */
    public void setPassword( String password ) {
        this.password = password;
    }

    /**
     * Set first name.
     *
     * @param fullName      First & last names of user.
     */
    public void setFullName( String fullName ) {
        this.fullName = fullName;
    }
}
