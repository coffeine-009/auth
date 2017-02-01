/**
 * Copyright (c) 2015 by Coffeine Inc
 *
 * @author Vitaliy Tsutsman <vitaliyacm&#64;gmail.com>
 *
 * @date 12/7/15 1:26 PM
 */

package com.thecoffeine.auth.security.model.persistence.mock;


import com.thecoffeine.auth.model.entity.Access;
import com.thecoffeine.auth.model.entity.Email;
import com.thecoffeine.auth.model.entity.Role;
import com.thecoffeine.auth.model.entity.User;

import java.util.ArrayList;

/**
 * Mock for persistence layout of users.
 *
 * @version 1.0
 * @see User
 */
public class UserMock {

    public static User create() {
        return new User(
            new ArrayList<Role>() {{
                add(
                    new Role("POET", "Poet", "Poet.")
                );
            }},
            new Access( "Te$t" ),
            new Email( "unit@test.com" ),
            "Unit",
            "Test",
            false,
            "en-US"
        );
    }
}
