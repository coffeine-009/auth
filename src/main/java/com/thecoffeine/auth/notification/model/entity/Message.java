/**
 * Copyright (c) 2014-2016 by Coffeine Inc
 *
 * @author <a href = "mailto:vitaliy.tsutsman@musician-virtuoso.com>Vitaliy Tsutsman</a>
 *
 * @date 12/3/15 11:47 PM
 */

package com.thecoffeine.auth.notification.model.entity;

/**
 * Message for inform.
 *
 * @version 1.0
 */
public interface Message {

    /**
     * Get subject of message.
     *
     * @return String
     */
    String getSubject();

    /**
     * Get content of message(body).
     *
     * @return String
     */
    String getText();
}
