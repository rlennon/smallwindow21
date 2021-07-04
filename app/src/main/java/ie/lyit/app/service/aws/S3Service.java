package ie.lyit.app.service.aws;

import java.io.IOException;
import org.apache.commons.compress.utils.ByteUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

/**
 * Service class for interacting with AWS S3.
 */
@Service
@Transactional
public class S3Service {

    private final Logger log = LoggerFactory.getLogger(S3Service.class);

    private final S3Client s3Client;

    @Value("${aws.storageBucketName}")
    private String storageBucketName;

    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    /**
     * Method to upload a file to AWS S3
     *
     * @param contents - the file contents in byte array format
     * @param key      the key for the file to upload
     * @return
     */
    public boolean uploadFile(byte[] contents, String key) {
        log.debug("Uploading file with key {} to AWS S3", key);

        if (contents == null || contents.length <= 0) {
            log.error("Contents passed in are null or empty");
            return false;
        }
        if (StringUtils.isEmpty(key)) {
            log.error("Key passed in is null or empty");
            return false;
        }

        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder().bucket(storageBucketName).key(key).build();
            RequestBody requestBody = RequestBody.fromBytes(contents);
            PutObjectResponse putObjectResponse = s3Client.putObject(putObjectRequest, requestBody);

            if (putObjectResponse == null) {
                log.error("Failed to upload the file {} to the bucket {}", key, storageBucketName);
                return false;
            }
        } catch (S3Exception e) {
            log.error("An S3Exception occurred trying to upload the file {} to the bucket {}. Exception: {}", key, storageBucketName, e);
            return false;
        }

        return true;
    }

    /**
     * Method to download a file from AWS S3
     *
     * @param key the key for the file to download
     * @return
     */
    public byte[] downloadFile(String key) {
        log.debug("Downloading file with key {} from AWS S3", key);
        byte[] response = null;
        if (StringUtils.isEmpty(key)) {
            log.error("Key passed in is null or empty");
            return response;
        }

        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(storageBucketName).key(key).build();

            ResponseInputStream<GetObjectResponse> responseInputStream = s3Client.getObject(getObjectRequest);

            if (responseInputStream == null) {
                log.error("Failed to download the file {} from the bucket {}", key, storageBucketName);
                return response;
            }
            response = responseInputStream.readAllBytes();
        } catch (S3Exception | IOException e) {
            log.error(
                "An S3Exception/IOException occurred trying to download the file {} from the bucket {}. Exception: {}",
                key,
                storageBucketName,
                e
            );
            return null;
        }

        return response;
    }

    /**
     * Method to delete a file from AWS S3
     *
     * @param key the key for the file to delete
     * @return
     */
    public boolean deleteFile(String key) {
        log.debug("Deleting file with key {} from AWS S3", key);

        if (StringUtils.isEmpty(key)) {
            log.error("Key passed in is null or empty");
            return false;
        }

        try {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder().bucket(storageBucketName).key(key).build();

            DeleteObjectResponse deleteObjectResponse = s3Client.deleteObject(deleteObjectRequest);
            if (deleteObjectResponse == null) {
                log.error("Failed to delete the file {} from the bucket {}", key, storageBucketName);
                return false;
            }
        } catch (S3Exception e) {
            log.error("An S3Exception occurred trying to delete the file {} from the bucket {}. Exception: {}", key, storageBucketName, e);
            return false;
        }

        return true;
    }
}