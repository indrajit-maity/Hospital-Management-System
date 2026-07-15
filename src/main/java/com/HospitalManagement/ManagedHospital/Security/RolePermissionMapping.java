package com.HospitalManagement.ManagedHospital.Security;

import com.HospitalManagement.ManagedHospital.entity.type.PermissionType;
import com.HospitalManagement.ManagedHospital.entity.type.PermissionType.*;
import com.HospitalManagement.ManagedHospital.entity.type.RoleType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.HospitalManagement.ManagedHospital.entity.type.PermissionType.*;
import static com.HospitalManagement.ManagedHospital.entity.type.RoleType.*;

public class RolePermissionMapping {
     private static final Map<RoleType,Set<PermissionType>> map=Map.of(
                PATIENT,Set.of(APPOINTMENT_WRITE,APPOINTMENT_READ,PATIENT_READ),
             DOCTOR,Set.of(APPOINTMENT_DELETE,APPOINTMENT_READ, APPOINTMENT_WRITE,PATIENT_READ,PATIENT_WRITE),
             ADMIN,Set.of(PATIENT_READ,PATIENT_WRITE,APPOINTMENT_READ, APPOINTMENT_WRITE,APPOINTMENT_DELETE,USER_MANAGE,REPORT_VIEW)
             );
     public static  Set<SimpleGrantedAuthority> getAuthoritiesForRole(RoleType role){
         return map.get(role).stream()
                 .map(permission->new SimpleGrantedAuthority(permission.getPermission()))
                 .collect(Collectors.toSet());
     }
//    return roles.stream()
//            .map(role->new SimpleGrantedAuthority("ROLE_"+role.name()))
//            .collect(Collectors.toSet());
}
