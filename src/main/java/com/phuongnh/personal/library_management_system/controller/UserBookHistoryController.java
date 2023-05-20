package com.phuongnh.personal.library_management_system.controller;

import com.phuongnh.personal.library_management_system.dto.UserBookHistoryDTO;
import com.phuongnh.personal.library_management_system.model.UserBookHistoryID;
import com.phuongnh.personal.library_management_system.service.UserBookHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/userBookHistory")
public class UserBookHistoryController {
    @Autowired
    private UserBookHistoryService userBookHistoryService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('SUPERUSER')")
    public ResponseEntity<List<UserBookHistoryDTO>> getAllUserBookHistories() {
        return ResponseEntity.ok(userBookHistoryService.getAllUserBookHistories());
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('SUPERUSER')")
    public ResponseEntity<UserBookHistoryDTO> createUserBookHistory(@RequestBody UserBookHistoryDTO userBookHistoryDTO) {
        return ResponseEntity.ok(userBookHistoryService.createUserBookHistory(userBookHistoryDTO));
    }

    @PutMapping("/{userId}/{bookId}")
    @PreAuthorize("hasAnyAuthority('SUPERUSER')")
    public ResponseEntity<UserBookHistoryDTO> updateUserBookHistory(@PathVariable UUID userId, @PathVariable UUID bookId,
                                                                    @RequestBody UserBookHistoryDTO userBookHistoryDTO) {
        return ResponseEntity.ok(userBookHistoryService.updateUserBookHistory(new UserBookHistoryID(userId, bookId), userBookHistoryDTO));
    }

    @DeleteMapping("/{userId}/{bookId}")
    @PreAuthorize("hasAnyAuthority('SUPERUSER')")
    public ResponseEntity<Void> deleteUserBookHistory(@PathVariable UUID userId, @PathVariable UUID bookId) {
        userBookHistoryService.deleteUserBookHistory(new UserBookHistoryID(userId, bookId));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/history/{userId}")
    @PreAuthorize("hasAnyAuthority('SUPERUSER')")
    public ResponseEntity<List<UserBookHistoryDTO>> getUserHistory(@PathVariable UUID userId) {
        return ResponseEntity.ok(userBookHistoryService.getUserHistory(userId));
    }
}
