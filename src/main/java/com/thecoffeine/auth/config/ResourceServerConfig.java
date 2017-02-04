package com.thecoffeine.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * Resource server configuration.
 *
 * @version 1.0
 */
@Configuration
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure( HttpSecurity http ) throws Exception {
        http
            .requestMatchers()
            .antMatchers( "/**" )
            .and()
            .authorizeRequests()
            .antMatchers( "/security/**" )
            .permitAll()
            .antMatchers( "/user/info" )
            .permitAll()
            .anyRequest()
            .authenticated();
    }
}
