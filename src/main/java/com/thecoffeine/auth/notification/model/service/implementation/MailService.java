/**
 * Copyright (c) 2014-2016 by Coffeine Inc
 *
 * @author <a href = "mailto:vitaliy.tsutsman@musician-virtuoso.com>Vitaliy Tsutsman</a>
 *
 * @date 12/3/15 11:51 PM
 */

package com.thecoffeine.auth.notification.model.service.implementation;


import com.thecoffeine.auth.notification.model.entity.Contact;
import com.thecoffeine.auth.notification.model.entity.Message;
import com.thecoffeine.auth.notification.model.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

/**
 * E-mail implementation of notification service.
 * Send e-mails.
 *
 * @version 1.1
 *
 * @see NotificationService
 */
@Service
public class MailService implements NotificationService {

    /// *** Properties  *** ///
    /**
     * Provider for sending e-mails.
     */
    @Autowired
    private JavaMailSender sender;


    /// *** Methods     *** ///
    /**
     * Send message.
     *
     * @param from    Contact of person who writes message
     * @param to      Contact of destination.
     * @param message Message for sending.
     */
    @Override
    public void send( Contact from, Contact to, Message message ) {

        //- Create e-mail -//
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            //- Set e-mail content -//
            mimeMessage.setHeader( "Content-Type", "text/plain; charset=UTF-8" );
            mimeMessage.setRecipients( javax.mail.Message.RecipientType.TO, to.getAddress() );
            mimeMessage.setSubject( message.getSubject(), "UTF-8" );
            mimeMessage.setContent( message.getText(), "text/plain" );
        };

        this.sender.send( messagePreparator );
    }
}
