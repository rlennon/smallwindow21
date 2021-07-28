package ie.lyit.app.repository;

import ie.lyit.app.domain.User;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the {@link User} entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Users by login cache
     */
    String USERS_BY_LOGIN_CACHE = "usersByLogin";

    /**
     * Users by email cache
     */
    String USERS_BY_EMAIL_CACHE = "usersByEmail";

    /**
     *
     * @param activationKey -
     * @return -
     */
    Optional<User> findOneByActivationKey(String activationKey);

    /**
     *
     * @param dateTime -
     * @return -
     */
    List<User> findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(Instant dateTime);

    /**
     *
     * @param resetKey -
     * @return -
     */
    Optional<User> findOneByResetKey(String resetKey);

    /**
     *
     * @param email -
     * @return -
     */
    Optional<User> findOneByEmailIgnoreCase(String email);

    /**
     *
     * @param login -
     * @return -
     */
    Optional<User> findOneByLogin(String login);

    /**
     *
     * @param login -
     * @return -
     */
    @EntityGraph(attributePaths = "authorities")
    @Cacheable(cacheNames = USERS_BY_LOGIN_CACHE)
    Optional<User> findOneWithAuthoritiesByLogin(String login);

    /**
     *
     * @param email -
     * @return -
     */
    @EntityGraph(attributePaths = "authorities")
    @Cacheable(cacheNames = USERS_BY_EMAIL_CACHE)
    Optional<User> findOneWithAuthoritiesByEmailIgnoreCase(String email);

    /**
     *
     * @param pageable -
     * @return -
     */
    Page<User> findAllByIdNotNullAndActivatedIsTrue(Pageable pageable);
}
