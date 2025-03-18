package com.example.part1.service;

import com.example.part1.domain.Appointments;
import com.example.part1.domain.Record;
import com.example.part1.dtos.AppointmentsDto;
import com.example.part1.dtos.RecordDto;
import org.springframework.stereotype.Service;

@Service
public class RecordService {

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
}
