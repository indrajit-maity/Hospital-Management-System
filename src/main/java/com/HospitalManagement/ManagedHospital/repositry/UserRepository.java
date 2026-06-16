package com.HospitalManagement.ManagedHospital.repositry;

import com.HospitalManagement.ManagedHospital.entity.User;
import com.HospitalManagement.ManagedHospital.entity.type.AuthProviderType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

//import java.lang.ScopedValue;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String userName);

    Optional<User> findByProviderIdAndProviderType(String providerId, AuthProviderType authProviderType);


//    @Query("SELECT u FROM User u WHERE u.providerid = :providerId AND u.providertype = :providerType")
//    Optional<User> findByProviderIdAndProviderType(
//            @Param("providerId") String providerId,
//            @Param("providerType") AuthProviderType providerType
//    );
}