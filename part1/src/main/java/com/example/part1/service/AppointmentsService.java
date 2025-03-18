package com.example.part1.service;

import com.example.part1.domain.Appointments;
import com.example.part1.dtos.AppointmentsDto;
import org.springframework.stereotype.Service;

@Service
public class AppointmentsService {

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
}
