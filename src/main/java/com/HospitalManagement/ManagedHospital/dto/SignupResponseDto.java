package com.HospitalManagement.ManagedHospital.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class SignupResponseDto {
    private  Long userId;
    private String username;
}
