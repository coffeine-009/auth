/**
 * Copyright (c) 2014-2016 by Coffeine Inc
 *
 * @author <a href = "mailto:vitaliy.tsutsman@musician-virtuoso.com>Vitaliy Tsutsman</a>
 *
 * @date 11/30/15 11:14 PM
 */

package com.thecoffeine.auth.model.provider.implementation;


import com.thecoffeine.auth.model.entity.Access;
import com.thecoffeine.auth.model.entity.AuthenticationToken;
import com.thecoffeine.auth.model.entity.SocialAccount;
import com.thecoffeine.auth.model.entity.User;
import com.thecoffeine.auth.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;

import static org.springframework.util.Assert.isTrue;
import static org.springframework.util.Assert.notNull;

/**
 * Authentication provider of users.
 *
 * @version 1.0
 */
@Component
public class UserAuthenticationManager implements AuthenticationManager {

    /// *** Properties  *** ///
    //- SECTION :: CRYPTOGRAPHY -//
    /**
     * Encoder for create hash of password.
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;


    /// *** Methods     *** ///
    //- SECTION :: MAIN -//
    /**
     * Authentication.
     *
     * @param authentication    Authentication object.
     *
     * @return Authentication AuthenticationToken
     *
     * @throws AuthenticationException if credentials are bad.
     */
    @Transactional
    @Override
    public Authentication authenticate(
        Authentication authentication
    ) throws AuthenticationException {

        try {
            final Map<String, String> details = (Map<String, String>) authentication.getDetails();
            final String socialToken = details.get( "social_token" );
            final String userId = details.get( "user_id" );
            final String expiresIn = details.get( "expires_in" );
            final boolean isSocialSignIn = !StringUtils.isEmpty( socialToken );

            //- Check params -//
            isTrue(
                isSocialSignIn && !StringUtils.isEmpty( userId )
                && !StringUtils.isEmpty( expiresIn )
                || !isSocialSignIn
            );

            //- Raw password -//
            final String username = "" + authentication.getPrincipal();
            final String password = "" + authentication.getCredentials();

            //- Search user -//
            User user = isSocialSignIn
                ? this.userService.findBySocialId( Long.parseLong( userId ) )
                : this.userService.findByUsername( username );

            //- Check if user exists -//
            notNull( user );

            final Access access = user.getAccess().stream().findFirst().get();

            //- Check if passwords are equal -//
            isTrue(
                isSocialSignIn
                || (
                    !isSocialSignIn
                    && this.passwordEncoder.matches( password, access.getPassword() )
                )
            );

            //- Update acces token -//
            if ( isSocialSignIn ) {
                final SocialAccount socialAccount = user.getSocialAccounts().iterator().next();
                //- Update access params -//
                socialAccount.setAccessToken( socialToken );
                socialAccount.setExpiresIn( Integer.parseInt( expiresIn ) );

                this.userService.update( user );
            }

            //- Set authorities -//
            List<GrantedAuthority> authorities = new ArrayList<>();

            //- Populate roles into authentication object -//
            user.getRoles().forEach(
                role -> authorities.add(
                    new SimpleGrantedAuthority(
                        role.getCode()
                    )
                )
            );

            //- Create new auth token -//
            AuthenticationToken authToken = new AuthenticationToken(
                authentication.getPrincipal(),
                authentication.getAuthorities(),
                authorities
            );

            return authToken;
        } catch ( IllegalArgumentException e ) {
            throw new BadCredentialsException( "Bad user credentials", e );
        }
    }
}
