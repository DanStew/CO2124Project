package com.example.part1.controller;

import com.example.part1.domain.Appointments;
import com.example.part1.domain.Doctor;
import com.example.part1.domain.ErrorInfo;
import com.example.part1.dtos.AppointmentsDto;
import com.example.part1.dtos.DoctorDto;
import com.example.part1.service.AppointmentsService;
import com.example.part1.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import com.example.part1.repo.DoctorRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class DoctorRestController {

    private final DoctorRepo doctorRepo;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private AppointmentsService appointmentsService;

    public DoctorRestController(DoctorRepo doctorRepo) {
        this.doctorRepo = doctorRepo;
    }

    @GetMapping("/doctors")
    public ResponseEntity<?> doctors() {
        List<Doctor> doctors = doctorRepo.findAll();

        List<DoctorDto> doctorDtos = new ArrayList<>();

        for (Doctor doctor: doctors) {
            doctorDtos.add(doctorService.convertToDoctorDto(doctor));
        }

        return doctors.isEmpty() ? ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ErrorInfo("No Doctors found"))
                : ResponseEntity.ok(doctorDtos);
    }

    @PostMapping("doctors")
    public ResponseEntity<?> newDoctor(@RequestBody Doctor doctor, UriComponentsBuilder ucBuilder) {
        if(doctorRepo.existsById(doctor.getId())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorInfo("Doctor " + doctor.getName() + " already exists"));
        }
        doctorRepo.save(doctor);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/doctors/{id}").buildAndExpand(doctor.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    @GetMapping("doctors/{id}")
    public ResponseEntity<?> getDoctor(@PathVariable Long id) {
        Optional<Doctor> doctor = doctorRepo.findById(id);
        return doctor.isPresent() ? ResponseEntity.ok(doctorService.convertToDoctorDto(doctor.get()))
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorInfo("Doctor " + id + " not found"));
    }

    @PutMapping("doctors/{id}")
    public ResponseEntity<?> updateDoctor(@RequestBody Doctor doctor, @PathVariable Long id) {
        Optional<Doctor> doctorOptional = doctorRepo.findById(id);
        if (doctorOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorInfo("Doctor " + id + " not found"));
        }
        Doctor currentDoctor = doctorOptional.get();
        currentDoctor.setName(doctor.getName());
        currentDoctor.setSpecialisation(doctor.getSpecialisation());
        currentDoctor.setEmail(doctor.getEmail());
        currentDoctor.setPhoneNumber(doctor.getPhoneNumber());
        currentDoctor.setAppointmentsList(doctor.getAppointmentsList());
        doctorRepo.save(currentDoctor);
        return ResponseEntity.ok(doctorService.convertToDoctorDto(doctor));
    }

    @DeleteMapping("doctor/{id}")
    public ResponseEntity<?> deleteDoctor(@PathVariable Long id) {
        if (!doctorRepo.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorInfo("Doctor " + id + " not found"));
        }
        doctorRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("doctor/{id}/appointments")
    public ResponseEntity<?> getDoctorAppointmentsById(@PathVariable Long id) {
        Optional<Doctor> optionalDoctor = doctorRepo.findById(id);
        if (optionalDoctor.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Doctor " + id + " appointments not found");
        }
        List<Appointments> appointments = optionalDoctor.get().getAppointmentsList();
        if (appointments.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("No Appointments found");
        }

        //Turning appointments into a list of appointmentDtos
        List<AppointmentsDto> appointmentsDtos = new ArrayList<>();

        for (Appointments appointment: appointments) {
            appointmentsDtos.add(appointmentsService.convertToAppointmentDto(appointment));
        }

        return ResponseEntity.ok(appointmentsDtos);
    }

}