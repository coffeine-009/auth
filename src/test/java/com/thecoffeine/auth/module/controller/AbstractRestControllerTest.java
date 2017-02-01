/**
 * Copyright (c) 2014-2016 by Coffeine Inc
 *
 * @author <a href = "mailto:vitaliy.tsutsman@musician-virtuoso.com>Vitaliy Tsutsman</a>
 *
 * @date 9/21/14 6:27 PM
 */

package com.thecoffeine.auth.module.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Date;

import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * Abstract test for rest controllers.
 *
 * @version 1.0
 */
@SqlGroup({
    @Sql( executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:init.sql" ),
    @Sql( executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:clean.sql" )
})
public abstract class AbstractRestControllerTest extends AbstractControllerTest {

    private static Token token;

    /**
     * Mocked session
     */
    protected Token session = new Token();

    /**
     * Authorization data.
     * Used for doing private requests.
     */
    protected static class Token {

        @JsonProperty( "token_type" )
        private String type;

        private String scope;

        @JsonProperty( "expires_in" )
        private Date expiresIn;

        @JsonProperty( "access_token" )
        private String accessToken;

        @JsonProperty( "refresh_token" )
        private String refreshToken;


        public String getType() {
            return type;
        }

        public String getScope() {
            return scope;
        }

        public Date getExpiresIn() {
            return expiresIn;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public String getRefreshToken() {
            return refreshToken;
        }

        public String getAuthorizationHeader() {
            return this.type.substring(0, 1).toUpperCase()
                + this.type.substring(1) + " " + this.accessToken;
        }


        public void setType( String type ) {
            this.type = type;
        }

        public void setScope( String scope ) {
            this.scope = scope;
        }

        public void setExpiresIn( Date expiresIn ) {
            this.expiresIn = expiresIn;
        }

        public void setAccessToken( String accessToken ) {
            this.accessToken = accessToken;
        }

        public void setRefreshToken( String refreshToken ) {
            this.refreshToken = refreshToken;
        }
    }


    /// *** Methods     *** ///
    /**
     * Prepare environment to run tests.
     */
    @Override
    public void tearUp() {

        //- Authorize user -//
        if (null == token) {
            authorization();
            token = this.session;
        } else {
            this.session = token;
        }

        //- Init mocks -//
        MockitoAnnotations.initMocks( this );
    }

    /**
     * Helper for authorization of user for testing.
     * FIXME: investigate how to mock session.
     */
    private void authorization() {
        //- Authorize user for tests -//
        try {
            //- Success -//
            final MvcResult result = this.mockMvc.perform(
                post( "/oauth/token" )
                    .contentType( MediaType.APPLICATION_FORM_URLENCODED )
                    .header(
                        "Authorization",
                        "Basic " + new String(
                            Base64.encodeBase64(
                                "developer:developer32".getBytes()
                            )
                        )
                    )
                    .param( "grant_type", "password" )
                    .param( "scope", "read" )
                    .param( "clientId", "developer" )
                    .param( "clientSecret", "developer32" )
                    .param( "username", "user@virtuoso.com" )
                    .param( "password", "123" )
            )
                .andReturn();

            this.session = new ObjectMapper().readValue(
                result.getResponse().getContentAsByteArray(),
                Token.class
            );

        } catch ( Exception e ) {
            fail( "Cannot pass authorization. " + e.getMessage() );
        }
    }
}
