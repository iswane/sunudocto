package com.xarala.sunudocto.services;

import com.xarala.sunudocto.entities.Patient;
import com.xarala.sunudocto.repositories.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {


    private final PatientRepository patientRepository;

    public List<Patient> getAll() {
        return patientRepository.findAll();
    }

    public Patient create(Patient patient) {
        return patientRepository.save(patient);
    }

    public Patient getById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient is not found"));
    }
}
