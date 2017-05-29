package com.thecoffeine.auth.model.matcher;

import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

/**
 * CSRF request matcher.
 *
 * @version 1.0
 */
public class CsrfSecurityRequestMatcher implements RequestMatcher {

    private RegexRequestMatcher matcher = new RegexRequestMatcher( "/login", null );

    /**
     * Decides whether the rule implemented by the strategy matches the supplied request.
     *
     * @param request the request to check for a match.
     *
     * @return true if the request matches, false otherwise.
     */
    @Override
    public boolean matches( HttpServletRequest request ) {

        return this.matcher.matches( request );
    }
}
