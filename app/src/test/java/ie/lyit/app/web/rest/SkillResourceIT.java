package ie.lyit.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ie.lyit.app.IntegrationTest;
import ie.lyit.app.domain.Category;
import ie.lyit.app.domain.Employee;
import ie.lyit.app.domain.Skill;
import ie.lyit.app.repository.SkillRepository;
import ie.lyit.app.service.SkillService;
import ie.lyit.app.service.criteria.SkillCriteria;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SkillResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class SkillResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/skills";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SkillRepository skillRepository;

    @Mock
    private SkillRepository skillRepositoryMock;

    @Mock
    private SkillService skillServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSkillMockMvc;

    private Skill skill;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Skill createEntity(EntityManager em) {
        Skill skill = new Skill().title(DEFAULT_TITLE).description(DEFAULT_DESCRIPTION);
        return skill;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Skill createUpdatedEntity(EntityManager em) {
        Skill skill = new Skill().title(UPDATED_TITLE).description(UPDATED_DESCRIPTION);
        return skill;
    }

    @BeforeEach
    public void initTest() {
        skill = createEntity(em);
    }

    @Test
    @Transactional
    void createSkill() throws Exception {
        int databaseSizeBeforeCreate = skillRepository.findAll().size();
        // Create the Skill
        restSkillMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(skill)))
            .andExpect(status().isCreated());

        // Validate the Skill in the database
        List<Skill> skillList = skillRepository.findAll();
        assertThat(skillList).hasSize(databaseSizeBeforeCreate + 1);
        Skill testSkill = skillList.get(skillList.size() - 1);
        assertThat(testSkill.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testSkill.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createSkillWithExistingId() throws Exception {
        // Create the Skill with an existing ID
        skill.setId(1L);

        int databaseSizeBeforeCreate = skillRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSkillMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(skill)))
            .andExpect(status().isBadRequest());

        // Validate the Skill in the database
        List<Skill> skillList = skillRepository.findAll();
        assertThat(skillList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSkills() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList
        restSkillMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(skill.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSkillsWithEagerRelationshipsIsEnabled() throws Exception {
        when(skillServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSkillMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(skillServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSkillsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(skillServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSkillMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(skillServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getSkill() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get the skill
        restSkillMockMvc
            .perform(get(ENTITY_API_URL_ID, skill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(skill.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getSkillsByIdFiltering() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        Long id = skill.getId();

        defaultSkillShouldBeFound("id.equals=" + id);
        defaultSkillShouldNotBeFound("id.notEquals=" + id);

        defaultSkillShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSkillShouldNotBeFound("id.greaterThan=" + id);

        defaultSkillShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSkillShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSkillsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where title equals to DEFAULT_TITLE
        defaultSkillShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the skillList where title equals to UPDATED_TITLE
        defaultSkillShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllSkillsByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where title not equals to DEFAULT_TITLE
        defaultSkillShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the skillList where title not equals to UPDATED_TITLE
        defaultSkillShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllSkillsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultSkillShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the skillList where title equals to UPDATED_TITLE
        defaultSkillShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllSkillsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where title is not null
        defaultSkillShouldBeFound("title.specified=true");

        // Get all the skillList where title is null
        defaultSkillShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllSkillsByTitleContainsSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where title contains DEFAULT_TITLE
        defaultSkillShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the skillList where title contains UPDATED_TITLE
        defaultSkillShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllSkillsByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where title does not contain DEFAULT_TITLE
        defaultSkillShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the skillList where title does not contain UPDATED_TITLE
        defaultSkillShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllSkillsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where description equals to DEFAULT_DESCRIPTION
        defaultSkillShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the skillList where description equals to UPDATED_DESCRIPTION
        defaultSkillShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllSkillsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where description not equals to DEFAULT_DESCRIPTION
        defaultSkillShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the skillList where description not equals to UPDATED_DESCRIPTION
        defaultSkillShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllSkillsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultSkillShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the skillList where description equals to UPDATED_DESCRIPTION
        defaultSkillShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllSkillsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where description is not null
        defaultSkillShouldBeFound("description.specified=true");

        // Get all the skillList where description is null
        defaultSkillShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllSkillsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where description contains DEFAULT_DESCRIPTION
        defaultSkillShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the skillList where description contains UPDATED_DESCRIPTION
        defaultSkillShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllSkillsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        // Get all the skillList where description does not contain DEFAULT_DESCRIPTION
        defaultSkillShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the skillList where description does not contain UPDATED_DESCRIPTION
        defaultSkillShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllSkillsByCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);
        Category category = CategoryResourceIT.createEntity(em);
        em.persist(category);
        em.flush();
        skill.addCategory(category);
        skillRepository.saveAndFlush(skill);
        Long categoryId = category.getId();

        // Get all the skillList where category equals to categoryId
        defaultSkillShouldBeFound("categoryId.equals=" + categoryId);

        // Get all the skillList where category equals to (categoryId + 1)
        defaultSkillShouldNotBeFound("categoryId.equals=" + (categoryId + 1));
    }

    @Test
    @Transactional
    void getAllSkillsByEmployeeIsEqualToSomething() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);
        Employee employee = EmployeeResourceIT.createEntity(em);
        em.persist(employee);
        em.flush();
        skill.addEmployee(employee);
        skillRepository.saveAndFlush(skill);
        Long employeeId = employee.getId();

        // Get all the skillList where employee equals to employeeId
        defaultSkillShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the skillList where employee equals to (employeeId + 1)
        defaultSkillShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSkillShouldBeFound(String filter) throws Exception {
        restSkillMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(skill.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restSkillMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSkillShouldNotBeFound(String filter) throws Exception {
        restSkillMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSkillMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSkill() throws Exception {
        // Get the skill
        restSkillMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    // @Test
    // @Transactional
    // void putNewSkill() throws Exception {
    //     // Initialize the database
    //     skillRepository.saveAndFlush(skill);

    //     int databaseSizeBeforeUpdate = skillRepository.findAll().size();

    //     // Update the skill
    //     Skill updatedSkill = skillRepository.findById(skill.getId()).get();
    //     // Disconnect from session so that the updates on updatedSkill are not directly saved in db
    //     em.detach(updatedSkill);
    //     updatedSkill.title(UPDATED_TITLE).description(UPDATED_DESCRIPTION);

    //     restSkillMockMvc
    //         .perform(
    //             put(ENTITY_API_URL_ID, updatedSkill.getId())
    //                 .contentType(MediaType.APPLICATION_JSON)
    //                 .content(TestUtil.convertObjectToJsonBytes(updatedSkill))
    //         )
    //         .andExpect(status().isOk());

    //     // Validate the Skill in the database
    //     List<Skill> skillList = skillRepository.findAll();
    //     assertThat(skillList).hasSize(databaseSizeBeforeUpdate);
    //     Skill testSkill = skillList.get(skillList.size() - 1);
    //     assertThat(testSkill.getTitle()).isEqualTo(UPDATED_TITLE);
    //     assertThat(testSkill.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    // }

    // @Test
    // @Transactional
    // void putNonExistingSkill() throws Exception {
    //     int databaseSizeBeforeUpdate = skillRepository.findAll().size();
    //     skill.setId(count.incrementAndGet());

    //     // If the entity doesn't have an ID, it will throw BadRequestAlertException
    //     restSkillMockMvc
    //         .perform(
    //             put(ENTITY_API_URL_ID, skill.getId())
    //                 .contentType(MediaType.APPLICATION_JSON)
    //                 .content(TestUtil.convertObjectToJsonBytes(skill))
    //         )
    //         .andExpect(status().isBadRequest());

    //     // Validate the Skill in the database
    //     List<Skill> skillList = skillRepository.findAll();
    //     assertThat(skillList).hasSize(databaseSizeBeforeUpdate);
    // }

    // @Test
    // @Transactional
    // void putWithIdMismatchSkill() throws Exception {
    //     int databaseSizeBeforeUpdate = skillRepository.findAll().size();
    //     skill.setId(count.incrementAndGet());

    //     // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    //     restSkillMockMvc
    //         .perform(
    //             put(ENTITY_API_URL_ID, count.incrementAndGet())
    //                 .contentType(MediaType.APPLICATION_JSON)
    //                 .content(TestUtil.convertObjectToJsonBytes(skill))
    //         )
    //         .andExpect(status().isBadRequest());

    //     // Validate the Skill in the database
    //     List<Skill> skillList = skillRepository.findAll();
    //     assertThat(skillList).hasSize(databaseSizeBeforeUpdate);
    // }

    @Test
    @Transactional
    void putWithMissingIdPathParamSkill() throws Exception {
        int databaseSizeBeforeUpdate = skillRepository.findAll().size();
        skill.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSkillMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(skill)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Skill in the database
        List<Skill> skillList = skillRepository.findAll();
        assertThat(skillList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSkillWithPatch() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        int databaseSizeBeforeUpdate = skillRepository.findAll().size();

        // Update the skill using partial update
        Skill partialUpdatedSkill = new Skill();
        partialUpdatedSkill.setId(skill.getId());

        partialUpdatedSkill.title(UPDATED_TITLE);

        restSkillMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSkill.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSkill))
            )
            .andExpect(status().isOk());

        // Validate the Skill in the database
        List<Skill> skillList = skillRepository.findAll();
        assertThat(skillList).hasSize(databaseSizeBeforeUpdate);
        Skill testSkill = skillList.get(skillList.size() - 1);
        assertThat(testSkill.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testSkill.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateSkillWithPatch() throws Exception {
        // Initialize the database
        skillRepository.saveAndFlush(skill);

        int databaseSizeBeforeUpdate = skillRepository.findAll().size();

        // Update the skill using partial update
        Skill partialUpdatedSkill = new Skill();
        partialUpdatedSkill.setId(skill.getId());

        partialUpdatedSkill.title(UPDATED_TITLE).description(UPDATED_DESCRIPTION);

        restSkillMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSkill.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSkill))
            )
            .andExpect(status().isOk());

        // Validate the Skill in the database
        List<Skill> skillList = skillRepository.findAll();
        assertThat(skillList).hasSize(databaseSizeBeforeUpdate);
        Skill testSkill = skillList.get(skillList.size() - 1);
        assertThat(testSkill.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testSkill.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingSkill() throws Exception {
        int databaseSizeBeforeUpdate = skillRepository.findAll().size();
        skill.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSkillMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, skill.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(skill))
            )
            .andExpect(status().isBadRequest());

        // Validate the Skill in the database
        List<Skill> skillList = skillRepository.findAll();
        assertThat(skillList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSkill() throws Exception {
        int databaseSizeBeforeUpdate = skillRepository.findAll().size();
        skill.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSkillMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(skill))
            )
            .andExpect(status().isBadRequest());

        // Validate the Skill in the database
        List<Skill> skillList = skillRepository.findAll();
        assertThat(skillList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSkill() throws Exception {
        int databaseSizeBeforeUpdate = skillRepository.findAll().size();
        skill.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSkillMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(skill)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Skill in the database
        List<Skill> skillList = skillRepository.findAll();
        assertThat(skillList).hasSize(databaseSizeBeforeUpdate);
    }
    // @Test
    // @Transactional
    // void deleteSkill() throws Exception {
    //     // Initialize the database
    //     skillRepository.saveAndFlush(skill);

    //     int databaseSizeBeforeDelete = skillRepository.findAll().size();

    //     // Delete the skill
    //     restSkillMockMvc
    //         .perform(delete(ENTITY_API_URL_ID, skill.getId()).accept(MediaType.APPLICATION_JSON))
    //         .andExpect(status().isNoContent());

    //     // Validate the database contains one less item
    //     List<Skill> skillList = skillRepository.findAll();
    //     assertThat(skillList).hasSize(databaseSizeBeforeDelete - 1);
    // }
}
