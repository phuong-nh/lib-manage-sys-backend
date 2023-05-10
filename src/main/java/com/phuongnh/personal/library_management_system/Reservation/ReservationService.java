package com.phuongnh.personal.library_management_system.Reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation getReservationById(UUID id) {
        return reservationRepository.findById(id).orElseThrow(() -> new RuntimeException("Reservation not found"));
    }

    public Reservation createReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public Reservation updateReservation(UUID id, Reservation reservation) {
        Reservation existingReservation = getReservationById(id);
        existingReservation.setBook(reservation.getBook());
        existingReservation.setUser(reservation.getUser());
        existingReservation.setReservationDate(reservation.getReservationDate());
        existingReservation.setExpirationDate(reservation.getExpirationDate());
        return reservationRepository.save(existingReservation);
    }

    public void deleteReservation(UUID id) {
        reservationRepository.delete(getReservationById(id));
    }
}
