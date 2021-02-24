package com.benz.security.web.api.config;

import com.benz.security.web.api.security.SecurityProperties;
import com.benz.security.web.api.service.CustomUserDetailsService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.sql.DataSource;
import java.security.KeyPair;

@Configuration
@EnableAuthorizationServer
@EnableConfigurationProperties(SecurityProperties.class)
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private DataSource dataSource;
    private UserDetailsService userDetailsService;
    private SecurityProperties securityProperties;

    private TokenStore tokenStore;

    public AuthorizationServerConfig(PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager
    , DataSource dataSource,CustomUserDetailsService userDetailsService, SecurityProperties securityProperties)
    {
        this.passwordEncoder=passwordEncoder;
        this.authenticationManager=authenticationManager;
        this.dataSource=dataSource;
        this.userDetailsService=userDetailsService;
        this.securityProperties=securityProperties;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.checkTokenAccess("isAuthenticated()").tokenKeyAccess("permitAll()");

        //when you go with permitAll() then you don't have to provide client_id and client_secret
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

       /* clients.inMemory().withClient("web")
                .secret(passwordEncoder.encode("webadmin")).scopes("read","write")
                .authorizedGrantTypes("authorization_code","password","implicit","client_credentials","refresh_token");*/

       clients.jdbc(dataSource).passwordEncoder(passwordEncoder);

    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .accessTokenConverter(jwtAccessTokenConverter(securityProperties))
                .userDetailsService(userDetailsService).tokenStore(tokenStore());
    }

    @Bean
    public TokenStore tokenStore()
    {
         if(tokenStore==null)
             tokenStore = new JwtTokenStore(jwtAccessTokenConverter(securityProperties));
         return tokenStore;
    }


    @Bean
    public DefaultTokenServices defaultTokenServices(ClientDetailsService clientDetailsService)
    {
        DefaultTokenServices tokenServices=new DefaultTokenServices();
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setTokenStore(tokenStore);
        tokenServices.setClientDetailsService(clientDetailsService);
        tokenServices.setAuthenticationManager(authenticationManager);
        return tokenServices;
    }

    private JwtAccessTokenConverter jwtAccessTokenConverter(SecurityProperties securityProperties)
    {
        SecurityProperties.JwtProperties jwtProperties= securityProperties.getJwt();

        KeyPair keyPair= keyPair(jwtProperties,keyStoreKeyFactory(jwtProperties));

        JwtAccessTokenConverter jwtConverter = new JwtAccessTokenConverter();

        jwtConverter.setKeyPair(keyPair);

        return jwtConverter;
    }


    private KeyPair keyPair(SecurityProperties.JwtProperties jwtProperties, KeyStoreKeyFactory keyStoreKeyFactory)
    {
       return keyStoreKeyFactory.getKeyPair(jwtProperties.getKeyPairAlias(),jwtProperties.getKeyPairPassword().toCharArray());
    }

    private KeyStoreKeyFactory keyStoreKeyFactory(SecurityProperties.JwtProperties jwtProperties)
    {
        return new KeyStoreKeyFactory(jwtProperties.getKeyStore(),jwtProperties.getKeyStorePassword().toCharArray());
    }

}
