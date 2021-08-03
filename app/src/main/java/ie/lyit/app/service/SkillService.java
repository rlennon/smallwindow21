package ie.lyit.app.service;

import ie.lyit.app.domain.Skill;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Skill}.
 */
public interface SkillService {
    /**
     * Save a skill.
     *
     * @param skill the entity to save.
     * @return the persisted entity.
     */
    Skill save(Skill skill);

    /**
     * Partially updates a skill.
     *
     * @param skill the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Skill> partialUpdate(Skill skill);

    /**
     * Get all the skills.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Skill> findAll(Pageable pageable);

    /**
     * Get all the skills with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Skill> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" skill.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Skill> findOne(Long id);

    /**
     * Delete the "id" skill.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
