package com.olegchir.streamscanner.init;
/**
 * Copyright (C) 2014 Oleg Chirukhin
 * Licensed under the Apache License 2.0,
 * see LICENSE-2.0.txt, LICENSE (it's a copy of LICENSE-2.0.txt) and NOTICE for additional information.
 */

import org.apache.wicket.protocol.http.WicketFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import javax.servlet.*;
import java.util.EnumSet;

/**
 * Created by olegchir on 25.12.14.
 *
 * Servlet 3.0 initialization INSTEAD of web.xml, WebApplicationInitializer is a part of spring-web
 */
public class AppInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        //Create webapp context, register dispatchers in filter chain, attach it to current servlet context
        AnnotationConfigWebApplicationContext root = new AnnotationConfigWebApplicationContext(); //part of spring-web
        root.register(SpringSecurityConfiguration.class); //register class by annotation. Here be all security rules.
        FilterRegistration.Dynamic springSecurityFilterChainReg = servletContext.addFilter("springSecurityFilterChain", DelegatingFilterProxy.class);
        springSecurityFilterChainReg.addMappingForUrlPatterns(EnumSet.of(DispatcherType.ERROR, DispatcherType.REQUEST), false, "/*");
        servletContext.addListener(new ContextLoaderListener(root));

        WicketFilter wicketFilter = new WicketFilter(new WicketApplication()) {
            @Override
            public void init(boolean isServlet, FilterConfig filterConfig) throws ServletException {
                setFilterPath(""); //don't use web.xml. WicketApplication.init is a custom override for it.
                super.init(isServlet, filterConfig);
            }
        };
        FilterRegistration.Dynamic wicketFilterReg = servletContext.addFilter("wicketFilter", wicketFilter);
        wicketFilterReg.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "*");
    }
}