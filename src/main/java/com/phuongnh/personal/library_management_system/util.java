package com.phuongnh.personal.library_management_system;

import com.phuongnh.personal.library_management_system.model.User;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

public class util {
    public static UUID getCurrentUserId() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User ?
                ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()
                : null;
    }
}
