/**
 * Copyright (c) 2015 by Coffeine Inc
 *
 * @author Vitaliy Tsutsman <vitaliyacm&#64;gmail.com>
 *
 * @date 12/7/15 1:25 PM
 */

package com.thecoffeine.auth.security.model.entity;

import com.thecoffeine.auth.model.entity.Role;
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
 * Tests for Role.
 *
 * @version 1.0
 * @see Role
 */
public class RoleTest extends AbstractModel {

    /**
     * Test field validation for entity filled correct.
     */
    @Test
    public void testRoleFieldsSuccess() {

        Set<ConstraintViolation<Role>> constraintViolationSet;

        //- Success -//
        //- Create entity -//
        Role roleSuccess = new Role(
            "COMPOSER",
            "Composer",
            "Composer role"
        );

        //- Validate -//
        constraintViolationSet = validator.validate( roleSuccess );

        assertEquals( 0, constraintViolationSet.size() );
    }

    /**
     * Test field validation for entity filled incorrect.
     */
    @Test
    public void testVideoTypeFieldsFailure() {

        Set<ConstraintViolation<Role>> constraintViolationSet;

        //- Failure -//
        //- Create entity -//
        Role roleFailure = new Role(
            null,
            null,
            "Composer role"
        );

        //- Validate -//
        constraintViolationSet = validator.validate( roleFailure );

        assertEquals( 4, constraintViolationSet.size() );
        for ( ConstraintViolation<Role> constraintViolation : constraintViolationSet ) {
            //- Property name -//
            assertTrue(
                new ArrayList<String>() {{
                    add( "code" );
                    add( "title" );
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

        //- Failure: Incorrect length -//
        //- Create entity -//
        Role roleFailureLength = new Role(
            "123456789",
            "123456789012345678901234567890123",
            null
        );

        //- Validate -//
        constraintViolationSet = validator.validate( roleFailureLength );

        assertEquals( 2, constraintViolationSet.size() );

        for ( ConstraintViolation<Role> constraintViolation : constraintViolationSet ) {
            //- Property name -//
            assertTrue(
                new ArrayList<String>() {{
                    add( "code" );
                    add( "title" );
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
                    add( "length must be between 0 and 8" );
                    add( "length must be between 0 and 32" );
                }}.contains( constraintViolation.getMessage() )
            );
        }
    }

    /*
    * Test field validation for entity failure(empty).
    */
    @Test
    public void testRoleFieldEmpty() {

        Set<ConstraintViolation<Role>> constraintViolationSet;

        //- Failure: fields is empty-//
        //- Create entity -//
        Role roleFailureEmpty = new Role(
            "",
            "",
            null
        );

        //- Validate -//
        constraintViolationSet = validator.validate( roleFailureEmpty );

        assertEquals( 2, constraintViolationSet.size() );

        for ( ConstraintViolation<Role> constraintViolation : constraintViolationSet ) {
            //- Property name -//
            assertTrue(
                new ArrayList<String>() {{
                    add( "code" );
                    add( "title" );
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
