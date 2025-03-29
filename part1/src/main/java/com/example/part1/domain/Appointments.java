package com.example.part1.domain;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table
public class Appointments {
    //Attributes for the Appointments entity
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Timestamp appointmentDate;
    private String status;
    private String notes;
    //Adding the additional relationships that occur

    //A Doctor can have Many appointments, each appointment only has one doctor
    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    //A Patient can have Many appointments, each appointment only has one patient
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @OneToOne(cascade = CascadeType.ALL)
    private Record medicalRecord;

    //Constructor to allow you make an appointment object
    public Appointments(long id, Timestamp appointmentDate, String status, String notes, Doctor doctor, Patient patient, Record record) {
        this.id = id;
        this.appointmentDate = appointmentDate;
        this.status = status;
        this.notes = notes;
        this.doctor = doctor;
        this.patient = patient;
        this.medicalRecord = record;
    }

    public Appointments() {

    }

    //Getters and Setters for the attributes
    public long getId() {return id;}
    public void setId(long id) {this.id = id;}
    public Timestamp getAppointmentDate() {return appointmentDate;}
    public void setAppointmentDate(Timestamp appointmentDate) {this.appointmentDate = appointmentDate;}
    public String getStatus() {return status;}
    public void setStatus(String status) {this.status = status;}
    public String getNotes() {return notes;}
    public void setNotes(String notes) {this.notes = notes;}
    public Doctor getDoctor() {return doctor;}
    public void setDoctor(Doctor doctor) {this.doctor = doctor;}
    public Patient getPatient() {return patient;}
    public void setPatient(Patient patient) {this.patient = patient;}
    public Record getMedicalRecord() {return medicalRecord;}
    public void setMedicalRecord(Record medicalRecord) {this.medicalRecord = medicalRecord;}
}
