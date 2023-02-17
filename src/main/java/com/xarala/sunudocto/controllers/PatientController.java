package com.xarala.sunudocto.controllers;

import com.xarala.sunudocto.entities.Patient;
import com.xarala.sunudocto.services.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/patient")
@RequiredArgsConstructor
@Slf4j
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    public ResponseEntity<List<Patient>> all() {
        log.info("Get all Patients");
        return new ResponseEntity<>(patientService.getAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Patient> create(Patient patient) {
        log.info("Create Patient");
        return new ResponseEntity<>(patientService.create(patient), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> get(@PathVariable Long id) {
        log.info("Get Patient by id [{}]", id);
        return new ResponseEntity<>(patientService.getById(id), HttpStatus.OK);
    }
}
