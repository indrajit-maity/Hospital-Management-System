package com.HospitalManagement.ManagedHospital.Security;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final AuthService authService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken token= (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User=(OAuth2User) authentication.getPrincipal();
//        String registrationId= token.getAuthorizedClientRegistrationId();
//        authService.handleOAuth2LoginRequest(oAuth2User,registrationId);
        Map<String,Object> attributes=oAuth2User.getAttributes();
        String email=(String) attributes.get("email");
        String name=(String) attributes.get("name");
        String picture=(String) attributes.get("picture");
        String googleId=(String) attributes.get("sub");
        System.out.println(email);
        System.out.println(name);
        System.out.println(googleId);
    }
}
