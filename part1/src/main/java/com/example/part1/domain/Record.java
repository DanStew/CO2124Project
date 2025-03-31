package com.example.part1.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Optional;

@Entity
@Table
public class Record {
    //The attributes for the MedicalRecord entity
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Timestamp recordDate;
    private String diagnosis;
    private String treatment;
    private String notes;
    @ManyToOne()
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    //Constructors for Records
    public Record(long id, Timestamp recordDate, String diagnosis, String treatment, String notes, Optional<Patient> patient) {
        this.id = id;
        this.recordDate = recordDate;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.notes = notes;
        this.patient = patient.orElse(null);
    }

    public Record() {}

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
    public Patient getPatient() {return patient;}
    public void setPatient(Patient patient) {this.patient = patient;}
}
