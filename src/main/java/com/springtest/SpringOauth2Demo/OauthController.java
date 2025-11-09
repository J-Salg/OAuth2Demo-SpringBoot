package com.springtest.SpringOauth2Demo;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OauthController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String greet(Model model,
                        @AuthenticationPrincipal OAuth2User principal,
                        OAuth2AuthenticationToken authentication){
        String name = null;
        String email = null;
        String provider = null;

        if (principal != null && principal.getAttributes() != null) {
            Object nameAttr = principal.getAttributes().get("name");
            Object loginAttr = principal.getAttributes().get("login");
            name = nameAttr != null ? nameAttr.toString() : (loginAttr != null ? loginAttr.toString() : principal.getName());

            Object emailAttr = principal.getAttributes().get("email");
            email = emailAttr != null ? emailAttr.toString() : null;
        }

        if (authentication != null) {
            provider = authentication.getAuthorizedClientRegistrationId();
        }

        model.addAttribute("name", name);
        model.addAttribute("email", email);
        model.addAttribute("provider", provider);

        return "home";
    }

}
