package com.HospitalManagement.ManagedHospital.Security;


import com.HospitalManagement.ManagedHospital.dto.*;
import com.HospitalManagement.ManagedHospital.entity.Admin;
import com.HospitalManagement.ManagedHospital.entity.Doctor;
import com.HospitalManagement.ManagedHospital.entity.Patient;
import com.HospitalManagement.ManagedHospital.entity.User;
import com.HospitalManagement.ManagedHospital.entity.type.AuthProviderType;
//import com.HospitalManagement.ManagedHospital.entity.type.RoleType;
import com.HospitalManagement.ManagedHospital.entity.type.BloodGroupType;
import com.HospitalManagement.ManagedHospital.entity.type.RoleType;
import com.HospitalManagement.ManagedHospital.repositry.AdminRepositry;
import com.HospitalManagement.ManagedHospital.repositry.DoctorRepository;
import com.HospitalManagement.ManagedHospital.repositry.PatientRepositry;
import com.HospitalManagement.ManagedHospital.repositry.UserRepository;
import jakarta.transaction.Transactional;
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

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;



@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final PatientRepositry patientRepositry;
    private final DoctorRepository doctorRepository;
    private final AdminRepositry adminRepositry;

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
//        System.out.println(signupRequestDto.getUsername());
        User user=userRepository.findByEmail(signupRequestDto.getEmail()).orElse(null);
        if(user!=null) throw new IllegalArgumentException("User already exits");
//        System.out.println(signupRequestDto.getUsername());

        try {

            user = userRepository.save(
                    User.builder()
                            .username(signupRequestDto.getUsername())
                            .email(signupRequestDto.getEmail())
                            .password(passwordEncoder.encode(signupRequestDto.getPassword()))
                            .providerId(null)
                            .providerType(AuthProviderType.EMAIL)
                            .roles(Set.of(signupRequestDto.getRoles().toArray(new RoleType[0])))
                            .build());

            if(signupRequestDto.getRoles().equals(Set.of(RoleType.PATIENT))){
                Patient patient=patientRepositry.save(
                        Patient.builder()
                                .name(signupRequestDto.getUsername())
                                .email(signupRequestDto.getEmail())
                                .bloodGroup(BloodGroupType.O_POSITIVE)
                                .disease("fever")
                                .user(user)
                                .birthdate(LocalDate.of(2005,01,18))
                                .build()
                );
            }
            if(signupRequestDto.getRoles().equals(Set.of(RoleType.DOCTOR))){
                Doctor doctor=doctorRepository.save(
                        Doctor.builder()
                                .email(signupRequestDto.getEmail())
                                .name(signupRequestDto.getUsername())
                                .user(user)
                                .specalization("Migrane_Specalist")
                                .build()
                );
            }
            if(signupRequestDto.getRoles().equals(Set.of(RoleType.ADMIN))){
                Admin admin=adminRepositry.save(
                        Admin.builder()
                                .email(signupRequestDto.getEmail())
                                .name(signupRequestDto.getUsername())
                                .user(user)
                                .build()
                );
            }

            System.out.println("Saved Successfully");

        } catch (Exception e) {

            e.printStackTrace();

        }
//        System.out.println(signupRequestDto.getUsername());
//       if(signupRequestDto.getRoles().equals(Set.of(RoleType.PATIENT))){
//           Patient patient=patientRepositry.save(
//                   Patient.builder()
//                           .name(signupRequestDto.getUsername())
//                           .email(signupRequestDto.getEmail())
//                           .bloodGroup(BloodGroupType.O_POSITIVE)
//                           .disease("fever")
//                           .birthdate(LocalDate.of(2005,01,18))
//                           .build()
//           );
//       }
//       if(signupRequestDto.getRoles().equals(Set.of(RoleType.DOCTOR))){
//           Doctor doctor=doctorRepository.save(
//                   Doctor.builder()
//                           .email(signupRequestDto.getEmail())
//                           .name(signupRequestDto.getUsername())
//                           .specalization("Migrane_Specalist")
//                           .build()
//           );
//       }

        return modelMapper.map(user,SignupResponseDto.class);
    }
    public SignupResponseDto OauthsignUp(Oauth2dto oauth2dto){
        User user=userRepository.findByEmail(oauth2dto.getEmail()).orElse(null);
        if(user!=null) throw new IllegalArgumentException("User already exits.");
        System.out.println(oauth2dto.getUsername()+10);
        user=userRepository.save(User.builder()
                .username(oauth2dto.getUsername())
                .email(oauth2dto.getEmail())
                .password(oauth2dto.getPassword())
                .providerId(oauth2dto.getProviderId())
                .providerType(oauth2dto.getAuthProviderType())
                .roles(Set.of(RoleType.PATIENT))
                .build()
        );
        return modelMapper.map(user,SignupResponseDto.class);
    }


    public String generateUniqueUsername(String baseName) {
        String base = baseName.toLowerCase().replaceAll("\\s+", "");
        String candidate = base;
        int suffix = 1;
        while (userRepository.existsByUsername(candidate)) {
            candidate = base + suffix;
            suffix++;
        }
        return candidate;
    }


@Transactional
    public ResponseEntity<LoginResponseDto> handleOAuth2LoginRequest(OAuth2User oAuth2User, String registrationId) {
        //fetch provider type and provider id to identify platform(google,github,.......)

        System.out.println(registrationId.toLowerCase());
        AuthProviderType authProviderType=authUtil.getProviderTypeFromRegistrationId(registrationId);
        String providerId= authUtil.determineProviderIdFromOAuth2User(oAuth2User,registrationId);
//        Optional<User> userObj=userRepository.findByProviderIdAndProviderType(providerId,authProviderType);
        User user=userRepository.findByProviderIdAndProviderType(providerId,authProviderType).orElse(null);
        String email=oAuth2User.getAttribute("email");
        User emailUser=userRepository.findByEmail(email).orElse(null);

        if(user==null && emailUser==null){
            //Signup flow
            String userName= authUtil.determineUsernameFromOAuth2User(oAuth2User,registrationId,providerId);
            String uniqueUsername=generateUniqueUsername(userName);
//            String email=authUtil.determineEmailFromOAuth2User(oAuth2User,registrationId,providerId);
            SignupResponseDto signupResponseDto=OauthsignUp(new Oauth2dto(uniqueUsername,email,null,authProviderType,providerId));
            user=userRepository.findByProviderIdAndProviderType(providerId,authProviderType).orElse(null);
        }
        else if(user!=null){
            if(email!=null && !email.isBlank() && !email.equals(user.getEmail())){
//                user.setUsername(user.getUsername());
                user.setEmail(email);
//                user u=user.get();
                userRepository.save(user);
            }
        }
        else{
            throw new BadCredentialsException("This email is already register with provider:"+emailUser.getProviderType());
        }
//        save the provider id and provider type(if client login from multiple platform to prevent this)
//        if the user has an account directly login
    LoginResponseDto loginResponseDto=new LoginResponseDto(authUtil.generateAccessToken(user),user.getId() );
        return ResponseEntity.ok(loginResponseDto);
    }
}
