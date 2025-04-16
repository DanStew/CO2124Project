package com.example.part1.controller;

import com.example.part1.domain.Appointments;
import com.example.part1.domain.ErrorInfo;
import com.example.part1.domain.Patient;
import com.example.part1.domain.Record;
import com.example.part1.dtos.AppointmentsDto;
import com.example.part1.repo.AppointmentsRepo;
import com.example.part1.repo.DoctorRepo;
import com.example.part1.repo.PatientRepo;
import com.example.part1.repo.RecordRepo;
import com.example.part1.service.AppointmentsService;
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
public class AppointmentRestController {

    @Autowired
    private AppointmentsRepo appointmentsRepo;

    @Autowired
    private AppointmentsService appointmentsService;

    @Autowired
    private RecordService recordService;

    @Autowired
    private RecordRepo recordRepo;

    @Autowired
    private PatientRepo patientRepo;

    @Autowired
    private DoctorRepo doctorRepo;

    //Function to get all of the appointments
    @GetMapping("/appointments")
    public ResponseEntity<?> appointments(Model model) {
        List<Appointments> appointments = appointmentsRepo.findAll();

        List<AppointmentsDto> appointmentsDtos = new ArrayList<>();

        for (Appointments appointment: appointments) {
            appointmentsDtos.add(appointmentsService.convertToAppointmentDto(appointment));
        }

        return appointments.isEmpty() ?   ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorInfo("No Appointments found"))
                : ResponseEntity.ok(appointmentsDtos);
    }

    //Function to create a new appointment
    @PostMapping("/appointments")
    public ResponseEntity<?> newAppointment(@RequestBody AppointmentsDto appointmentsDto, UriComponentsBuilder ucBuilder) {
        //If the appointment id already exists in the db
        if(appointmentsRepo.existsById(appointmentsDto.getId())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorInfo("Appointment " + appointmentsDto.getId() + " already exists"));
        }
        //If the doctor id doesn't exist in the db, return error
        if(!doctorRepo.existsById(appointmentsDto.getDoctorId())){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorInfo("Doctor " + appointmentsDto.getDoctorId() + " doesn't exists"));
        }
        //If the patient id doesn't exist in the db, return error
        if(!patientRepo.existsById(appointmentsDto.getPatientId())){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorInfo("Patient " + appointmentsDto.getPatientId() + " doesn't exists"));
        }
        //If the record id doesn't exist in the db, return error
        if(appointmentsDto.getRecordId() != null && !recordRepo.existsById(appointmentsDto.getRecordId())){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorInfo("Record " + appointmentsDto.getRecordId() + " doesn't exists"));
        }
        //If all the ids exist, make the appointment object and save
        Appointments appointments = appointmentsService.convertToAppointments(appointmentsDto);
        appointments = appointmentsRepo.save(appointments);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/appointments/{id}").buildAndExpand(appointments.getId()).toUri());
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(appointmentsService.convertToAppointmentDto(appointments));
    }

    //Function to get an appointment by a specific id
    @GetMapping("appointments/{id}")
    public ResponseEntity<?> getAppointment(@PathVariable Long id) {
        Optional<Appointments> appointments = appointmentsRepo.findById(id);
        return appointments.isPresent() ? ResponseEntity.ok(appointmentsService.convertToAppointmentDto(appointments.get()))
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorInfo("Appointment " + id + " not found"));
    }

    //Function to update an appointment by a specific id
    @PutMapping("appointments/{id}")
    public ResponseEntity<?> updateAppointment(@PathVariable Long id, @RequestBody AppointmentsDto appointmentsDto) {
        Optional<Appointments> appointmentsOptional = appointmentsRepo.findById(id);
        if (appointmentsOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorInfo("Appointment " + id + " not found"));
        }
        //Ensuring the given appointment id matches the id from the URL
        if (id != appointmentsDto.getId()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorInfo("Appointment " + id + " doesn't match URL Id"));
        }
        //Ensuring there is a valid patient, doctor and record
        //If the doctor id doesn't exist in the db, return error
        if(!doctorRepo.existsById(appointmentsDto.getDoctorId())){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorInfo("Doctor " + appointmentsDto.getDoctorId() + " doesn't exists"));
        }
        //If the patient id doesn't exist in the db, return error
        if(!patientRepo.existsById(appointmentsDto.getPatientId())){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorInfo("Patient " + appointmentsDto.getPatientId() + " doesn't exists"));
        }
        //If the record id doesn't exist in the db, return error
        if(appointmentsDto.getRecordId() != null && !recordRepo.existsById(appointmentsDto.getRecordId())){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorInfo("Record " + appointmentsDto.getRecordId() + " doesn't exists"));
        }
        //Converting the appointmentDto into an appointment
        Appointments newAppointments = appointmentsService.convertToAppointments(appointmentsDto);
        //Updating the current appointment
        Appointments currentAppointment = appointmentsOptional.get();
        currentAppointment.setId(newAppointments.getId());
        currentAppointment.setAppointmentDate(newAppointments.getAppointmentDate());
        currentAppointment.setStatus(newAppointments.getStatus());
        currentAppointment.setNotes(newAppointments.getNotes());
        currentAppointment.setDoctor(newAppointments.getDoctor());
        currentAppointment.setPatient(newAppointments.getPatient());
        currentAppointment.setMedicalRecord(newAppointments.getMedicalRecord());
        currentAppointment = appointmentsRepo.save(currentAppointment);
        return ResponseEntity.ok(appointmentsService.convertToAppointmentDto(currentAppointment));
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
        return ResponseEntity.ok(recordService.convertToRecordDto(medicalRecord));
    }
}