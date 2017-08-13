package com.thecoffeine.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

/**
 * OAuth2.0 configuration.
 *
 * @version 1.0
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void configure( ClientDetailsServiceConfigurer clients ) throws Exception {
        clients
            .inMemory()
            .withClient( "developer" )
            .secret( "developer32" )
            .scopes( "read", "write" )
            .authorizedGrantTypes( "password", "authorization_code", "refresh_token" )
            .accessTokenValiditySeconds( 900 );
    }

    @Override
    public void configure( AuthorizationServerEndpointsConfigurer endpoints ) throws Exception {
        endpoints
            .authenticationManager( this.authenticationManager )
            .userDetailsService( this.userDetailsService );
    }

    @Override
    public void configure( AuthorizationServerSecurityConfigurer security ) throws Exception {
        security.allowFormAuthenticationForClients();
    }
}
