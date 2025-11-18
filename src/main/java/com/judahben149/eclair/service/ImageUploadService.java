package com.judahben149.eclair.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageUploadService {

    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10 MB
    private static final String[] ALLOWED_TYPES = {"image/jpeg", "image/jpg", "image/png", "image/gif", "image/webp"};

    private final Cloudinary cloudinary;

    public String uploadImage(MultipartFile file) throws IOException {
        log.info("Starting image upload for file: {}", file.getOriginalFilename());

        // Validate file
        validateFile(file);

        // Generate unique filename
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename != null && originalFilename.contains(".")
                ? originalFilename.substring(originalFilename.lastIndexOf("."))
                : "";
        String uniqueFilename = "concept_" + UUID.randomUUID().toString() + extension;

        log.debug("Generated unique filename: {}", uniqueFilename);

        // Upload to Cloudinary with transformation
        Map<String, Object> uploadParams = ObjectUtils.asMap(
            "folder", "eclair/concepts",
            "public_id", uniqueFilename.substring(0, uniqueFilename.lastIndexOf(".")),
            "resource_type", "image",
            "overwrite", false,
            "transformation", new Transformation<>()
                .width(1920).height(1080)
                .crop("limit")
                .quality("auto")
                .fetchFormat("auto")
        );

        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), uploadParams);

        String imageUrl = (String) uploadResult.get("secure_url");
        log.info("Image uploaded successfully. URL: {}", imageUrl);

        return imageUrl;
    }

    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            log.error("File is empty");
            throw new IllegalArgumentException("File is empty");
        }

        // Check file size
        if (file.getSize() > MAX_FILE_SIZE) {
            log.error("File size {} exceeds 10 MB limit", file.getSize());
            throw new IllegalArgumentException("File size exceeds 10 MB limit");
        }

        // Check file type
        String contentType = file.getContentType();
        boolean isValidType = false;
        for (String allowedType : ALLOWED_TYPES) {
            if (allowedType.equals(contentType)) {
                isValidType = true;
                break;
            }
        }

        if (!isValidType) {
            log.error("Invalid file type: {}", contentType);
            throw new IllegalArgumentException(
                "Invalid file type. Only JPG, PNG, GIF, and WebP are allowed."
            );
        }

        log.debug("File validation passed for: {}", file.getOriginalFilename());
    }
}
