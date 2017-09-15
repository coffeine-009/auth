package com.thecoffeine.auth.controllers;

import com.thecoffeine.auth.model.entity.Access;
import com.thecoffeine.auth.model.entity.Email;
import com.thecoffeine.auth.model.entity.User;
import com.thecoffeine.auth.model.service.UserService;
import com.thecoffeine.auth.view.form.RegistrationForm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Security Controller.
 *
 * @version 1.0
 */
@Controller
public class SecurityController {

    /**
     * Logger.
     */
    private static final Logger log = LogManager.getLogger( SecurityController.class );

    /**
     * Marker for actions.
     */
    private static final Marker ACTION_MARKER = MarkerManager.getMarker( "ACTION" );

    //- SECTION :: CRYPTOGRAPHY -//
    /**
     * Encoder for create hash of password.
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Service for work with users.
     */
    @Autowired
    private UserService userService;


    /**
     * Display LogIn form.
     *
     * @param request   HTTP request.
     * @param model     View's model.
     *
     * @return View name.
     */
    @RequestMapping( value = "/login" )
    public String login( HttpServletRequest request, Model model ) {
        //- Set params for view -//
        model.addAttribute( "_csrf", request.getAttribute( "_csrf" ) );
        model.addAttribute( "error", request.getParameterMap().containsKey( "error" ) );

        return "login";
    }

    /**
     * Handle Sign Up.
     *
     * @param request HTTP request.
     *
     * @return View name.
     */
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signUp(
        @Valid
        @ModelAttribute
        RegistrationForm form,

        HttpServletRequest request,
        Model model
    ) {

        //- Log action -//
        log.info( ACTION_MARKER, "Sign Up" );

        try {
            String[] names = form.getFullName().split( " " );
            //- Create new user -//
            User newUser = new User(
                //- Add access params -//
                new Access( this.passwordEncoder.encode( form.getPassword() ) ),
                //- Add e-mail -//
                new Email( form.getUsername() ),
                //- User info -//
                names[ 0 ],
                names.length == 2 ? names[ 1 ] : null,
                //- User's locale -//
                request.getLocale()
            );

            //- Persist -//
            this.userService.create( newUser );

            //- Success -//
            return "redirect:/signup/success?id=" + newUser.getId() + "&username=" + form.getUsername();
        } catch ( DataIntegrityViolationException | IllegalArgumentException e ) {
            //- Cannot save this data -//
            //- Set params for view -//
            model.addAttribute( "_csrf", request.getAttribute( "_csrf" ) );
            model.addAttribute( "isSignUp", true );
            model.addAttribute( "fullName", form.getFullName() );
            model.addAttribute( "username", form.getUsername() );
            model.addAttribute( "duplicate", true );

            log.warn( "Attempt to register duplicate.", e );
        }

        return "login";
    }

    @RequestMapping( value = "/signup/success", method = RequestMethod.GET )
    public String signUpSuccessAction(
        @RequestParam("id") Long id,
        @RequestParam("username") String username,

        HttpServletRequest request,
        Model model
    ) {
        //- Log action -//
        log.info( ACTION_MARKER, "Sign Up success. Id: {}", id );

        //- Set params for view -//
        model.addAttribute( "id", id );
        model.addAttribute( "username", username );
        model.addAttribute( "referer", request.getHeader( "Referer" ) );

        return "sign-up.success";
    }
}
