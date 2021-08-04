package ie.lyit.app.service;

import ie.lyit.app.domain.Employee;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Employee}.
 */
public interface EmployeeService {
    /**
     * Save a employee.
     *
     * @param employee the entity to save.
     * @return the persisted entity.
     */
    Employee save(Employee employee);

    /**
     * Partially updates a employee.
     *
     * @param employee the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Employee> partialUpdate(Employee employee);

    /**
     * Get all the employees.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Employee> findAll(Pageable pageable);

    /**
     * Get the "id" employee.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Employee> findOne(Long id);

    /**
     * Delete the "id" employee.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
