package com.xarala.sunudocto.services;

import com.xarala.sunudocto.entities.Doctor;
import com.xarala.sunudocto.repositories.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorService {


    private final DoctorRepository doctorRepository;

    public List<Doctor> getAll() {
        return doctorRepository.findAll();
    }

    public Doctor create(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public Doctor getById(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor is not found"));
    }

    public List<Doctor> getByKey(String key) {
        return doctorRepository.findByFirstNameOrLastName(key, key)
                .stream()
                .sorted(Comparator.comparing(Doctor::getLatitude))
                .sorted(Comparator.comparing(Doctor::getLongitude))
                .collect(Collectors.toList());
    }
}
