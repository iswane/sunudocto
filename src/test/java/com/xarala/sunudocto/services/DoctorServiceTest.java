package com.xarala.sunudocto.services;

import com.xarala.sunudocto.entities.Doctor;
import com.xarala.sunudocto.entities.Gender;
import com.xarala.sunudocto.repositories.DoctorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DoctorServiceTest {

    @InjectMocks
    DoctorService service;

    @Mock
    DoctorRepository repository;

    private static Doctor getDoctor() {
        Doctor doctor = new Doctor();
        doctor.setFirstName("Xarala");
        doctor.setLastName("DIOP");
        doctor.setMail("xarala.diop@sunudoctor.sn");
        doctor.setPhone("77 123 45 67");
        doctor.setRib("FR67 2536 87637 76N");
        doctor.setSpeciality("Dentiste");
        doctor.setCreatedAt(LocalDateTime.now().toString());
        doctor.setGender(Gender.MALE);
        return doctor;
    }

    @Test
    void getAllDoctorShouldReturnAll() {
        Doctor doctor = getDoctor();

        List<Doctor> doctors = List.of(doctor);

        when(repository.findAll()).thenReturn(doctors);

        List<Doctor> all = service.getAll();

        assertEquals(1, all.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void createDoctorShouldReturnDoctor() {
        Doctor doctor = getDoctor();

        when(repository.save(any())).thenReturn(doctor);

        Doctor doctorSaved = service.create(doctor);
        assertEquals(doctorSaved.getFirstName(), doctor.getFirstName());

        verify(repository, times(1)).save(doctor);
    }

    @Test
    void findDoctorByIdShouldReturnDoctor() {
        Doctor doctor = getDoctor();
        doctor.setId(12L);

        when(repository.findById(anyLong())).thenReturn(Optional.of(doctor));

        Doctor doctorFinded = service.getById(doctor.getId());
        assertEquals(doctorFinded, doctor);

        verify(repository, times(1)).findById(12L);
    }
}
