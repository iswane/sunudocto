package com.xarala.sunudocto.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xarala.sunudocto.entities.Gender;
import com.xarala.sunudocto.entities.Patient;
import com.xarala.sunudocto.services.PatientService;
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
@WebMvcTest(PatientController.class)
class PatientControllerTest {

    private final String urlTemplate = "/api/v1/patient";

    @MockBean
    private PatientService patientService;


    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenGetPatientsShouldReturnAll() throws Exception {
        Patient patient = getPatient();

        List<Patient> patients = List.of(patient);

        given(patientService.getAll()).willReturn(patients);

        this.mockMvc.perform(get(this.urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].id", equalTo(patient.getId())))
                .andExpect(jsonPath("$.[0].lastName", equalTo(patient.getLastName())))
                .andExpect(jsonPath("$.[0].firstName", equalTo(patient.getFirstName())))
                .andExpect(jsonPath("$.[0].phone", equalTo(patient.getPhone())))
                .andExpect(jsonPath("$.[0].mail", equalTo(patient.getMail())));
    }

    @Test
    void whenAddPatientShouldReturnPatient() throws Exception {
        Patient patient = getPatient();

        given(patientService.create(any())).willReturn(patient);
        ObjectMapper mapper = new ObjectMapper();
        byte[] patientToByte = mapper.writeValueAsBytes(patient);

        this.mockMvc.perform(post(this.urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patientToByte))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(patient.getId()))
                .andExpect(jsonPath("$.firstName").value(patient.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(patient.getLastName()))
                .andExpect(jsonPath("$.phone").value(patient.getPhone()))
                .andExpect(jsonPath("$.mail").value(patient.getMail()));
    }

    @Test
    void whenGetPatientByIdShouldReturnOne() throws Exception {
        Patient patient = getPatient();
        patient.setId(1L);

        given(patientService.getById(anyLong())).willReturn(patient);

        this.mockMvc.perform(get(this.urlTemplate + "/{id}", patient.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(patient.getId().intValue())))
                .andExpect(jsonPath("$.lastName", equalTo(patient.getLastName())))
                .andExpect(jsonPath("$.firstName", equalTo(patient.getFirstName())))
                .andExpect(jsonPath("$.phone", equalTo(patient.getPhone())))
                .andExpect(jsonPath("$.mail", equalTo(patient.getMail())));
    }

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
}
