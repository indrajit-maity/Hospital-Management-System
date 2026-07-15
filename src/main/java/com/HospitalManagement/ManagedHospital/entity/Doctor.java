package com.HospitalManagement.ManagedHospital.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Builder
//@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {
   @Id
//   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false,length = 100)
    private  String name;


    @Column(length = 100)
    private String specalization;


    @Column(nullable = false,unique = true,length = 100)
    private String email;

    @OneToMany(mappedBy = "doctor")
//    @Builder.Default
    private List<Appointment> appointments=new ArrayList<>();

    @ManyToMany(mappedBy = "doctors")
//   @Builder.Default
    private Set<Department> departments=new HashSet<>();

//    @OneToMany(mappedBy = "doctor")
//    private List<Appointment> Appointments=new ArrayList<>();
}
