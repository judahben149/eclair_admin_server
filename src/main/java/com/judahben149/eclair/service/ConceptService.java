package com.judahben149.eclair.service;

import com.judahben149.eclair.dto.ConceptDetailDto;
import com.judahben149.eclair.dto.ConceptListDto;
import com.judahben149.eclair.dto.CreateConceptRequest;
import com.judahben149.eclair.exception.ResourceNotFoundException;
import com.judahben149.eclair.mapper.ConceptMapper;
import com.judahben149.eclair.model.Concept;
import com.judahben149.eclair.repository.ConceptRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConceptService {

    private final ConceptRepository conceptRepository;
    private final ConceptMapper conceptMapper;

    @Transactional(readOnly = true)
    public List<ConceptListDto> getAllPublishedConcepts() {
        log.info("Fetching all published concepts");
        return conceptRepository.findAllByPublishedTrueOrderByDisplayOrderAsc()
                .stream()
                .map(conceptMapper::toListDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ConceptListDto> getAllConcepts() {
        log.info("Fetching all concepts (admin)");
        return conceptRepository.findAllByOrderByDisplayOrderAsc()
                .stream()
                .map(conceptMapper::toListDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ConceptDetailDto getPublishedConceptById(Long id) {
        log.info("Fetching published concept with id: {}", id);
        Concept concept = conceptRepository.findPublishedByIdWithDetails(id)
                .orElseThrow(() -> new ResourceNotFoundException("Concept not found with id: " + id));
        return conceptMapper.toDetailDto(concept);
    }

    @Transactional(readOnly = true)
    public ConceptDetailDto getConceptById(Long id) {
        log.info("Fetching concept with id: {}", id);
        Concept concept = conceptRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new ResourceNotFoundException("Concept not found with id: " + id));
        return conceptMapper.toDetailDto(concept);
    }

    @Transactional
    public ConceptDetailDto createConcept(CreateConceptRequest request) {
        log.info("Creating new concept: {}", request.getTitle());
        Concept concept = conceptMapper.toEntity(request);
        Concept savedConcept = conceptRepository.save(concept);
        return conceptMapper.toDetailDto(savedConcept);
    }

    @Transactional
    public ConceptDetailDto updateConcept(Long id, CreateConceptRequest request) {
        log.info("Updating concept with id: {}", id);
        Concept concept = conceptRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Concept not found with id: " + id));

        conceptMapper.updateEntity(concept, request);
        Concept updatedConcept = conceptRepository.save(concept);
        return conceptMapper.toDetailDto(updatedConcept);
    }

    @Transactional
    public void deleteConcept(Long id) {
        log.info("Deleting concept with id: {}", id);
        if (!conceptRepository.existsById(id)) {
            throw new ResourceNotFoundException("Concept not found with id: " + id);
        }
        conceptRepository.deleteById(id);
    }
}
