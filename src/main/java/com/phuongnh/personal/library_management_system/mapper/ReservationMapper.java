package com.phuongnh.personal.library_management_system.mapper;

import com.phuongnh.personal.library_management_system.dto.ReservationDTO;
import com.phuongnh.personal.library_management_system.model.Reservation;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {
    public static ReservationDTO toDTO(Reservation reservation) {
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setId(reservation.getId());
        reservationDTO.setBookId(reservation.getBook().getId());
        reservationDTO.setUserId(reservation.getUser().getId());
        reservationDTO.setReservationDate(reservation.getReservationDate());
        reservationDTO.setFulfillmentDate(reservation.getFulfillmentDate());
        reservationDTO.setExpirationDate(reservation.getExpirationDate());
        reservationDTO.setIsFulfilled(reservation.getIsFulfilled());
        return reservationDTO;
    }

    public static Reservation toEntity(ReservationDTO reservationDTO) {
        Reservation reservation = new Reservation();
        reservation.setId(reservationDTO.getId());
        reservation.setReservationDate(reservationDTO.getReservationDate());
        reservation.setFulfillmentDate(reservationDTO.getFulfillmentDate());
        reservation.setExpirationDate(reservationDTO.getExpirationDate());
        reservation.setIsFulfilled(reservationDTO.getIsFulfilled());
        return reservation;
    }
}
