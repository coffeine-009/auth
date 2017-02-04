/**
 * Copyright (c) 2014-2016 by Coffeine Inc
 *
 * @author <a href = "mailto:vitaliy.tsutsman@musician-virtuoso.com>Vitaliy Tsutsman</a>
 *
 * @date 11/30/15 11:14 PM
 */

package com.thecoffeine.auth.security.view.form;

import com.thecoffeine.auth.library.validator.anotation.Event;
import com.thecoffeine.auth.library.validator.anotation.InEnum;
import com.thecoffeine.auth.module.model.AbstractModel;

import com.thecoffeine.auth.view.form.RegistrationForm;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test of RegistrationForm.
 * @see RegistrationForm
 *
 * @version 1.0
 */
@RunWith( Parameterized.class )
public class RegistrationFormTest extends AbstractModel {

    /// *** Properties  *** ///
    //- Assumptions -//
    /**
     * Username(e-mail) for register
     */
    private final String username;

    /**
     * Password
     */
    private final String password;

    /**
     * First name
     */
    private final String firstName;

    /**
     * Last name
     */
    private final String lastName;

    /**
     * Gender of user.
     * true - man, false - woman
     */
    private final Boolean gender;

    /**
     * Locale of user.
     * e.g. en-US
     */
    private final String locale;

    /**
     * Birthday
     */
    private final LocalDate birthday;

    /**
     * Day of death if it is known
     */
    private final LocalDate deathDay;

    /**
     * List of roles for assign to user
     */
    private final List<String> roles;

    //- Expected results -//
    /**
     * Count of constraints
     */
    private final int countConstraints;

    /**
     * List of properties that contains constraints
     */
    private final List<String> propertyNames;

    /**
     * List of constraint annotations that were involved
     */
    private final List<Class> annotationTypes;

    /**
     * List of messages that were generated
     */
    private final List<String> messages;


    /// *** Methods     *** ///
    /**
     * Constructor for create parametrized test.
     *
     * @param username           Username - e-mail
     * @param password           Password of user
     * @param firstName          First name
     * @param lastName           Last name
     * @param gender             Gender
     * @param locale             Locale(e.g. en-US)
     * @param roles              List of roles
     * @param birthday           Birthday
     * @param deathDay           Day of death if it is known
     * @param countConstraints   Count of constraints for this case
     * @param propertyNames      List of names of properties with constraint error
     * @param annotationTypes    Annotation type that generate this constraint
     * @param messages           List of messages that can be generated
     */
    public RegistrationFormTest(
        String username,
        String password,
        String firstName,
        String lastName,
        Boolean gender,
        String locale,
        LocalDate birthday,
        LocalDate deathDay,
        List< String > roles,
        int countConstraints,
        List< String > propertyNames,
        List< Class > annotationTypes,
        List< String > messages
    ) {
        //- Initialization -//
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.locale = locale;
        this.birthday = birthday;
        this.deathDay = deathDay;
        this.roles = roles;
        this.countConstraints = countConstraints;
        this.propertyNames = propertyNames;
        this.annotationTypes = annotationTypes;
        this.messages = messages;
    }


