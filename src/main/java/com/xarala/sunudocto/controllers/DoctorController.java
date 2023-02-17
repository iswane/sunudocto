package com.xarala.sunudocto.controllers;

import com.xarala.sunudocto.entities.Doctor;
import com.xarala.sunudocto.services.DoctorService;
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

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/doctor")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @GetMapping
    public ResponseEntity<List<Doctor>> all() {
        log.info("Get all doctors");
        return new ResponseEntity<>(doctorService.getAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Doctor> create(Doctor doctor) {
        log.info("Create doctor");
        return new ResponseEntity<>(doctorService.create(doctor), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doctor> get(@PathVariable Long id) {
        log.info("Get doctor by id [{}]", id);
        return new ResponseEntity<>(doctorService.getById(id), HttpStatus.OK);
    }

    @GetMapping("/find/{key}")
    public ResponseEntity<List<Doctor>> getByKey(@PathVariable String key) {
        log.info("Get doctor by key [{}]", key);
        return new ResponseEntity<>(doctorService.getByKey(key), HttpStatus.OK);
    }
}
