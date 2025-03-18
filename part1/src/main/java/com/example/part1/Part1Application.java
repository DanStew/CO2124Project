package com.example.part1;

import com.example.part1.domain.Appointments;
import com.example.part1.domain.Doctor;
import com.example.part1.domain.Patient;
import com.example.part1.domain.Record;
import com.example.part1.repo.AppointmentsRepo;
import com.example.part1.repo.DoctorRepo;
import com.example.part1.repo.PatientRepo;
import com.example.part1.repo.RecordRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@SpringBootApplication
public class Part1Application implements CommandLineRunner {

	@Autowired
	private PatientRepo patientRepo;

	@Autowired
	private DoctorRepo doctorRepo;

	@Autowired
	private RecordRepo recordRepo;

	@Autowired
	private AppointmentsRepo appointmentsRepo;

	public static void main(String[] args) {
		SpringApplication.run(Part1Application.class, args);
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {
						//Creating a patient
								Patient patient1 = new Patient();
								patient1.setName("John");
								patient1.setAddress("Street");
								patient1.setEmail("john@gmail.com");
								patient1.setPhoneNumber("01234");
								patientRepo.save(patient1);
				
								Patient patient2 = new Patient();
								patient2.setName("John2");
								patient2.setAddress("Street2");
								patient2.setEmail("john2@gmail.com");
								patient2.setPhoneNumber("01234");
								patientRepo.save(patient2);
				
								//Creating a doctor
								Doctor doctor1 = new Doctor();
								doctor1.setName("Jim");
								doctor1.setSpecialisation("Doctor");
								doctor1.setEmail("jim@gmail.com");
								doctor1.setPhoneNumber("43210");
								doctorRepo.save(doctor1);
				
								Doctor doctor2 = new Doctor();
								doctor2.setName("Jim2");
								doctor2.setSpecialisation("Doctor2");
								doctor2.setEmail("jim2@gmail.com");
								doctor2.setPhoneNumber("43210");
								doctorRepo.save(doctor2);
				
								//Creating a Medical Record
								Record medicalRecord1 = new Record();
								medicalRecord1.setDiagnosis("Bad");
								medicalRecord1.setNotes("Good record notes");
								medicalRecord1.setRecordDate(new Timestamp(System.currentTimeMillis()));
								medicalRecord1.setTreatment("No Treatment");
								medicalRecord1.setPatient(patient1);
								recordRepo.save(medicalRecord1);
				
								Record medicalRecord2 = new Record();
								medicalRecord2.setDiagnosis("Bad");
								medicalRecord2.setNotes("Good record notes");
								medicalRecord2.setRecordDate(new Timestamp(System.currentTimeMillis()));
								medicalRecord2.setTreatment("No Treatment");
								medicalRecord2.setPatient(patient1);
								recordRepo.save(medicalRecord2);
				
								//Creating a Appointment
								Appointments appointments1 = new Appointments();
								appointments1.setDoctor(doctor1);
								appointments1.setPatient(patient1);
								appointments1.setAppointmentDate(new Timestamp(System.currentTimeMillis()));
								appointments1.setNotes("No Notes");
								appointments1.setStatus("Good");
								appointments1.setMedicalRecord(medicalRecord1);
								appointmentsRepo.save(appointments1);
				
				
								Appointments appointments2 = new Appointments();
								appointments2.setDoctor(doctor1);
								appointments2.setPatient(patient1);
								appointments2.setAppointmentDate(new Timestamp(System.currentTimeMillis()));
								appointments2.setNotes("No Notes");
								appointments2.setStatus("Good");
								appointmentsRepo.save(appointments2);
	}
}
