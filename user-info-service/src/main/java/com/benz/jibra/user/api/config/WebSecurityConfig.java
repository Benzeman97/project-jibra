package com.benz.jibra.user.api.config;

import com.benz.jibra.user.api.security.AuthEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private AuthEntryPoint authEntryPoint;

    public WebSecurityConfig(AuthEntryPoint authEntryPoint)
    {
        this.authEntryPoint=authEntryPoint;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().exceptionHandling().authenticationEntryPoint(authEntryPoint)
                .and().authorizeRequests().anyRequest().permitAll();
    }
}
