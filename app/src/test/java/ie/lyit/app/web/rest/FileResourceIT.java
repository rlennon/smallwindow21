package ie.lyit.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ie.lyit.app.IntegrationTest;
import ie.lyit.app.domain.Employee;
import ie.lyit.app.domain.File;
import ie.lyit.app.repository.FileRepository;
import ie.lyit.app.service.criteria.FileCriteria;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link FileResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FileResourceIT {

    private static final String DEFAULT_S_3_FILE_KEY = "AAAAAAAAAA";
    private static final String UPDATED_S_3_FILE_KEY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/files";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFileMockMvc;

    private File file;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static File createEntity(EntityManager em) {
        File file = new File().s3FileKey(DEFAULT_S_3_FILE_KEY);
        return file;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static File createUpdatedEntity(EntityManager em) {
        File file = new File().s3FileKey(UPDATED_S_3_FILE_KEY);
        return file;
    }

    @BeforeEach
    public void initTest() {
        file = createEntity(em);
    }

    @Test
    @Transactional
    void createFile() throws Exception {
        int databaseSizeBeforeCreate = fileRepository.findAll().size();
        // Create the File
        restFileMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(file)))
            .andExpect(status().isCreated());

        // Validate the File in the database
        List<File> fileList = fileRepository.findAll();
        assertThat(fileList).hasSize(databaseSizeBeforeCreate + 1);
        File testFile = fileList.get(fileList.size() - 1);
        assertThat(testFile.gets3FileKey()).isEqualTo(DEFAULT_S_3_FILE_KEY);
    }

    @Test
    @Transactional
    void createFileWithExistingId() throws Exception {
        // Create the File with an existing ID
        file.setId(1L);

        int databaseSizeBeforeCreate = fileRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFileMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(file)))
            .andExpect(status().isBadRequest());

        // Validate the File in the database
        List<File> fileList = fileRepository.findAll();
        assertThat(fileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFiles() throws Exception {
        // Initialize the database
        fileRepository.saveAndFlush(file);

        // Get all the fileList
        restFileMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(file.getId().intValue())))
            .andExpect(jsonPath("$.[*].s3FileKey").value(hasItem(DEFAULT_S_3_FILE_KEY)));
    }

    @Test
    @Transactional
    void getFile() throws Exception {
        // Initialize the database
        fileRepository.saveAndFlush(file);

        // Get the file
        restFileMockMvc
            .perform(get(ENTITY_API_URL_ID, file.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(file.getId().intValue()))
            .andExpect(jsonPath("$.s3FileKey").value(DEFAULT_S_3_FILE_KEY));
    }

    @Test
    @Transactional
    void getFilesByIdFiltering() throws Exception {
        // Initialize the database
        fileRepository.saveAndFlush(file);

        Long id = file.getId();

        defaultFileShouldBeFound("id.equals=" + id);
        defaultFileShouldNotBeFound("id.notEquals=" + id);

        defaultFileShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFileShouldNotBeFound("id.greaterThan=" + id);

        defaultFileShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFileShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFilesBys3FileKeyIsEqualToSomething() throws Exception {
        // Initialize the database
        fileRepository.saveAndFlush(file);

        // Get all the fileList where s3FileKey equals to DEFAULT_S_3_FILE_KEY
        defaultFileShouldBeFound("s3FileKey.equals=" + DEFAULT_S_3_FILE_KEY);

        // Get all the fileList where s3FileKey equals to UPDATED_S_3_FILE_KEY
        defaultFileShouldNotBeFound("s3FileKey.equals=" + UPDATED_S_3_FILE_KEY);
    }

    @Test
    @Transactional
    void getAllFilesBys3FileKeyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fileRepository.saveAndFlush(file);

        // Get all the fileList where s3FileKey not equals to DEFAULT_S_3_FILE_KEY
        defaultFileShouldNotBeFound("s3FileKey.notEquals=" + DEFAULT_S_3_FILE_KEY);

        // Get all the fileList where s3FileKey not equals to UPDATED_S_3_FILE_KEY
        defaultFileShouldBeFound("s3FileKey.notEquals=" + UPDATED_S_3_FILE_KEY);
    }

    @Test
    @Transactional
    void getAllFilesBys3FileKeyIsInShouldWork() throws Exception {
        // Initialize the database
        fileRepository.saveAndFlush(file);

        // Get all the fileList where s3FileKey in DEFAULT_S_3_FILE_KEY or UPDATED_S_3_FILE_KEY
        defaultFileShouldBeFound("s3FileKey.in=" + DEFAULT_S_3_FILE_KEY + "," + UPDATED_S_3_FILE_KEY);

        // Get all the fileList where s3FileKey equals to UPDATED_S_3_FILE_KEY
        defaultFileShouldNotBeFound("s3FileKey.in=" + UPDATED_S_3_FILE_KEY);
    }

    @Test
    @Transactional
    void getAllFilesBys3FileKeyIsNullOrNotNull() throws Exception {
        // Initialize the database
        fileRepository.saveAndFlush(file);

        // Get all the fileList where s3FileKey is not null
        defaultFileShouldBeFound("s3FileKey.specified=true");

        // Get all the fileList where s3FileKey is null
        defaultFileShouldNotBeFound("s3FileKey.specified=false");
    }

    @Test
    @Transactional
    void getAllFilesBys3FileKeyContainsSomething() throws Exception {
        // Initialize the database
        fileRepository.saveAndFlush(file);

        // Get all the fileList where s3FileKey contains DEFAULT_S_3_FILE_KEY
        defaultFileShouldBeFound("s3FileKey.contains=" + DEFAULT_S_3_FILE_KEY);

        // Get all the fileList where s3FileKey contains UPDATED_S_3_FILE_KEY
        defaultFileShouldNotBeFound("s3FileKey.contains=" + UPDATED_S_3_FILE_KEY);
    }

    @Test
    @Transactional
    void getAllFilesBys3FileKeyNotContainsSomething() throws Exception {
        // Initialize the database
        fileRepository.saveAndFlush(file);

        // Get all the fileList where s3FileKey does not contain DEFAULT_S_3_FILE_KEY
        defaultFileShouldNotBeFound("s3FileKey.doesNotContain=" + DEFAULT_S_3_FILE_KEY);

        // Get all the fileList where s3FileKey does not contain UPDATED_S_3_FILE_KEY
        defaultFileShouldBeFound("s3FileKey.doesNotContain=" + UPDATED_S_3_FILE_KEY);
    }

    @Test
    @Transactional
    void getAllFilesByEmployeeIsEqualToSomething() throws Exception {
        // Initialize the database
        fileRepository.saveAndFlush(file);
        Employee employee = EmployeeResourceIT.createEntity(em);
        em.persist(employee);
        em.flush();
        file.setEmployee(employee);
        fileRepository.saveAndFlush(file);
        Long employeeId = employee.getId();

        // Get all the fileList where employee equals to employeeId
        defaultFileShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the fileList where employee equals to (employeeId + 1)
        defaultFileShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFileShouldBeFound(String filter) throws Exception {
        restFileMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(file.getId().intValue())))
            .andExpect(jsonPath("$.[*].s3FileKey").value(hasItem(DEFAULT_S_3_FILE_KEY)));

        // Check, that the count call also returns 1
        restFileMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFileShouldNotBeFound(String filter) throws Exception {
        restFileMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFileMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFile() throws Exception {
        // Get the file
        restFileMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    // @Test
    // @Transactional
    // void putNewFile() throws Exception {
    //     // Initialize the database
    //     fileRepository.saveAndFlush(file);

    //     int databaseSizeBeforeUpdate = fileRepository.findAll().size();

    //     // Update the file
    //     File updatedFile = fileRepository.findById(file.getId()).get();
    //     // Disconnect from session so that the updates on updatedFile are not directly saved in db
    //     em.detach(updatedFile);
    //     updatedFile.s3FileKey(UPDATED_S_3_FILE_KEY);

    //     restFileMockMvc
    //         .perform(
    //             put(ENTITY_API_URL_ID, updatedFile.getId())
    //                 .contentType(MediaType.APPLICATION_JSON)
    //                 .content(TestUtil.convertObjectToJsonBytes(updatedFile))
    //         )
    //         .andExpect(status().isOk());

    //     // Validate the File in the database
    //     List<File> fileList = fileRepository.findAll();
    //     assertThat(fileList).hasSize(databaseSizeBeforeUpdate);
    //     File testFile = fileList.get(fileList.size() - 1);
    //     assertThat(testFile.gets3FileKey()).isEqualTo(UPDATED_S_3_FILE_KEY);
    // }

    // @Test
    // @Transactional
    // void putNonExistingFile() throws Exception {
    //     int databaseSizeBeforeUpdate = fileRepository.findAll().size();
    //     file.setId(count.incrementAndGet());

    //     // If the entity doesn't have an ID, it will throw BadRequestAlertException
    //     restFileMockMvc
    //         .perform(
    //             put(ENTITY_API_URL_ID, file.getId())
    //                 .contentType(MediaType.APPLICATION_JSON)
    //                 .content(TestUtil.convertObjectToJsonBytes(file))
    //         )
    //         .andExpect(status().isBadRequest());

    //     // Validate the File in the database
    //     List<File> fileList = fileRepository.findAll();
    //     assertThat(fileList).hasSize(databaseSizeBeforeUpdate);
    // }

    // @Test
    // @Transactional
    // void putWithIdMismatchFile() throws Exception {
    //     int databaseSizeBeforeUpdate = fileRepository.findAll().size();
    //     file.setId(count.incrementAndGet());

    //     // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    //     restFileMockMvc
    //         .perform(
    //             put(ENTITY_API_URL_ID, count.incrementAndGet())
    //                 .contentType(MediaType.APPLICATION_JSON)
    //                 .content(TestUtil.convertObjectToJsonBytes(file))
    //         )
    //         .andExpect(status().isBadRequest());

    //     // Validate the File in the database
    //     List<File> fileList = fileRepository.findAll();
    //     assertThat(fileList).hasSize(databaseSizeBeforeUpdate);
    // }

    @Test
    @Transactional
    void putWithMissingIdPathParamFile() throws Exception {
        int databaseSizeBeforeUpdate = fileRepository.findAll().size();
        file.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFileMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(file)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the File in the database
        List<File> fileList = fileRepository.findAll();
        assertThat(fileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFileWithPatch() throws Exception {
        // Initialize the database
        fileRepository.saveAndFlush(file);

        int databaseSizeBeforeUpdate = fileRepository.findAll().size();

        // Update the file using partial update
        File partialUpdatedFile = new File();
        partialUpdatedFile.setId(file.getId());

        partialUpdatedFile.s3FileKey(UPDATED_S_3_FILE_KEY);

        restFileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFile))
            )
            .andExpect(status().isOk());

        // Validate the File in the database
        List<File> fileList = fileRepository.findAll();
        assertThat(fileList).hasSize(databaseSizeBeforeUpdate);
        File testFile = fileList.get(fileList.size() - 1);
        assertThat(testFile.gets3FileKey()).isEqualTo(UPDATED_S_3_FILE_KEY);
    }

    @Test
    @Transactional
    void fullUpdateFileWithPatch() throws Exception {
        // Initialize the database
        fileRepository.saveAndFlush(file);

        int databaseSizeBeforeUpdate = fileRepository.findAll().size();

        // Update the file using partial update
        File partialUpdatedFile = new File();
        partialUpdatedFile.setId(file.getId());

        partialUpdatedFile.s3FileKey(UPDATED_S_3_FILE_KEY);

        restFileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFile))
            )
            .andExpect(status().isOk());

        // Validate the File in the database
        List<File> fileList = fileRepository.findAll();
        assertThat(fileList).hasSize(databaseSizeBeforeUpdate);
        File testFile = fileList.get(fileList.size() - 1);
        assertThat(testFile.gets3FileKey()).isEqualTo(UPDATED_S_3_FILE_KEY);
    }

    @Test
    @Transactional
    void patchNonExistingFile() throws Exception {
        int databaseSizeBeforeUpdate = fileRepository.findAll().size();
        file.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, file.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(file))
            )
            .andExpect(status().isBadRequest());

        // Validate the File in the database
        List<File> fileList = fileRepository.findAll();
        assertThat(fileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFile() throws Exception {
        int databaseSizeBeforeUpdate = fileRepository.findAll().size();
        file.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(file))
            )
            .andExpect(status().isBadRequest());

        // Validate the File in the database
        List<File> fileList = fileRepository.findAll();
        assertThat(fileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFile() throws Exception {
        int databaseSizeBeforeUpdate = fileRepository.findAll().size();
        file.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFileMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(file)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the File in the database
        List<File> fileList = fileRepository.findAll();
        assertThat(fileList).hasSize(databaseSizeBeforeUpdate);
    }
    // @Test
    // @Transactional
    // void deleteFile() throws Exception {
    //     // Initialize the database
    //     fileRepository.saveAndFlush(file);

    //     int databaseSizeBeforeDelete = fileRepository.findAll().size();

    //     // Delete the file
    //     restFileMockMvc
    //         .perform(delete(ENTITY_API_URL_ID, file.getId()).accept(MediaType.APPLICATION_JSON))
    //         .andExpect(status().isNoContent());

    //     // Validate the database contains one less item
    //     List<File> fileList = fileRepository.findAll();
    //     assertThat(fileList).hasSize(databaseSizeBeforeDelete - 1);
    // }
}
