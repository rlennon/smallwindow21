package ie.lyit.app.service.impl;

import ie.lyit.app.domain.Skill;
import ie.lyit.app.repository.SkillRepository;
import ie.lyit.app.service.SkillService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Skill}.
 */
@Service
@Transactional
public class SkillServiceImpl implements SkillService {

    private final Logger log = LoggerFactory.getLogger(SkillServiceImpl.class);

    private final SkillRepository skillRepository;

    public SkillServiceImpl(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @Override
    public Skill save(Skill skill) {
        log.debug("Request to save Skill : {}", skill);
        return skillRepository.save(skill);
    }

    @Override
    public Optional<Skill> partialUpdate(Skill skill) {
        log.debug("Request to partially update Skill : {}", skill);

        return skillRepository
            .findById(skill.getId())
            .map(
                existingSkill -> {
                    if (skill.getTitle() != null) {
                        existingSkill.setTitle(skill.getTitle());
                    }
                    if (skill.getDescription() != null) {
                        existingSkill.setDescription(skill.getDescription());
                    }

                    return existingSkill;
                }
            )
            .map(skillRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Skill> findAll(Pageable pageable) {
        log.debug("Request to get all Skills");
        return skillRepository.findAll(pageable);
    }

    public Page<Skill> findAllWithEagerRelationships(Pageable pageable) {
        return skillRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Skill> findOne(Long id) {
        log.debug("Request to get Skill : {}", id);
        return skillRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Skill : {}", id);
        skillRepository.deleteById(id);
    }
}
