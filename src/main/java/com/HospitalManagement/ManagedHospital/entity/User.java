package com.HospitalManagement.ManagedHospital.entity;

import com.HospitalManagement.ManagedHospital.Security.RolePermissionMapping;
import com.HospitalManagement.ManagedHospital.entity.type.AuthProviderType;
import com.HospitalManagement.ManagedHospital.entity.type.RoleType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "user",indexes ={
        @Index(
                name = "idx_provider_id_provider_type",
                columnList = "providerid, auth_provider_type")})
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(unique = true,nullable = false)
    private String username;//here we can store email,may be any id  or name
    @Column(unique = true,nullable = false)
    private String email;
    private String password;

    @Column(name="providerid",nullable = true)
    private String providerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "auth_provider_type")
    private AuthProviderType providerType;
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    Set<RoleType> roles=new HashSet<>();


    @Override
    public String getUsername() {
        return username;
    }
    @Override
    public String getPassword(){
        return password;
    }

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        Set<SimpleGrantedAuthority> authorities=new HashSet<>();
//        roles.forEach(role ->{
////            Object RolePermissionMapping;
//            Set<SimpleGrantedAuthority> permissions= RolePermissionMapping.getAuthoritiesFoeRole(role);
//            authorities.addAll(permissions);
//            authorities.add(new SimpleGrantedAuthority("ROLE_"+role.name()));
//        } );
//        return authorities;
//    }
//

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        roles.forEach(
                role -> {
                    Set<SimpleGrantedAuthority> permissions = RolePermissionMapping.getAuthoritiesForRole(role);
                    authorities.addAll(permissions);
                    authorities.add(new SimpleGrantedAuthority("ROLE_"+role.name()));
                }
        );
        return authorities;
    }
}

