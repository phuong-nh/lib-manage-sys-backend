package com.phuongnh.personal.library_management_system.service;

import com.phuongnh.personal.library_management_system.mapper.UserMapper;
import com.phuongnh.personal.library_management_system.dto.UserDTO;
import com.phuongnh.personal.library_management_system.model.User;
import com.phuongnh.personal.library_management_system.model.UserRole;
import com.phuongnh.personal.library_management_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.phuongnh.personal.library_management_system.dto.BookCopyDTO;
import com.phuongnh.personal.library_management_system.mapper.BookCopyMapper;
import com.phuongnh.personal.library_management_system.repository.BookCopyRepository;
import com.phuongnh.personal.library_management_system.dto.ReservationDTO;
import com.phuongnh.personal.library_management_system.mapper.ReservationMapper;
import com.phuongnh.personal.library_management_system.repository.ReservationRepository;

import java.util.stream.Collectors;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookCopyRepository bookCopyRepository;
    @Autowired
    private ReservationRepository reservationRepository;

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return UserMapper.toDTO(user);
    }

    public List<BookCopyDTO> getLoanedBooks(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return bookCopyRepository.findAllByBorrower(user).stream()
                .map(BookCopyMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<ReservationDTO> getReservedBooks(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return reservationRepository.findAllByUser(user).stream()
                .map(ReservationMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UserDTO createUser(UserDTO userDTO) {
        User user = UserMapper.toEntity(userDTO);
//        user.setRole(UserRole.USER);
        User savedUser = userRepository.save(user);
        return UserMapper.toDTO(savedUser);
    }

    public UserDTO updateUser(UUID id, UserDTO userDTO) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!currentUser.getId().equals(id) && (currentUser.getRole() == UserRole.USER || currentUser.getRole() == UserRole.STAFF)) {
            throw new RuntimeException("You are not allowed to update this user");
        }

        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        if(userDTO.getGivenName() != null) {
            user.setGivenName(userDTO.getGivenName());
        }
        if(userDTO.getSurName() != null) {
            user.setSurName(userDTO.getSurName());
        }
        if(userDTO.getIsGivenSurName() != null) {
            user.setIsGivenSurName(userDTO.getIsGivenSurName());
        }
        if(userDTO.getEmail() != null) {
            user.setEmail(userDTO.getEmail());
        }
        if(userDTO.getRole() != null && (currentUser.getRole() == UserRole.ADMIN || currentUser.getRole() == UserRole.SUPERUSER)) {
            user.setRole(UserRole.valueOf(userDTO.getRole()));
        }
        if(userDTO.getImgsrc() != null) {
            user.setImgsrc(userDTO.getImgsrc());
        }
        if(userDTO.getIsBanned() != null) {
            user.setIsBanned(userDTO.getIsBanned());
        }
        User updatedUser = userRepository.save(user);
        return UserMapper.toDTO(updatedUser);
    }

    public void deleteUser(UUID id) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!currentUser.getId().equals(id) && (currentUser.getRole() == UserRole.USER || currentUser.getRole() == UserRole.STAFF)) {
            throw new RuntimeException("You are not allowed to delete this user");
        }

        userRepository.deleteById(id);
    }

    public UserDTO getUserBasicInfoById(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return UserMapper.toBasicInfoDTO(user);
    }

    public UserDTO getUserOwnInfo() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return UserMapper.toDTO(currentUser);
    }

    public List<BookCopyDTO> getUserOwnLoans() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return bookCopyRepository.findAllByBorrower(currentUser).stream()
                .map(BookCopyMapper::toDTO)
                .collect(Collectors.toList());
    }
}
