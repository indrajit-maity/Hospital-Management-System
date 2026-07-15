package com.HospitalManagement.ManagedHospital.entity;

import jakarta.persistence.*;
import lombok.*;


@ToString
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    @Column(nullable = false,unique = true)
    private  String name;
    @Column(nullable = false,unique = true)
    private String email;
    @OneToOne
    @MapsId
    private User user;
}
