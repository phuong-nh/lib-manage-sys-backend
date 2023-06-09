package com.phuongnh.personal.library_management_system.controller;

import com.phuongnh.personal.library_management_system.dto.BookCopyDTO;
import com.phuongnh.personal.library_management_system.dto.UserDTO;
import com.phuongnh.personal.library_management_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/basicinfo/{id}")
    public ResponseEntity<UserDTO> getUserBasicInfoById(@PathVariable UUID id) {
        return new ResponseEntity<>(userService.getUserBasicInfoById(id), HttpStatus.OK);
    }

    @GetMapping("/owninfo")
    @PreAuthorize("hasAnyAuthority('ADMIN','STAFF','SUPERUSER','USER')")
    public ResponseEntity<UserDTO> getUserOwnInfo() {
        return new ResponseEntity<>(userService.getUserOwnInfo(), HttpStatus.OK);
    }

    @GetMapping("/ownloans")
    @PreAuthorize("hasAnyAuthority('ADMIN','STAFF','SUPERUSER','USER')")
    public ResponseEntity<List<BookCopyDTO>> getUserOwnLoans() {
        return new ResponseEntity<>(userService.getUserOwnLoans(), HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','STAFF','SUPERUSER')")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','STAFF','SUPERUSER')")
    public ResponseEntity<UserDTO> getUserById(@PathVariable UUID id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','SUPERUSER')")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(userService.createUser(userDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','STAFF','SUPERUSER','USER')")
    public ResponseEntity<UserDTO> updateUser(@PathVariable UUID id, @RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(userService.updateUser(id, userDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','STAFF','SUPERUSER','USER')")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
