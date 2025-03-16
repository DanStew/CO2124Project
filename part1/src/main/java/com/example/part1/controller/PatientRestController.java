package com.example.part1.controller;

import com.example.part1.repo.AppointmentsRepo;
import com.example.part1.repo.DoctorRepo;
import com.example.part1.repo.PatientRepo;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PatientRestController {

    private final PatientRepo patientRepo;
    private final AppointmentsRepo appointmentsRepo;
    private final DoctorRepo doctorRepo;

    public PatientRestController(PatientRepo patientRepo, AppointmentsRepo appointmentsRepo, DoctorRepo doctorRepo) {
        this.patientRepo = patientRepo;
        this.appointmentsRepo = appointmentsRepo;
        this.doctorRepo = doctorRepo;
    }

    @RequestMapping("/deletePatient")
    @Transactional
    public String deletePatient() {
        patientRepo.deleteById(1L);
        return "Deleted Patient";
    }

    @RequestMapping("/deleteAppointment")
    public String deleteAppointment() {
        appointmentsRepo.deleteById(1L);
        return "Deleted Appointment";
    }

    @RequestMapping("/deleteDoctor")
    public String deleteDoctor() {
        doctorRepo.deleteById(1L);
        return "Deleted Doctor";
    }
}
