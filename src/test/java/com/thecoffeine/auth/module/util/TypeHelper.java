/**
 * Copyright (c) 2014-2016 by Coffeine Inc
 *
 * @author <a href = "mailto:vitaliy.tsutsman@musician-virtuoso.com>Vitaliy Tsutsman</a>
 *
 * @date 5/7/16 4:22 PM
 */

package com.thecoffeine.auth.module.util;

import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Helper for checking type resources.
 */
public class TypeHelper {

    /**
     * Check response for types resources.
     *
     * @param responseActions    Actions.
     * @param code               Code.
     * @param title              Title.
     * @param description        Description.
     *
     * @throws Exception    Generall exception of application.
     */
    public static void check(
        ResultActions responseActions,
        String code,
        String title,
        String description
    ) throws Exception {
        responseActions
            .andExpect( jsonPath( "$", notNullValue() ) )
            .andExpect( jsonPath( "$", hasSize( 1 ) ) )
            .andExpect( jsonPath( "$[*].id", notNullValue() ) )
            .andExpect( jsonPath( "$[*].code", notNullValue() ) )
            .andExpect( jsonPath( "$[*].code", containsInAnyOrder( code ) ) )
            .andExpect( jsonPath( "$[*].title", notNullValue() ) )
            .andExpect( jsonPath( "$[*].title", containsInAnyOrder( title) ) )
            .andExpect( jsonPath( "$[*].description", notNullValue() ) )
            .andExpect( jsonPath( "$[*].description", containsInAnyOrder( description ) ) );
    }
}
