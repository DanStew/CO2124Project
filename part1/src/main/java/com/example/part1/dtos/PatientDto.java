package com.example.part1.dtos;

import java.util.ArrayList;
import java.util.List;

public class PatientDto {
    //Attributes we want to expose in the dto
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private List<Long> appointmentIds = new ArrayList<>();
    private List<Long> medicalRecordIds = new ArrayList<>();

    //Constructor to make a Patient Dto
    public PatientDto(Long id, String name, String email, String phoneNumber, String address, List<Long> appointmentIds, List<Long> medicalRecordIds) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.appointmentIds = appointmentIds;
        this.medicalRecordIds = medicalRecordIds;
    }

    //Getters for the attributes
    public Long getId() {return id;}
    public String getName() {return name;}
    public String getEmail() {return email;}
    public String getPhoneNumber() {return phoneNumber;}
    public String getAddress() {return address;}
    public List<Long> getAppointmentIds() {return appointmentIds;}
    public List<Long> getMedicalRecordIds() {return medicalRecordIds;}
}
