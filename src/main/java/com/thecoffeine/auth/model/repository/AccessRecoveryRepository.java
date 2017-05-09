/**
 * Copyright (c) 2014-2016 by Coffeine Inc
 *
 * @author <a href = "mailto:vitaliy.tsutsman@musician-virtuoso.com>Vitaliy Tsutsman</a>
 *
 * @date 12/5/15 4:11 PM
 */

package com.thecoffeine.auth.model.repository;


import com.thecoffeine.auth.model.entity.RecoveryAccess;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for recovery access.
 *
 * @version 1.0
 */
public interface AccessRecoveryRepository extends JpaRepository<RecoveryAccess, Long> {

    /**
     * Find by hash.
     *
     * @param hash    One time hash.
     *
     * @return RecoveryAccess.
     */
    RecoveryAccess findByHash( String hash );
}
