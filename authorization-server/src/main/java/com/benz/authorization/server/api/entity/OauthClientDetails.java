package com.benz.authorization.server.api.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "OAUTH_CLIENT_DETAILS", schema = "SECURITYDB")
@Getter
@Setter
public class OauthClientDetails {

    @Id
    @Column(name = "CLIENT_ID")
    private String clientId;
    @Column(name = "CLIENT_SECRET")
    private String clientSecret;
    @Column(name = "WEB_SERVER_REDIRECT_URI")
    private String webServerRedirectUri;
    @Column(name = "SCOPE")
    private String scope;
    @Column(name = "ACCESS_TOKEN_VALIDITY")
    private Long accessTokenValidity;
    @Column(name = "REFRESH_TOKEN_VALIDITY")
    private Long refreshTokenValidity;
    @Column(name = "RESOURCE_IDS")
    private String resourceIds;
    @Column(name = "AUTHORIZED_GRANT_TYPES")
    private String authorizedGrantTypes;
    @Column(name = "AUTHORITIES")
    private String authorities;
    @Column(name = "ADDITIONAL_INFORMATION")
    private String additionalInformation;
    @Column(name = "AUTOAPPROVE")
    private String autoapprove;

}
