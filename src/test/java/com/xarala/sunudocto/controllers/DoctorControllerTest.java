package com.xarala.sunudocto.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xarala.sunudocto.entities.Doctor;
import com.xarala.sunudocto.entities.Gender;
import com.xarala.sunudocto.services.DoctorService;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DoctorController.class)
class DoctorControllerTest {

    private final String urlTemplate = "/api/v1/doctor";

    @MockBean
    private DoctorService doctorService;


    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenGetDoctorsShouldReturnAll() throws Exception {
        Doctor doctor = getDoctor();

        List<Doctor> doctors = List.of(doctor);

        given(doctorService.getAll()).willReturn(doctors);

        this.mockMvc.perform(get(this.urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].id", equalTo(doctor.getId())))
                .andExpect(jsonPath("$.[0].lastName", equalTo(doctor.getLastName())))
                .andExpect(jsonPath("$.[0].firstName", equalTo(doctor.getFirstName())))
                .andExpect(jsonPath("$.[0].phone", equalTo(doctor.getPhone())))
                .andExpect(jsonPath("$.[0].mail", equalTo(doctor.getMail())));
    }

    @Test
    void whenAddDoctorShouldReturnDoctor() throws Exception {
        Doctor doctor = getDoctor();

        given(doctorService.create(any())).willReturn(doctor);
        ObjectMapper mapper = new ObjectMapper();
        byte[] doctorToByte = mapper.writeValueAsBytes(doctor);

        this.mockMvc.perform(post(this.urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(doctorToByte))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(doctor.getId()))
                .andExpect(jsonPath("$.firstName").value(doctor.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(doctor.getLastName()))
                .andExpect(jsonPath("$.phone").value(doctor.getPhone()))
                .andExpect(jsonPath("$.mail").value(doctor.getMail()));
    }

    @Test
    void whenGetDoctorByIdShouldReturnOne() throws Exception {
        Doctor doctor = getDoctor();
        doctor.setId(1L);

        given(doctorService.getById(anyLong())).willReturn(doctor);

        this.mockMvc.perform(get(this.urlTemplate + "/{id}", doctor.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(doctor.getId().intValue())))
                .andExpect(jsonPath("$.lastName", equalTo(doctor.getLastName())))
                .andExpect(jsonPath("$.firstName", equalTo(doctor.getFirstName())))
                .andExpect(jsonPath("$.phone", equalTo(doctor.getPhone())))
                .andExpect(jsonPath("$.mail", equalTo(doctor.getMail())));
    }

    @Test
    void whenGetDoctorByKeyShouldReturnOne() throws Exception {
        Doctor doctor = getDoctor();
        List<Doctor> doctors = List.of(doctor);

        given(doctorService.getByKey(anyString())).willReturn(doctors);

        this.mockMvc.perform(get(this.urlTemplate + "/find/{key}", doctor.getFirstName())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].lastName", equalTo(doctor.getLastName())))
                .andExpect(jsonPath("$.[0].firstName", equalTo(doctor.getFirstName())))
                .andExpect(jsonPath("$.[0].phone", equalTo(doctor.getPhone())))
                .andExpect(jsonPath("$.[0].mail", equalTo(doctor.getMail())));
    }

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
}
