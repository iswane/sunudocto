package com.xarala.sunudocto.repositories;

import com.xarala.sunudocto.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findAllByDoctorId(Long id);

    List<Appointment> findAllByPatientId(Long id);
}
