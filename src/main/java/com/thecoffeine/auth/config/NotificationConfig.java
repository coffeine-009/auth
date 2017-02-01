package com.thecoffeine.auth.config;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Notification configuration.
 *
 * @version 1.0
 */
@Configuration
public class NotificationConfig {

    /**
     * Template loader.
     *
     * @return TemplateLoader
     */
    @Bean
    public TemplateLoader templateLoader() {
        return new ClassPathTemplateLoader(
            "/notifications/templates",
            ".hbs"
        );
    }

    /**
     * Handlebars manager.
     *
     * @return Handlebars
     */
    @Bean
    public Handlebars handlebars() {
        return new Handlebars( templateLoader() );
    }
}
