package ie.lyit.app.service;

import ie.lyit.app.domain.File;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link File}.
 */
public interface FileService {
    /**
     * Save a file.
     *
     * @param file the entity to save.
     * @return the persisted entity.
     */
    File save(File file);

    /**
     * Partially updates a file.
     *
     * @param file the entity to update partially.
     * @return the persisted entity.
     */
    Optional<File> partialUpdate(File file);

    /**
     * Get all the files.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<File> findAll(Pageable pageable);

    /**
     * Get the "id" file.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<File> findOne(Long id);

    /**
     * Delete the "id" file.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
