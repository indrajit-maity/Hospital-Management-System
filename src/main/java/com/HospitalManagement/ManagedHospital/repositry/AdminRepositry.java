package com.HospitalManagement.ManagedHospital.repositry;

import com.HospitalManagement.ManagedHospital.entity.Admin;
import com.HospitalManagement.ManagedHospital.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepositry extends JpaRepository<Admin,Long> {


}
