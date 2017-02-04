/**
 * Copyright (c) 2015 by Coffeine Inc
 *
 * @author Vitaliy Tsutsman <vitaliyacm&#64;gmail.com>
 *
 * @date 12/7/15 1:25 PM
 */

package com.thecoffeine.auth.security.model.entity;


import com.thecoffeine.auth.model.entity.Access;
import com.thecoffeine.auth.model.entity.Email;
import com.thecoffeine.auth.model.entity.Role;
import com.thecoffeine.auth.model.entity.User;
import com.thecoffeine.auth.module.model.AbstractModel;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.constraints.NotNull;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Tests for Access.
 *
 * @version 1.0
 * @see Access
 */
public class AccessTest extends AbstractModel {

    /**
     * Test field validation for entity correct.
     */
    @Test
    public void testFieldsSuccess() {

        Set<ConstraintViolation<Access>> constraintViolationSet;

        //- Success -//
        //- Create entity-//
        Access accessSuccess = new Access(
            new User(
                //- Roles -//
                new ArrayList<Role>() {{
                    add( new Role( "POET", "Poet" ) );
                }},
                //- Access -//
                new Access( "MyP@$$w0rd" ),
                //- Emails -//
                new Email( "myemail@virtuoso.com" ),
                "Tester",
                "Unit",
                "JUnit",
                "uk-UA"
            ),
            "MyP@$$w0rd"
        );

        //- Validate -//
        constraintViolationSet = validator.validate( accessSuccess );

        assertEquals( 0, constraintViolationSet.size() );
    }

    /*
    * Test field validation for entity failure
    */
    @Test
    public void testFieldsNotNullFailure() {

        Set<ConstraintViolation<Access>> constraintViolationSet;

        //- Failure: Incorrect user -//
        //- Create entity-//
        Access accessFailureUser = new Access( "London" );

        //- Validate access -//
        constraintViolationSet = validator.validate( accessFailureUser );

        assertEquals( 1, constraintViolationSet.size() );

        for ( ConstraintViolation<Access> constraintViolation : constraintViolationSet ) {

            //- Property name -//
            assertTrue(
                new ArrayList<String>() {{
                    add( "user" );
                }}.contains(
                    this.getPropertyName(
                        constraintViolation.getPropertyPath()
                    )
                )
            );

            //- Annotation type -//
            assertTrue(
                new ArrayList<Class>() {{
                    add( NotNull.class );
                }}.contains(
                    constraintViolation.getConstraintDescriptor().getAnnotation().annotationType()
                )
            );

            //- Message -//
            assertTrue(
                new ArrayList<String>() {{
                    add( "may not be null" );
                }}.contains(
                    constraintViolation.getMessage()
                )
            );
        }

        //- Failure: Incorrect password -//
        //- Create entity-//
        Access emailFailurePassword = new Access(
            new User(
                //- Roles -//
                new ArrayList<Role>() {{
                    add( new Role( "POET", "Poet" ) );
                }},
                //- Access -//
                new Access( "MyP@$$w0rd" ),
                //- Emails -//
                new Email( "myemail@virtuoso.com" ),
                "Tester",
                "Unit",
                "JUnit",
                "uk-UA"
            ),
            null
        );

        //- Validate emails address -//
        constraintViolationSet = validator.validate( emailFailurePassword );

        assertEquals( 2, constraintViolationSet.size() );

        for( ConstraintViolation<Access> constraintViolation : constraintViolationSet ) {

            //- Property name -//
            assertTrue(
                new ArrayList<String>() {{
                    add("password");
                }}.contains(
                    this.getPropertyName(
                        constraintViolation.getPropertyPath()
                    )
                )
            );

            //- Annotation type -//
            assertTrue(
                new ArrayList<Class>() {{
                    add( NotNull.class );
                    add( NotEmpty.class );
                }}.contains(
                    constraintViolation.getConstraintDescriptor().getAnnotation().annotationType()
                )
            );

            //- Message -//
            assertTrue(
                new ArrayList<String>() {{
                    add( "may not be null" );
                    add( "may not be empty" );
                }}.contains(
                    constraintViolation.getMessage()
                )
            );
        }

        //- Failure: Incorrect length -//
        //- Create entity -//
        Access accessFailureLength = new Access(
            new User(
                //- Roles -//
                new ArrayList<Role>() {{
                    add( new Role( "POET", "Poet" ) );
                }},
                //- Access -//
                new Access( "MyP@$$w0rd" ),
                //- Emails -//
                new Email( "myemail@virtuoso.com" ),
                "Tester",
                "Unit",
                "JUnit",
                "uk-UA"
            ),
            "123456789012345678901234567890123456789012345678901234567890" +
            "123456789012345678901234567890123456789012345678901234567890" +
            "123456789012345678901234567890123456789012345678901234567890" +
            "123456789012345678901234567890123456789012345678901234567890" +
            "12345678901234567"
        );

        //- Validate -//
        constraintViolationSet = validator.validate( accessFailureLength );

        assertEquals( 1, constraintViolationSet.size() );

        for ( ConstraintViolation<Access> constraintViolation : constraintViolationSet ) {

            //- Property name -//
            assertTrue(
                new ArrayList<String>() {{
                    add( "password" );
                }}.contains(
                    this.getPropertyName(
                        constraintViolation.getPropertyPath()
                    )
                )
            );

            //- Annotation type -//
            assertTrue(
                new ArrayList<Class>() {{
                    add( Length.class );
                }}.contains(
                    constraintViolation.getConstraintDescriptor().getAnnotation().annotationType()
                )
            );
            //- Message -//
            assertTrue(
                new ArrayList<String>() {{
                    add( "length must be between 0 and 80" );
                }}.contains(
                    constraintViolation.getMessage()
                )
            );
        }
    }

    /*
    * Test field validation for entity failure( empty )
    */
    @Test
    public void testFieldsEmptyFailure() {

        Set<ConstraintViolation<Access>> constraintViolationSet;

        //- Failure: fields is empty-//
        //- Create entity -//
        Access accessFailureEmpty = new Access(
            new User(
                //- Roles -//
                new ArrayList<Role>() {{
                    add( new Role( "POET", "Poet" ) );
                }},
                //- Access -//
                new Access( "MyP@$$w0rd" ),
                //- Emails -//
                new Email( "myemail@virtuoso.com" ),
                "Tester",
                "Unit",
                "JUnit",
                "uk-UA"
            ),
            ""
        );

        //- Validate -//
        constraintViolationSet = validator.validate( accessFailureEmpty );

        assertEquals( 1, constraintViolationSet.size() );

        for ( ConstraintViolation<Access> constraintViolation : constraintViolationSet ) {

            //- Property name -//
            assertTrue(
                new ArrayList<String>() {{
                    add( "password" );
                }}.contains(
                    this.getPropertyName(
                        constraintViolation.getPropertyPath()
                    )
                )
            );

            //- Annotation type -//
            assertTrue(
                new ArrayList<Class>() {{
                    add( NotEmpty.class );
                }}.contains(
                    constraintViolation.getConstraintDescriptor().getAnnotation().annotationType()
                )
            );

            //- Message -//
            assertTrue(
                new ArrayList<String>() {{
                    add( "may not be empty" );
                }}.contains(
                    constraintViolation.getMessage()
                )
            );
        }
    }
}
