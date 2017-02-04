/**
 * Copyright (c) 2014-2016 by Coffeine Inc
 *
 * @author <a href = "mailto:vitaliy.tsutsman@musician-virtuoso.com>Vitaliy Tsutsman</a>
 *
 * @date 3/13/16 5:07 PM
 */

package com.thecoffeine.auth.module;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Base class for functional tests.
 *
 * @version 1.0
 */
@ActiveProfiles( "tests" )
@RunWith( SpringRunner.class )
@AutoConfigureRestDocs( "build/generated-snippets" )
@AutoConfigureMockMvc
@SpringBootTest
public abstract class AbstractTest {
    /// *** Properties  *** ///
    /**
     * MVC mock use for test with out real data base.
     */
    @Autowired
    protected MockMvc mockMvc;


    /// *** Methods     *** ///
    /**
     * Prepare environment to run tests.
     */
    public abstract void tearUp();

    /**
     * Clean environment.
     */
    public abstract void tearDown();
}
