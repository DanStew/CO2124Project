package com.example.part1.service;

import com.example.part1.domain.Appointments;
import com.example.part1.domain.Doctor;
import com.example.part1.domain.Patient;
import com.example.part1.domain.Record;
import com.example.part1.dtos.AppointmentsDto;
import com.example.part1.repo.DoctorRepo;
import com.example.part1.repo.PatientRepo;
import com.example.part1.repo.RecordRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentsService {

    @Autowired
    private PatientRepo patientRepo;
    @Autowired
    private DoctorRepo doctorRepo;
    @Autowired
    private RecordRepo recordRepo;

    //Function to convert an appointment into an AppointmentDto, ready to be output
    public AppointmentsDto convertToAppointmentDto(Appointments appointments){
        return new AppointmentsDto(
                appointments.getId(),
                appointments.getAppointmentDate(),
                appointments.getStatus(),
                appointments.getNotes(),
                appointments.getDoctor().getId(),
                appointments.getPatient().getId(),
                appointments.getMedicalRecord() != null? appointments.getMedicalRecord().getId() : null
        );
    }

    //Function to convert an appointment dto back into an Appointment
    public Appointments convertToAppointments(AppointmentsDto appointmentsDto){
        //Finding the patient, doctor and medical record by id
        //Don't need to check whether id's exist or not, as they have already been checked
        Patient patient = patientRepo.findById(appointmentsDto.getPatientId()).orElse(null);
        Doctor doctor = doctorRepo.findById(appointmentsDto.getDoctorId()).orElse(null);
        Record medicalRecord = recordRepo.findById(appointmentsDto.getRecordId()).orElse(null);
        //Making and Returning the complete appointment record
        return new Appointments(appointmentsDto.getId(),appointmentsDto.getAppointmentDate(),
                                appointmentsDto.getStatus(),appointmentsDto.getNotes(),
                                doctor,patient,medicalRecord);
    }
}
