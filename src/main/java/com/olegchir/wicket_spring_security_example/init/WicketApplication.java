package com.olegchir.wicket_spring_security_example.init;
/**
 * Copyright (C) 2014 Oleg Chirukhin
 * Licensed under the Apache License 2.0,
 * see LICENSE-2.0.txt, LICENSE (it's a copy of LICENSE-2.0.txt) and NOTICE for additional information.
 */
import com.olegchir.wicket_spring_security_example.page.HomePage;
import com.olegchir.wicket_spring_security_example.page.LoginPage;
import com.olegchir.wicket_spring_security_example.page.LogoutSuccessPage;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AnnotationsRoleAuthorizationStrategy;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

/**
 * Created by olegchir on 24.12.14.
 */
public class WicketApplication extends AuthenticatedWebApplication  {
    public WicketApplication() {
    }

    /**
     * @see org.apache.wicket.Application#getHomePage()
     */
    @Override
    public Class getHomePage() {
        return HomePage.class;
    }

    /**
     * @see org.apache.wicket.Application#init()
     */
    @Override
    public void init() {
        super.init();
        getComponentInstantiationListeners().add(new SpringComponentInjector(this));
        getSecuritySettings().setAuthorizationStrategy(new AnnotationsRoleAuthorizationStrategy(this));
        mountPage("/home", HomePage.class);
        mountPage("/login", LoginPage.class);
        mountPage("/logout_success", LogoutSuccessPage.class);
    }

    @Override
    protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
        return UserAuthenticatedWebSession.class;
    }

    @Override
    protected Class<? extends WebPage> getSignInPageClass() {
        return LoginPage.class;
    }
}