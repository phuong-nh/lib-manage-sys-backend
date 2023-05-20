package com.phuongnh.personal.library_management_system.controller;

import com.phuongnh.personal.library_management_system.dto.ReservationDTO;
import com.phuongnh.personal.library_management_system.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('SUPERUSER')")
    public ResponseEntity<List<ReservationDTO>> getAllReservations() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('SUPERUSER')")
    public ResponseEntity<ReservationDTO> getReservationById(@PathVariable UUID id) {
        return ResponseEntity.ok(reservationService.getReservationById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('SUPERUSER')")
    public ResponseEntity<ReservationDTO> createReservation(@RequestBody ReservationDTO reservationDTO) {
        return new ResponseEntity<>(reservationService.createReservation(reservationDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('SUPERUSER')")
    public ResponseEntity<Void> deleteReservation(@PathVariable UUID id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
