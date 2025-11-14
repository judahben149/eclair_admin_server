package com.judahben149.eclair.service;

import com.judahben149.eclair.dto.ChangePasswordRequest;
import com.judahben149.eclair.dto.ChangePasswordResponse;
import com.judahben149.eclair.model.User;
import com.judahben149.eclair.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public ChangePasswordResponse changePassword(ChangePasswordRequest request) {
        // Get currently authenticated user
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("Password change attempt for user: {}", username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Verify current password
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            log.warn("Failed password change attempt for user {}: incorrect current password", username);
            throw new BadCredentialsException("Current password is incorrect");
        }

        // Update password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        log.info("Password changed successfully for user: {}", username);

        return ChangePasswordResponse.builder()
                .message("Password changed successfully")
                .username(username)
                .build();
    }
}
