package com.HospitalManagement.ManagedHospital.controller;


import com.HospitalManagement.ManagedHospital.dto.DoctorResponseDto;
import com.HospitalManagement.ManagedHospital.dto.OnboardDoctorRequestDto;
import com.HospitalManagement.ManagedHospital.dto.PatientResponseDto;
import com.HospitalManagement.ManagedHospital.entity.Patient;
import com.HospitalManagement.ManagedHospital.service.DoctorService;
import com.HospitalManagement.ManagedHospital.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@Tag(name="Admin API",description = "Operation related to admin")
public class AdminController {
    private final PatientService patientService;
    private final DoctorService doctorService;

    @GetMapping("/home")
    public String Home(){
        return "Wellcome!!!!";
    }

    @Operation(summary = "Get all Patients")
    @GetMapping("/patients")
    public ResponseEntity<List<PatientResponseDto>> getAllPatients(
            @RequestParam(value = "page",defaultValue = "0") Integer pageNumber,
            @RequestParam(value="size",defaultValue = "10") Integer pageSize
    ){
            return ResponseEntity.status(HttpStatus.CREATED).body(patientService.getAllpatient(pageNumber,pageSize));
    }

    @Operation(summary = "Add new Doctor")
    @PostMapping("/onBoardnewDoctor")
    public  ResponseEntity<DoctorResponseDto> onBoardnewdoctor(@RequestBody OnboardDoctorRequestDto onboardDoctorRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(doctorService.OnboardNewdoctor(onboardDoctorRequestDto));
    }
}
