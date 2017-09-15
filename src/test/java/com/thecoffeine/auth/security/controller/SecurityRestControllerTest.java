/**
 * Copyright (c) 2014-2015 by Coffeine Inc
 *
 * @author Vitaliy Tsutsman <vitaliyacm@gmail.com>
 *
 * @date 11/30/15 11:14 PM
 */

package com.thecoffeine.auth.security.controller;

import com.thecoffeine.auth.controllers.SecurityRestController;
import com.thecoffeine.auth.model.service.AccessRecoveryService;
import com.thecoffeine.auth.model.service.RoleService;
import com.thecoffeine.auth.model.service.UserService;
import com.thecoffeine.auth.module.controller.AbstractControllerTest;
import com.thecoffeine.auth.notification.model.entity.Contact;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for Security controller.
 * @see SecurityRestController
 *
 * @version 1.0
 */
public class SecurityRestControllerTest extends AbstractControllerTest {

    /// *** Properties  *** ///
    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleService roleService;

    @Mock
    private UserService userService;

    @Mock
    private AccessRecoveryService accessRecoveryService;

    @InjectMocks
    private SecurityRestController securityRestController = new SecurityRestController();


    /// *** Methods     *** ///
    /**
     * Init environment for run test
     */
    @Before
    @Override
    public void tearUp() {

        //- Set up application -//
        this.mockMvc = MockMvcBuilders.standaloneSetup( securityRestController ).build();
    }

    /**
     * Reset environment to previous state.
     */
    @After
    @Override
    public void tearDown() {
        //- Clean environment after run tests -//
    }


    /**
     * Test for success of forgotPassword action.
     *
     * @throws Exception    General Exception of application.
     */
    @Test
    public void testForgotPasswordActionSuccess() throws Exception {

        doNothing().when( this.accessRecoveryService ).lostAccess( any( Contact.class ) );

        this.mockMvc.perform(
            post( "/security/forgotPassword" )
                .contentType( MediaType.APPLICATION_JSON )
                .content(
                    "{" +
                        "\"email\": \"unit@test.com\"" +
                    "}"
                )
        )
            .andExpect( status().isOk() );
    }

    /**
     * Test for failure of forgotPassword action.
     *
     * @throws Exception    General Exception of application.
     */
    @Test
    public void testForgotPasswordActionFailure() throws Exception {

        //- Perform request -//
        this.mockMvc.perform(
            post( "/security/forgotPassword" )
                .contentType( MediaType.APPLICATION_JSON )
                .content(
                    "{" +
                        "\"email\": \"unit#test.com\"" +
                    "}"
                )
        )
            .andExpect( status().isBadRequest() );
    }

    /**
     * Test for failure of forgotPassword action.
     *
     * @throws Exception    General Exception of application.
     */
    @Test
    public void testForgotPasswordActionFailureEmail() throws Exception {

        doThrow( IllegalArgumentException.class ).when( this.accessRecoveryService )
            .lostAccess( any( Contact.class ) );

        //- Perform request -//
        this.mockMvc.perform(
            post( "/security/forgotPassword" )
                .contentType( MediaType.APPLICATION_JSON )
                .content(
                    "{" +
                        "\"email\": \"unit-non-exists@test.com\"" +
                    "}"
                )
        )
            .andExpect( status().isNotFound() );
    }

    /**
     * Test of successful recovering of access.
     *
     * @throws Exception    General Exception of application.
     */
    @Test
    public void testAccessRecoverySuccess() throws Exception {
        //- Mocking -//
        doNothing().when( this.accessRecoveryService ).restore( anyString(), anyString() );

        //- Perform request -//
        this.mockMvc.perform(
            post( "/security/access/recovery" )
                .contentType( MediaType.APPLICATION_JSON )
                .content(
                    "{" +
                        "\"hash\": \"4aa46f256305e166c4c63d178dc883c45ec87812\"," +
                        "\"password\": \"p@$sw0rd\"" +
                    "}"
                )
        )
            .andExpect( status().isOk() );
    }

    /**
     * Test of unsuccessful recovering of access.
     * Invalid hash.
     *
     * @throws Exception    General Exception of application.
     */
    @Test
    public void testAccessRecoveryFailure() throws Exception {
        //- Mocking -//
        doThrow( IllegalArgumentException.class ).when( this.accessRecoveryService ).restore( anyString(), anyString() );

        //- Perform request -//
        this.mockMvc.perform(
            post( "/security/access/recovery" )
                .contentType( MediaType.APPLICATION_JSON )
                .content(
                    "{" +
                        "\"hash\": \"invalid_hash\"," +
                        "\"password\": \"p@$sw0rd\"" +
                    "}"
                )
        )
            .andExpect( status().isBadRequest() );
    }
}
