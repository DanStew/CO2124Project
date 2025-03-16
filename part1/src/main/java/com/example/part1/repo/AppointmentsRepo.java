package com.example.part1.repo;

import com.example.part1.domain.Appointments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentsRepo extends JpaRepository<Appointments, Long> {
}
