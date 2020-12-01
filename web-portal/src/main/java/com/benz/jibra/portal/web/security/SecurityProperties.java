package com.benz.jibra.portal.web.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "security")
public class SecurityProperties {


    private OAuth2Properies oauth2;

    public OAuth2Properies getOauth2() {
        return oauth2;
    }

    public void setOauth2(OAuth2Properies oauth2) {
        this.oauth2 = oauth2;
    }

    public static class OAuth2Properies{

        private String accessTokenUri;
        private String clientId;
        private String clientSecret;
        private String grantType;

        public String getAccessTokenUri() {
            return accessTokenUri;
        }

        public void setAccessTokenUri(String accessTokenUri) {
            this.accessTokenUri = accessTokenUri;
        }

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getClientSecret() {
            return clientSecret;
        }

        public void setClientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
        }

        public String getGrantType() {
            return grantType;
        }

        public void setGrantType(String grantType) {
            this.grantType = grantType;
        }
    }
}
