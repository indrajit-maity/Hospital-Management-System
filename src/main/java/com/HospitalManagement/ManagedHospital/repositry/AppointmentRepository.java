package com.HospitalManagement.ManagedHospital.repositry;

import com.HospitalManagement.ManagedHospital.entity.Appointment;
//import com.HospitalManagement.ManagedHospital.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

//import java.lang.ScopedValue;
//import java.util.List;
//import java.lang.ScopedValue;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

//    Optional<Appointment> findById(Long id);
//
//    Optional<Appointment> findByID(Long doctorId);

}