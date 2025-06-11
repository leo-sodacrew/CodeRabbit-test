package com.sodagift.biz.config.security;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class OAuth2Service extends DefaultOAuth2UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OAuth2Service.class);

    private static final String DOMAIN_ATTRIBUTE = "hd";
    private static final String SODACREW_DOMAIN = "sodacrew.com";

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        LOGGER.info("OAuth2UserRequest: {}", userRequest);
        DefaultOAuth2User oauthUser = (DefaultOAuth2User) super.loadUser(userRequest);

        if (isNotAllow(oauthUser)) {
            throw new BadCredentialsException("Allows only 'sodacrew.com' account");
        }

        return new BizAdminOAuth2User(oauthUser);
    }

    private boolean isNotAllow(DefaultOAuth2User oauthUser) {
        return !StringUtils.equals(oauthUser.<String>getAttribute(DOMAIN_ATTRIBUTE), SODACREW_DOMAIN);
    }

}
