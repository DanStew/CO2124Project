package com.example.part1.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Doctor {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    // unique identifier for a doctor
    private String name;    // the doctor's full name, e.g., "Dr. Kehinde"
    private String specialisation;  // the doctor's specialisation (e.g., "Cardiology", "Neurology")
    private String email;   // the doctor's email address
    private String phoneNumber;     // the doctor's phone number
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Appointments> appointmentsList = new ArrayList<>();
    // All doctor entity attributes

    public Doctor(Long id, String name, String specialisation, String email, String phoneNumber){
        this.id = id;
        this.name = name;
        this.specialisation = specialisation;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
    public Doctor() {}
    // Method for creating a Doctor entity

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSpecialisation() {
        return specialisation;
    }
    public void setSpecialisation(String specialisation) {
        this.specialisation = specialisation;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public List<Appointments> getAppointmentsList() {
        return appointmentsList;
    }
    public void setAppointmentsList(List<Appointments> appointmentsList) {
        this.appointmentsList = appointmentsList;
    }
    // Getters and setters for the Doctor attributes

}

// Doctor is Ayo's part (Domain and Controller), due 20th March
// Whole project due on the 24th of April