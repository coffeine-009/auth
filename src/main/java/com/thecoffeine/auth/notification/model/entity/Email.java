/**
 * Copyright (c) 2014-2016 by Coffeine Inc
 *
 * @author <a href = "mailto:vitaliy.tsutsman@musician-virtuoso.com>Vitaliy Tsutsman</a>
 *
 * @date 12/4/15 12:13 AM
 */

package com.thecoffeine.auth.notification.model.entity;

/**
 * E-mail message.
 *
 * @version 1.0
 */
public class Email implements Message {

    /**
     * Subject of e-mail.
     */
    private String subject;

    /**
     * Content(body) of e-mail.
     */
    private String text;


    /**
     * Create a new email.
     *
     * @param subject    Subject of message.
     * @param text       Body of message.
     */
    public Email( String subject, String text ) {
        //- Initialization -//
        this.subject = subject;
        this.text = text;
    }

    /**
     * Get subject of message.
     *
     * @return String
     */
    @Override
    public String getSubject() {
        return this.subject;
    }

    /**
     * Get content of message(body).
     *
     * @return String
     */
    @Override
    public String getText() {
        return this.text;
    }
}
