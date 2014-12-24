package com.olegchir.streamscanner.init;
/**
 * Copyright (C) 2014 Oleg Chirukhin
 * Licensed under the Apache License 2.0,
 * see LICENSE-2.0.txt, LICENSE (it's a copy of LICENSE-2.0.txt) and NOTICE for additional information.
 */


/**
 * Created by olegchir on 25.12.14.
 */
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Created by olegchir on 25.12.14.
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .addFilterAfter(new CsrfTokenFilter(), CsrfFilter.class)
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
            .logout()
                .deleteCookies("remove")
                .invalidateHttpSession(true)
                .logoutUrl("/logout")
                .logoutSuccessUrl("/logout_success")
                //http://stackoverflow.com/questions/24108585/spring-security-java-config-not-generating-logout-url
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .and()
            .authorizeRequests()
                .antMatchers("/favicon.ico").permitAll()
                .antMatchers("/logout_success").permitAll()
                .antMatchers("/**").hasRole("USER");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("testuser")
                .password("password")
                .roles("USER");
    }
}
