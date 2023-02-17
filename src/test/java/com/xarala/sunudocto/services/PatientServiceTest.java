package com.xarala.sunudocto.services;

import com.xarala.sunudocto.entities.Gender;
import com.xarala.sunudocto.entities.Patient;
import com.xarala.sunudocto.repositories.PatientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @InjectMocks
    PatientService service;

    @Mock
    PatientRepository repository;

    private static Patient getPatient() {
        Patient patient = new Patient();
        patient.setFirstName("Adama");
        patient.setLastName("DIOP");
        patient.setMail("adama.diop@sunudoctor.sn");
        patient.setPhone("78 123 45 67");
        patient.setCardNumber("2535 2625 2725 O898");
        patient.setCreatedAt(LocalDateTime.now().toString());
        patient.setGender(Gender.FEMALE);
        return patient;
    }

    @Test
    void getAllDoctorShouldReturnAll() {
        Patient patient = getPatient();

        List<Patient> doctors = List.of(patient);

        when(repository.findAll()).thenReturn(doctors);

        List<Patient> all = service.getAll();

        assertEquals(1, all.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void createDoctorShouldReturnDoctor() {
        Patient patient = getPatient();

        when(repository.save(any())).thenReturn(patient);

        Patient doctorSaved = service.create(patient);
        assertEquals(doctorSaved.getFirstName(), patient.getFirstName());

        verify(repository, times(1)).save(patient);
    }

    @Test
    void findDoctorByIdShouldReturnDoctor() {
        Patient patient = getPatient();
        patient.setId(12L);

        when(repository.findById(anyLong())).thenReturn(Optional.of(patient));

        Patient doctorFinded = service.getById(patient.getId());
        assertEquals(doctorFinded, patient);

        verify(repository, times(1)).findById(12L);
    }
}
