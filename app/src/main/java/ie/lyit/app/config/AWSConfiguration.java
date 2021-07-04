package ie.lyit.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class AWSConfiguration {

    @Value("${aws.region}")
    private String awsRegion;

    /**
     * Support for the AWS S3 Client.
     * @return an s3 client instance
     */
    @Bean
    public S3Client s3Client() {
        Region region = Region.of(awsRegion);
        return S3Client.builder().region(region).build();
    }
}
