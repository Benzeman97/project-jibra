package com.benz.security.web.api.security;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties(SecurityProperties.class)
public class OAuth2AuthenticationProvider {


    private SecurityProperties securityProperties;

    public OAuth2AuthenticationProvider(SecurityProperties securityProperties)
    {
        this.securityProperties=securityProperties;
    }

    public OAuth2AccessToken obtainToken(String username,String password)
    {
        SecurityProperties.OAuth2Properties oAuth2Properties = securityProperties.getOauth2();

        ResourceOwnerPasswordResourceDetails resourceDetails = new ResourceOwnerPasswordResourceDetails();

        resourceDetails.setAccessTokenUri(oAuth2Properties.getAccessTokenUri());
        resourceDetails.setClientId(oAuth2Properties.getClientId());
        resourceDetails.setClientSecret(oAuth2Properties.getClientSecret());
        resourceDetails.setUsername(username);
        resourceDetails.setPassword(password);

        DefaultAccessTokenRequest accessTokenRequest=new DefaultAccessTokenRequest();

        OAuth2AccessToken accessToken=null;

        try {
            accessToken = new ResourceOwnerPasswordAccessTokenProvider().obtainAccessToken(resourceDetails, accessTokenRequest);
        }catch (OAuth2AccessDeniedException ex)
        {
            throw new BadCredentialsException("Invalid Credentials",ex);
        }
        return accessToken;
    }
}
