package com.benz.security.web.api.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;

@ConfigurationProperties(prefix = "security")
public class SecurityProperties {

    private JwtProperties jwt;
    private OAuth2Properties oauth2;


    public JwtProperties getJwt() {
        return jwt;
    }

    public void setJwt(JwtProperties jwt) {
        this.jwt = jwt;
    }

    public OAuth2Properties getOauth2() {
        return oauth2;
    }

    public void setOauth2(OAuth2Properties oauth2) {
        this.oauth2 = oauth2;
    }


    public static class JwtProperties{

        private Resource keyStore;
        private String keyStorePassword;
        private String keyPairAlias;
        private String keyPairPassword;

        public Resource getKeyStore() {
            return keyStore;
        }

        public String getKeyStorePassword() {
            return keyStorePassword;
        }

        public String getKeyPairAlias() {
            return keyPairAlias;
        }

        public String getKeyPairPassword() {
            return keyPairPassword;
        }

        public void setKeyStore(Resource keyStore) {
            this.keyStore = keyStore;
        }

        public void setKeyStorePassword(String keyStorePassword) {
            this.keyStorePassword = keyStorePassword;
        }

        public void setKeyPairAlias(String keyPairAlias) {
            this.keyPairAlias = keyPairAlias;
        }

        public void setKeyPairPassword(String keyPairPassword) {
            this.keyPairPassword = keyPairPassword;
        }
    }


    public static class OAuth2Properties{

        private String accessTokenUri;
        private String clientId;
        private String clientSecret;
        private String grantType;

        public String getAccessTokenUri() {
            return accessTokenUri;
        }

        public String getClientId() {
            return clientId;
        }

        public String getClientSecret() {
            return clientSecret;
        }

        public String getGrantType() {
            return grantType;
        }

        public void setAccessTokenUri(String accessTokenUri) {
            this.accessTokenUri = accessTokenUri;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public void setClientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
        }

        public void setGrantType(String grantType) {
            this.grantType = grantType;
        }
    }
}
