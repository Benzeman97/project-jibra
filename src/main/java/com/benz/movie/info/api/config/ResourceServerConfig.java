package com.benz.movie.info.api.config;

import com.benz.movie.info.api.security.SecurityProperties;
import org.apache.commons.io.IOUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;

//@Configuration
//@EnableResourceServer
//@EnableConfigurationProperties(SecurityProperties.class)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private SecurityProperties securityProperties;

    private TokenStore tokenStore;

    public ResourceServerConfig(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @Bean
    public DefaultTokenServices tokenServices(TokenStore tokenStore) {

        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore);
        return tokenServices;
    }

    @Bean
    public TokenStore tokenStore() throws IOException {
        if (tokenStore == null)
            tokenStore = new JwtTokenStore(tokenConverter());
        return tokenStore;
    }

    @Bean
    public JwtAccessTokenConverter tokenConverter() throws IOException {
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        accessTokenConverter.setVerifierKey(getPublicKeyAsString());
        return accessTokenConverter;

    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(tokenStore);
        resources.resourceId("movie-info-service");
    }

    private String getPublicKeyAsString() throws IOException {
        return IOUtils.toString(securityProperties.getJwt().getPublicKey().getInputStream(), UTF_8);
    }

}
