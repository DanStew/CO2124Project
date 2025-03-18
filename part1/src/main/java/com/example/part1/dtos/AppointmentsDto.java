package com.example.part1.dtos;

import java.sql.Timestamp;
import com.example.part1.domain.Record;


public class AppointmentsDto {
    //Attributes to be returned
    private long id;
    private Timestamp appointmentDate;
    private String status;
    private String notes;
    private long doctorId;
    private long patientId;
    private Long recordId;

    //Constructor to make the AssignmentDto
    public AppointmentsDto(long id, Timestamp appointmentDate, String status, String notes, long doctorId, long patientId, Long recordId) {
        this.id = id;
        this.appointmentDate = appointmentDate;
        this.status = status;
        this.notes = notes;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.recordId = recordId;
    }

    //Getters and Setters
    public long getId(){return id;}
    public Timestamp getAppointmentDate(){return appointmentDate;}
    public String getStatus(){return status;}
    public String getNotes(){return notes;}
    public long getDoctorId(){return doctorId;}
    public long getPatientId(){return patientId;}
    public Long getRecordId(){return recordId;}
}
