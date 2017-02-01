/**
 * Copyright (c) 2014-2016 by Coffeine Inc
 *
 * @author <a href = "mailto:vitaliy.tsutsman@musician-virtuoso.com>Vitaliy Tsutsman</a>
 *
 * @date 12/3/15 11:40 PM
 */

package com.thecoffeine.auth.notification.model.service;


import com.thecoffeine.auth.notification.model.entity.Contact;
import com.thecoffeine.auth.notification.model.entity.Message;

/**
 * Service for work with e-mails.
 *
 * @version 1.0
 */
public interface NotificationService {

    /**
     * Send message.
     *
     * @param from       Contact of person who writes message
     * @param to         Contact of destination.
     * @param message    Message for sending.
     */
    void send( Contact from, Contact to, Message message );
}