    /**
     * Parameters for each case.
     *
     * @return Collection of parameters
     */
    @Parameterized.Parameters
    public static Collection inputCases() {

        //- Assumptions -//
        List< String > fieldNames = new ArrayList< String >() {{
            add( "username" );
            add( "password" );
            add( "firstName" );
            add( "lastName" );
            add( "gender" );
            add( "locale" );
            add( "roles" );
            add( "birthday" );
            add( "deathDate" );
        }};

        return Arrays.asList(
            new Object[][] {
                //- Success. Correct input. Single role -//
                {
                    "unit@test.com",
                    "te$t",
                    "Unit",
                    "Test",
                    false,
                    "en-US",
                    LocalDate.now().minusYears( 1 ),
                    LocalDate.now().plusYears( 90 ),
                    new ArrayList< String >() {{
                        add( "STUDENT" );
                    }},
                    0,
                    new ArrayList< String >(),
                    new ArrayList< Class >(),
                    new ArrayList< String >()
                },
                //- Success. Correct input. Multiple roles -//
                {
                    "unit@test.com",
                    "te$t",
                    "Unit",
                    "Test",
                    true,
                    "en-US",
                    LocalDate.now().minusYears( 1 ),
                    LocalDate.now().plusYears( 90 ),
                    new ArrayList< String >() {{
                        add( "STUDENT" );
                        add( "POET" );
                        add( "COMPOSER" );
                    }},
                    0,
                    new ArrayList< String >(),
                    new ArrayList< Class >(),
                    new ArrayList< String >()
                },
                //- Failure. incorrect input, bad email -//
                {
                    "unit#test.com",
                    "te$t",
                    "Unit",
                    "Test",
                    false,
                    "en-US",
                    LocalDate.now().minusYears( 1 ),
                    LocalDate.now().plusYears( 90 ),
                    new ArrayList< String >() {{
                        add( "STUDENT" );
                    }},
                    1,
                    new ArrayList< String >() {{
                        add( "username" );
                    }},
                    new ArrayList< Class >() {{
                        add( Email.class );
                    }},
                    new ArrayList< String >() {{
                        add( "not a well-formed email address" );
                    }}
                },
                //- Failure. incorrect input, empty form -//
                {
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    15,
                    fieldNames,
                    new ArrayList< Class >() {{
                        add( NotNull.class );
                        add( NotEmpty.class );
                        add( Valid.class );
                        add( InEnum.class );
                        add( Event.class );
                    }},
                    new ArrayList< String >() {{
                        add( "may not be null" );
                        add( "may not be empty" );
                        add( "Value is not valid" );
                        add( "{javax.validation.constraints.Event.message}" );
                    }}
                },
                //- Failure. incorrect input, bad filled form -//
                {
                    "",
                    "",
                    "",
                    "",
                    false,
                    "",
                    LocalDate.now().minusYears( 1 ),
                    null,
                    new ArrayList< String >(),
                    6,
                    fieldNames,
                    new ArrayList< Class >() {{
                        add( NotEmpty.class );
                        add( Valid.class );
                        add( InEnum.class );
                    }},
                    new ArrayList< String >() {{
                        add( "may not be null" );
                        add( "may not be empty" );
                        add( "Value is not valid" );
                    }}
                },
                //- Failure. incorrect input, bad roles -//
                {
                    "unit@test.com",
                    "Te$t",
                    "Unit",
                    "Test",
                    false,
                    "en-US",
                    LocalDate.now().minusYears( 1 ),
                    null,
                    new ArrayList< String >() {{
                        add( "UNIT_TEST" );
                    }},
                    1,
                    fieldNames,
                    new ArrayList< Class >() {{
                        add( Valid.class );
                        add( InEnum.class );
                    }},
                    new ArrayList< String >() {{
                        add( "Value is not valid" );
                    }}
                },
                //- Failure. incorrect input, bad day of death -//
                {
                    "unit@test.com",
                    "Te$t",
                    "Unit",
                    "Test",
                    false,
                    "en-US",
                    LocalDate.now().plusYears( 1 ),
                    LocalDate.now(),
                    new ArrayList< String >() {{
                        add( "POET" );
                    }},
                    1,
                    new ArrayList< String >() {{
                        add( "deathDate" );
                    }},
                    new ArrayList< Class >() {{
                        add( Event.class );
                    }},
                    new ArrayList< String >() {{
                        add( "{javax.validation.constraints.Event.message}" );
                    }}
                },
                //- Failure. incorrect input, bad day of death -//
                {
                    "unit@test.com",
                    "Te$t",
                    "Unit",
                    "Test",
                    false,
                    "en-US",
                    LocalDate.now().minusYears( 100 ),
                    LocalDate.now().minusYears( 101 ),
                    new ArrayList< String >() {{
                        add( "POET" );
                    }},
                    1,
                    new ArrayList< String >() {{
                        add( "deathDate" );
                    }},
                    new ArrayList< Class >() {{
                        add( Event.class );
                    }},
                    new ArrayList< String >() {{
                        add( "{javax.validation.constraints.Event.message}" );
                    }}
                },
                //- Failure. incorrect input, bad roles -//
                {
                    "unit@test.com",
                    "Te$t",
                    "Unit",
                    "Test",
                    false,
                    "en-US",
                    LocalDate.now().minusYears( 1 ),
                    LocalDate.now().plusYears( 90 ),
                    new ArrayList< String >() {{
                        add( "TEST_UNIT" );
                        add( "STUDENT" );
                    }},
                    1,
                    new ArrayList< String >() {{
                        add( "roles" );
                    }},
                    new ArrayList< Class >() {{
                        add( InEnum.class );
                    }},
                    new ArrayList< String >() {{
                        add( "Value is not valid" );
                    }}
                }
            }
        );
    }


    /**
     * Test of input(filled form).
     */
    @Test
    public void testRequiredFieldsFailure() {
        //- Set of constraints after validation -//
        Set< ConstraintViolation<RegistrationForm> > constraintViolationSet;

        //- Evaluation -//
        RegistrationForm form = new RegistrationForm(
            this.username,
            this.password,
            this.firstName,
            this.lastName,
            this.gender,
            this.roles,
            this.locale,
            this.birthday,
            this.deathDay
        );

        //- Validate -//
        constraintViolationSet = validator.validate( form );

        //- Assertions -//
        assertEquals( this.countConstraints, constraintViolationSet.size() );
        for ( ConstraintViolation< RegistrationForm > constraintViolation : constraintViolationSet ) {
            //- Property name -//
            assertTrue(
                this.propertyNames.contains(
                    this.getPropertyName(
                        constraintViolation.getPropertyPath()
                    )
                )
            );
            //- Annotation type -//
            assertTrue(
                this.annotationTypes.contains(
                    constraintViolation.getConstraintDescriptor().getAnnotation().annotationType()
                )
            );
            //- Message -//
            assertTrue( this.messages.contains( constraintViolation.getMessage() ) );
        }
    }
}
