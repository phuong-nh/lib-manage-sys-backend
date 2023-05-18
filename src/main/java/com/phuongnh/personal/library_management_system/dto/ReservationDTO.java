package com.phuongnh.personal.library_management_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {
    private UUID id;
    private UUID BookId;
    private UUID userId;
    private LocalDate reservationDate;
    private LocalDate fulfillmentDate;
    private LocalDate expirationDate;
    private Boolean isFulfilled;
}

