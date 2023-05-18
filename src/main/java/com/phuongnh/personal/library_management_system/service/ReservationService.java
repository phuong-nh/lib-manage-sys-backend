package com.phuongnh.personal.library_management_system.service;

import com.phuongnh.personal.library_management_system.mapper.ReservationMapper;
import com.phuongnh.personal.library_management_system.dto.ReservationDTO;
import com.phuongnh.personal.library_management_system.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    public List<ReservationDTO> getAllReservations() {
        return reservationRepository.findAll().stream().map(ReservationMapper::toDTO).toList();
    }

    public ReservationDTO getReservationById(UUID id) {
        return ReservationMapper.toDTO(reservationRepository.findById(id).orElseThrow(() -> new RuntimeException("Reservation not found")));
    }

    public ReservationDTO createReservation(ReservationDTO reservationDTO) {
        return ReservationMapper.toDTO(reservationRepository.save(ReservationMapper.toEntity(reservationDTO)));
    }



    public void deleteReservation(UUID id) {
        reservationRepository.deleteById(id);
    }
}
