/**
 * Copyright (c) 2014-2015 by Coffeine Inc
 *
 * @author Vitaliy Tsutsman <vitaliyacm@gmail.com>
 *
 * @date 11/30/15 11:14 PM
 */

package com.thecoffeine.auth.security.controller;

import com.thecoffeine.auth.controllers.SecurityController;
import com.thecoffeine.auth.model.entity.User;
import com.thecoffeine.auth.model.service.AccessRecoveryService;
import com.thecoffeine.auth.model.service.RoleService;
import com.thecoffeine.auth.model.service.UserService;
import com.thecoffeine.auth.module.controller.AbstractRestControllerTest;
import com.thecoffeine.auth.notification.model.entity.Contact;
import com.thecoffeine.auth.security.model.persistence.mock.RoleMock;
import com.thecoffeine.auth.security.model.persistence.mock.UserMock;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests for Security controller.
 * @see SecurityController
 *
 * @version 1.0
 */
public class SecurityControllerTest extends AbstractRestControllerTest {

    /// *** Properties  *** ///
    @Mock
    private ShaPasswordEncoder passwordEncoder;

    @Mock
    private RoleService roleService;

    @Mock
    private UserService userService;

    @Mock
    private AccessRecoveryService accessRecoveryService;

    @InjectMocks
    private SecurityController securityController = new SecurityController();


    /// *** Methods     *** ///
    /**
     * Init environment for run test
     */
    @Before
    @Override
    public void tearUp() {

        super.tearUp();

        //- Set up application -//
        this.mockMvc = MockMvcBuilders.standaloneSetup( securityController ).build();
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
     * Test for sign up a new user.
     *
     * @throws Exception    General Exception of application.
     */
    @Test
    public void testSignupActionSuccess() throws Exception {

        //- Mock service -//
        when( this.roleService.findByCodes( anyList() ) ).thenReturn( RoleMock.findByCodes() );
        when( this.userService.create( any( User.class ) ) ).thenReturn( UserMock.create() );
        when( this.passwordEncoder.encodePassword( anyString(), any() ) ).thenReturn( "Te$t" );

        //- Do Sign Up request -//
        this.mockMvc.perform(
            post( "/security/signup" )
                .contentType( MediaType.APPLICATION_JSON )
                .content(
                    "{" +
                        "\"username\": \"unit@test.com\", " +
                        "\"password\": \"Te$t\", " +
                        "\"firstName\": \"Unit\", " +
                        "\"lastName\": \"test\", " +
                        "\"gender\": false, " +
                        "\"locale\": \"en-US\", " +
                        "\"roles\": [" +
                            "\"POET\"" +
                        "], " +
                        "\"birthday\": \"1990-08-10\"" +
                    "}"
                )
        )
            .andExpect( status().isCreated() )
            .andExpect( jsonPath( "$", notNullValue() ) )
            .andExpect( jsonPath( "$.roles[0].code", notNullValue() ) );
            //TODO: finish
    }

    /**
     * Test for sign up a new user with invalid input.
     *
     * @throws Exception    General Exception of application.
     */
    @Test
    public void testSignupActionFailure() throws Exception {

        //- Mock service -//
        when( this.roleService.findByCodes( anyList() ) ).thenReturn( RoleMock.findByCodes() );
        when( this.userService.create( any( User.class ) ) ).thenReturn( UserMock.create() );
        when( this.passwordEncoder.encodePassword( anyString(), any() ) ).thenReturn( "Te$t" );

        //- Do Sign Up request -//
        this.mockMvc.perform(
            post("/security/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{" +
                        "\"username\": \"unit#test.com\", " +
                        "\"password\": \"Te$t\"" +
                    "}"
                )
        )
            .andExpect( status().isBadRequest() );
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
