package com.judahben149.eclair.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConceptDetailDto {
    private Long id;
    private String title;
    private String description;
    private List<SectionDto> sections;
    private LocalDateTime updatedAt;
    private Long version;
}
