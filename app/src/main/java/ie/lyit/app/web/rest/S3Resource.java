package ie.lyit.app.web.rest;

import ie.lyit.app.service.UserService;
import ie.lyit.app.service.aws.S3Service;
import ie.lyit.app.service.dto.UserDTO;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;

@RestController
@RequestMapping("/api/s3")
public class S3Resource {

    private final Logger log = LoggerFactory.getLogger(S3Resource.class);

    private final S3Service s3Service;

    public S3Resource(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    /**
     * Method to download a file and serve it
     * @param filename
     * @return
     */
    // See https://spring.io/guides/gs/uploading-files/
    @GetMapping("/{filename:.+}")
    @ResponseBody
    public ResponseEntity<byte[]> downloadFile(@PathVariable String filename) {
        byte[] file = s3Service.downloadFile(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"").body(file);
    }

    /**
     * Method to upload a file
     * @param file to upload
     * @return
     */
    @PostMapping("/{filename:.+}")
    public boolean handleFileUpload(@RequestParam("file") MultipartFile file, @PathVariable String filename) {
        try {
            return s3Service.uploadFile(file.getBytes(), filename);
        } catch (IOException e) {
            log.error("IOException occurred handling the handle the file upload.");
            return false;
        }
    }

    /**
     * Method to delete a file
     * @param filename
     * @return
     */
    // See https://spring.io/guides/gs/uploading-files/
    @DeleteMapping("/{filename:.+}")
    public boolean deleteFile(@PathVariable String filename) {
        return s3Service.deleteFile(filename);
    }
}
