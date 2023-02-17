package com.xarala.sunudocto.services;

import com.xarala.sunudocto.entities.Appointment;
import com.xarala.sunudocto.repositories.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentService {


    private final AppointmentRepository appointmentRepository;

    public List<Appointment> getAll() {
        return appointmentRepository.findAll();
    }

    public Appointment create(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    public Appointment getById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor is not found"));
    }

    public void deleteById(Long id) {
        appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        appointmentRepository.deleteById(id);
    }

    public List<Appointment> getAllByDoctor(Long id) {
        return appointmentRepository.findAllByDoctorId(id)
                .stream()
                .sorted(Comparator.comparing(Appointment::getCratedAt))
                .collect(Collectors.toList());
    }

    public List<Appointment> getAllByPatient(Long id) {
        return appointmentRepository.findAllByPatientId(id);
    }
}
