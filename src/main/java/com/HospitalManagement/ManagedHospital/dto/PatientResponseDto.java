package com.HospitalManagement.ManagedHospital.dto;

import com.HospitalManagement.ManagedHospital.entity.type.BloodGroupType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Data
@Getter
@Setter
public class PatientResponseDto {
    private Long id;
    private String name;
    private String gender;
    private LocalDate birthDate;
    private BloodGroupType bloodGroup;
}
