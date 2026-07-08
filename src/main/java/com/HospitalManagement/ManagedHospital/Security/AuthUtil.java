package com.HospitalManagement.ManagedHospital.Security;


import com.HospitalManagement.ManagedHospital.entity.User;
import com.HospitalManagement.ManagedHospital.entity.type.AuthProviderType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@Slf4j
public class AuthUtil {

//JWT SECRETKEY
    @Value("${jwt.secretKey}")
    private  String jwtSecretKey;


//    JWT HEADER
    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }


//    JWT PAYLOAD
    public String generateAccessToken(User user){
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("userId",user.getId().toString())
                .claim("email",user.getEmail())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000*60*10))
                .signWith(getSecretKey())
                .compact();
    }


    public String getUsernameFromToken(String token) {
        Claims claims=Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return  claims.getSubject();
    }




    public AuthProviderType getProviderTypeFromRegistrationId(String registartionId){
        return switch(registartionId.toLowerCase()){
            case "google"->AuthProviderType.GOOGLE;
            case "github"->AuthProviderType.GITHUB;
            case "facebook"->AuthProviderType.FACEBOOK;
            default -> throw new IllegalArgumentException("Unsupported OAuth2 provider: "+registartionId);
        };
    }

    public String determineProviderIdFromOAuth2User(OAuth2User oAuth2User,String registrationId){
        String providerId=switch (registrationId.toLowerCase()){
            case "google"->oAuth2User.getAttribute("sub");
            case "github"->oAuth2User.getAttribute("id").toString();
            default -> {
                log.error("Unsupported OAuth2Provider: {}",registrationId);
                throw new IllegalArgumentException("Unsupported Oauth2Provider: "+registrationId);
            }
        };
        if(providerId==null||providerId.isBlank()){
            log.error("Unable to determine providerid for provider: {}",registrationId);
            throw  new IllegalArgumentException("Unable to determine providerid for provider: "+registrationId);
        }
        return providerId;
    }

    public String determineUsernameFromOAuth2User(OAuth2User oAuth2User,String registrationId,String providerId){
//        String email=oAuth2User.getAttribute("email");
        String userName=oAuth2User.getAttribute("name");
        if(userName!=null&& !userName.isBlank()){
            return userName;
        }
        return switch (registrationId.toLowerCase()){
            case "google"->oAuth2User.getAttribute("sub");
            case "github"->oAuth2User.getAttribute("login");
            default -> providerId;
        };
    }


    public String determineEmailFromOAuth2User(OAuth2User oAuth2User, String registrationId, String providerId) {
        String email=oAuth2User.getAttribute("email");
        if(email!=null && !email.isBlank()){
            return email;
        }
        return switch (registrationId.toLowerCase()){
            case "google"->oAuth2User.getAttribute("sub");
            case "github"->oAuth2User.getAttribute("login");
            default -> providerId;
        };
    }
}
