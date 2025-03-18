package com.example.part1.dtos;

import java.sql.Time;
import java.sql.Timestamp;

public class RecordDto {
    //Attributes we want to expose, as part of the dto
    private long id;
    private Timestamp recordDate;
    private String diagnosis;
    private String treatment;
    private String notes;
    private long patientId;

    //Constructor to make the dto
    public RecordDto(long id, Timestamp recordDate, String diagnosis, String treatment, String notes, long patientId) {
        this.id = id;
        this.recordDate = recordDate;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.notes = notes;
        this.patientId = patientId;
    }

    //Getters for the attributes
    public long getId() {return id;}
    public Timestamp getRecordDate() {return recordDate;}
    public String getDiagnosis() {return diagnosis;}
    public String getTreatment() {return treatment;}
    public String getNotes() {return notes;}
    public long getPatientId() {return patientId;}
}
