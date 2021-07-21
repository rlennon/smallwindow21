package ie.lyit.app.repository;

import ie.lyit.app.domain.Skill;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Skill entity.
 */
@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
    /**
     *
     * @param pageable -
     * @return -
     */
    @Query(
        value = "select distinct skill from Skill skill left join fetch skill.categories left join fetch skill.employees",
        countQuery = "select count(distinct skill) from Skill skill"
    )
    Page<Skill> findAllWithEagerRelationships(Pageable pageable);

    /**
     *
     * @return -
     */
    @Query("select distinct skill from Skill skill left join fetch skill.categories left join fetch skill.employees")
    List<Skill> findAllWithEagerRelationships();

    /**
     *
     * @param id -
     * @return -
     */
    @Query("select skill from Skill skill left join fetch skill.categories left join fetch skill.employees where skill.id =:id")
    Optional<Skill> findOneWithEagerRelationships(@Param("id") Long id);
}
