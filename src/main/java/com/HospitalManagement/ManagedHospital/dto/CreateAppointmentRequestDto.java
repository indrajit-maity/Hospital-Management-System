package com.HospitalManagement.ManagedHospital.dto;


import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateAppointmentRequestDto {
    private  Long doctor_id;
    private  Long patient_id;
    private String reason;
    private LocalDateTime appointmentTime;
}
