/**
 * Copyright (c) 2014-2016 by Coffeine Inc
 *
 * @author <a href = "mailto:vitaliy.tsutsman@musician-virtuoso.com>Vitaliy Tsutsman</a>
 *
 * @date 12/4/15 12:12 AM
 */

package com.thecoffeine.auth.model.service.implementation;


import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.thecoffeine.auth.model.entity.Access;
import com.thecoffeine.auth.model.entity.RecoveryAccess;
import com.thecoffeine.auth.model.entity.User;
import com.thecoffeine.auth.model.repository.AccessRecoveryRepository;
import com.thecoffeine.auth.model.service.AccessRecoveryService;
import com.thecoffeine.auth.model.service.UserService;
import com.thecoffeine.auth.notification.model.entity.Contact;
import com.thecoffeine.auth.notification.model.entity.Email;
import com.thecoffeine.auth.notification.model.entity.EmailAddress;
import com.thecoffeine.auth.notification.model.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;
import javax.transaction.Transactional;

import static org.springframework.util.Assert.notNull;

/**
 * Implementation of service for recovery access to account.
 *
 * @version 1.0
 * @see AccessRecoveryService
 */
@Transactional
@Service
public class AccessRecoveryServiceImpl implements AccessRecoveryService {

    /**
     * Encoder for create hash of password.
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Handlebars templateManager;

    @Autowired
    private MessageSource messageSource;

    /**
     * Service for work with users.
     */
    @Autowired
    private UserService userService;

    /**
     * Service for sending notifications.
     */
    @Autowired
    private NotificationService notificationService;

    /**
     * Repository for work with access recovery.
     */
    @Autowired
    private AccessRecoveryRepository accessRecoveryRepository;


    /**
     * Make request loosing access to account.
     *
     * @param contact Contact associated to account.
     *
     * @throws IOException  Cannot send via SMTP.
     */
    @Override
    public void lostAccess( Contact contact ) throws IOException {
        //- Create one-time link for recovery access -//
        //- Search user -//
        User user = this.userService.findByUsername( contact.getAddress() );

        //- Check user -//
        notNull( user );

        //- Generate one-time hash -//
        String hash = this.passwordEncoder.encode( UUID.randomUUID().toString() );

        //- Add one-time hash for recovery access -//
        this.accessRecoveryRepository.save(
            new RecoveryAccess(
                user,
                hash,
                OffsetDateTime.now().plusMinutes( 15L )
            )
        );

        //- Prepare content -//
        Template template = this.templateManager.compile( "access-recovery" );

        //- Send notification-//
        this.notificationService.send(
            new EmailAddress( "system@virtuoso.com" ),
            contact,
            new Email(
                this.messageSource.getMessage(
                    "notification.access-recovery.subject",
                    null,
                    LocaleContextHolder.getLocale()
                ),
                template.apply( hash )
            )
        );
    }

    /**
     * Restore access.
     *
     * @param hash     One time hash.
     * @param password New password.
     */
    @Override
    public void restore( String hash, String password ) {
        //- Search request for recovering access -//
        RecoveryAccess recoveryAccess = this.accessRecoveryRepository.findByHash( hash );

        //- Check if request was -//
        notNull( recoveryAccess );

        //- Get user -//
        User user = recoveryAccess.getUser();

        //- Get access -//
        Set<Access> access = user.getAccess();

        //- Update access params -//
        access.iterator().next().setPassword(
            this.passwordEncoder.encode( password )
        );

        //- Save changes -//
        this.userService.update( user );

        //- Delete request for recovering -//
        this.accessRecoveryRepository.delete( recoveryAccess );
    }
}
