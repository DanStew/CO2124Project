package com.example.part1.controller;

import com.example.part1.domain.Appointments;
import com.example.part1.domain.ErrorInfo;
import com.example.part1.domain.Record;
import com.example.part1.repo.AppointmentsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
public class AppointmentRestController {

    @Autowired
    private AppointmentsRepo appointmentsRepo;

    //Function to get all of the appointments
    @GetMapping("/appointments")
    public ResponseEntity<?> appointments(Model model) {
        List<Appointments> appointments = appointmentsRepo.findAll();

        return appointments.isEmpty() ?   ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorInfo("No Appointments found"))
                : ResponseEntity.ok(appointments);
    }

    //Function to create a new appointment
    @PostMapping("/appointments")
    public ResponseEntity<?> newAppointment(@RequestBody Appointments appointments, UriComponentsBuilder ucBuilder) {
        if(appointmentsRepo.existsById(appointments.getId())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorInfo("Appointment " + appointments.getId() + " already exists"));
        }
        appointmentsRepo.save(appointments);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/appointments/{id}").buildAndExpand(appointments.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    //Function to get an appointment by a specific id
    @GetMapping("appointments/{id}")
    public ResponseEntity<?> getAppointment(@PathVariable Long id) {
        Optional<Appointments> appointments = appointmentsRepo.findById(id);
        return appointments.isPresent() ? ResponseEntity.ok(appointments.get())
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorInfo("Appointment " + id + " not found"));
    }

    //Function to update an appointment by a specific id
    @PutMapping("appointments/{id}")
    public ResponseEntity<?> updateAppointment(@PathVariable Long id, @RequestBody Appointments appointments) {
        Optional<Appointments> appointmentsOptional = appointmentsRepo.findById(id);
        if (appointmentsOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorInfo("Appointment " + id + " not found"));
        }
        Appointments currentAppointment = appointmentsOptional.get();
        currentAppointment.setId(appointments.getId());
        currentAppointment.setAppointmentDate(appointments.getAppointmentDate());
        currentAppointment.setStatus(appointments.getStatus());
        currentAppointment.setNotes(appointments.getNotes());
        currentAppointment.setDoctor(appointments.getDoctor());
        currentAppointment.setPatient(appointments.getPatient());
        currentAppointment.setMedicalRecord(appointments.getMedicalRecord());
        appointmentsRepo.save(currentAppointment);
        return ResponseEntity.ok(appointments);
    }

    //Function to delete an appointment by a specific id
    @DeleteMapping("appointments/{id}")
    public ResponseEntity<?> deleteAppointment(@PathVariable Long id) {
        if (!appointmentsRepo.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorInfo("Appointment " + id + " not found"));
        }
        appointmentsRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    //Function to get the medical record for a specific appointment
    @GetMapping("appointments/{id}/medical-records")
    public ResponseEntity<?> getAppointmentMedicalRecord(@PathVariable Long id) {
        Optional<Appointments> optionalAppointments = appointmentsRepo.findById(id);
        if (optionalAppointments.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Appointment " + id + " not found");
        }
        Record medicalRecord = optionalAppointments.get().getMedicalRecord();
        if(medicalRecord == null){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No medical record found");
        }
        return ResponseEntity.ok(medicalRecord);
    }
}
