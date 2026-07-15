package com.HospitalManagement.ManagedHospital.entity;

import com.HospitalManagement.ManagedHospital.entity.type.BloodGroupType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@ToString
@Entity
@Getter
@Setter
@Builder
@Table(name = "patient",
        uniqueConstraints = @UniqueConstraint(name = "unique_patientName_birthDate",
        columnNames ={"name","birthdate"}),
        indexes = @Index(name = "idx_patient_birthdate",columnList = "birthdate")
)
@AllArgsConstructor
@NoArgsConstructor
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,unique = true)
    private String name;
    private LocalDate birthdate;
    @Column(nullable = false,unique = true)
    private String email;
    private String gender;
    private String disease;

    @CreationTimestamp
    @Column(updatable = true)
    private LocalDate createdAt;


    @Enumerated(EnumType.STRING)
    private BloodGroupType bloodGroup;

    @OneToOne
    @MapsId
    private User user;

    @OneToOne(cascade = {CascadeType.ALL},orphanRemoval = true)//owning side
    @JoinColumn(name = "patient_insurance_id")
    private Insurance insurance;


    @OneToMany(mappedBy = "patient",cascade = {CascadeType.REMOVE},orphanRemoval = true,fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Appointment> appointments=new ArrayList<>();

}
