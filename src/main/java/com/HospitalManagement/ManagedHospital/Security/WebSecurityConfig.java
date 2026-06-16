package com.HospitalManagement.ManagedHospital.Security;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class WebSecurityConfig {

    private final PasswordEncoder passwordEncoder;
    private  final JwtAuthFilter jwtAuthFilter;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrfConfig-> csrfConfig.disable())
                .sessionManagement(sessionConfig->sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth-> auth
                                .requestMatchers("/patient/**").permitAll()
                                .requestMatchers("/swagger-ui/**",
                                        "/v3/api-docs/**",
                                        "/swagger-ui.html").permitAll()
                                .requestMatchers("/auth/sign-up","/auth/log-in").permitAll()
//                        .requestMatchers("/admin/**").authenticated()
//                        .requestMatchers("/admin/patients").hasRole("Admin")
//                        .requestMatchers("/admin/**").hasAnyRole("Doctor","Admin")
                                .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oauth2->oauth2
                        .failureHandler(
                        (request, response, exception) -> {
                            log.error("OAuth2 error: {}",exception.getMessage());
                        })
                        .successHandler(oAuth2SuccessHandler)
                );
//                .formLogin(form->form.permitAll());//this is browser

        return httpSecurity.build();
    }


//    @Bean
//    UserDetailsService userDetailsService(){
//        UserDetails user1 =User.withUsername("admin")
//                .password(passwordEncoder.encode("pass"))
//                .roles("Admin")
//                .build();
//

//        UserDetails user2=User.withUsername("patient")
//                .password(passwordEncoder.encode("pass"))
//                .roles("Patient")
//                .build();
//
//
//
//        return new void InMemoryUserDetailsManager(user1, user2);
//    }

}
