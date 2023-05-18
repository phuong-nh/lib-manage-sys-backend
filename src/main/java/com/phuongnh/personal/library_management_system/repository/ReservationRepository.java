package com.phuongnh.personal.library_management_system.repository;

import com.phuongnh.personal.library_management_system.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.phuongnh.personal.library_management_system.model.User;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
    List<Reservation> findAllByUser(User user);
}
