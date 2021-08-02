package ie.lyit.app.web.rest;

import ie.lyit.app.domain.Employee;
import ie.lyit.app.domain.File;
import ie.lyit.app.repository.EmployeeRepository;
import ie.lyit.app.repository.FileRepository;
import ie.lyit.app.security.AuthoritiesConstants;
import ie.lyit.app.service.aws.S3Service;
import ie.lyit.app.web.rest.errors.BadRequestAlertException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ie.lyit.app.domain.Employee}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EmployeeResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeResource.class);

    private static final String ENTITY_NAME = "employee";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeRepository employeeRepository;
    private final S3Service s3Service;
    private final FileRepository fileRepository;

    /**
     *
     * @param employeeRepository -
     * @param s3Service -
     * @param fileRepository -
     */
    public EmployeeResource(EmployeeRepository employeeRepository, S3Service s3Service, FileRepository fileRepository) {
        this.employeeRepository = employeeRepository;
        this.s3Service = s3Service;
        this.fileRepository = fileRepository;
    }

    /**
     * {@code POST  /employees} : Create a new employee.
     *
     * @param employee the employee to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employee, or with status {@code 400 (Bad Request)} if the employee has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employees")
    @ApiOperation(value = "Create a new employee", notes = "Allows you to create a new employee on the system")
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee employee) throws URISyntaxException {
        log.debug("REST request to save Employee : {}", employee);
        if (employee.getId() != null) {
            throw new BadRequestAlertException("A new employee cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Employee result = employeeRepository.save(employee);
        return ResponseEntity
            .created(new URI("/api/employees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employees/:id} : Updates an existing employee.
     *
     * @param id the id of the employee to save.
     * @param employee the employee to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employee,
     * or with status {@code 400 (Bad Request)} if the employee is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employee couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employees/{id}")
    @ApiOperation(value = "Update existing employee", notes = "Allows you to update an existing employee on the system")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Employee> updateEmployee(
        @PathVariable(value = "id", required = false) @ApiParam(value = "Id of the employee to update") Long id,
        @Valid @RequestBody Employee employee
    ) throws URISyntaxException {
        log.debug("REST request to update Employee : {}, {}", id, employee);
        if (employee.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employee.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Employee result = employeeRepository.save(employee);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employee.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /employees/:id} : Partial updates given fields of an existing employee, field will ignore if it is null
     *
     * @param id the id of the employee to save.
     * @param employee the employee to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employee,
     * or with status {@code 400 (Bad Request)} if the employee is not valid,
     * or with status {@code 404 (Not Found)} if the employee is not found,
     * or with status {@code 500 (Internal Server Error)} if the employee couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/employees/{id}", consumes = "application/merge-patch+json")
    @ApiOperation(value = "Partially Update existing employee", notes = "Allows you to partially update an existing employee on the system")
    public ResponseEntity<Employee> partialUpdateEmployee(
        @PathVariable(value = "id", required = false) @ApiParam(value = "Id of the employee to update") Long id,
        @NotNull @RequestBody Employee employee
    ) throws URISyntaxException {
        log.debug("REST request to partial update Employee partially : {}, {}", id, employee);
        if (employee.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employee.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Employee> result = employeeRepository
            .findById(employee.getId())
            .map(
                existingEmployee -> {
                    if (employee.getFirstName() != null) {
                        existingEmployee.setFirstName(employee.getFirstName());
                    }
                    if (employee.getLastName() != null) {
                        existingEmployee.setLastName(employee.getLastName());
                    }
                    if (employee.getEmail() != null) {
                        existingEmployee.setEmail(employee.getEmail());
                    }
                    if (employee.gets3ImageKey() != null) {
                        existingEmployee.sets3ImageKey(employee.gets3ImageKey());
                    }

                    return existingEmployee;
                }
            )
            .map(employeeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employee.getId().toString())
        );
    }

    /**
     * {@code GET  /employees} : get all the employees.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employees in body.
     */
    @GetMapping("/employees")
    @ApiOperation(value = "Retrieve all employees", notes = "Allows you to retrieve all employees on the system")
    public ResponseEntity<List<Employee>> getAllEmployees(Pageable pageable) {
        log.debug("REST request to get a page of Employees");
        Page<Employee> page = employeeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /employees/:id} : get the "id" employee.
     *
     * @param id the id of the employee to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employee, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employees/{id}")
    @ApiOperation(value = "Retrieve an employee", notes = "Allows you to retrieve an employee on the system based on id")
    public ResponseEntity<Employee> getEmployee(@PathVariable @ApiParam(value = "Id of the employee to retrieve") Long id) {
        log.debug("REST request to get Employee : {}", id);
        Optional<Employee> employee = employeeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(employee);
    }

    /**
     * {@code DELETE  /employees/:id} : delete the "id" employee.
     *
     * @param id the id of the employee to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employees/{id}")
    @ApiOperation(value = "Delete an employee", notes = "Allows you to delete a employee on the system based on id")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteEmployee(@PathVariable @ApiParam(value = "Id of the employee to delete") Long id) {
        log.debug("REST request to delete Employee : {}", id);
        employeeRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * Method to upload a profile image
     *
     * @param file image file to upload
     * @param filename name of uploaded file
     * @return -
     */
    @PostMapping("/employees/profileImage/{filename:.+}")
    @ApiOperation(value = "Upload the users profile image", notes = "Allows you to upload a users profile image to S3")
    public boolean handleFileUpload(
        @RequestParam("file") MultipartFile file,
        @PathVariable @ApiParam(value = "Name of the profile image") String filename
    ) {
        try {
            return s3Service.uploadFile(file.getBytes(), filename);
        } catch (IOException e) {
            log.error("IOException occurred handling the handle the file upload.");
            return false;
        }
    }

    /**
     * Method to download a profile image and serve it
     *
     * @param filename -
     * @return -
     */
    // See https://spring.io/guides/gs/uploading-files/
    @GetMapping("/employees/profileImage/{filename:.+}")
    @ResponseBody
    @ApiOperation(value = "Download profile image", notes = "Allows you to download a profile image from S3")
    public ResponseEntity<String> downloadProfileImage(
        @PathVariable @ApiParam(value = "Name of the profile image to download") String filename
    ) {
        byte[] file = s3Service.downloadFile(filename);
        if (file != null && file.length > 0) {
            String base64Image = Base64.getEncoder().encodeToString(file);
            return ResponseEntity.ok().body(base64Image);
        }
        return ResponseEntity.badRequest().body(null);
    }

    /**
     * Method to delete a profile image
     *
     * @param id - the id of the employee to delete the profile image for
     * @return -
     */
    // See https://spring.io/guides/gs/uploading-files/
    @DeleteMapping("/employees/profileImage/{id}")
    @ResponseBody
    @ApiOperation(value = "Delete profile image", notes = "Allows you to delete a profile image from S3")
    public ResponseEntity<Boolean> deleteProfileImage(
        @PathVariable @ApiParam(value = "Id of the employee to delete the profile image for") Long id
    ) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if (!employeeOptional.isPresent()) {
            log.error("No employee found for the id {}", id);
            return ResponseEntity.badRequest().body(false);
        }
        Employee employee = employeeOptional.get();
        String s3ProfileImage = employee.gets3ImageKey();
        employee.sets3ImageKey(null);

        employeeRepository.save(employee);

        boolean result = s3Service.deleteFile(s3ProfileImage);
        return ResponseEntity.ok().body(result);
    }

    /**
     * Method to get all files uploaded for an empoloyee
     *
     * @param id - the id of the employee to delete the profile image for
     * @return -
     */
    @GetMapping("/employees/files/{id}")
    @ResponseBody
    @ApiOperation(value = "List all files", notes = "Allows you to list all files for an employee")
    public ResponseEntity<List<File>> getFiles(
        @PathVariable @ApiParam(value = "Id of the employee to delete the profile image for") Long id
    ) {
        List<File> fileList = fileRepository.findByEmployeeId(id);
        return ResponseEntity.ok().body(fileList);
    }

    /**
     * Method to upload a file
     *
     * @param file file to upload
     * @param id - the id of the employee to delete the profile image for
     * @return -
     */
    @PostMapping("/employees/files/{id}")
    @ResponseBody
    @ApiOperation(value = "Upload a file", notes = "Allows you to upload a file for an employee")
    public boolean uploadFile(
        @RequestParam("file") MultipartFile file,
        @PathVariable @ApiParam(value = "Id of the employee to delete the profile image for") Long id
    ) {
        try {
            Optional<Employee> employeeOptional = employeeRepository.findById(id);
            if (!employeeOptional.isPresent()) {
                log.error("Failed to retrieve employee for the id {}", id);
                return false;
            }
            Employee employee = employeeOptional.get();
            String fileName = file.getOriginalFilename();
            log.info("fileName:{}", fileName);
            String fileType = file.getContentType();
            log.info("fileType:{}", fileType);

            String s3FileKey = id + "/files/" + fileName;
            File fileObj = new File();
            fileObj.setEmployee(employee);
            fileObj.sets3FileKey(s3FileKey);
            fileObj.sets3FileType(fileType);
            fileRepository.save(fileObj);
            return s3Service.uploadFile(file.getBytes(), s3FileKey);
        } catch (IOException e) {
            log.error("IOException occurred handling the handle the file upload.");
            return false;
        }
    }

    /**
     * Method to download a file
     *
     * @param id - the id of the file to download
     * @return -
     */
    @GetMapping("/employees/files/download/{id}")
    @ResponseBody
    @ApiOperation(value = "Download a file", notes = "Allows you to download a file based on fileId")
    public ResponseEntity<byte[]> downloadFile(@PathVariable @ApiParam(value = "Id of the file to download") Long id) {
        Optional<File> fileOptional = fileRepository.findById(id);
        if (!fileOptional.isPresent()) {
            log.error("Failed to retrieve file for the id {}", id);
            return ResponseEntity.badRequest().body("Unable to find file".getBytes());
        }

        File file = fileOptional.get();
        String s3FileKey = file.gets3FileKey();

        byte[] fileByteArray = s3Service.downloadFile(s3FileKey);
        return ResponseEntity
            .ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + s3FileKey + "\"")
            .body(fileByteArray);
    }

    /**
     * Method to delete a file based on id
     *
     * @param id - the id of the file to delete
     * @return -
     */
    // See https://spring.io/guides/gs/uploading-files/
    @DeleteMapping("/employees/files/{id}")
    @ResponseBody
    @ApiOperation(value = "Delete file", notes = "Allows you to delete a file")
    public ResponseEntity<Boolean> deleteFile(@PathVariable @ApiParam(value = "Id of the file to delete") Long id) {
        Optional<File> fileOptional = fileRepository.findById(id);
        if (!fileOptional.isPresent()) {
            log.error("No file found for the id {}", id);
            return ResponseEntity.badRequest().body(false);
        }
        File file = fileOptional.get();
        String s3Key = file.gets3FileKey();

        fileRepository.deleteById(id);

        boolean result = s3Service.deleteFile(s3Key);
        return ResponseEntity.ok().body(result);
    }
}
