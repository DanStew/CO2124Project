package com.example.part1.service;

import com.example.part1.domain.Appointments;
import com.example.part1.domain.Patient;
import com.example.part1.domain.Record;
import com.example.part1.dtos.AppointmentsDto;
import com.example.part1.dtos.RecordDto;
import com.example.part1.repo.PatientRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RecordService {

    private final PatientRepo patientRepo;

    public RecordService(PatientRepo patientRepo) {
        this.patientRepo = patientRepo;
    }

    //Function to convert a record into a recordDto, ready to be output
    public RecordDto convertToRecordDto(Record record) {
        return new RecordDto(
                record.getId(),
                record.getRecordDate(),
                record.getDiagnosis(),
                record.getTreatment(),
                record.getNotes(),
                record.getPatient() != null ? record.getPatient().getId() : null
        );
    }

    //Function to convert a recordDto into a record, ready to save
    public Record convertToRecord(RecordDto recordDto) {
        //Getting the patient object
        Optional<Patient> patient = patientRepo.findById(recordDto.getPatientId());
        //Returning the record
        return new Record(recordDto.getId(), recordDto.getRecordDate(),
                recordDto.getDiagnosis(), recordDto.getTreatment(),
                recordDto.getNotes(), patient);
    }
}
