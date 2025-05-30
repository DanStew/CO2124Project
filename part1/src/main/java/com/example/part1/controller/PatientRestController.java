package com.example.part1.controller;

import com.example.part1.domain.*;
import com.example.part1.domain.Record;
import com.example.part1.dtos.AppointmentsDto;
import com.example.part1.dtos.PatientDto;
import com.example.part1.dtos.RecordDto;
import com.example.part1.repo.PatientRepo;
import com.example.part1.service.AppointmentsService;
import com.example.part1.service.PatientService;
import com.example.part1.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PatientRestController {

    private final PatientRepo patientRepo;

    @Autowired
    private PatientService patientService;

    @Autowired
    private AppointmentsService appointmentsService;

    @Autowired
    private RecordService recordService;

    public PatientRestController(PatientRepo patientRepo) {
        this.patientRepo = patientRepo;
    }

    @GetMapping("/patients")
    public ResponseEntity<?> patients(Model model) {
        List<Patient> patients = patientRepo.findAll();

        List<PatientDto> patientDtos = new ArrayList<>();

        for (Patient patient : patients) {
            patientDtos.add(patientService.convertToPatientDto(patient));
        }

        return patients.isEmpty() ?   ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorInfo("No Patients found"))
                : ResponseEntity.ok(patientDtos);

    }
    @PostMapping("patients")
    public ResponseEntity<?> newPatient(@RequestBody Patient patient, UriComponentsBuilder ucBuilder) {
        if(patient.getId() != null && patientRepo.existsById(patient.getId())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorInfo("Patient " + patient.getName() + " already exists"));
        }
        patient = patientRepo.save(patient);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/patients/{id}").buildAndExpand(patient.getId()).toUri());
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(patientService.convertToPatientDto(patient));
    }
    @GetMapping("patients/{id}")
    public ResponseEntity<?> getPatient(@PathVariable Long id) {
        Optional<Patient> patient = patientRepo.findById(id);
        return patient.isPresent() ? ResponseEntity.ok(patientService.convertToPatientDto(patient.get()))
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
        patient = patientRepo.save(currentPatient);
        return ResponseEntity.ok(patientService.convertToPatientDto(patient));

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
                    .body("Patient " + id + " not found");
        }

        List<Appointments> appointments = optionalPatient.get().getAppointmentsList();

        if (appointments.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("No Appointments found");
        }

        //Converting the appointments to appointmentDtos
        List<AppointmentsDto> appointmentsDtos = new ArrayList<>();

        for( Appointments appointment : appointments){
            appointmentsDtos.add(appointmentsService.convertToAppointmentDto(appointment));
        }

        return ResponseEntity.ok(appointmentsDtos);
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

        //Converting the Records to a list of RecordDtos
        List<RecordDto> recordDtos = new ArrayList<>();

        for (Record record : medicalRecords){
            recordDtos.add(recordService.convertToRecordDto(record));
        }

        return ResponseEntity.ok(recordDtos);
    }
}