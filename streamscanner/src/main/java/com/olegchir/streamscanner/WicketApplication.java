package com.olegchir.streamscanner;
/**
 * Copyright (C) 2014 Oleg Chirukhin
 * Licensed under the Apache License 2.0,
 * see LICENSE-2.0.txt, LICENSE (it's a copy of LICENSE-2.0.txt) and NOTICE for additional information.
 */
import org.apache.wicket.protocol.http.WebApplication;

/**
 * Created by olegchir on 24.12.14.
 */
public class WicketApplication extends WebApplication {
    public WicketApplication() {
    }

    /**
     * @see org.apache.wicket.Application#getHomePage()
     */
    @Override
    public Class getHomePage() {
        return IndexPage.class;
    }
}