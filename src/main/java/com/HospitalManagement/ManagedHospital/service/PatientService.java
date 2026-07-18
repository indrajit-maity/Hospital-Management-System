package com.HospitalManagement.ManagedHospital.service;


import com.HospitalManagement.ManagedHospital.dto.PatientDto;
import com.HospitalManagement.ManagedHospital.dto.PatientResponseDto;
import com.HospitalManagement.ManagedHospital.entity.Patient;
import com.HospitalManagement.ManagedHospital.entity.User;
import com.HospitalManagement.ManagedHospital.repositry.PatientRepositry;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final ModelMapper modelMapper;
    private final PatientRepositry patientRepositry;

    public PatientDto getById(Long id){
        Patient patient=patientRepositry.findById(id).orElseThrow();
        return modelMapper.map(patient,PatientDto.class);
    }

    @Transactional
    public PatientDto createnewPatient(PatientDto patientDto) {
        try {
            System.out.println("Before Save");
            User user=new User();

            Patient newpatient = modelMapper.map(patientDto, Patient.class);
            Patient patient = patientRepositry.save(newpatient);
            System.out.println("After Save: " + patient.getId());
            return modelMapper.map(patient, PatientDto.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

//    public  List<PatientDto> getallpatient() {
//        System.out.println("all students in patinet service:");
//        List<Patient> patients=patientRepositry.findAll();
//        System.out.println("Students are: ");
//        return patients.stream().toList();
//    }
//in admin controller
    public List<PatientResponseDto> getallpatient(Integer pageNumber, Integer pageSize) {
        return patientRepositry.findAllPatients(PageRequest.of(pageNumber,pageSize))
                .stream()
                .map(patient -> modelMapper.map(patient,PatientResponseDto.class))
                .collect(Collectors.toList());
    }
}
