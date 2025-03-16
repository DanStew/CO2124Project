package com.example.part1.controller;

import com.example.part1.domain.Appointments;
import com.example.part1.domain.Patient;
import com.example.part1.domain.Record;
import com.example.part1.repo.AppointmentsRepo;
import com.example.part1.repo.DoctorRepo;
import com.example.part1.repo.PatientRepo;
import com.example.part1.domain.ErrorInfo;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")

public class PatientRestController {

    private final PatientRepo patientRepo;
    private final AppointmentsRepo appointmentsRepo;
    private final DoctorRepo doctorRepo;

    public PatientRestController(PatientRepo patientRepo, AppointmentsRepo appointmentsRepo, DoctorRepo doctorRepo) {
        this.patientRepo = patientRepo;
        this.appointmentsRepo = appointmentsRepo;
        this.doctorRepo = doctorRepo;
    }

    @GetMapping("/patients")
    public ResponseEntity<?> patients(Model model) {
        List<Patient> patients = patientRepo.findAll();

        return patients.isEmpty() ?   ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ErrorInfo("No Patients found"))
                : ResponseEntity.ok(patients);

    }
    @PostMapping("patients")
    public ResponseEntity<?> newPatient(@RequestBody Patient patient, UriComponentsBuilder ucBuilder) {
        if(patientRepo.existsById(patient.getId())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorInfo("Patient " + patient.getName() + " already exists"));
        }
        patientRepo.save(patient);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/patients/{id}").buildAndExpand(patient.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
    @GetMapping("patients/{id}")
    public ResponseEntity<?> getPatient(@PathVariable Long id) {
        Optional<Patient> patient = patientRepo.findById(id);
        return patient.isPresent() ? ResponseEntity.ok(patient.get())
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorInfo("Patient " + id + " not found"));
    }
    @PutMapping("patients/{id}")
    public ResponseEntity<?> updatePatient(@PathVariable Long id, @RequestBody Patient patient) {
        Optional<Patient> patientOptional = patientRepo.findById(id);
        if (patientOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorInfo("Patient " + id + " not found"));
        }
        Patient currentPatient = patientOptional.get();
        currentPatient.setName(patient.getName());
        currentPatient.setAddress(patient.getAddress());
        currentPatient.setEmail(patient.getEmail());
        currentPatient.setPhoneNumber(patient.getPhoneNumber());
        currentPatient.setAppointmentsList(patient.getAppointmentsList());
        patientRepo.save(currentPatient);
        return ResponseEntity.ok(patient);

    }
    @DeleteMapping("patients/{id}")
    public ResponseEntity<?> deletePatient(@PathVariable Long id) {
        if (!patientRepo.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorInfo("Patient " + id + " not found"));
        }

        patientRepo.deleteById(id);
        return ResponseEntity.noContent().build();

    }
    @GetMapping("patients/{id}/appointments")
    public ResponseEntity<?> getPatientAppointmentsById(@PathVariable Long id) {
        Optional<Patient> optionalPatient = patientRepo.findById(id);

        if (optionalPatient.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Patient " + id + " appointments not found");
        }

        List<Appointments> appointments = optionalPatient.get().getAppointmentsList();

        if (appointments.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("No Appointments found");
        }

        return ResponseEntity.ok(appointments);
    }
    @GetMapping("patients/{id}/medical-records")
    public ResponseEntity<?> getPatientMedicalRecords(@PathVariable Long id) {
        Optional<Patient> optionalPatient = patientRepo.findById(id);
        if (optionalPatient.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient " + id + " not found");
        }
        List<Record> medicalRecords = optionalPatient.get().getMedicalRecords();
        if(medicalRecords.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No medical records found");
        }
        return ResponseEntity.ok(medicalRecords);
    }


}
