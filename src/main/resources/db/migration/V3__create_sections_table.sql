CREATE TABLE sections (
    id BIGSERIAL PRIMARY KEY,
    heading VARCHAR(255) NOT NULL,
    display_order INTEGER NOT NULL DEFAULT 0,
    concept_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_sections_concept FOREIGN KEY (concept_id) REFERENCES concepts(id) ON DELETE CASCADE
);

CREATE INDEX idx_sections_concept_id ON sections(concept_id);
CREATE INDEX idx_sections_display_order ON sections(display_order);
