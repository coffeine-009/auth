/**
 * Copyright (c) 2014-2016 by Coffeine Inc
 *
 * @author <a href = "mailto:vitaliy.tsutsman@musician-virtuoso.com">Vitaliy Tsutsman</a>
 *
 * @date 11/30/15 11:14 PM
 */

package com.thecoffeine.auth.controllers;


import com.thecoffeine.auth.model.service.AccessRecoveryService;
import com.thecoffeine.auth.model.service.RoleService;
import com.thecoffeine.auth.model.service.UserService;
import com.thecoffeine.auth.notification.model.entity.EmailAddress;
import com.thecoffeine.auth.view.form.AccessRecoveryForm;
import com.thecoffeine.auth.view.form.ForgotPasswordForm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * Security controller.
 * Registration, forgot password.
 *
 * @version 1.0
 */
@RestController
@RequestMapping( value = "/security" )
public class SecurityRestController {

    /// *** Constants   *** ///
    /**
     * Logger.
     */
    private static final Logger log = LogManager.getLogger( SecurityRestController.class );

    /**
     * Marker for actions.
     */
    private static final Marker ACTION_MARKER = MarkerManager.getMarker( "ACTION" );


    /// *** Properties  *** ///
    //- SECTION :: CRYPTOGRAPHY -//
    /**
     * Encoder for create hash of password.
     */
    @Autowired
    private PasswordEncoder passwordEncoder;


    //- SECTION :: SERVICES -//
    /**
     * Service for work with roles.
     */
    @Autowired
    private RoleService roleService;

    /**
     * Service for work with users.
     */
    @Autowired
    private UserService userService;

    /**
     * Service for recovery access to account.
     */
    @Autowired
    private AccessRecoveryService accessRecoveryService;


    /// *** Methods     *** ///
    //- SECTION :: ACTIONS -//
    /**
     * Forgot password.
     * Request for recovering access.
     *
     * @param form      Filled form if you forgot password.
     * @param response  Use for work with HTTP.
     */
    @RequestMapping( value = "/forgotPassword", method = RequestMethod.POST )
    @ResponseBody
    public void forgotPasswordAction(
        @Valid
        @RequestBody
        final ForgotPasswordForm form,

        HttpServletResponse response
    ) {
        //- Log action -//
        log.info( ACTION_MARKER, "Request for recovering password." );

        //- Try to make request for recovery access to account -//
        try {
            //- Inform about loosing access -//
            this.accessRecoveryService.lostAccess(
                new EmailAddress( form.getEmail() )
            );
        } catch ( IllegalArgumentException e ) {
            //- Error. Cannot find user -//
            response.setStatus( HttpServletResponse.SC_NOT_FOUND );
        } catch ( IOException e ) {
            //- Error. Cannot apply template -//
            response.setStatus( HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
        }
    }

    /**
     * Recovery access.
     *
     * @param form        Form with data for recovering.
     * @param response    HTTP response.
     */
    @RequestMapping( value = "/access/recovery", method = RequestMethod.POST )
    @ResponseBody
    public void accessRecoveryAction(
        @Valid
        @RequestBody
        AccessRecoveryForm form,

        HttpServletResponse response
    ) {
        //- Log action -//
        log.info( ACTION_MARKER, "Access recovery::Reset password." );

        try {
            //- Set up new access params -//
            this.accessRecoveryService.restore(
                form.getHash(),
                form.getPassword()
            );
        } catch ( DataIntegrityViolationException | IllegalArgumentException e ) {
            //- Invalid request -//
            response.setStatus( HttpServletResponse.SC_BAD_REQUEST );

            log.error( "Cannot recovery access.", e );
        }
    }
}
