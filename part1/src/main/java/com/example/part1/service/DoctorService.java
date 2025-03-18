package com.example.part1.service;

import com.example.part1.domain.Appointments;
import com.example.part1.domain.Doctor;
import com.example.part1.dtos.AppointmentsDto;
import com.example.part1.dtos.DoctorDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorService {

    //Function to convert a Doctor into a DoctorDto, ready to be output
    public DoctorDto convertToDoctorDto(Doctor doctor){

        //Making the list of AppointmentIds
        List<Long> appointmentsIds = new ArrayList<>();
        for (Appointments appointments : doctor.getAppointmentsList()){
            appointmentsIds.add(appointments.getId());
        }

        //Returning the DoctorDto
        return new DoctorDto(
                doctor.getId(),
                doctor.getName(),
                doctor.getSpecialisation(),
                doctor.getEmail(),
                doctor.getPhoneNumber(),
                appointmentsIds
        );
    }
}
