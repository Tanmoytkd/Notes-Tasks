package net.therap.notestasks.config;

import net.therap.notestasks.web.filter.AuthFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

/**
 * @author tanmoy.das
 * @since 4/12/20
 */
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{
                AppConfig.class,
                WebMvcConfig.class
        };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }

    @Override
    protected Filter[] getServletFilters() {

        return new Filter[]{
                new AuthFilter()
        };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

}
