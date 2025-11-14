package com.judahben149.eclair.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateConceptRequest {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    private Integer displayOrder;

    private Boolean published;

    @Valid
    private List<CreateSectionRequest> sections;
}
