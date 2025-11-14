package com.judahben149.eclair.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateContentItemRequest {

    @NotBlank(message = "Type is required")
    @Pattern(regexp = "^(text|image)$", message = "Type must be 'text' or 'image'")
    private String type;

    @NotBlank(message = "Value is required")
    private String value;

    private Integer displayOrder;
}
