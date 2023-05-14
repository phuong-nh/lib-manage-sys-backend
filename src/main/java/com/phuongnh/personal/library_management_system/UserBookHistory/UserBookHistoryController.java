package com.phuongnh.personal.library_management_system.UserBookHistory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/userBookHistory")
public class UserBookHistoryController {
    @Autowired
    private UserBookHistoryService userBookHistoryService;

    @GetMapping
    public ResponseEntity<List<UserBookHistoryDTO>> getAllUserBookHistories() {
        return ResponseEntity.ok(userBookHistoryService.getAllUserBookHistories());
    }

    @PostMapping
    public ResponseEntity<UserBookHistoryDTO> createUserBookHistory(@RequestBody UserBookHistoryDTO userBookHistoryDTO) {
        return ResponseEntity.ok(userBookHistoryService.createUserBookHistory(userBookHistoryDTO));
    }

    @PutMapping("/{userId}/{bookId}")
    public ResponseEntity<UserBookHistoryDTO> updateUserBookHistory(@PathVariable UUID userId, @PathVariable UUID bookId,
                                                                    @RequestBody UserBookHistoryDTO userBookHistoryDTO) {
        return ResponseEntity.ok(userBookHistoryService.updateUserBookHistory(new UserBookHistoryID(userId, bookId), userBookHistoryDTO));
    }

    @DeleteMapping("/{userId}/{bookId}")
    public ResponseEntity<Void> deleteUserBookHistory(@PathVariable UUID userId, @PathVariable UUID bookId) {
        userBookHistoryService.deleteUserBookHistory(new UserBookHistoryID(userId, bookId));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<List<UserBookHistoryDTO>> getUserHistory(@PathVariable UUID userId) {
        return ResponseEntity.ok(userBookHistoryService.getUserHistory(userId));
    }
}
