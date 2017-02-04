package com.thecoffeine.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * Auth server.
 *
 * @version 1.0
 */
@EnableAuthorizationServer
@EnableResourceServer
@SpringBootApplication
public class AuthApplication {

    /**
     * Entry point.
     *
     * @param args    Command line args.
     */
    public static void main( String [] args ) {
        SpringApplication.run( AuthApplication.class, args );
    }
}
