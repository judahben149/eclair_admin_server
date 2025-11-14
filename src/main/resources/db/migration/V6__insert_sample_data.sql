-- Sample concept: Stage Basics
INSERT INTO concepts (title, description, display_order, published)
VALUES ('Stage Basics', 'Fundamental concepts of stage lighting and terminology', 1, true);

-- Get the concept_id (assuming it's 1 since it's the first insert)
-- Section 1: Introduction
INSERT INTO sections (heading, display_order, concept_id)
VALUES ('Introduction to Stage Lighting', 1, 1);

-- Content for Section 1
INSERT INTO content_items (type, value, display_order, section_id)
VALUES
    ('TEXT', 'Stage lighting is the craft of illuminating performers and creating atmosphere in theatrical productions. It combines art and technology to enhance storytelling.', 1, 1),
    ('TEXT', 'Understanding the basics of stage lighting is essential for anyone working in theatre, concerts, or live events.', 2, 1);

-- Section 2: Key Terminology
INSERT INTO sections (heading, display_order, concept_id)
VALUES ('Key Terminology', 2, 1);

-- Content for Section 2
INSERT INTO content_items (type, value, display_order, section_id)
VALUES
    ('TEXT', 'Upstage: The area of the stage farthest from the audience', 1, 2),
    ('TEXT', 'Downstage: The area of the stage closest to the audience', 2, 2),
    ('TEXT', 'Stage Left/Right: Directions from the performer''s perspective facing the audience', 3, 2),
    ('TEXT', 'Wings: The off-stage areas to the left and right of the performance space', 4, 2);

-- Sample concept: Concert Lighting
INSERT INTO concepts (title, description, display_order, published)
VALUES ('Concert Lighting', 'Techniques and approaches for lighting live music performances', 2, true);

-- Section for Concert Lighting
INSERT INTO sections (heading, display_order, concept_id)
VALUES ('Concert Lighting Fundamentals', 1, 2);

INSERT INTO content_items (type, value, display_order, section_id)
VALUES
    ('TEXT', 'Concert lighting differs from theatrical lighting in its focus on energy, movement, and audience engagement.', 1, 3),
    ('TEXT', 'Moving lights, color mixing, and synchronized effects are common tools in concert lighting design.', 2, 3);

-- Sample concept: DMX (unpublished draft)
INSERT INTO concepts (title, description, display_order, published)
VALUES ('DMX Protocol', 'Understanding DMX512 protocol for lighting control', 3, false);

INSERT INTO sections (heading, display_order, concept_id)
VALUES ('What is DMX?', 1, 3);

INSERT INTO content_items (type, value, display_order, section_id)
VALUES
    ('TEXT', 'DMX512 is a digital communication standard for controlling stage lighting and effects.', 1, 4),
    ('TEXT', 'DMX uses a series of channels (up to 512 per universe) to control individual parameters of lighting fixtures.', 2, 4);
