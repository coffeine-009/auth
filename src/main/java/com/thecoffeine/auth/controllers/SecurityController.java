package com.thecoffeine.auth.controllers;

import com.thecoffeine.auth.view.form.RegistrationForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Security Controller.
 *
 * @version 1.0
 */
@Controller
public class SecurityController {

    /**
     * Display LogIn form.
     *
     * @param request   HTTP request.
     * @param model     View's model.
     *
     * @return View name.
     */
    @RequestMapping( value = "/login" )
    public String login( HttpServletRequest request, Model model ) {
        //- Set params for view -//
        model.addAttribute( "_csrf", request.getAttribute( "_csrf" ) );
        model.addAttribute( "error", request.getParameterMap().containsKey( "error" ) );

        return "login";
    }

    /**
     * Handle Sign Up.
     *
     * @param request HTTP request.
     *
     * @return View name.
     */
    @RequestMapping(method = RequestMethod.POST)
    public String signUp(
        @RequestBody
        RegistrationForm form,

        HttpServletRequest request
    ) {
        return "sign-up.success";
    }
}
