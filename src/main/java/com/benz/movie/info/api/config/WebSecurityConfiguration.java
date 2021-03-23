package com.benz.movie.info.api.config;

import com.benz.movie.info.api.security.AuthEntryPoint;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private AuthEntryPoint authEntryPoint;

    public WebSecurityConfiguration(AuthEntryPoint authEntryPoint)
    {
        this.authEntryPoint=authEntryPoint;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().exceptionHandling().authenticationEntryPoint(authEntryPoint)
                .and().authorizeRequests().anyRequest().permitAll();
    }
}
