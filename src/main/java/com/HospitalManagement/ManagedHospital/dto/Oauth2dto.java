package com.HospitalManagement.ManagedHospital.dto;


import com.HospitalManagement.ManagedHospital.entity.type.AuthProviderType;
import com.HospitalManagement.ManagedHospital.entity.type.RoleType;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@Getter
@Setter
//@AllArgsConstructor
@NoArgsConstructor
public class Oauth2dto {
    private String username;
    private String email;
    private String password;
    private AuthProviderType authProviderType;
    private String providerId;
    private Set<RoleType> roles=new HashSet<>();
    public Oauth2dto(String username,String email,String password,AuthProviderType authProviderType,String providerId){
        this.username=username;
        this.email=email;
        this.password=password;
        this.authProviderType=authProviderType;
        this.providerId=providerId;

    }

    public Set<RoleType> getRoles() {
        return roles;
    }
//    public String getProviderId() {
//        return getProviderId();
//    }

    //    private get
}
