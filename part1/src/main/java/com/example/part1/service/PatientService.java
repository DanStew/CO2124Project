package com.example.part1.service;

import com.example.part1.domain.Appointments;
import com.example.part1.domain.Doctor;
import com.example.part1.domain.Patient;
import com.example.part1.domain.Record;
import com.example.part1.dtos.DoctorDto;
import com.example.part1.dtos.PatientDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientService {

    //Function to convert a Patient into a PatientDto, ready to be output
    public PatientDto convertToPatientDto(Patient patient){

        //Making the list of AppointmentIds
        List<Long> appointmentsIds = new ArrayList<>();
        for (Appointments appointments : patient.getAppointmentsList()){
            appointmentsIds.add(appointments.getId());
        }

        //Making a list of MedicalRecord Ids
        List<Long> medicalRecordIds = new ArrayList<>();
        for (Record record : patient.getMedicalRecords()){
            medicalRecordIds.add(record.getId());
        }

        //Returning the PatientDto
        return new PatientDto(
                patient.getId(),
                patient.getName(),
                patient.getEmail(),
                patient.getPhoneNumber(),
                patient.getAddress(),
                appointmentsIds,
                medicalRecordIds
        );
    }
}
