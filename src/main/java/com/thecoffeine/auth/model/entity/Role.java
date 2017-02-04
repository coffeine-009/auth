/**
 * Copyright (c) 2014-2016 by Coffeine Inc
 *
 * @author <a href = "mailto:vitaliy.tsutsman@musician-virtuoso.com>Vitaliy Tsutsman</a>
 *
 * @date 12/5/15 11:49 AM
 */

package com.thecoffeine.auth.model.entity;

import com.google.common.base.MoreObjects;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

/**
 * Class for reflect table role from persistence layout.
 *
 * @version 1.0
 */
@SuppressWarnings( "serial" )
@Entity
@Table(
    name = "role",
    uniqueConstraints = @UniqueConstraint(
        columnNames = {
            "code"
        }
    )
)
public class Role implements Serializable {

    /// *** Properties  *** ///
    /**
     * Id of role.
     * Primary key.
     */
    @Id
    @GeneratedValue
    @Column
    protected Long id;

    /**
     * Code of role.
     * Title/short_alias of role in upper case
     */
    @NotNull
    @NotEmpty
    @Length( max = 8 )
    @Column( length = 8 )
    protected String code;

    /**
     * Title of role.
     * For display as default value.
     */
    @NotNull
    @NotEmpty
    @Length( max = 32 )
    @Column( length = 32 )
    protected String title;

    /**
     * Description of role.
     */
    @Column( length = 128 )
    protected String description;


    /// *** Methods     *** ///
    /**
     * Default constructor.
     */
    public Role() {

    }

    /**
     * Constructor for create temporary role.
     *
     * @param code    Code of role in UPPER case.
     * @param title   Title of role.
     */
    public Role(
        String code,
        String title
    ) {
        //- Initialization -//
        this.code = code;
        this.title = title;
    }

    /**
     * Constructor for create a new role.
     *
     * @param code           Code Code of role in UPPER case.
     * @param title          Title of role.
     * @param description    Description of role.
     */
    public Role(
        String code,
        String title,
        String description
    ) {
        //- Initialization -//
        this.code = code;
        this.title = title;
        this.description = description;
    }


    //- SECTION :: GET -//
    /**
     * Get ID of song.
     *
     * @return Long ID of song
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Get code of role.
     *
     * @return String code.
     */
    public String getCode() {
        return code;
    }

    /**
     * Get title of role.
     *
     * @return String title.
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Get description about role.
     *
     * @return String description.
     */
    public String getDescription() {
        return description;
    }


    //- SECTION :: SET -//
    /**
     * Set ID of song.
     *
     * @param id ID of song.
     */
    public void setId( Long id ) {
        this.id = id;
    }

    /**
     * Set code of role.
     * Ex. ADMIN, MANAGER, USER
     *
     * @param code Code of role in UPPER case.
     */
    public void setCode( String code ) {
        this.code = code;
    }

    /**
     * Set title of role.
     *
     * @param title Title of role.
     */
    public void setTitle( String title ) {
        this.title = title;
    }

    /**
     * Set description of role.
     *
     * @param description Description of role.
     */
    public void setDescription( String description ) {
        this.description = description;
    }


    @Override
    public int hashCode() {
        return Objects.hash( id, code, title, description );
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null || getClass() != obj.getClass() ) {
            return false;
        }
        final Role other = (Role) obj;
        return Objects.equals( this.id, other.id )
            && Objects.equals( this.code, other.code )
            && Objects.equals( this.title, other.title )
            && Objects.equals( this.description, other.description );
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper( this )
            .add( "id", id )
            .add( "code", code )
            .add( "title", title )
            .add( "description", description )
            .toString();
    }
}
