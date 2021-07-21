package ie.lyit.app.repository;

import ie.lyit.app.domain.File;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the File entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    /**
     *
     * @param id -
     * @return -
     */
    List<File> findByEmployeeId(Long id);
}
