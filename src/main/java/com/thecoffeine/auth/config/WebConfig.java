package com.thecoffeine.auth.config;

import com.github.jknack.handlebars.springmvc.HandlebarsViewResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;

/**
 * Web configuration.
 *
 * @version 1..0
 */
@Configuration
public class WebConfig {

    /**
     * View resolver.
     *
     * @return ViewResolver
     */
    @Bean
    public ViewResolver viewResolver() {
        final HandlebarsViewResolver viewResolver = new HandlebarsViewResolver();
        //- Set path to views -//
        viewResolver.setPrefix( "classpath:security/templates/" );
        //- Set extension -//
        viewResolver.setSuffix( ".hbs" );

        return viewResolver;
    }
}
