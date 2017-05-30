package com.thecoffeine.auth.controllers;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Controller for working with access.
 *
 * @version 1.0.
 */
@Controller
@RequestMapping( "/oauth" )
@SessionAttributes( "authorizationRequest" )
@Scope( "session" )
public class AccessController {

    @RequestMapping( value = "/confirm_access" )
    public String confirmation( HttpServletRequest request, Model model, HttpSession session ) {

        //- Set params for view -//
        model.addAttribute( "_csrf", request.getAttribute( "_csrf" ) );
        model.addAttribute( "scopes", request.getAttribute( "scopes" ) );
        model.addAttribute( "authorizationRequest", session.getAttribute( "authorizationRequest" ) );

        return "access-confirmation";
    }
}
