package com.HospitalManagement.ManagedHospital.dto;

import lombok.*;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;


@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientDto {
    private String name;
    private String disease;
    private long phone;
    private String email;
    private LocalDate birthdate;
}
