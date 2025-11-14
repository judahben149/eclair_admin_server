package com.judahben149.eclair.controller;

import com.judahben149.eclair.dto.ConceptDetailDto;
import com.judahben149.eclair.dto.ConceptListDto;
import com.judahben149.eclair.service.ConceptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/concepts")
@RequiredArgsConstructor
@Tag(name = "Public Concepts", description = "Public API for accessing published lighting concepts")
public class PublicConceptController {

    private final ConceptService conceptService;

    @GetMapping
    @Operation(summary = "Get all published concepts", description = "Returns a list of all published lighting concepts")
    public ResponseEntity<List<ConceptListDto>> getAllConcepts() {
        return ResponseEntity.ok(conceptService.getAllPublishedConcepts());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get concept by ID", description = "Returns detailed content for a specific published concept")
    public ResponseEntity<ConceptDetailDto> getConceptById(@PathVariable Long id) {
        return ResponseEntity.ok(conceptService.getPublishedConceptById(id));
    }
}
