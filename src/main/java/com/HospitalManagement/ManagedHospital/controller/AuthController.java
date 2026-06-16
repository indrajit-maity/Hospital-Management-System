package com.HospitalManagement.ManagedHospital.controller;


import com.HospitalManagement.ManagedHospital.Security.AuthService;
import com.HospitalManagement.ManagedHospital.dto.LoginRequestDto;
import com.HospitalManagement.ManagedHospital.dto.LoginResponseDto;
import com.HospitalManagement.ManagedHospital.dto.SignupResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name="Authentiction API",description = "Operation related to authentication")
public class AuthController {
    private final AuthService authService;


    @Operation(summary = "LogIn")
    @PostMapping("/log-in")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto){
        return  ResponseEntity.status(HttpStatus.CREATED).body(authService.login(loginRequestDto));
    }

    @Operation(summary = "SignIn")
    @PostMapping("/sign-up")

    public ResponseEntity<SignupResponseDto> signup(@RequestBody LoginRequestDto signupRequestDto){
        return  ResponseEntity.status(HttpStatus.CREATED).body(authService.signUp(signupRequestDto));
    }


}
