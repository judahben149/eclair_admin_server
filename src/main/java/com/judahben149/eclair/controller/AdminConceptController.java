package com.judahben149.eclair.controller;

import com.judahben149.eclair.dto.ConceptDetailDto;
import com.judahben149.eclair.dto.ConceptListDto;
import com.judahben149.eclair.dto.CreateConceptRequest;
import com.judahben149.eclair.service.ConceptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/concepts")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin Concepts", description = "Admin API for managing lighting concepts")
@SecurityRequirement(name = "bearerAuth")
public class AdminConceptController {

    private final ConceptService conceptService;

    @GetMapping
    @Operation(summary = "Get all concepts (admin)", description = "Returns all concepts including unpublished ones")
    public ResponseEntity<List<ConceptListDto>> getAllConcepts() {
        return ResponseEntity.ok(conceptService.getAllConcepts());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get concept by ID (admin)", description = "Returns detailed content for any concept")
    public ResponseEntity<ConceptDetailDto> getConceptById(@PathVariable Long id) {
        return ResponseEntity.ok(conceptService.getConceptById(id));
    }

    @PostMapping
    @Operation(summary = "Create concept", description = "Creates a new lighting concept")
    public ResponseEntity<ConceptDetailDto> createConcept(@Valid @RequestBody CreateConceptRequest request) {
        ConceptDetailDto created = conceptService.createConcept(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update concept", description = "Updates an existing lighting concept")
    public ResponseEntity<ConceptDetailDto> updateConcept(
            @PathVariable Long id,
            @Valid @RequestBody CreateConceptRequest request) {
        return ResponseEntity.ok(conceptService.updateConcept(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete concept", description = "Deletes a lighting concept")
    public ResponseEntity<Void> deleteConcept(@PathVariable Long id) {
        conceptService.deleteConcept(id);
        return ResponseEntity.noContent().build();
    }
}
