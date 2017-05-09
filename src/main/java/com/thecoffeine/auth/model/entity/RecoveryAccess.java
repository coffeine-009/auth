/**
 * Copyright (c) 2014-2016 by Coffeine Inc
 *
 * @author <a href = "mailto:vitaliy.tsutsman@musician-virtuoso.com>Vitaliy Tsutsman</a>
 *
 * @date 12/5/15 3:06 PM
 */

package com.thecoffeine.auth.model.entity;

import com.google.common.base.MoreObjects;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.NotEmpty;

import java.time.OffsetDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

/**
 * Entity for persist requests about lost access.
 *
 * @version 1.0
 */
@Entity
@Table(
    name = "recovery_access",
    uniqueConstraints = {
        @UniqueConstraint(
            columnNames = {
                "id_user"
            }
        )
    }
)
public class RecoveryAccess {

    /// *** Properties  *** ///
    /**
     * Unique id of record.
     */
    @Id
    @GeneratedValue
    @Column
    protected Long id;

    /**
     * User for recovery access.
     */
    @NotNull
    @Valid
    @OneToOne
    @JoinColumn( name = "id_user" )
    protected User user;

    /**
     * Unique hash as one-time link for reset password.
     */
    @NotNull
    @NotEmpty
    @Column( length = 256 )
    protected String hash;

    /**
     * Time of expiration.
     */
    @NotNull
    @Future
    @Column( name = "expired_at" )
    protected OffsetDateTime expiredAt;

    /**
     * Date and time of creation.
     */
    @ColumnDefault( "CURRENT_TIMESTAMP" )
    @Column( name = "created_at", insertable = false, nullable = false )
    protected OffsetDateTime createdAt;


    /// *** Methods     *** ///
    /**
     * Default constructor.
     */
    public RecoveryAccess() {
        //- Initialization -//
    }

    /**
     * Create request for recovery access.
     *
     * @param user         User for recovery access.
     * @param hash         One-time hash for recovery access.
     * @param expiredAt    Expiry time.
     */
    public RecoveryAccess(
        User user,
        String hash,
        OffsetDateTime expiredAt
    ) {
        this.user = user;
        this.hash = hash;
        this.expiredAt = expiredAt;
    }


    //- SECTION :: GET -//
    /**
     * Getter for id.
     *
     * @return Long
     */
    public Long getId() {
        return id;
    }

    /**
     * Getter for user.
     *
     * @return User
     */
    public User getUser() {
        return user;
    }

    /**
     * Getter for unique hash for recovery access.
     *
     * @return String
     */
    public String getHash() {
        return hash;
    }

    /**
     * Getter for expiry time.
     *
     * @return OffsetDateTime
     */
    public OffsetDateTime getExpiredAt() {
        return expiredAt;
    }

    /**
     * Getter for createdAt.
     *
     * @return OffsetDateTime
     */
    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }


    //- SECCTION :: SET -//
    /**
     * Setter for id.
     *
     * @param id    Id of hash.
     */
    public void setId( Long id ) {
        this.id = id;
    }

    /**
     * Setter for user.
     *
     * @param user    User for recovery access.
     */
    public void setUser( User user ) {
        this.user = user;
    }

    /**
     * Setter for unique hash.
     *
     * @param hash    Unique hash for recovery access.
     */
    public void setHash( String hash ) {
        this.hash = hash;
    }

    /**
     * Set expiry time.
     *
     * @param expiredAt    Expiry time for using this hash.
     */
    public void setExpiredAt( OffsetDateTime expiredAt ) {
        this.expiredAt = expiredAt;
    }


    @Override
    public int hashCode() {
        return Objects.hash( id, user, hash, expiredAt, createdAt );
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null || getClass() != obj.getClass() ) {
            return false;
        }
        final RecoveryAccess other = (RecoveryAccess) obj;
        return Objects.equals( this.id, other.id )
            && Objects.equals( this.user, other.user )
            && Objects.equals( this.hash, other.hash )
            && Objects.equals( this.expiredAt, other.expiredAt )
            && Objects.equals( this.createdAt, other.createdAt );
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper( this )
            .add( "id", id )
            .add( "user", user )
            .add( "hash", hash )
            .add( "expiredAt", expiredAt )
            .add( "createdAt", createdAt )
            .toString();
    }
}
