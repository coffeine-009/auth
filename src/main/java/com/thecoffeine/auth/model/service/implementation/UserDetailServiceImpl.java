package com.thecoffeine.auth.model.service.implementation;

import com.thecoffeine.auth.model.entity.User;
import com.thecoffeine.auth.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.util.Assert.notNull;

/**
 * UserDetailService.
 *
 * @version 1.0
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private UserRepository userRepository;


    @Autowired
    public UserDetailServiceImpl( UserRepository userRepository ) {
        this.userRepository = userRepository;
    }

    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */
    @Override
    public UserDetails loadUserByUsername( String username ) throws UsernameNotFoundException {

        try {
            final User user = this.userRepository.findByUsername( username );

            //- Check if user exists -//
            notNull( user, "User not found." );

            //- Set authorities -//
            List<GrantedAuthority> authorities = new ArrayList<>();

            //- Populate roles into authentication object -//
            user.getRoles().forEach(
                role -> authorities.add(
                    new SimpleGrantedAuthority(
                        role.getCode()
                    )
                )
            );

            return new org.springframework.security.core.userdetails.User(
                username,
                user.getAccess().stream().findFirst().get().getPassword(),
                authorities
            );
        } catch ( IllegalArgumentException e ) {
            throw new UsernameNotFoundException(e.getMessage(), e);//FIXME: Localize message
        }
    }
}
