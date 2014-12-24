package com.olegchir.streamscanner.init;
/**
 * Copyright (C) 2014 Oleg Chirukhin
 * Licensed under the Apache License 2.0,
 * see LICENSE-2.0.txt, LICENSE (it's a copy of LICENSE-2.0.txt) and NOTICE for additional information.
 */


import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by olegchir on 25.12.14.
 * https://gist.github.com/pgrimard/8241644
 * http://stackoverflow.com/questions/5009650/where-can-i-find-a-java-servlet-filter-that-applies-regex-to-the-output
 */
public final class CsrfTokenFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //Inspired by Spring documentation:
        //http://spring.io/blog/2013/08/21/spring-security-3-2-0-rc1-highlights-csrf-protection/
        //http://docs.spring.io/spring-security/site/docs/3.2.0.CI-SNAPSHOT/reference/html/csrf.html
        //And this discussion about Tymeleaf:
        //http://stackoverflow.com/questions/23669424/cant-create-csrf-token-with-spring-security

        //Set HTTP Headers
        CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
        // Spring Security will allow the Token to be included in this header name
        response.setHeader("X-CSRF-HEADER", token.getHeaderName());
        // Spring Security will allow the token to be included in this parameter name
        response.setHeader("X-CSRF-PARAM", token.getParameterName());
        // this is the value of the token to be included as either a header or an HTTP parameter
        response.setHeader("X-CSRF-TOKEN", token.getToken());

        //Modify HTML
        // Wrap the response in a wrapper so we can get at the text after calling the next filter
        PrintWriter out = response.getWriter();
        CharResponseWrapper wrapper = new CharResponseWrapper((HttpServletResponse) response);
        filterChain.doFilter(request, wrapper);
        String modifiedHtml = wrapper.toString(); // Extract the text from the completed servlet and apply the regexes
        modifiedHtml = modifiedHtml.replace("${_csrf.token}", token.getToken());
        modifiedHtml = modifiedHtml.replace("${_csrf.parameterName}",token.getParameterName());
        modifiedHtml = modifiedHtml.replace("${_csrf.headerName}",token.getHeaderName());
        // Write our modified text to the real response
        response.setContentLength(modifiedHtml.getBytes().length);
        out.write(modifiedHtml);
        out.close();
    }
}
