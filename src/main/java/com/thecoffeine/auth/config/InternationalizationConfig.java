package com.thecoffeine.auth.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;

/**
 * Internationalization configuration.
 *
 * @version 1.0
 */
@Configuration
public class InternationalizationConfig extends WebMvcConfigurerAdapter {

    /**
     * Locale resolver.
     * Detecting locale.
     *
     * @return LocaleResolver
     */
    @Bean
    public LocaleResolver localeResolver() {
        final AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
        //- Set default locale -//
        localeResolver.setDefaultLocale( Locale.US );

        return localeResolver;
    }

    /**
     * Manual change locale interceptor.
     *
     * @return LocaleChangeInterceptor
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        final LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        //- Set request param for changing locale -//
        localeChangeInterceptor.setParamName( "lang" );

        return localeChangeInterceptor;
    }

    /**
     * Define message source.
     *
     * @return MessageSource
     */
    @Bean
    public MessageSource messageSource() {
        final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        //- Base name -//
        messageSource.setBasenames( "classpath:i18n/messages" );
        //- File encoding -//
        messageSource.setDefaultEncoding( "UTF-8" );

        return messageSource;
    }

    /**
     * Add locale change interceptor.
     *
     * @param registry    Interceptor registry.
     */
    @Override
    public void addInterceptors( InterceptorRegistry registry ) {
        registry.addInterceptor( localeChangeInterceptor() );
    }
}
