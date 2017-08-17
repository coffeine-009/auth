package com.thecoffeine.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * Resource server configuration.
 *
 * @version 1.0
 */
@Profile( "default" )
@Configuration
@EnableResourceServer
public class ResourceServerConfig {
}
