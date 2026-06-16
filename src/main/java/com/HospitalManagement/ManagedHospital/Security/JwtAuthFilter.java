package com.HospitalManagement.ManagedHospital.Security;

import com.HospitalManagement.ManagedHospital.entity.User;
import com.HospitalManagement.ManagedHospital.repositry.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Optional;


@Component
@Slf4j //login framwork form lombok
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private  final UserRepository userRepository;
    private final AuthUtil authUtil;
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            log.info("incoming request:{}",request.getRequestURI());
            try {
                final String requestTokenHeader = request.getHeader("Authorization");
                if (requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer")) {
                    filterChain.doFilter(request, response);
                    return;
                }
                String token = requestTokenHeader.split("Bearer ")[1];
                String UserName = authUtil.getUsernameFromToken(token);
                if (UserName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    User user = userRepository.findByUsername(UserName).orElseThrow();
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new
                            UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    System.out.println(SecurityContextHolder.getContext().getAuthentication());
                }
                filterChain.doFilter(request, response);
            }catch(Exception ex){
                handlerExceptionResolver.resolveException(request,response,null,ex);
        }
    }
}
