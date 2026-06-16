package com.HospitalManagement.ManagedHospital.error;


import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

//@RestControllerAdvice
public class GlobalExceptionHandler {

@ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiError> handleUsernameNotFoundException(UsernameNotFoundException ex){
    ApiError apiError=new ApiError("User name not found: "+ ex.getMessage(), HttpStatus.NOT_FOUND);
    return new ResponseEntity<>(apiError,apiError.getStatusCode());
}
@ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handleAuthenticationException(AuthenticationException ex){
    ApiError apiError=new ApiError("Authentication Failed: "+ex.getMessage(),HttpStatus.UNAUTHORIZED);
    return new ResponseEntity<>(apiError,apiError.getStatusCode());
}
@ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiError> handleJwtException(JwtException ex){
    ApiError apiError=new ApiError("Invalid Jwt Token: "+ex.getMessage(),HttpStatus.UNAUTHORIZED);
    return new ResponseEntity<>(apiError,apiError.getStatusCode());
}
@ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDeniedException(AccessDeniedException ex){
    ApiError apiError=new ApiError("Access Denied: Insuffecient Permission: "+ex.getMessage(),HttpStatus.FORBIDDEN);
    return new ResponseEntity<>(apiError,apiError.getStatusCode());
}
@ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception ex){
    ApiError apiError=new ApiError("An Unexpected error occured: "+ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    return new ResponseEntity<>(apiError,apiError.getStatusCode());
}
@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiError> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex){
    ApiError apiError=new ApiError("Http Request Not Support: "+ex.getMessage(),HttpStatus.METHOD_NOT_ALLOWED);
    return new ResponseEntity<>(apiError,apiError.getStatusCode());
}

}
