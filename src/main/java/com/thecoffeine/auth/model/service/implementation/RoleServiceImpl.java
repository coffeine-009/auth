/**
 * Copyright (c) 2014-2016 by Coffeine Inc
 *
 * @author <a href = "mailto:vitaliy.tsutsman@musician-virtuoso.com>Vitaliy Tsutsman</a>
 *
 * @date 12/5/15 2:07 PM
 */

package com.thecoffeine.auth.model.service.implementation;


import com.thecoffeine.auth.model.entity.Role;
import com.thecoffeine.auth.model.repository.RoleRepository;
import com.thecoffeine.auth.model.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import javax.transaction.Transactional;

/**
 * Interface for work with persistence layout.
 *
 * @version 1.0
 */
@Transactional
@Service
public class RoleServiceImpl implements RoleService {

    /// *** Properties  *** ///
    @Autowired
    protected RoleRepository roleRepository;


    /// *** Methods     *** ///
    /**
     * Find roles by codes.
     *
     * @param codes    List of role codes.
     *
     * @return List of Roles.
     */
    public List<Role> findByCodes( List<String> codes ) {
        return this.roleRepository.findByCodes( codes );
    }
}
