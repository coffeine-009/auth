package com.thecoffeine.auth.config;

import com.thecoffeine.auth.model.matcher.CsrfSecurityRequestMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.ManagementServerProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Web security configuration.
 *
 * @version 1.0
 */
@Configuration
@Order( ManagementServerProperties.ACCESS_OVERRIDE_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

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
            .authorizeRequests()
                .antMatchers( "/security/**" ).permitAll()
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

    /**
     * Used by the default implementation of {@link #authenticationManager()} to attempt
     * to obtain an {@link AuthenticationManager}. If overridden, the
     * {@link AuthenticationManagerBuilder} should be used to specify the
     * {@link AuthenticationManager}.
     * <p>
     * <p>
     * The {@link #authenticationManagerBean()} method can be used to expose the resulting
     * {@link AuthenticationManager} as a Bean. The {@link #userDetailsServiceBean()} can
     * be used to expose the last populated {@link UserDetailsService} that is created
     * with the {@link AuthenticationManagerBuilder} as a Bean. The
     * {@link UserDetailsService} will also automatically be populated on
     * {@link HttpSecurity#getSharedObject(Class)} for use with other
     * {@link SecurityContextConfigurer} (i.e. RememberMeConfigurer )
     * </p>
     * <p>
     * <p>
     * For example, the following configuration could be used to register in memory
     * authentication that exposes an in memory {@link UserDetailsService}:
     * </p>
     * <p>
     * <pre>
     * &#064;Override
     * protected void configure(AuthenticationManagerBuilder auth) {
     * 	auth
     * 	// enable in memory based authentication with a user named
     * 	// &quot;user&quot; and &quot;admin&quot;
     * 	.inMemoryAuthentication().withUser(&quot;user&quot;).password(&quot;password&quot;).roles(&quot;USER&quot;).and()
     * 			.withUser(&quot;admin&quot;).password(&quot;password&quot;).roles(&quot;USER&quot;, &quot;ADMIN&quot;);
     * }
     *
     * // Expose the UserDetailsService as a Bean
     * &#064;Bean
     * &#064;Override
     * public UserDetailsService userDetailsServiceBean() throws Exception {
     * 	return super.userDetailsServiceBean();
     * }
     *
     * </pre>
     *
     * @param auth the {@link AuthenticationManagerBuilder} to use
     * @throws Exception
     */
    @Override
    protected void configure( AuthenticationManagerBuilder auth ) throws Exception {
        auth.parentAuthenticationManager( this.authenticationManager );
    }
}
