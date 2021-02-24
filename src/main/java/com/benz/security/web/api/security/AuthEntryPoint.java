package com.benz.security.web.api.security;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component("AuthEntryPoint")
public class AuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException e) throws IOException, ServletException {

        final Map<String,Object> exceptions = new ConcurrentHashMap<>();

        exceptions.put("errorCode", HttpStatus.UNAUTHORIZED.value());
        exceptions.put("errorMsg",HttpStatus.UNAUTHORIZED.getReasonPhrase());
        exceptions.put("documentation","www.benz.com");

        res.setContentType("application/json");
        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        new ObjectMapper().writeValue(res.getOutputStream(),exceptions);

    }
}
