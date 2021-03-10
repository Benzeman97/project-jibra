package com.benz.security.web.api.config;

import com.benz.security.web.api.security.AuthEntryPoint;
import com.benz.security.web.api.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;


@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService;
    private AuthEntryPoint authEntryPoint;

    public SecurityConfiguration(CustomUserDetailsService customUserDetailsService,@Qualifier("AuthEntryPoint") AuthEntryPoint authEntryPoint)
    {
        this.userDetailsService=customUserDetailsService;
        this.authEntryPoint=authEntryPoint;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       /* auth.inMemoryAuthentication()
                .withUser("benz")
                .password(passwordEncoder().encode("14292")).roles("USER")
                .and().withUser("kelly")
                .password(passwordEncoder().encode("1979")).roles("ADMIN");*/
       auth.authenticationProvider(authenticationProvider_1());
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      /*  http.authorizeRequests()
                 .antMatchers("/admin/**").hasRole("ADMIN")
                 .antMatchers("/user/**").hasAnyRole("USER","ADMIN")
                .antMatchers("/**").permitAll().and().formLogin();*/


     /*  http.csrf().disable().exceptionHandling()
               .and().authorizeRequests().anyRequest().permitAll().and().formLogin();
*/
       // use AuthEntryPoint with password and client_credentials flow

        http.csrf().disable().exceptionHandling().authenticationEntryPoint(authEntryPoint)
                .and().authorizeRequests().anyRequest().permitAll();

       //formLogin() is enabled for authorization_code flow
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    private AuthenticationProvider authenticationProvider_1()
    {
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }


}
