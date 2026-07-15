package com.HospitalManagement.ManagedHospital.service;

import com.HospitalManagement.ManagedHospital.dto.DoctorResponseDto;
import com.HospitalManagement.ManagedHospital.dto.OnboardDoctorRequestDto;
import com.HospitalManagement.ManagedHospital.entity.Doctor;
import com.HospitalManagement.ManagedHospital.entity.User;
import com.HospitalManagement.ManagedHospital.entity.type.RoleType;
import com.HospitalManagement.ManagedHospital.repositry.DoctorRepository;
import com.HospitalManagement.ManagedHospital.repositry.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DoctorService {

    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;


    @Transactional
    public List<DoctorResponseDto> getAllDoctors(Integer pageNumber, Integer size) {
        Page<Doctor> doctors=doctorRepository.findAllDoctor(PageRequest.of(pageNumber,size));
        return doctors.stream()
                .map(doctor -> modelMapper.map(doctor, DoctorResponseDto.class))
                .collect(Collectors.toList());
    }


    @Transactional
    @PreAuthorize("hasRole('ADMIN'),hasRole('DOCTOR')")
    public DoctorResponseDto OnboardNewdoctor(OnboardDoctorRequestDto onboardDoctorRequestDto) {

        User user=userRepository.findById(onboardDoctorRequestDto.getUserId())
                .orElseThrow(()->new IllegalArgumentException("Not Exits "+onboardDoctorRequestDto.getName()));

        if(doctorRepository.existsById(onboardDoctorRequestDto.getUserId())){
            throw new IllegalArgumentException("Already exits this docotr.");
        }
        Doctor doctor=Doctor.builder()
                .name(onboardDoctorRequestDto.getName())
                .specalization(onboardDoctorRequestDto.getSpecalization())
                .email(onboardDoctorRequestDto.getEmail())
                .user(user)
                .build();

            user.getRoles().add(RoleType.DOCTOR);
       return modelMapper.map(doctorRepository.save(doctor), DoctorResponseDto.class);
    }
}
