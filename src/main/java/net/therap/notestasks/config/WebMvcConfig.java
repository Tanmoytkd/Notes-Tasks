package net.therap.notestasks.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Locale;

/**
 * @author tanmoy.das
 * @since 4/12/20
 */
@Configuration
@EnableWebMvc
@ComponentScan({"net.therap.notestasks"})
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean(name = "viewResolver")
    public InternalResourceViewResolver createViewResolver() {
        InternalResourceViewResolver vr = new InternalResourceViewResolver();

        vr.setPrefix("/WEB-INF/views/");
        vr.setSuffix(".jsp");

        return vr;
    }

    @Bean(name = "messageSource")
    public ReloadableResourceBundleMessageSource createMessageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");

        return messageSource;
    }

    @Bean(name = "localeResolver")
    public CookieLocaleResolver createLocaleResolver() {
        CookieLocaleResolver localeResolver = new CookieLocaleResolver();

        localeResolver.setDefaultLocale(Locale.ENGLISH);
        localeResolver.setCookieName("myAppLocaleCookie");
        localeResolver.setCookieMaxAge(3600);

        return localeResolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("locale");

        registry.addInterceptor(localeChangeInterceptor);
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/lib/**").addResourceLocations("/lib/");
        registry.addResourceHandler("/js/**").addResourceLocations("/js/");
        registry.addResourceHandler("/css/**").addResourceLocations("/css/");
        registry.addResourceHandler("/img/**").addResourceLocations("/img/");
    }
}
