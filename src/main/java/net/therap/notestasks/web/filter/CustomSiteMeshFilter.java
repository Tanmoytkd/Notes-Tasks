package net.therap.notestasks.web.filter;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;

/**
 * @author tanmoy.das
 * @since 4/29/20
 */
public class CustomSiteMeshFilter extends ConfigurableSiteMeshFilter {
    @Override
    protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
        // Assigning default decorator if no path specific decorator found
//        builder.addDecoratorPath("/*", "/WEB-INF/sitemesh/DefaultDecorator.jsp");
    }
}