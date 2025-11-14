package com.judahben149.eclair.controller;

import com.judahben149.eclair.dto.ChangePasswordRequest;
import com.judahben149.eclair.dto.ChangePasswordResponse;
import com.judahben149.eclair.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/users")
@RequiredArgsConstructor
@Tag(name = "User Management", description = "User account management endpoints")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserService userService;

    @PatchMapping("/me/password")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Change password", description = "Change password for currently authenticated user")
    public ResponseEntity<ChangePasswordResponse> changePassword(@Valid @RequestBody ChangePasswordRequest request) {
        return ResponseEntity.ok(userService.changePassword(request));
    }
}
