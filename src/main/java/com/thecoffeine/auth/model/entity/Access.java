/**
 * Copyright (c) 2014-2016 by Coffeine Inc
 *
 * @author <a href = "mailto:vitaliy.tsutsman@musician-virtuoso.com>Vitaliy Tsutsman</a>
 *
 * @date 12/5/15 1:59 PM
 */

package com.thecoffeine.auth.model.entity;

import com.google.common.base.MoreObjects;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Entity for store access information.
 *
 * @version 1.0
 */
@Entity
@Table(
    name = "access"
)
public class Access implements Serializable {

    /// *** Properties  *** ///
    /**
     * Unique id of access for user.
     */
    @Id
    @GeneratedValue
    @Column
    protected  Long id;

    /**
     * User, owner of this access.
     */
    @NotNull
    @Valid
    @ManyToOne
    @JoinColumn( name = "id_user" )
    protected User user;

    /**
     * Hash of secret password for get access.
     */
    @NotNull
    @NotEmpty
    @Length( max = 80 )
    @Column( length = 80 )
    protected String password;

    /**
     * Date and time of last modification.
     * //FIXME: use java.time
     */
    @Column( columnDefinition = "TIMESTAMP NULL" )
    protected OffsetDateTime modification;

    /**
     * Date and time of create this access.
     * FIXME: use java.time
     */
    @Column( columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP" )
    protected OffsetDateTime creation;


    /// *** Methods     *** ///
    /**
     * Default constructor.
     */
    public Access() {
        // Initialization
    }

    /**
     * Constructor for create access.
     *
     * @param password    Hash of password.
     */
    public Access( String password ) {
        //- Initialization -//
        this.password = password;
    }

    /**
     * Constructor for create access.
     *
     * @param user        User, owner of access.
     * @param password    Hash of password.
     */
    public Access(
        User user,
        String password
    ) {
        //- Initialization -//
        this.user = user;
        this.password = password;

        //- Set relation -//
        this.user.addAccess( this );
    }

    //- SECTION :: GET -//
    /**
     * Get ID.
     *
     * @return Long ID.
     */
    public Long getId() {
        return id;
    }

    /**
     * Get user.
     *
     * @return User user @see com.coffeine.virtuoso.security.model.entity.User.
     */
    public User getUser() {
        return user;
    }

    /**
     * Get secret key for access.
     *
     * @return String Secret key 256 bytes.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Get last modification.
     *
     * @return Timestamp.
     */
    public OffsetDateTime getModification() {
        return modification;
    }

    /**
     * Get creation time.
     *
     * @return Timestamp.
     */
    public OffsetDateTime getCreation() {
        return creation;
    }


    //- SECTION :: SET -//
    /**
     * Set ID.
     *
     * @param id Unique id.
     */
    public void setId( Long id ) {
        this.id = id;
    }

    /**
     * Set user. Owner of this access key.
     *
     * @param user User is owner of this access.
     */
    public void setUser( User user ) {
        this.user = user;
    }

    /**
     * Set secret key.
     *
     * @param password Secret key 256 bytes.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    //- SECTION :: HELPER -//
    /**
     * Update modification time before save.
     */
    @PreUpdate
    protected void onUpdate() {
        this.modification = OffsetDateTime.now();
    }


    @Override
    public int hashCode() {
        return Objects.hash( id, user, password, modification, creation );
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null || getClass() != obj.getClass() ) {
            return false;
        }
        final Access other = (Access) obj;
        return Objects.equals( this.id, other.id )
            && Objects.equals( this.user, other.user )
            && Objects.equals( this.password, other.password )
            && Objects.equals( this.modification, other.modification )
            && Objects.equals( this.creation, other.creation );
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper( this )
            .add( "id", id )
            .add( "user", user )
            .add( "password", password )
            .add( "modification", modification )
            .add( "creation", creation )
            .toString();
    }
}
