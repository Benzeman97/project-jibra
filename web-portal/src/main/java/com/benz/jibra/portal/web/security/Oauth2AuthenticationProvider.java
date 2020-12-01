package com.benz.jibra.portal.web.security;

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
public class Oauth2AuthenticationProvider {

    private SecurityProperties securityProperties;

    public Oauth2AuthenticationProvider(SecurityProperties securityProperties)
    {
        this.securityProperties=securityProperties;
    }

    public OAuth2AccessToken obtainToken(String username, String password) {

        SecurityProperties.OAuth2Properies oAuthDetails=securityProperties.getOauth2();

        ResourceOwnerPasswordResourceDetails resourceDetails = new ResourceOwnerPasswordResourceDetails();
        resourceDetails.setAccessTokenUri(oAuthDetails.getAccessTokenUri());
        resourceDetails.setClientId(oAuthDetails.getClientId());
        resourceDetails.setClientSecret(oAuthDetails.getClientSecret());
        resourceDetails.setGrantType(oAuthDetails.getGrantType());
        resourceDetails.setUsername(username);
        resourceDetails.setPassword(password);
        DefaultAccessTokenRequest defaultAccessTokenRequest = new DefaultAccessTokenRequest();

        OAuth2AccessToken token;

        try {
            token = new ResourceOwnerPasswordAccessTokenProvider().obtainAccessToken(resourceDetails, defaultAccessTokenRequest);
        } catch (OAuth2AccessDeniedException accessDeniedException) {
            throw new BadCredentialsException("Invalid credentials", accessDeniedException);
        }

        return token;

    }

    //implement another method for refresh token

}
