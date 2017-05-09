/**
 * Copyright (c) 2014-2016 by Coffeine Inc
 *
 * @author <a href = "mailto:vitaliy.tsutsman@musician-virtuoso.com>Vitaliy Tsutsman</a>
 *
 * @date 8/2/16 10:29 PM
 */

package com.thecoffeine.auth.model.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.thecoffeine.auth.model.configuration.Social;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Entity to persist social accounts;
 *
 * @version 1.0
 */
@Entity
@Table( name = "social_accounts" )
@SequenceGenerator(
    name = "social_accounts_sequence",
    sequenceName = "social_accounts_sequence",
    allocationSize = 1
)
public class SocialAccount implements Serializable {

    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "social_accounts_sequence"
    )
    @Column
    protected Long id;

    @JsonBackReference
    @NotNull
    @Valid
    @ManyToOne
    @JoinColumn( name = "id_user" )
    protected User user;

    @Enumerated( EnumType.STRING )
    @Column( name = "social_name" )
    protected Social socialName;

    @NotNull
    @Column( name = "social_id" )
    protected Long socialId;

    @NotNull
    @NotEmpty
    @Length( min = 32 )
    @Column( name = "access_token" )
    protected String accessToken;

    @NotNull
    @Min( 100 )
    @Column( name = "expires_in" )
    protected Integer expiresIn;


    /**
     * Default constructor.
     */
    public SocialAccount() {
    }

    /**
     * Create a new Social account.
     *
     * @param socialId       Social id.
     * @param accessToken    Social access token.
     * @param expiresIn      Access token expiration time.
     */
    public SocialAccount(
        Long socialId,
        String accessToken,
        Integer expiresIn
    ) {
        this.socialId = socialId;
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
    }


    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Social getSocialName() {
        return socialName;
    }

    public Long getSocialId() {
        return socialId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }


    public void setId( Long id ) {
        this.id = id;
    }

    public void setUser( User user ) {
        this.user = user;
    }

    public void setSocialName( Social socialName ) {
        this.socialName = socialName;
    }

    public void setSocialId( Long socialId ) {
        this.socialId = socialId;
    }

    public void setAccessToken( String accessToken ) {
        this.accessToken = accessToken;
    }

    public void setExpiresIn( Integer expiresIn ) {
        this.expiresIn = expiresIn;
    }
}
