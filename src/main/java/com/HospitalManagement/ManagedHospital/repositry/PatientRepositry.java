package com.HospitalManagement.ManagedHospital.repositry;

import com.HospitalManagement.ManagedHospital.dto.BloodGroupCountResponseEntity;
import com.HospitalManagement.ManagedHospital.entity.Patient;
//import com.SpringBoot.HospitalManagement.entity.Patient;
import com.HospitalManagement.ManagedHospital.entity.type.BloodGroupType;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.net.ProtocolFamily;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepositry extends JpaRepository<Patient,Long> {
// static ProtocolFamily builder() {
// }

 Optional<Patient> findById(Long id);

    List<Patient> findByNameAndDisease(String name, String fever);

    List<Patient> findByNameOrEmail(String name, String email);

    @Query(value = "SELECT * FROM Patient p",nativeQuery = true)
    Page<Patient> findAllPatients(Pageable pageable);

    @Query("SELECT p FROM Patient p where p.bloodGroup = ?1")
    List<Patient> findByBloodGroup(@Param("bloodGroup") BloodGroupType bloodGroup);


    @Query("select new com.HospitalManagement.ManagedHospital.dto.BloodGroupCountResponseEntity (p.bloodGroup," +
            " Count(p)) from Patient p group by p.bloodGroup")
    List<BloodGroupCountResponseEntity> countEachBloodGroupType();


    @Transactional
    @Modifying
    @Query("UPDATE Patient p SET p.name = :name where p.id = :id")
    int updateNameWithId(@Param("name") String name, @Param("id") Long id);


    @Query("SELECT p FROM Patient p LEFT JOIN FETCH p.appointments")
    List<Patient> findAllPatientWithAppointment();
//    List<Patient> findByNameOrEmail(LocalDate of, String mail);

//    Optional<Patient> findByName(String name);

//    Patient findbyPhone(long phone);
}
