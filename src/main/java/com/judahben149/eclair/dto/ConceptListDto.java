package com.judahben149.eclair.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConceptListDto {
    private Long id;
    private String title;
    private String description;
    private Integer displayOrder;
    private Boolean published;
    private LocalDateTime updatedAt;
    private Long version;
}
