package ie.lyit.app.service.aws;

import static org.junit.Assert.*;

import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

@RunWith(MockitoJUnitRunner.class)
public class S3ServiceTest {

    @Mock
    S3Client mockS3Client;

    @Mock
    ResponseInputStream mockResponseInputStream;

    S3Service s3Service;

    @Before
    public void setUp() {
        s3Service = new S3Service(mockS3Client);
    }

    @Test
    public void testUploadFile_ContentsIsNull() {
        byte[] contents = null;
        String fileKey = "fileKey";
        boolean result = s3Service.uploadFile(contents, fileKey);
        assertFalse(result);
    }

    @Test
    public void testUploadFile_ContentsIsEmpty() {
        byte[] contents = new byte[] {};
        String fileKey = "fileKey";
        boolean result = s3Service.uploadFile(contents, fileKey);
        assertFalse(result);
    }

    @Test
    public void testUploadFile_FileKeyIsNull() {
        byte[] contents = "contents".getBytes();
        String fileKey = null;
        boolean result = s3Service.uploadFile(contents, fileKey);
        assertFalse(result);
    }

    @Test
    public void testUploadFile_FileKeyIsEmpty() {
        byte[] contents = "contents".getBytes();
        String fileKey = "";
        boolean result = s3Service.uploadFile(contents, fileKey);
        assertFalse(result);
    }

    @Test
    public void testUploadFile_PutObjectResponseIsNull() {
        byte[] contents = "contents".getBytes();
        String fileKey = "fileKey";

        PutObjectResponse putObjectResponse = null;
        Mockito
            .when(mockS3Client.putObject(Mockito.isA(PutObjectRequest.class), Mockito.isA(RequestBody.class)))
            .thenReturn(putObjectResponse);

        boolean result = s3Service.uploadFile(contents, fileKey);
        assertFalse(result);

        Mockito.verify(mockS3Client, Mockito.times(1)).putObject(Mockito.isA(PutObjectRequest.class), Mockito.isA(RequestBody.class));
    }

    @Test
    public void testUploadFile_S3Exception() {
        byte[] contents = "contents".getBytes();
        String fileKey = "fileKey";

        PutObjectResponse putObjectResponse = null;
        Mockito
            .when(mockS3Client.putObject(Mockito.isA(PutObjectRequest.class), Mockito.isA(RequestBody.class)))
            .thenThrow(S3Exception.builder().build());

        boolean result = s3Service.uploadFile(contents, fileKey);
        assertFalse(result);

        Mockito.verify(mockS3Client, Mockito.times(1)).putObject(Mockito.isA(PutObjectRequest.class), Mockito.isA(RequestBody.class));
    }

    @Test
    public void testUploadFile_Success() {
        byte[] contents = "contents".getBytes();
        String fileKey = "fileKey";

        PutObjectResponse putObjectResponse = PutObjectResponse.builder().build();
        Mockito
            .when(mockS3Client.putObject(Mockito.isA(PutObjectRequest.class), Mockito.isA(RequestBody.class)))
            .thenReturn(putObjectResponse);

        boolean result = s3Service.uploadFile(contents, fileKey);
        assertTrue(result);

        Mockito.verify(mockS3Client, Mockito.times(1)).putObject(Mockito.isA(PutObjectRequest.class), Mockito.isA(RequestBody.class));
    }

    @Test
    public void testDownloadFile_FileKeyIsNull() {
        String fileKey = null;
        byte[] result = s3Service.downloadFile(fileKey);
        assertNull(result);
    }

    @Test
    public void testDownloadFile_FileKeyIsEmpty() {
        String fileKey = "";
        byte[] result = s3Service.downloadFile(fileKey);
        assertNull(result);
    }

    @Test
    public void testDownloadFile_ResponseInputStreamIsNull() {
        byte[] contents = "contents".getBytes();
        String fileKey = "fileKey";

        ResponseInputStream responseInputStream = null;
        Mockito.when(mockS3Client.getObject(Mockito.isA(GetObjectRequest.class))).thenReturn(responseInputStream);

        byte[] result = s3Service.downloadFile(fileKey);
        assertNull(result);

        Mockito.verify(mockS3Client, Mockito.times(1)).getObject(Mockito.isA(GetObjectRequest.class));
    }

