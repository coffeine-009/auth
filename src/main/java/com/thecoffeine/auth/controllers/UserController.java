package com.thecoffeine.auth.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Controller to work with user.
 *
 * @version 1.0
 */
@RestController
@RequestMapping( "/user" )
public class UserController {

    @RequestMapping( "/info" )
    public Principal userInfoAction( Principal user ) {
        return user;
    }
}
