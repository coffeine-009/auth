/**
 * Copyright (c) 2014-2016 by Coffeine Inc
 *
 * @author <a href = "mailto:vitaliy.tsutsman@musician-virtuoso.com>Vitaliy Tsutsman</a>
 *
 * @date 11/30/15 11:14 PM
 */

package com.thecoffeine.auth.view.form;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.thecoffeine.auth.library.validator.anotation.Event;
import com.thecoffeine.auth.library.validator.anotation.InEnum;
import com.thecoffeine.auth.library.validator.anotation.RequiredGroup;
import com.thecoffeine.auth.model.entity.Roles;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.time.LocalDate;
import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Form for registration of a new users.
 *
 * @version 1.0
 */
@Event( start = "birthday", end = "deathDate" )
@RequiredGroup(
    fields = {
        "socialId",
        "accessToken",
        "expiresIn"
    },
    message = "{RequiredGroup.registrationForm.social}"
)
public class RegistrationForm {

    /// *** Properties  *** ///
    @NotNull
    @NotEmpty
    @Email
    protected String username;

    @NotEmpty
    protected String password;

    protected Long socialId;

    @Length( min = 32 )
    protected String accessToken;

    @Min( 100 )
    protected Integer expiresIn;

    @NotNull
    @NotEmpty
    @Size( max = 16, message = "{Size.registrationForm.firstName}" )
    protected String firstName;

    @NotNull
    @NotEmpty
    @Size( max = 16 )
    protected String lastName;

    @NotNull
    protected Boolean gender;

    @NotNull
    @NotEmpty
    @InEnum( enumClass = Roles.class )//TODO: add exists validator
    protected List<String> roles;

    @NotNull
    @NotEmpty
    @Size( max = 5 )
    protected String locale;

    @NotNull
    @JsonDeserialize( using = LocalDateDeserializer.class )//FIXME: Add validation for date format
    protected LocalDate birthday;

    @JsonDeserialize( using = LocalDateDeserializer.class )
    protected LocalDate deathDate;


    /// *** Methods     *** ///
    /**
     * Create an empty form.
     */
    public RegistrationForm() {
        //- Initialization -//
    }

    /**
     * Create form with required fields.
     *
     * @param username     Unique username of user.
     * @param password     Password of user.
     * @param firstName    First name of user.
     * @param lastName     Last name of user.
     * @param gender       True - man, false - woman.
     * @param roles        List of roles.
     * @param locale       User's locale.
     * @param birthday     Birthday of user.
     */
    public RegistrationForm(
        String username,
        String password,
        String firstName,
        String lastName,
        Boolean gender,
        List<String> roles,
        String locale,
        LocalDate birthday
    ) {
        //- Initialization -//
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.roles = roles;
        this.locale = locale;
        this.birthday = birthday;
    }

    /**
     * Create form with all fields.
     *
     * @param username      Unique username of user.
     * @param password      Password of user.
     * @param firstName     First name of user.
     * @param lastName      Last name of user.
     * @param gender        True - man, false - woman.
     * @param roles         List of roles.
     * @param locale        User's locale.
     * @param birthday      Birthday of user.
     * @param deathDate     Birthday of death if it is known.
     */
    public RegistrationForm(
        String username,
        String password,
        String firstName,
        String lastName,
        Boolean gender,
        List<String> roles,
        String locale,
        LocalDate birthday,
        LocalDate deathDate
    ) {
        //- Initialization -//
        this(
            username,
            password,
            firstName,
            lastName,
            gender,
            roles,
            locale,
            birthday
        );
        this.deathDate = deathDate;
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
     * Get social network id.
     *
     * @return Social id.
     */
    public Long getSocialId() {
        return socialId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    /**
     * Get first name.
     *
     * @return String First name.
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Get last name.
     *
     * @return String Last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Get gender.
     *
     * @return boolean true - man, false - woman.
     */
    public Boolean getGender() {
        return gender;
    }

    /**
     * Get roles.
     *
     * @return List of roles.
     */
    public List<String> getRoles() {
        return roles;
    }

    /**
     * Get locale.
     *
     * @return String Locale code.
     */
    public String getLocale() {
        return locale;
    }

    /**
     * Get birthday.
     *
     * @return Date Birthday.
     */
    public LocalDate getBirthday() {
        return birthday;
    }

    /**
     * Get day of death.
     *
     * @return Date Day of death.
     */
    public LocalDate getDeathDate() {
        return deathDate;
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
     * Set social network id.
     *
     * @param socialId    Id of social network.
     */
    public void setSocialId( Long socialId ) {
        this.socialId = socialId;
    }

    public void setAccessToken( String accessToken ) {
        this.accessToken = accessToken;
    }

    public void setExpiresIn( Integer expiresIn ) {
        this.expiresIn = expiresIn;
    }

    /**
     * Set first name.
     *
     * @param firstName    First name.
     */
    public void setFirstName( String firstName ) {
        this.firstName = firstName;
    }

    /**
     * Set last name.
     *
     * @param lastName  Last name.
     */
    public void setLastName( String lastName ) {
        this.lastName = lastName;
    }

    /**
     * Set gender.
     *
     * @param gender true - man, false - woman.
     */
    public void setGender( Boolean gender ) {
        this.gender = gender;
    }

    /**
     * Set roles.
     *
     * @param roles    List of roles.
     */
    public void setRoles( List<String> roles ) {
        this.roles = roles;
    }

    /**
     * Set locale.
     *
     * @param locale    Locale code.
     */
    public void setLocale( String locale ) {
        this.locale = locale;
    }

    /**
     * Set birthday.
     *
     * @param birthday    Date of birthday.
     */
    public void setBirthday( LocalDate birthday ) {
        this.birthday = birthday;
    }

    /**
     * Set day of death.
     *
     * @param deathDate    Day of death.
     */
    public void setDeathDate(LocalDate deathDate) {
        this.deathDate = deathDate;
    }
}
