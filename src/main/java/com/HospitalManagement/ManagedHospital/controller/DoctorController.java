package com.HospitalManagement.ManagedHospital.controller;


import com.HospitalManagement.ManagedHospital.dto.AppointmentResponcedto;
import com.HospitalManagement.ManagedHospital.dto.DoctorResponseDto;
import com.HospitalManagement.ManagedHospital.entity.Appointment;
import com.HospitalManagement.ManagedHospital.entity.User;
import com.HospitalManagement.ManagedHospital.service.AppoinmentService;
import com.HospitalManagement.ManagedHospital.service.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/doctor")
@Tag(name="Doctor Api",description = "Doctors related api")
public class DoctorController {

    private  final DoctorService doctorService;
    private final AppoinmentService appoinmentService;

    @Operation(summary = "Get all doctors")
    @GetMapping("/doctors")
    public ResponseEntity<List<DoctorResponseDto>> getAlldoctor(
            @RequestParam(value = "page",defaultValue = "0") Integer pageNumber,
            @RequestParam(value="size",defaultValue = "5") Integer size
    ){

        return ResponseEntity.status(HttpStatus.CREATED).body(doctorService.getAllDoctors(pageNumber,size));
    }

//    get all appointment of particular doctor
    @Operation(summary = "Get All appointments.")
    @GetMapping("/appointments")
    public ResponseEntity <List<AppointmentResponcedto>> getAppointment(){
        User user= (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED).body(appoinmentService.getAllAppointmentsofDoctor(user.getId()));
    }
}
