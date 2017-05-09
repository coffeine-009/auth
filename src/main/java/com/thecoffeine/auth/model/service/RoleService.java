/**
 * Copyright (c) 2014-2016 by Coffeine Inc
 *
 * @author <a href = "mailto:vitaliy.tsutsman@musician-virtuoso.com>Vitaliy Tsutsman</a>
 *
 * @date 12/5/15 2:06 PM
 */

package com.thecoffeine.auth.model.service;


import com.thecoffeine.auth.model.entity.Role;

import java.util.List;

/**
 * Interface for work with persistence layout.
 *
 * @version 1.0
 */
public interface RoleService {

    /**
     * Find roles by codes.
     *
     * @param codes    List of role's codes.
     *
     * @return List of roles.
     */
    List<Role> findByCodes( List<String> codes );
}
