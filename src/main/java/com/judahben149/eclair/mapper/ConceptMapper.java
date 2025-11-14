package com.judahben149.eclair.mapper;

import com.judahben149.eclair.dto.*;
import com.judahben149.eclair.model.Concept;
import com.judahben149.eclair.model.ContentItem;
import com.judahben149.eclair.model.Section;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ConceptMapper {

    public ConceptListDto toListDto(Concept concept) {
        return ConceptListDto.builder()
                .id(concept.getId())
                .title(concept.getTitle())
                .description(concept.getDescription())
                .displayOrder(concept.getDisplayOrder())
                .published(concept.getPublished())
                .updatedAt(concept.getUpdatedAt())
                .version(concept.getVersion())
                .build();
    }

    public ConceptDetailDto toDetailDto(Concept concept) {
        return ConceptDetailDto.builder()
                .id(concept.getId())
                .title(concept.getTitle())
                .description(concept.getDescription())
                .sections(concept.getSections().stream()
                        .map(this::toSectionDto)
                        .collect(Collectors.toList()))
                .updatedAt(concept.getUpdatedAt())
                .version(concept.getVersion())
                .build();
    }

    private SectionDto toSectionDto(Section section) {
        return SectionDto.builder()
                .id(section.getId())
                .heading(section.getHeading())
                .content(section.getContentItems().stream()
                        .map(this::toContentItemDto)
                        .collect(Collectors.toList()))
                .build();
    }

    private ContentItemDto toContentItemDto(ContentItem contentItem) {
        return ContentItemDto.builder()
                .type(contentItem.getType().name().toLowerCase())
                .value(contentItem.getValue())
                .build();
    }

    public Concept toEntity(CreateConceptRequest request) {
        Concept concept = Concept.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .displayOrder(request.getDisplayOrder() != null ? request.getDisplayOrder() : 0)
                .published(request.getPublished() != null ? request.getPublished() : false)
                .build();

        if (request.getSections() != null) {
            request.getSections().forEach(sectionRequest -> {
                Section section = toSectionEntity(sectionRequest);
                concept.addSection(section);
            });
        }

        return concept;
    }

    private Section toSectionEntity(CreateSectionRequest request) {
        Section section = Section.builder()
                .heading(request.getHeading())
                .displayOrder(request.getDisplayOrder() != null ? request.getDisplayOrder() : 0)
                .build();

        if (request.getContent() != null) {
            request.getContent().forEach(contentRequest -> {
                ContentItem contentItem = toContentItemEntity(contentRequest);
                section.addContentItem(contentItem);
            });
        }

        return section;
    }

    private ContentItem toContentItemEntity(CreateContentItemRequest request) {
        return ContentItem.builder()
                .type(ContentItem.ContentType.valueOf(request.getType().toUpperCase()))
                .value(request.getValue())
                .displayOrder(request.getDisplayOrder() != null ? request.getDisplayOrder() : 0)
                .build();
    }

    public void updateEntity(Concept concept, CreateConceptRequest request) {
        concept.setTitle(request.getTitle());
        concept.setDescription(request.getDescription());
        concept.setDisplayOrder(request.getDisplayOrder() != null ? request.getDisplayOrder() : 0);
        concept.setPublished(request.getPublished() != null ? request.getPublished() : false);

        // Clear existing sections
        concept.getSections().clear();

        // Add new sections
        if (request.getSections() != null) {
            request.getSections().forEach(sectionRequest -> {
                Section section = toSectionEntity(sectionRequest);
                concept.addSection(section);
            });
        }
    }
}
