package com.HospitalManagement.ManagedHospital.dto;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DoctorResponseDto {
    private Long id;
    private  String name;
    private  String specalization;
    private String email;

}
