package com.HospitalManagement.ManagedHospital.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AppointmentResponcedto {
    private Long id;
    private LocalDateTime localDateTime;
    private String reason;
    private DoctorResponseDto doctor;
//    private  PatientResponseDto patient;
}
