package ie.lyit.app.web.rest;

import ie.lyit.app.domain.Skill;
import ie.lyit.app.repository.SkillRepository;
import ie.lyit.app.security.AuthoritiesConstants;
import ie.lyit.app.web.rest.errors.BadRequestAlertException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ie.lyit.app.domain.Skill}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SkillResource {

    private final Logger log = LoggerFactory.getLogger(SkillResource.class);

    private static final String ENTITY_NAME = "skill";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SkillRepository skillRepository;

    /**
     * Constructor
     * @param skillRepository -
     */
    public SkillResource(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    /**
     * {@code POST  /skills} : Create a new skill.
     *
     * @param skill the skill to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new skill, or with status {@code 400 (Bad Request)} if the skill has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/skills")
    @ApiOperation(value = "Create a new skill", notes = "Allows you to create a new skill on the system")
    public ResponseEntity<Skill> createSkill(@RequestBody Skill skill) throws URISyntaxException {
        log.debug("REST request to save Skill : {}", skill);
        if (skill.getId() != null) {
            throw new BadRequestAlertException("A new skill cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Skill result = skillRepository.save(skill);
        return ResponseEntity
            .created(new URI("/api/skills/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /skills/:id} : Updates an existing skill.
     *
     * @param id the id of the skill to save.
     * @param skill the skill to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated skill,
     * or with status {@code 400 (Bad Request)} if the skill is not valid,
     * or with status {@code 500 (Internal Server Error)} if the skill couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/skills/{id}")
    @ApiOperation(value = "Update existing skill", notes = "Allows you to update an existing skill on the system")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Skill> updateSkill(
        @PathVariable(value = "id", required = false) @ApiParam(value = "Id of the skill to update") final Long id,
        @RequestBody Skill skill
    ) throws URISyntaxException {
        log.debug("REST request to update Skill : {}, {}", id, skill);
        if (skill.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, skill.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!skillRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Skill result = skillRepository.save(skill);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, skill.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /skills/:id} : Partial updates given fields of an existing skill, field will ignore if it is null
     *
     * @param id the id of the skill to save.
     * @param skill the skill to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated skill,
     * or with status {@code 400 (Bad Request)} if the skill is not valid,
     * or with status {@code 404 (Not Found)} if the skill is not found,
     * or with status {@code 500 (Internal Server Error)} if the skill couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/skills/{id}", consumes = "application/merge-patch+json")
    @ApiOperation(value = "Partially Update existing skill", notes = "Allows you to partially update an existing skill on the system")
    public ResponseEntity<Skill> partialUpdateSkill(
        @PathVariable(value = "id", required = false) @ApiParam(value = "Id of the file to partially update") final Long id,
        @RequestBody Skill skill
    ) throws URISyntaxException {
        log.debug("REST request to partial update Skill partially : {}, {}", id, skill);
        if (skill.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, skill.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!skillRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Skill> result = skillRepository
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

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, skill.getId().toString())
        );
    }

    /**
     * {@code GET  /skills} : get all the skills.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of skills in body.
     */
    @GetMapping("/skills")
    @ApiOperation(value = "Retrieve all skills", notes = "Allows you to retrieve all skills on the system")
    public ResponseEntity<List<Skill>> getAllSkills(
        Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of Skills");
        Page<Skill> page;
        if (eagerload) {
            page = skillRepository.findAllWithEagerRelationships(pageable);
        } else {
            page = skillRepository.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /skills/:id} : get the "id" skill.
     *
     * @param id the id of the skill to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the skill, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/skills/{id}")
    @ApiOperation(value = "Retrieve a skill", notes = "Allows you to retrieve a skill on the system based on id")
    public ResponseEntity<Skill> getSkill(@PathVariable @ApiParam(value = "Id of the skill to retrieve") Long id) {
        log.debug("REST request to get Skill : {}", id);
        Optional<Skill> skill = skillRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(skill);
    }

    /**
     * {@code DELETE  /skills/:id} : delete the "id" skill.
     *
     * @param id the id of the skill to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/skills/{id}")
    @ApiOperation(value = "Delete a skill", notes = "Allows you to delete a skill on the system based on id")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteSkill(@PathVariable @ApiParam(value = "Id of the skill to delete") Long id) {
        log.debug("REST request to delete Skill : {}", id);
        skillRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
