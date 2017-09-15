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
    private final String fullName;

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
     * @param fullName           First & last names
     * @param countConstraints   Count of constraints for this case
     * @param propertyNames      List of names of properties with constraint error
     * @param annotationTypes    Annotation type that generate this constraint
     * @param messages           List of messages that can be generated
     */
    public RegistrationFormTest(
        String username,
        String password,
        String fullName,
        int countConstraints,
        List< String > propertyNames,
        List< Class > annotationTypes,
        List< String > messages
    ) {
        //- Initialization -//
        this.username = username;
        this.password = password;
        this.fullName = fullName;
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
            add( "fullName" );
        }};

        return Arrays.asList(
            new Object[][] {
                //- Success. Correct input. Single role -//
                {
                    "unit@test.com",
                    "te$t",
                    "Unit Test",
                    0,
                    new ArrayList< String >(),
                    new ArrayList< Class >(),
                    new ArrayList< String >()
                },
                //- Failure. incorrect input, bad email -//
                {
                    "unit#test.com",
                    "te$t",
                    "Unit test",
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
                    5,
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
                    3,
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
            this.fullName
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
