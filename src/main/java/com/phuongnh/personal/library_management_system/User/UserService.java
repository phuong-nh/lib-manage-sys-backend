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

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
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

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(UUID id, User user) {
        User existingUser = getUserById(id);

        if(user.getGivenName() != null) {
            existingUser.setGivenName(user.getGivenName());
        }
        if(user.getSurName() != null) {
            existingUser.setSurName(user.getSurName());
        }
        if(user.getIsGivenSurname() != null) {
            existingUser.setIsGivenSurname(user.getIsGivenSurname());
        }
        if(user.getEmail() != null) {
            existingUser.setEmail(user.getEmail());
        }
        if(user.getRole() != null) {
            existingUser.setRole(user.getRole());
        }
        if(user.getImgsrc() != null) {
            existingUser.setImgsrc(user.getImgsrc());
        }
        if(user.getIsBanned() != null) {
            existingUser.setIsBanned(user.getIsBanned());
        }
        return userRepository.save(existingUser);
    }

    public void deleteUser(UUID id) {
        userRepository.delete(getUserById(id));
    }
}
