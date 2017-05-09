/**
 * Copyright (c) 2014-2016 by Coffeine Inc
 *
 * @author <a href = "mailto:vitaliy.tsutsman@musician-virtuoso.com>Vitaliy Tsutsman</a>
 *
 * @date 12/5/15 2:06 PM
 */

package com.thecoffeine.auth.model.service;


import com.thecoffeine.auth.model.entity.User;

import java.util.List;

/**
 * Service for work with user.
 *
 * @version 1.0
 */
public interface UserService {

    //- SECTION :: MAIN -//
    /**
     * Find user by email and hash of password.
     *
     * @param username    Username of user(e-mail).
     * @param password    Hash of password.
     *
     * @return User
     */
    User findByUsernameAndPassword( String username, String password );

    /**
     * Find user by email.
     *
     * @param username    Username of user(e-mail).
     *
     * @return User
     */
    User findByUsername( String username );

    /**
     * Find user by social id.
     *
     * @param socialId    Id from social network.
     *
     * @return User.
     */
    User findBySocialId( Long socialId );

    /**
     * Find all.
     *
     * @param page  Requested page.
     * @param limit Count items per page.
     *
     * @return List of users.
     */
    List<User> findAll( int page, int limit );

    /**
     * Create a new user.
     *
     * @param user    User entity.
     *
     * @return User
     */
    User create( User user );

    /**
     * Find.
     *
     * @param id Id of user.
     *
     * @return User
     */
    User find( Long id );

    /**
     * Update user.
     *
     * @param user    User entity.
     *
     * @return User
     */
    User update( User user );

    /**
     * Delete user.
     *
     * @param id    Id of user.
     */
    void delete( Long id );
}
