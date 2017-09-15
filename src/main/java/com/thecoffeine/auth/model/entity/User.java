/**
 * Copyright (c) 2014-2016 by Coffeine Inc
 *
 * @author <a href = "mailto:vitaliy.tsutsman@musician-virtuoso.com>Vitaliy Tsutsman</a>
 *
 * @date 12/5/15 11:47 AM
 */

package com.thecoffeine.auth.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.google.common.base.MoreObjects;
import com.thecoffeine.auth.model.configuration.Social;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Class for reflect table from persistence layout.
 *
 * @version 1.0
 */
@JsonIgnoreProperties( ignoreUnknown = true )
@Entity
@Table( name = "users" )
@SequenceGenerator(
    name = "users_sequence",
    sequenceName = "users_sequence",
    allocationSize = 1
)
public class User implements Serializable {

    /// *** Properties  *** ///
    /**
     * Unique id of user.
     * Primary key.
     */
    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "users_sequence"
    )
    @Column
    protected Long id;

    /**
     * List of roles assigned to user.
     */
    @NotNull
    @Valid
    @ManyToMany( fetch = FetchType.EAGER )
    @JoinTable(
        name = "user_roles", 
        uniqueConstraints = {
            @UniqueConstraint(
                columnNames = {
                    "id_user", 
                    "id_role"
                }
            )
        }, 
        joinColumns = {
            @JoinColumn(
                name = "id_user",
                unique = false, 
                nullable = false,
                updatable = false
            )
        },
        inverseJoinColumns = {
            @JoinColumn(
                name = "id_role", 
                unique = false, 
                nullable = false,
                updatable = false
            )
        }
    )
    protected List<Role> roles = new ArrayList<>();

    /**
     * List of accesses.
     */
    @JsonIgnore
    @NotNull
    @NotEmpty
    @Valid
    @OneToMany(
        mappedBy = "user",
        fetch = FetchType.EAGER,
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    protected Set<Access> access = new HashSet<>();

    /**
     * List of e-mails.
     */
    @JsonIgnore
    @NotNull
    @NotEmpty
    @Valid
    @OneToMany(
        mappedBy = "user",
        cascade = CascadeType.ALL,
        orphanRemoval = false
    )
    protected List<Email> emails = new ArrayList<>();

    /**
     * List of social accounts.
     */
    @JsonManagedReference
    @OneToMany(
        mappedBy = "user",
        fetch = FetchType.EAGER,
        cascade = CascadeType.ALL,
        orphanRemoval = false
    )
    protected Set<SocialAccount> socialAccounts = new HashSet<>();

    /**
     * First name.
     */
    @NotNull
    @NotEmpty
    @Length( max = 16 )
    @Column( name = "first_name", length = 16 )
    protected String firstName;

    /**
     * Last name.
     */
    @Length( max = 16 )
    @Column( name = "last_name", length = 16 )
    protected String lastName;

    /**
     * Middle name.
     */
    @Length( max = 32 )
    @Column( name = "middle_name", length = 32 )
    protected String middleName;

    /**
     * Gender. True - man, false - woman, null - unknown.
     */
    @Column
    protected Boolean gender;

    /**
     * Code of user's locale.
     */
    @NotNull
    @NotEmpty
    @Length( max = 5 )
    @Column( length = 5 )
    protected String locale;

    /**
     * Time of registration.
     */
    @Column( name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP" )
    protected Calendar creation;


    /// *** Methods     *** ///
    /**
     * Default constructor.
     */
    public User() {
        //- Initialization -//
    }

    /**
     * Constructor for create user.
     *
     * @param roles         List of roles
     * @param email         Email
     * @param firstName     First name
     * @param lastName      Last name
     * @param middleName    Father's name
     * @param locale        Default locale
     */
    public User(
        List<Role> roles,
        Email email,
        String firstName,
        String lastName,
        String middleName,
        String locale
    ) {
        //- Call default constructor -//
        this();

        //- Initialization -//
        this.roles = roles;
        this.addEmail(email);
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.locale = locale;
    }

    /**
     * Constructor for create user.
     *
     * @param roles         List of roles
     * @param access        List of permissions
     * @param email         Email
     * @param firstName     First name
     * @param lastName      Last name
     * @param middleName    Father's name
     * @param locale        Default locale
     */
    public User(
        List<Role> roles,
        Access access,
        Email email,
        String firstName,
        String lastName,
        String middleName,
        String locale
    ) {
        //- Call default constructor -//
        this();

        //- Initialization -//
        this.roles = roles;
        this.addAccess( access );
        this.addEmail(email);
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.locale = locale;
    }

    /**
     * Constructor for create user.
     *
     * @param roles         List of roles
     * @param access        List of permissions
     * @param email         Email
     * @param firstName     First name
     * @param lastName      Last name
     * @param gender        Gender
     * @param locale        Default locale
     */
    public User(
        List<Role> roles,
        Access access,
        Email email,
        String firstName,
        String lastName,
        Boolean gender,
        String locale
    ) {
        //- Call default constructor -//
        this();

        //- Initialization -//
        this.roles = roles;
        this.addAccess( access );
        this.addEmail( email );
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.locale = locale;
    }

    /**
     * Constructor for create user.
     *
     * @param roles        List of roles
     * @param firstName    First name
     * @param locale       Default locale
     */
    public User(
        List<Role> roles,
        String firstName,
        String locale
    ) {
        //- Call default constructor -//
        this();

        //- Initialization -//
        this.roles = roles;
        this.access = null;
        this.emails = null;
        this.firstName = firstName;
        this.locale = locale;
    }

    /**
     * Constructor for create user.
     *
     * @param access       Access,
     * @param email        E-mail.
     * @param firstName    First name
     * @param locale       Default locale
     */
    public User(
        Access access,
        Email email,
        String firstName,
        String lastName,
        Locale locale
    ) {
        //- Call default constructor -//
        this();
        access.setUser( this );
        email.setUser( this );

        //- Initialization -//
        this.access.add( access );
        this.emails.add( email );
        this.firstName = firstName;
        this.lastName = lastName;
        this.locale = locale.toLanguageTag();
    }

    /**
     * Create user only with role list.
     *
     * @param roles    List of roles
     */
    public User( List<Role> roles ) {
        this.roles = roles;
    }


    //- SECTION :: GET -//
    /**
     * Get ID of song.
     *
     * @return Long ID of song
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Get role.
     *
     * @return Role
     */
    public List<Role> getRoles() {
        return this.roles;
    }

    /**
     * Get access.
     *
     * @return List of access
     */
    public Set<Access> getAccess() {
        return access;
    }

    /**
     * Get emails for this user.
     *
     * @return List of emails
     */
    public List<Email> getEmails() {
        return emails;
    }

    /**
     * Get social accounts.
     *
     * @return  List of social accounts.
     */
    public Set<SocialAccount> getSocialAccounts() {
        return socialAccounts;
    }

    /**
     * Get first name.
     *
     * @return String
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Get last name.
     *
     * @return String
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * Get middle name.
     *
     * @return String
     */
    public String getMiddleName() {
        return this.middleName;
    }

    /**
     * Get gender.
     *
     * @return Boolean true - man, false - woman, null - unknown.
     */
    public Boolean getGender() {
        return gender;
    }

    /**
     * Get locale code.
     *
     * @return String
     */
    public String getLocale() {
        return this.locale;
    }

    /**
     * Get time of create this record.
     *
     * @return Calendar
     */
    public Calendar getCreation() {
        return this.creation;
    }


    //- SECTION :: SET -//
    /**
     * Set ID of song.
     *
     * @param id ID of song
     */
    public void setId( Long id ) {
        this.id = id;
    }

    /**
     * Set role.
     *
     * @param roles    List of roles.
     */
    public void setRoles( List<Role> roles ) {
        this.roles = roles;
    }

    /**
     * Set access.
     *
     * @param access    List of accesses.
     */
    public void setAccess( Set<Access> access ) {
        this.access = access;
    }

    /**
     * Set email of this user.
     *
     * @param emails    List of e-mails.
     */
    public void setEmails( List<Email> emails ) {
        this.emails = emails;
    }

    /**
     * Set list of social accounts.
     *
     * @param socialAccounts    List of social accounts.
     */
    public void setSocialAccounts( Set<SocialAccount> socialAccounts ) {
        this.socialAccounts = socialAccounts;
    }

    /**
     * Set first name.
     *
     * @param firstName    First name of user.
     */
    public void setFirstName( String firstName ) {
        this.firstName = firstName;
    }

    /**
     * Set last name.
     *
     * @param lastName     Last name of user.
     */
    public void setLastName( String lastName ) {
        this.lastName = lastName;
    }

    /**
     * Set middle name.
     *
     * @param middleName    Father's name of user if exists.
     */
    public void setMiddleName( String middleName ) {
        this.middleName = middleName;
    }

    /**
     * Set gender.
     *
     * @param gender true - man, false - woman, null - undefined
     */
    public void setGender( Boolean gender ) {
        this.gender = gender;
    }

    /**
     * Set locale code.
     *
     * @param locale Locale code
     */
    public void setLocale( String locale ) {
        this.locale = locale;
    }


    //- SECTION :: MAIN -//
    /**
     * Add access for user.
     *
     * @param access    Access for user.
     */
    public void addAccess( Access access ) {

        // Set user
        access.setUser( this );

        // Check exists access list
        if ( !this.access.contains( access ) ) {
            // Add a new access for user
            this.access.add( access );
        }
    }

    /**
     * Add a new email.
     *
     * @param email    E-mail address.
     */
    public void addEmail( Email email ) {

        // Set user
        email.setUser( this );

        // Check exists email list
        if ( !this.emails.contains( email ) ) {
            // Add a new email for user
            this.emails.add( email );
        }
    }

    /**
     * Add a new social account.
     *
     * @param socialAccount    Social account data.
     */
    public void addSocialAccount( SocialAccount socialAccount ) {
        socialAccount.setUser( this );
        socialAccount.setSocialName( Social.FACEBOOK );

        if (!this.socialAccounts.contains( socialAccount )) {
            this.socialAccounts.add( socialAccount );
        }
    }


    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            roles,
            emails,
            firstName,
            lastName,
            middleName,
            gender,
            locale
        );
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null || getClass() != obj.getClass() ) {
            return false;
        }
        final User other = (User) obj;
        return Objects.equals( this.id, other.id )
            && Objects.equals( this.roles, other.roles )
            && Objects.equals( this.emails, other.emails )
            && Objects.equals( this.firstName, other.firstName )
            && Objects.equals( this.lastName, other.lastName )
            && Objects.equals( this.middleName, other.middleName )
            && Objects.equals( this.gender, other.gender )
            && Objects.equals( this.locale, other.locale );
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper( this )
            .add( "id", id )
            .add( "roles", roles )
            .add( "emails", emails )
            .add( "firstName", firstName )
            .add( "lastName", lastName )
            .add( "middleName", middleName )
            .add( "gender", gender )
            .add( "locale", locale )
            .add( "creation", creation )
            .toString();
    }
}
