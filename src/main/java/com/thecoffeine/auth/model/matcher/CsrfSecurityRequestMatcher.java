package com.thecoffeine.auth.model.matcher;

import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;

/**
 * CSRF request matcher.
 *
 * @version 1.0
 */
public class CsrfSecurityRequestMatcher implements RequestMatcher {

    // Allow HTTP GET to load pages.
    private Pattern allowedMethods = Pattern.compile("^POST$");

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

        return this.allowedMethods.matcher( request.getMethod() ).matches()
            && this.matcher.matches( request );
    }
}
