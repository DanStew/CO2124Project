package com.example.part1.domain;

public class Doctor {

    public Long id;    // unique identifier for a doctor
    public String name;    // the doctor's full name, e.g., "Dr. Kehinde"
    public String specialisation;  // the doctor's specialisation (e.g., "Cardiology", "Neurology")
    public String email;   // the doctor's email address
    public String phoneNumber;     // the doctor's phone number

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

}

// Doctor is Ayo's part (Domain and Controller), due 20th March
// Whole project due on the 24th of April