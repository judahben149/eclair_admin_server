package com.judahben149.eclair.repository;

import com.judahben149.eclair.model.Concept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConceptRepository extends JpaRepository<Concept, Long> {

    List<Concept> findAllByPublishedTrueOrderByDisplayOrderAsc();

    List<Concept> findAllByOrderByDisplayOrderAsc();

    @Query("SELECT c FROM Concept c LEFT JOIN FETCH c.sections s LEFT JOIN FETCH s.contentItems WHERE c.id = :id ORDER BY s.displayOrder")
    Optional<Concept> findByIdWithDetails(Long id);

    @Query("SELECT c FROM Concept c LEFT JOIN FETCH c.sections s LEFT JOIN FETCH s.contentItems WHERE c.id = :id AND c.published = true ORDER BY s.displayOrder")
    Optional<Concept> findPublishedByIdWithDetails(Long id);
}
