package com.xarala.sunudocto.repositories;

import com.xarala.sunudocto.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    List<Doctor> findByFirstNameOrLastName(String firstName, String lastName);

//    List<Doctor> findAllByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String key);
}
