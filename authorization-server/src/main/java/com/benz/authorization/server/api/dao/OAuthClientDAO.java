package com.benz.authorization.server.api.dao;

import com.benz.authorization.server.api.entity.OauthClientDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OAuthClientDAO extends JpaRepository<OauthClientDetails, String> {

    Optional<OauthClientDetails> findByClientId(String clientId);
}
