package com.olegchir.wicket_spring_security_example.init;
/**
 * Copyright (C) 2014 Oleg Chirukhin
 * Licensed under the Apache License 2.0,
 * see LICENSE-2.0.txt, LICENSE (it's a copy of LICENSE-2.0.txt) and NOTICE for additional information.
 */


/**
 * Created by olegchir on 25.12.14.
 * http://stackoverflow.com/questions/5009650/where-can-i-find-a-java-servlet-filter-that-applies-regex-to-the-output
 */

import java.io.CharArrayWriter;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * Created by olegchir on 25.12.14.
 * Wraps the response object to capture the text written to it.
 */
public class CharResponseWrapper extends HttpServletResponseWrapper {
    private CharArrayWriter output;

    public CharResponseWrapper(HttpServletResponse response) {
        super(response);
        this.output = new CharArrayWriter();
    }

    public String toString() {
        return output.toString();
    }

    public PrintWriter getWriter() {
        return new PrintWriter(output);
    }
}