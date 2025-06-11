package com.sodagift.biz.user.resource;

import com.sodagift.biz.config.security.BizAdminOAuth2User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/user")
public class UserResource {

    @GetMapping
    public UserOnlyForLogin getUser(@AuthenticationPrincipal BizAdminOAuth2User user) {
        return new UserOnlyForLogin("test");
    }
}
