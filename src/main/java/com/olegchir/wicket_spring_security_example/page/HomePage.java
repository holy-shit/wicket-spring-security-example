package com.olegchir.wicket_spring_security_example.page;
/**
 * Copyright (C) 2014 Oleg Chirukhin
 * Licensed under the Apache License 2.0,
 * see LICENSE-2.0.txt, LICENSE (it's a copy of LICENSE-2.0.txt) and NOTICE for additional information.
 */

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebPage;

/**
 * Created by olegchir on 24.12.14.
 */

@SuppressWarnings("serial")
@AuthorizeInstantiation("ROLE_USER")
public class HomePage extends WebPage {
    public HomePage() {
        super();
    }
}