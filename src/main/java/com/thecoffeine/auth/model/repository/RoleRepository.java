/**
 * Copyright (c) 2014-2016 by Coffeine Inc
 *
 * @author <a href = "mailto:vitaliy.tsutsman@musician-virtuoso.com>Vitaliy Tsutsman</a>
 *
 * @date 12/5/15 2:05 PM
 */

package com.thecoffeine.auth.model.repository;


import com.thecoffeine.auth.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Interface for work with persistence layout.
 *
 * @version 1.0
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Find roles by codes.
     *
     * @param codes    List role's codes.
     *
     * @return List of roles.
     */
    @Query( "SELECT r FROM Role r WHERE r.code IN ( :codes )" )
    List<Role> findByCodes( @Param( "codes" ) List<String> codes );
}
