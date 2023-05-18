package com.phuongnh.personal.library_management_system.mapper;

import com.phuongnh.personal.library_management_system.dto.UserDTO;
import com.phuongnh.personal.library_management_system.model.User;
import com.phuongnh.personal.library_management_system.model.UserRole;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public static UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setGivenName(user.getGivenName());
        dto.setSurName(user.getSurName());
        dto.setIsGivenSurName(user.getIsGivenSurName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole().name());
        dto.setImgsrc(user.getImgsrc());
        dto.setIsBanned(user.getIsBanned());
        return dto;
    }

    public static User toEntity(UserDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setGivenName(dto.getGivenName());
        user.setSurName(dto.getSurName());
        user.setIsGivenSurName(dto.getIsGivenSurName());
        user.setEmail(dto.getEmail());
        user.setRole(UserRole.valueOf(dto.getRole()));
        user.setImgsrc(dto.getImgsrc());
        user.setIsBanned(dto.getIsBanned());
        return user;
    }
}
