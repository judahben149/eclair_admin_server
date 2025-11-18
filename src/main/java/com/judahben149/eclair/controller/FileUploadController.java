package com.judahben149.eclair.controller;

import com.judahben149.eclair.service.ImageUploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/upload")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "File Upload", description = "Admin endpoints for file uploads")
@SecurityRequirement(name = "Bearer Authentication")
public class FileUploadController {

    private final ImageUploadService imageUploadService;

    @PostMapping("/image")
    @Operation(summary = "Upload an image", description = "Upload an image to Cloudinary cloud storage")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Image uploaded successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid file (wrong type or too large)"),
        @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing JWT token"),
        @ApiResponse(responseCode = "403", description = "Forbidden - Admin access required"),
        @ApiResponse(responseCode = "500", description = "Failed to upload image to cloud storage")
    })
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        log.info("Received image upload request for file: {}", file.getOriginalFilename());

        try {
            String imageUrl = imageUploadService.uploadImage(file);

            Map<String, String> response = new HashMap<>();
            response.put("imageUrl", imageUrl);

            log.info("Image upload successful. URL: {}", imageUrl);
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            // Validation errors (400 Bad Request)
            log.warn("Validation error during image upload: {}", e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);

        } catch (IOException e) {
            // Upload errors (500 Internal Server Error)
            log.error("Failed to upload image to cloud storage", e);
            Map<String, String> error = new HashMap<>();
            error.put("message", "Failed to upload image to cloud storage");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
