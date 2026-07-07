package com.HospitalManagement.ManagedHospital.controller;

import com.HospitalManagement.ManagedHospital.dto.AppointmentResponcedto;
import com.HospitalManagement.ManagedHospital.dto.CreateAppointmentRequestDto;
import com.HospitalManagement.ManagedHospital.dto.PatientDto;
import com.HospitalManagement.ManagedHospital.entity.Patient;
import com.HospitalManagement.ManagedHospital.service.AppoinmentService;
import com.HospitalManagement.ManagedHospital.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/patient")
@Tag(name="Patient",description = "operation related to patient")
public class PatientControler {

    private final PatientService patientService;
    private final AppoinmentService appoinmentService;


    @Operation(summary = "Get all patient")
    @GetMapping("/all")
    public ResponseEntity<List<Patient>> getAllpatient(){
        return ResponseEntity.status(HttpStatus.CREATED).body(patientService.getallpatient());
    }
@Operation(summary = "Get patient through id")
    @GetMapping("/{id}")
    public ResponseEntity<PatientDto> getbyid(@PathVariable Long id){
        return  ResponseEntity.status(HttpStatus.CREATED).body(patientService.getById(id));
    }

@Operation(summary = "add patient")
    @PostMapping("/newPatient")
    public ResponseEntity<PatientDto> Addpatient(@RequestBody PatientDto patientDto){
        System.out.println("Controller reached");
        return ResponseEntity.status(HttpStatus.CREATED).body(patientService.createnewPatient(patientDto));

    }
@Operation(summary = "Create appointment")
    @PostMapping("/newAppointment")
    public ResponseEntity<AppointmentResponcedto> createAppointment(@RequestBody CreateAppointmentRequestDto createAppointmentRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(appoinmentService.createnewAppointment(createAppointmentRequestDto));
    }
    @Operation(summary = "Csrf-token")
    @GetMapping("/csrf-token")
    public CsrfToken getcsrfToken(HttpServletRequest request){
    return (CsrfToken) request.getAttribute("_csrf");
    }

}
