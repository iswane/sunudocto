package com.xarala.sunudocto.services;

import com.xarala.sunudocto.entities.Appointment;
import com.xarala.sunudocto.entities.Doctor;
import com.xarala.sunudocto.entities.Gender;
import com.xarala.sunudocto.entities.Patient;
import com.xarala.sunudocto.repositories.AppointmentRepository;
import org.junit.jupiter.api.Assertions;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @InjectMocks
    AppointmentService service;

    @Mock
    AppointmentRepository repository;

    @Test
    void getAllAppointmentShouldReturnAll() {
        Appointment appointment = getAppointment();

        List<Appointment> appointments = List.of(appointment);

        when(repository.findAll()).thenReturn(appointments);

        List<Appointment> all = service.getAll();

        assertEquals(1, all.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void createAppointmentShouldReturnAppointment() {
        Appointment appointment = getAppointment();

        when(repository.save(any())).thenReturn(appointment);

        Appointment appointmentSaved = service.create(appointment);
        assertEquals(appointmentSaved.getDoctor().getId(), appointment.getDoctor().getId());

        verify(repository, times(1)).save(appointment);
    }

    @Test
    void findAppointmentByIdShouldReturnAppointment() {
        Appointment appointment = getAppointment();
        appointment.setId(1L);

        when(repository.findById(anyLong())).thenReturn(Optional.of(appointment));

        Appointment AppointmentFinded = service.getById(appointment.getId());
        assertEquals(AppointmentFinded, appointment);

        verify(repository, times(1)).findById(1L);
    }

    @Test
    void wheDeleteAppointmentByIdShouldAppointmentExist() {
        Appointment appointment = getAppointment();
        appointment.setId(1L);

        when(repository.findById(anyLong())).thenReturn(Optional.of(appointment));

        service.deleteById(appointment.getId());

        verify(repository).deleteById(appointment.getId());
    }

    @Test
    void wheDeleteAppointmentByIdShouldAppointmentNotExist() {
        Appointment appointment = getAppointment();
        Assertions.assertThrows(RuntimeException.class, () -> {
            given(repository.findById(anyLong())).willReturn(Optional.ofNullable(null));
            service.deleteById(appointment.getId());
        });


    }

    private static Appointment getAppointment() {
        Doctor doctor = new Doctor();
        doctor.setFirstName("Xarala");
        doctor.setLastName("DIOP");
        doctor.setMail("xarala.diop@sunudoctor.sn");
        doctor.setPhone("77 123 45 67");
        doctor.setRib("FR67 2536 87637 76N");
        doctor.setSpeciality("Dentiste");
        doctor.setCreatedAt(LocalDateTime.now().toString());
        doctor.setGender(Gender.MALE);

        Patient patient = new Patient();
        patient.setFirstName("Adama");
        patient.setLastName("DIOP");
        patient.setMail("adama.diop@sunudoctor.sn");
        patient.setPhone("78 123 45 67");
        patient.setCardNumber("2535 2625 2725 O898");
        patient.setCreatedAt(LocalDateTime.now().toString());
        patient.setGender(Gender.FEMALE);

        return Appointment.builder()
                .hourly(LocalDateTime.of(2023, 2, 1, 15, 0, 0).withSecond(2).toString())
                .reason("Premier rdv")
                .doctor(doctor)
                .patient(patient)
                .build();
    }
}
