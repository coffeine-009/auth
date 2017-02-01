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
 * Tests for User.
 *
 * @version 1.0
 * @see User
 */
public class UserTest extends AbstractModel {

    /*
     * Test field validation for entity correct.
     */
    @Test
    public void testUserFieldsSuccess() {

        Set<ConstraintViolation<User>> constraintViolationSet;

        //- Success -//
        //- Create entity-//
        User userSuccess = new User(
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
        );
        //- Validate -//
        constraintViolationSet = validator.validate( userSuccess );

        assertEquals( 0, constraintViolationSet.size() );
    }

    /*
     * Test field validation for entity failure.
     */
    @Test
    public void testUserFieldsFailure() {

        Set<ConstraintViolation<User>> constraintViolationSet;

        //- Failure -//
        //- Create entity-//
        User userFailure = new User(
            //- Roles-//
            new ArrayList<Role>() {{
               add( new Role( "POET", "Poet" ) );
            }},
            null,
            null
        );

        //- Validate -//
        constraintViolationSet = validator.validate( userFailure );

        assertEquals( 8, constraintViolationSet.size() );
        for ( ConstraintViolation<User> constraintViolation : constraintViolationSet ) {
            //- Property name -//
            assertTrue(
                new ArrayList<String>() {{
                    add("firstName");
                    add("locale");
                    add("access");
                    add("emails");
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
                }}.contains( constraintViolation.getMessage() )
            );
        }
        //--//
        User userFailureRoles = new User(
            //- Roles -//
            null,
            //- Access -//
            new Access( "MyP@$$w0rd" ),
            //- Emails -//
            new Email( "myemail@virtuoso.com" ),
            "Tester",
            "Unit",
            "JUnit",
            "uk-UA"
        );

        constraintViolationSet = validator.validate( userFailureRoles );

        assertEquals( 1, constraintViolationSet.size() );
        for ( ConstraintViolation<User> constraintViolation : constraintViolationSet ) {
            //- Property name -//
            assertTrue(
                new ArrayList<String>() {{
                    add( "roles" );
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
                }}.contains( constraintViolation.getMessage() )
            );
        }

        //- Failure: Incorrect length -//
        //- Create entity -//
        User userFailureLength = new User(
            //- Roles -//
            new ArrayList<Role>() {{
                add( new Role( "POET", "Poet" ) );
            }},
            //- Access -//
            new Access( "MyP@$$w0rd" ),
            //- Emails -//
            new Email( "myemail@virtuoso.com" ),
            "12345678901234567",
            "12345678901234567",
            "123456789012345678901234567890123",
            "123456"
        );

        //- Validate -//
        constraintViolationSet = validator.validate( userFailureLength );

        assertEquals( 4, constraintViolationSet.size() );

        for ( ConstraintViolation<User> constraintViolation : constraintViolationSet ) {
            //- Property name -//
            assertTrue(
                new ArrayList<String>() {{
                    add( "firstName" );
                    add( "lastName" );
                    add( "middleName" );
                    add( "locale" );
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
                    add( "length must be between 0 and 5" );
                    add( "length must be between 0 and 16" );
                    add( "length must be between 0 and 32" );
                }}.contains( constraintViolation.getMessage() )
            );
        }
    }

    /*
    * Test field validation for entity failure(empty).
    */
    @Test
    public void testUserFieldEmpty() {

        Set<ConstraintViolation<User>> constraintViolationSet;

        //- Failure: fields is empty-//
        //- Create entity -//
        User userFailureEmpty = new User(
            //- Roles -//
            new ArrayList<Role>() {{
                add( new Role( "POET", "Poet" ) );
            }},
            //- Access -//
            new Access( "MyP@$$word" ),
            //- Emails -//
            new Email( "user@virtuoso.com" ),
            "",
            "Unit",
            "Junit",
            ""
        );

        //- Validate -//
        constraintViolationSet = validator.validate( userFailureEmpty );

        assertEquals( 2, constraintViolationSet.size() );

        for ( ConstraintViolation<User> constraintViolation : constraintViolationSet ) {
            //- Property name -//
            assertTrue(
                new ArrayList<String>() {{
                    add( "firstName" );
                    add("locale");
                    add("access");
                    add("emails");
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
                }}.contains( constraintViolation.getMessage() )
            );
        }
    }
}
