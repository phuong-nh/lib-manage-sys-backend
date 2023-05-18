package com.phuongnh.personal.library_management_system.service;

import com.phuongnh.personal.library_management_system.model.AuthenticationRequest;
import com.phuongnh.personal.library_management_system.model.AuthenticationResponse;
import com.phuongnh.personal.library_management_system.model.RegisterRequest;
import com.phuongnh.personal.library_management_system.model.User;
import com.phuongnh.personal.library_management_system.repository.UserRepository;
import com.phuongnh.personal.library_management_system.model.UserRole;
import com.phuongnh.personal.library_management_system.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .givenName(request.getGivenName())
                .surName(request.getSurName())
                .isGivenSurName(request.getIsGivenSurName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.USER)
                .isBanned(false)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
