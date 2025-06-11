package com.sodagift.biz.config.security;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;


public class BizAdminOAuth2User implements OAuth2User, Serializable {

    private Map<String, Object> attributes;

    public BizAdminOAuth2User(DefaultOAuth2User oAuth2User) {
        this.attributes = oAuth2User.getAttributes();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptySet();
    }

    @Override
    public String getName() {
        return Optional.ofNullable(attributes.get("email"))
                .map(Object::toString)
                .map(email -> email.split("@")[0])
                .orElse("Anonymous");
    }
}
