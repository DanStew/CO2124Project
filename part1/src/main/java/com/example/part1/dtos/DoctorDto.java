package com.example.part1.dtos;

import java.util.ArrayList;
import java.util.List;

public class DoctorDto {
    //Attributes exposed by the Dto
    private long id;
    private String name;
    private String specialisation;
    private String email;
    private String phoneNumber;
    private List<Long> appointmentsIds = new ArrayList<>();

    //Constructor to make a DoctorDto
    public DoctorDto(long id, String name, String specialisation, String email, String phoneNumber, List<Long> appointmentsIds) {
        this.id = id;
        this.name = name;
        this.specialisation = specialisation;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.appointmentsIds = appointmentsIds;
    }

    //Getters for the attributes
    public long getId(){return id;}
    public String getName(){return name;}
    public String getSpecialisation(){return specialisation;}
    public String getEmail(){return email;}
    public String getPhoneNumber(){return phoneNumber;}
    public List<Long> getAppointmentsIds(){return appointmentsIds;}
}
