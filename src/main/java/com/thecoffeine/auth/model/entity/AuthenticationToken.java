/**
 * Copyright (c) 2014-2016 by Coffeine Inc
 *
 * @author <a href = "mailto:vitaliy.tsutsman@musician-virtuoso.com>Vitaliy Tsutsman</a>
 *
 * @date 11/30/15 11:14 PM
 */

package com.thecoffeine.auth.model.entity;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Token for authenticated users.
 *
 * @version 1.0
 */
public class AuthenticationToken extends AbstractAuthenticationToken {

    /// *** Properties  *** ///
    /**
     * Principal.
     */
    protected final Object principal;

    /**
     * Credentials.
     */
    protected Object credential;


    /// *** Methods     *** ///
    /**
     * Constructor for create new Auth token.
     *
     * @param principal      Principal for access.
     * @param credential     Credentials for access.
     * @param authorities    List of authorities.
     */
    public AuthenticationToken(
        Object principal,
        Object credential,
        Collection<? extends GrantedAuthority> authorities
    ) {
        //- Delegate authorities -//
        super( authorities );

        //- Init -//
        this.principal = principal;
        this.credential = credential;

        //- Mark user as authenticated -//
        super.setAuthenticated( true );
    }

    //- SECTION :: GET -//
    /**
     * Get Credentials.
     *
     * @return Object
     */
    @Override
    public Object getCredentials() {
        return this.credential;
    }

    /**
     * Get principal.
     *
     * @return Object
     */
    @Override
    public Object getPrincipal() {
        return this.principal;
    }
}
