/**
 * Copyright (c) 2014-2016 by Coffeine Inc
 *
 * @author <a href = "mailto:vitaliy.tsutsman@musician-virtuoso.com>Vitaliy Tsutsman</a>
 *
 * @date 12/5/15 2:07 PM
 */


package com.thecoffeine.auth.model.service.implementation;


import com.github.jknack.handlebars.Context;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.thecoffeine.auth.model.entity.User;
import com.thecoffeine.auth.model.repository.UserRepository;
import com.thecoffeine.auth.model.service.UserService;
import com.thecoffeine.auth.notification.model.entity.Email;
import com.thecoffeine.auth.notification.model.entity.EmailAddress;
import com.thecoffeine.auth.notification.model.service.NotificationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import javax.transaction.Transactional;

import static org.springframework.util.Assert.notNull;

/**
 * Implementation of user service.
 *
 * @version 1.0
 */
@Transactional
@Service
public class UserServiceImpl implements UserService {

    /// *** Constants   *** ///
    /**
     * Logger.
     */
    private static final Logger log = LogManager.getLogger( UserServiceImpl.class );


    /// *** Properties  *** ///
    //- SECTION :: REPOSITORIES -//
    /**
     * Repository for users.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Source of localized messages.
     */
    @Autowired
    private MessageSource messageSource;

    /**
     * Manager of templates.
     */
    @Autowired
    private Handlebars templateManager;

    /**
     * Service for work with notifications.
     */
    @Autowired
    private NotificationService notificationService;


    /// *** Methods     *** ///
    //- SECTION :: MAIN -//
    /**
     * Find user by email and hash of password.
     *
     * @param username    Username(e-mail).
     * @param password    Hash of password.
     * @return boolean true - user is exist, false - not exist.
     */
    @Override
    public User findByUsernameAndPassword(
        String username,
        String password
    ) {
        return this.userRepository.findByUsernameAndPassword(
            username,
            password
        );
    }

    /**
     * Find user by email.
     *
     * @param username Username of user(e-mail).
     *
     * @return User
     */
    @Override
    public User findByUsername( String username ) {
        return this.userRepository.findByUsername( username );
    }

    /**
     * Find user by social id.
     *
     * @param socialId Id from social network.
     * @return User.
     */
    @Override
    public User findBySocialId( Long socialId ) {
        return this.userRepository.findBySocialId( socialId );
    }

    /**
     * Find all.
     *
     * @param page  Requested page.
     * @param limit Count items per page.
     * @return List of users.
     */
    public List<User> findAll( int page, int limit ) {
        return this.userRepository.findAll(
            new PageRequest(
                page,
                limit
            )
        )
            .getContent();
    }

    /**
     * Create a new user.
     *
     * @param user    User object for creating.
     *
     * @return Created user.
     */
    @Override
    public User create( User user ) {
        //- Save user to persistence -//
        final User newUser = this.userRepository.save(user);

        //- Check created user -//
        notNull( newUser, "Cannot save user." );

        //- Send notification -//
        try {
            //- Prepare content -//
            Template template = this.templateManager.compile( "security.signup" );

            Context context = Context
                .newBuilder( newUser )
                .combine( "locale", LocaleContextHolder.getLocale().toLanguageTag() )
                .build();

            //FIXME: get email more safely
            //- Send notification-//
            this.notificationService.send(
                new EmailAddress( "support@musician-virtuoso.com" ),
                new EmailAddress( newUser.getEmails().get( 0 ).getAddress() ),
                new Email(
                    this.messageSource.getMessage(
                        "notification.security.signup.subject",
                        null,
                        LocaleContextHolder.getLocale()
                    ),
                    template.apply( context )
                )
            );
        } catch ( IOException e ) {
            //- Error. Cannot send notification -//
            log.error( "Cannot prepare or send notification.", e );
        }

        return newUser;
    }

    /**
     * Find.
     *
     * @param id Id of user.
     *
     * @return User.
     */
    public User find( Long id ) {
        return this.userRepository.findOne( id );
    }

    /**
     * Update user.
     *
     * @param user    User object for update.
     *
     * @return Updated user.
     */
    @Override
    public User update( User user ) {
        //- Save user to persistence -//
        return this.userRepository.save( user );
    }

    /**
     * Delete user.
     *
     * @param id    Id of user.
     */
    @Override
    public void delete( Long id ) {
        this.userRepository.delete( id );
    }
}
