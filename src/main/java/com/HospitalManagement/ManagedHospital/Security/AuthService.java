package com.HospitalManagement.ManagedHospital.Security;


import com.HospitalManagement.ManagedHospital.dto.LoginRequestDto;
import com.HospitalManagement.ManagedHospital.dto.LoginResponseDto;
import com.HospitalManagement.ManagedHospital.dto.SignupRequestDto;
import com.HospitalManagement.ManagedHospital.dto.SignupResponseDto;
import com.HospitalManagement.ManagedHospital.entity.User;
import com.HospitalManagement.ManagedHospital.entity.type.AuthProviderType;
import com.HospitalManagement.ManagedHospital.repositry.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        System.out.println("hiii");
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(),loginRequestDto.getPassword()));
        User user=(User) authentication.getPrincipal();
    String token=authUtil.generateAccessToken(user);
    System.out.println("hiiiii!");
    System.out.println(token);
    return new LoginResponseDto(token,user.getId());
    }

    public SignupResponseDto signUp(SignupRequestDto signupRequestDto) {
//        User user=userRepository.findByUsername(signupRequestDto.getUsername()).orElse(null);
        User user=userRepository.findByEmail(signupRequestDto.getEmail()).orElse(null);
        if(user!=null) throw new IllegalArgumentException("User already exits");
        user =userRepository.save(User.builder()
                .username(signupRequestDto.getUsername())
                .email(signupRequestDto.getEmail())
                .password( passwordEncoder.encode(signupRequestDto.getPassword()))
                .build()
        );
        return modelMapper.map(user,SignupResponseDto.class);
    }




    public ResponseEntity<LoginResponseDto> handleOAuth2LoginRequest(OAuth2User oAuth2User, String registrationId) {
        //fetch provider type and provider id to identify platform(google,github,.......)


        AuthProviderType authProviderType=authUtil.getProviderTypeFromRegistrationId(registrationId);
        String providerId= authUtil.determineProviderIdFromOAuth2User(oAuth2User,registrationId);
        Optional<User> userObj=userRepository.findByProviderIdAndProviderType(providerId,authProviderType);
        User user=userObj.get();
        String email=oAuth2User.getAttribute("email");
        User emailUser=userRepository.findByEmail(email).orElse(null);

        if(user==null && emailUser==null){
            //Signup flow
            String userName= authUtil.determineUsernameFromOAuth2User(oAuth2User,registrationId,providerId);
//            String email=authUtil.determineEmailFromOAuth2User(oAuth2User,registrationId,providerId);
            SignupResponseDto signupResponseDto=signUp(new SignupRequestDto(userName,null,email));
        }
        else if(user!=null){
            if(email!=null && !email.isBlank() && email.equals(user.getUsername())){
                user.setUsername(email);
//                user u=user.get();
                userRepository.save(user);
            }
        }
        else{
            throw new BadCredentialsException("This email is already register with provider:"+email);
        }
//        save the provider id and provider type(if client login from multiple platform to prevent this)
//        if the user has an account directly login
    LoginResponseDto loginResponseDto=new LoginResponseDto(authUtil.generateAccessToken(user),user.getId() );
        return ResponseEntity.ok(loginResponseDto);
    }
}
