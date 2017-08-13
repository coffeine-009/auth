package com.thecoffeine.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Auth server.
 *
 * @version 1.0
 */
@EnableDiscoveryClient
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
