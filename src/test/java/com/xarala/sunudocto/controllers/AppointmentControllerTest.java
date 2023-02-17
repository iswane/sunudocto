package com.xarala.sunudocto.controllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xarala.sunudocto.entities.Appointment;
import com.xarala.sunudocto.entities.Doctor;
import com.xarala.sunudocto.entities.Gender;
import com.xarala.sunudocto.entities.Patient;
import com.xarala.sunudocto.services.AppointmentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AppointmentController.class)
class AppointmentControllerTest {

    private final String urlTemplate = "/api/v1/appointment";

    @MockBean
    private AppointmentService appointmentService;


    @Autowired
    private MockMvc mockMvc;

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

    @Test
    void whenGetAppointmentsShouldReturnAll() throws Exception {
        Appointment appointment = getAppointment();
        appointment.setId(1L);

        List<Appointment> appointments = List.of(appointment);

        given(appointmentService.getAll()).willReturn(appointments);

        this.mockMvc.perform(get(this.urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].id", equalTo(appointment.getId().intValue())))
                .andExpect(jsonPath("$.[0].doctor.mail", equalTo(appointment.getDoctor().getMail())))
                .andExpect(jsonPath("$.[0].patient.mail", equalTo(appointment.getPatient().getMail())))
                .andExpect(jsonPath("$.[0].hourly", equalTo(appointment.getHourly().toString())));
    }

    @Test
    void whenAddAppointmentShouldReturnAppointment() throws Exception {
        Appointment appointment = getAppointment();

        given(appointmentService.create(any())).willReturn(appointment);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        byte[] appointmentToByte = mapper.writeValueAsBytes(appointment);

        this.mockMvc.perform(post(this.urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(appointmentToByte))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.doctor.mail", equalTo(appointment.getDoctor().getMail())))
                .andExpect(jsonPath("$.patient.mail", equalTo(appointment.getPatient().getMail())))
                .andExpect(jsonPath("$.hourly", equalTo(appointment.getHourly().toString())));
    }

    @Test
    void whenGetAppointmentByIdShouldReturnOne() throws Exception {
        Appointment appointment = getAppointment();
        appointment.setId(1L);

        given(appointmentService.getById(anyLong())).willReturn(appointment);

        this.mockMvc.perform(get(this.urlTemplate + "/{id}", appointment.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(appointment.getId().intValue())))
                .andExpect(jsonPath("$.doctor.mail", equalTo(appointment.getDoctor().getMail())))
                .andExpect(jsonPath("$.patient.mail", equalTo(appointment.getPatient().getMail())))
                .andExpect(jsonPath("$.hourly", equalTo(appointment.getHourly().toString())));
    }

    @Test
    void whenGetAppointmentsByPatientShouldReturnAll() throws Exception {
        Appointment appointment = getAppointment();
        appointment.setId(11L);
        appointment.getPatient().setId(1L);

        List<Appointment> appointments = List.of(appointment);

        given(appointmentService.getAllByPatient(anyLong())).willReturn(appointments);

        this.mockMvc.perform(get(this.urlTemplate + "/all/patient/{id}", appointment.getPatient().getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].id", equalTo(appointment.getId().intValue())))
                .andExpect(jsonPath("$.[0].doctor.mail", equalTo(appointment.getDoctor().getMail())))
                .andExpect(jsonPath("$.[0].patient.mail", equalTo(appointment.getPatient().getMail())))
                .andExpect(jsonPath("$.[0].hourly", equalTo(appointment.getHourly().toString())));
    }

    @Test
    void whenGetAppointmentsByDoctorShouldReturnAll() throws Exception {
        Appointment appointment = getAppointment();
        appointment.setId(12L);
        appointment.getDoctor().setId(1L);

        List<Appointment> appointments = List.of(appointment);

        given(appointmentService.getAllByDoctor(anyLong())).willReturn(appointments);

        this.mockMvc.perform(get(this.urlTemplate + "/all/doctor/{id}", appointment.getDoctor().getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].id", equalTo(appointment.getId().intValue())))
                .andExpect(jsonPath("$.[0].doctor.mail", equalTo(appointment.getDoctor().getMail())))
                .andExpect(jsonPath("$.[0].patient.mail", equalTo(appointment.getPatient().getMail())))
                .andExpect(jsonPath("$.[0].hourly", equalTo(appointment.getHourly().toString())));
    }
}
