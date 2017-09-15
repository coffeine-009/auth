package com.thecoffeine.auth.config;

import com.thecoffeine.auth.model.matcher.CsrfSecurityRequestMatcher;
import org.springframework.boot.actuate.autoconfigure.ManagementServerProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Web security configuration.
 *
 * @version 1.0
 */
@Profile( "default" )
@Configuration
@Order( ManagementServerProperties.ACCESS_OVERRIDE_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Override this method to configure the {@link HttpSecurity}. Typically subclasses
     * should not invoke this method by calling super as it may override their
     * configuration. The default configuration is:
     * <p>
     * <pre>
     * http.authorizeRequests().anyRequest().authUserAuthenticationManager.java:74enticated().and().formLogin().and().httpBasic();
     * </pre>
     *
     * @param http the {@link HttpSecurity} to modify
     * @throws Exception if an error occurs
     */
    @Override
    protected void configure( HttpSecurity http ) throws Exception {
        http//TODO: disable csrf for AJAX
            .cors()
            .and()
            .requestMatchers()
            .antMatchers( "/", "/login", "/security/**", "/signup", "/signup/success", "/oauth/authorize", "/oauth/confirm_access" )
            .and()
            .authorizeRequests()
                .antMatchers( "/security/**" ).permitAll()
                .antMatchers( "/signup", "/signup/success" ).permitAll()
                .antMatchers( "/user/info" ).permitAll()
                .anyRequest().authenticated()
            .and()
            .csrf().requireCsrfProtectionMatcher( new CsrfSecurityRequestMatcher() )
            .and()
            .formLogin()
                .loginPage( "/login" )
                .permitAll()
            .and()
                .logout()
                .permitAll();
    }
}
