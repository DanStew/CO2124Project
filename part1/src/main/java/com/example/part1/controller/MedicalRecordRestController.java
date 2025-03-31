package com.example.part1.controller;

import com.example.part1.domain.ErrorInfo;
import com.example.part1.domain.Record;
import com.example.part1.dtos.RecordDto;
import com.example.part1.repo.PatientRepo;
import com.example.part1.repo.RecordRepo;
import com.example.part1.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class MedicalRecordRestController {

    @Autowired
    private RecordRepo recordRepo;

    @Autowired
    private RecordService recordService;

    @Autowired
    private PatientRepo patientRepo;

    //Function to create a new medical record
    @PostMapping("/medical-records")
    public ResponseEntity<?> newMedicalRecord(@RequestBody RecordDto medicalRecordDto, UriComponentsBuilder ucBuilder) {
        if(medicalRecordDto != null && recordRepo.existsById(medicalRecordDto.getId())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorInfo("Medical Record " + medicalRecordDto.getId() + " already exists"));
        }
        //Checking if the Patient Id exists in Patients
        if(!patientRepo.existsById(medicalRecordDto.getPatientId())){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorInfo("Patient " + medicalRecordDto.getPatientId() + " not found"));
        }
        //Turning the dto into an actual record
        Record medicalRecord = recordService.convertToRecord(medicalRecordDto);
        //Saving the medicalRecord
        medicalRecord = recordRepo.save(medicalRecord);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/medical-records/{id}").buildAndExpand(medicalRecord.getId()).toUri());
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(recordService.convertToRecordDto(medicalRecord));
    }

    //Function to get a record by a specific id
    @GetMapping("medical-records/{id}")
    public ResponseEntity<?> getMedicalRecord(@PathVariable Long id) {
        Optional<Record> optionalMedicalRecord = recordRepo.findById(id);
        return optionalMedicalRecord.isPresent() ? ResponseEntity.ok(recordService.convertToRecordDto(optionalMedicalRecord.get()))
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorInfo("Medical Record " + id + " not found"));
    }
}
