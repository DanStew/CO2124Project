package com.example.part1.domain;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table
public class Record {
    //The attributes for the MedicalRecord entity
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private Timestamp recordDate;
    private String diagnosis;
    private String treatment;
    private String notes;

    //Storing the appointment this record is attached to
    //There must be an appointment and each appointment can only have one record
    @OneToOne
    @JoinColumn(name = "appointment_id", nullable = false, unique = true)
    private Appointments appointment;

    //Getters and setter for the attributes
    public long getId() {return id;}
    public void setId(long id) {this.id = id;}
    public Timestamp getRecordDate() {return recordDate;}
    public void setRecordDate(Timestamp recordDate) {this.recordDate = recordDate;}
    public String getDiagnosis() {return diagnosis;}
    public void setDiagnosis(String diagnosis) {this.diagnosis = diagnosis;}
    public String getTreatment() {return treatment;}
    public void setTreatment(String treatment) {this.treatment = treatment;}
    public String getNotes() {return notes;}
    public void setNotes(String notes) {this.notes = notes;}
    public Appointments getAppointment() {return appointment;}
    public void setAppointment(Appointments appointment) {this.appointment = appointment;}
}
