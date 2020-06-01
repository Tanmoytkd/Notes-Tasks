package net.therap.notestasks.config;

import net.therap.notestasks.web.filter.AuthFilter;
import net.therap.notestasks.web.filter.CustomSiteMeshFilter;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
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
        return new Class[]{};
    }

    @Override
    protected Filter[] getServletFilters() {
        AuthFilter authFilter = new AuthFilter();

        OpenEntityManagerInViewFilter openEntityManagerInViewFilter = new OpenEntityManagerInViewFilter();
        openEntityManagerInViewFilter.setEntityManagerFactoryBeanName("entityManagerFactory");

        CustomSiteMeshFilter siteMeshFilter = new CustomSiteMeshFilter();

        return new Filter[]{
                authFilter,
                openEntityManagerInViewFilter,
                siteMeshFilter
        };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

}
