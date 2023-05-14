package com.phuongnh.personal.library_management_system.UserBookHistory;

import com.phuongnh.personal.library_management_system.BookCopy.BookCopy;
import com.phuongnh.personal.library_management_system.BookCopy.BookCopyRepository;
import com.phuongnh.personal.library_management_system.User.User;
import com.phuongnh.personal.library_management_system.User.UserRepository;
import com.phuongnh.personal.library_management_system.Book.Book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserBookHistoryService {
    @Autowired
    private UserBookHistoryRepository userBookHistoryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookCopyRepository bookCopyRepository;

    public List<UserBookHistoryDTO> getAllUserBookHistories() {
        return userBookHistoryRepository.findAll().stream()
                .map(UserBookHistoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UserBookHistoryDTO getUserBookHistoryById(UserBookHistoryID id) {
        UserBookHistory userBookHistory = userBookHistoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User Book History not found"));
        return UserBookHistoryMapper.toDTO(userBookHistory);
    }

    public UserBookHistoryDTO createUserBookHistory(UserBookHistoryDTO userBookHistoryDTO) {
        User user = userRepository.findById(userBookHistoryDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        BookCopy bookCopy = bookCopyRepository.findById(userBookHistoryDTO.getBookCopyId())
                .orElseThrow(() -> new RuntimeException("Book Copy not found"));
        Book book = bookCopy.getBook();


        UserBookHistory userBookHistory = UserBookHistoryMapper.toEntity(userBookHistoryDTO, user, book, bookCopy);
        return UserBookHistoryMapper.toDTO(userBookHistoryRepository.save(userBookHistory));
    }

    public UserBookHistoryDTO updateUserBookHistory(UserBookHistoryID id, UserBookHistoryDTO userBookHistoryDTO) {
        UserBookHistory existingUserBookHistory = userBookHistoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User Book History not found"));

        User user = userRepository.findById(userBookHistoryDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        BookCopy bookCopy = bookCopyRepository.findById(userBookHistoryDTO.getBookCopyId())
                .orElseThrow(() -> new RuntimeException("Book Copy not found"));
        Book book = bookCopy.getBook();

        UserBookHistory updatedUserBookHistory = UserBookHistoryMapper.toEntity(userBookHistoryDTO, user, book, bookCopy);
        updatedUserBookHistory.setId(existingUserBookHistory.getId());
        return UserBookHistoryMapper.toDTO(userBookHistoryRepository.save(updatedUserBookHistory));
    }

    public void deleteUserBookHistory(UserBookHistoryID id) {
        userBookHistoryRepository.deleteById(id);
    }

    public List<UserBookHistoryDTO> getUserHistory(UUID userId) {
        return userBookHistoryRepository.findByUserId(userId).stream()
                .map(UserBookHistoryMapper::toDTO)
                .collect(Collectors.toList());
    }
}


