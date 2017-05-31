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

    /**
     * Form for confirming access.
     *
     * @param request   HTTP request.
     * @param model     View model.
     * @param session   HTTP session.
     *
     * @return View name.
     */
    @RequestMapping( value = "/confirm_access" )
    public String confirmation(
        final HttpServletRequest request,
        final Model model,
        final HttpSession session
    ) {

        //- Set params for view -//
        //- CSRF protection -//
        model.addAttribute( "_csrf", request.getAttribute( "_csrf" ) );
        //- Requested scopes by client -//
        model.addAttribute( "scopes", request.getAttribute( "scopes" ) );
        //- Request with client info -//
        model.addAttribute(
            "authorizationRequest",
            session.getAttribute( "authorizationRequest" )
        );

        return "access-confirmation";
    }
}