    @Test
    public void testDownloadFile_S3Exception() {
        byte[] contents = "contents".getBytes();
        String fileKey = "fileKey";

        Mockito.when(mockS3Client.getObject(Mockito.isA(GetObjectRequest.class))).thenThrow(S3Exception.builder().build());

        byte[] result = s3Service.downloadFile(fileKey);
        assertNull(result);

        Mockito.verify(mockS3Client, Mockito.times(1)).getObject(Mockito.isA(GetObjectRequest.class));
    }

    @Test
    public void testDownloadFile_IOException() throws IOException {
        byte[] contents = "contents".getBytes();
        String fileKey = "fileKey";

        Mockito.when(mockS3Client.getObject(Mockito.isA(GetObjectRequest.class))).thenReturn(mockResponseInputStream);
        Mockito.when(mockResponseInputStream.readAllBytes()).thenThrow(new IOException());

        byte[] result = s3Service.downloadFile(fileKey);
        assertNull(result);

        Mockito.verify(mockS3Client, Mockito.times(1)).getObject(Mockito.isA(GetObjectRequest.class));
        Mockito.verify(mockResponseInputStream, Mockito.times(1)).readAllBytes();
    }

    @Test
    public void testDownloadFile_Success() throws IOException {
        byte[] contents = "contents".getBytes();
        String fileKey = "fileKey";

        Mockito.when(mockS3Client.getObject(Mockito.isA(GetObjectRequest.class))).thenReturn(mockResponseInputStream);
        Mockito.when(mockResponseInputStream.readAllBytes()).thenReturn("test".getBytes());

        byte[] result = s3Service.downloadFile(fileKey);
        assertNotNull(result);

        Mockito.verify(mockS3Client, Mockito.times(1)).getObject(Mockito.isA(GetObjectRequest.class));
        Mockito.verify(mockResponseInputStream, Mockito.times(1)).readAllBytes();
    }

    @Test
    public void testDeleteFile_FileKeyIsNull() {
        String fileKey = null;
        boolean result = s3Service.deleteFile(fileKey);
        assertFalse(result);
    }

    @Test
    public void testDeleteFile_FileKeyIsEmpty() {
        String fileKey = "";
        boolean result = s3Service.deleteFile(fileKey);
        assertFalse(result);
    }

    @Test
    public void testDeleteFile_DeleteObjectResponseIsNull() throws IOException {
        byte[] contents = "contents".getBytes();
        String fileKey = "fileKey";

        DeleteObjectResponse deleteObjectRequest = null;
        Mockito.when(mockS3Client.deleteObject(Mockito.isA(DeleteObjectRequest.class))).thenReturn(deleteObjectRequest);

        boolean result = s3Service.deleteFile(fileKey);
        assertFalse(result);

        Mockito.verify(mockS3Client, Mockito.times(1)).deleteObject(Mockito.isA(DeleteObjectRequest.class));
    }

    @Test
    public void testDeleteFile_S3Exception() throws IOException {
        byte[] contents = "contents".getBytes();
        String fileKey = "fileKey";

        DeleteObjectResponse deleteObjectRequest = null;
        Mockito.when(mockS3Client.deleteObject(Mockito.isA(DeleteObjectRequest.class))).thenThrow(S3Exception.builder().build());

        boolean result = s3Service.deleteFile(fileKey);
        assertFalse(result);

        Mockito.verify(mockS3Client, Mockito.times(1)).deleteObject(Mockito.isA(DeleteObjectRequest.class));
    }

    @Test
    public void testDeleteFile_Success() throws IOException {
        byte[] contents = "contents".getBytes();
        String fileKey = "fileKey";

        DeleteObjectResponse deleteObjectRequest = DeleteObjectResponse.builder().build();
        Mockito.when(mockS3Client.deleteObject(Mockito.isA(DeleteObjectRequest.class))).thenReturn(deleteObjectRequest);

        boolean result = s3Service.deleteFile(fileKey);
        assertTrue(result);

        Mockito.verify(mockS3Client, Mockito.times(1)).deleteObject(Mockito.isA(DeleteObjectRequest.class));
    }
}
