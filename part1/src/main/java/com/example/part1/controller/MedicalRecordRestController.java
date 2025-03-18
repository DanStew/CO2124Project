package com.example.part1.controller;

import com.example.part1.domain.ErrorInfo;
import com.example.part1.domain.Record;
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

    //Function to create a new medical record
    @PostMapping("/medical-records")
    public ResponseEntity<?> newMedicalRecord(@RequestBody Record medicalRecord, UriComponentsBuilder ucBuilder) {
        if(recordRepo.existsById(medicalRecord.getId())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorInfo("Medical Record " + medicalRecord.getId() + " already exists"));
        }
        recordRepo.save(medicalRecord);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/medical-records/{id}").buildAndExpand(medicalRecord.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    //Function to get a record by a specific id
    @GetMapping("medical-records/{id}")
    public ResponseEntity<?> getMedicalRecord(@PathVariable Long id) {
        Optional<Record> optionalMedicalRecord = recordRepo.findById(id);
        return optionalMedicalRecord.isPresent() ? ResponseEntity.ok(recordService.convertToRecordDto(optionalMedicalRecord.get()))
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorInfo("Medical Record " + id + " not found"));
    }
}
