package com.HospitalManagement.ManagedHospital.dto;


import com.HospitalManagement.ManagedHospital.entity.type.RoleType;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@Getter
@Setter
//@AllArgsConstructor
@NoArgsConstructor
public class SignupRequestDto {
    private String username;
    private String password;
//    private String name;
    private String email;
    private Set<RoleType> roles=new HashSet<>();
    public SignupRequestDto(String username,String password,String email,Set<RoleType> roles){
        this.username=username;
        this.password=password;
        this.email=email;
        this.roles=roles;
    }


    public String getPassword() {
        return password;
    }
}
