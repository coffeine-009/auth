/**
 * Copyright (c) 2014-2016 by Coffeine Inc
 *
 * @author <a href = "mailto:vitaliy.tsutsman@musician-virtuoso.com>Vitaliy Tsutsman</a>
 *
 * @date 12/4/15 12:05 AM
 */

package com.thecoffeine.auth.model.service;


import com.thecoffeine.auth.notification.model.entity.Contact;

import java.io.IOException;

/**
 * Service for recovery access to account.
 *
 * @version 1.0
 */
public interface AccessRecoveryService {

    /**
     * Make request loosing access to account.
     *
     * @param contact    Contact associated to account.
     *
     * @throws IOException  Cannot send via SMTP.
     */
    void lostAccess( Contact contact ) throws IOException;

    /**
     * Restore access.
     *
     * @param hash        One time hash.
     * @param password    New password.
     */
    void restore( String hash, String password );
}
