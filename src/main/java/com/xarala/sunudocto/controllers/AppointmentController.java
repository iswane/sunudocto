package com.xarala.sunudocto.controllers;

import com.xarala.sunudocto.entities.Appointment;
import com.xarala.sunudocto.services.AppointmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/appointment")
@RequiredArgsConstructor
@Slf4j
public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping
    public ResponseEntity<List<Appointment>> all() {
        log.info("Get all appointments");
        return new ResponseEntity<>(appointmentService.getAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Appointment> create(Appointment appointment) {
        log.info("Create appointment");
        return new ResponseEntity<>(appointmentService.create(appointment), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> get(@PathVariable Long id) {
        log.info("Get appointment by id [{}]", id);
        return new ResponseEntity<>(appointmentService.getById(id), HttpStatus.OK);
    }

    @GetMapping("/all/doctor/{id}")
    public ResponseEntity<List<Appointment>> getAllByDoctor(@PathVariable Long id) {
        log.info("Get all appointments by doctor id [{}]", id);
        return new ResponseEntity<>(appointmentService.getAllByDoctor(id), HttpStatus.OK);
    }

    @GetMapping("/all/patient/{id}")
    public ResponseEntity<List<Appointment>> getAllByPatient(@PathVariable Long id) {
        log.info("Get all appointments by patient id [{}]", id);
        return new ResponseEntity<>(appointmentService.getAllByPatient(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        log.info("Delete appointment by id [{}]", id);
        appointmentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
