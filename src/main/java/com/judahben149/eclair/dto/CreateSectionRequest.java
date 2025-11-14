package com.judahben149.eclair.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateSectionRequest {

    @NotBlank(message = "Heading is required")
    private String heading;

    private Integer displayOrder;

    @Valid
    private List<CreateContentItemRequest> content;
}
