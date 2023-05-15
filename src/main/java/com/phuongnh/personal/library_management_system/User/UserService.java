package com.phuongnh.personal.library_management_system.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phuongnh.personal.library_management_system.BookCopy.BookCopyDTO;
import com.phuongnh.personal.library_management_system.BookCopy.BookCopyMapper;
import com.phuongnh.personal.library_management_system.BookCopy.BookCopyRepository;
import com.phuongnh.personal.library_management_system.Reservation.ReservationDTO;
import com.phuongnh.personal.library_management_system.Reservation.ReservationMapper;
import com.phuongnh.personal.library_management_system.Reservation.ReservationRepository;

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
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        if(userDTO.getGivenName() != null) {
            user.setGivenName(userDTO.getGivenName());
        }
        if(userDTO.getSurName() != null) {
            user.setSurName(userDTO.getSurName());
        }
        if(userDTO.getIsGivenSurName() != null) {
            user.setIsGivenSurname(userDTO.getIsGivenSurName());
        }
        if(userDTO.getEmail() != null) {
            user.setEmail(userDTO.getEmail());
        }
        if(userDTO.getRole() != null) {
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
        userRepository.deleteById(id);
    }
}
