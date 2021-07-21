package ie.lyit.app.config;

import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ClientBuilder;

/**
 * Main AWS Config class
 *
 * @author smallwindow21 team
 *
 */
@Configuration
public class AWSConfiguration {

    @Value("${aws.region}")
    private String awsRegion;

    @Value("${aws.local.endpoint:#{null}}")
    private String awsEndpoint;

    /**
     * Support for the AWS S3 Client.
     * @return an s3 client instance
     * @throws URISyntaxException exception thrown
     */
    @Bean
    public S3Client s3Client() throws URISyntaxException {
        Region region = Region.of(awsRegion);
        S3ClientBuilder s3ClientBuilder = S3Client.builder();
        s3ClientBuilder.region(region);
        if (awsEndpoint != null) {
            s3ClientBuilder.endpointOverride(new URI(awsEndpoint));
        }
        return s3ClientBuilder.build();
    }
}
