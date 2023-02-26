
package f3f.infra.aws.S3;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Slf4j
@Service
@NoArgsConstructor
public class S3Config {

    private AmazonS3 s3Client;

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    @PostConstruct
    public void setS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);

        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(this.region)
                .build();
    }


    public String upload(Long id,MultipartFile file, String dirName) throws IOException {
        String fileName = id+"_"+file.getOriginalFilename();

        bucket = bucket + "/" + dirName;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getBytes().length);
        s3Client.putObject(new PutObjectRequest(bucket, fileName, file.getInputStream(), metadata)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return s3Client.getUrl(bucket, fileName).toString();
    }

    public void delete(String fileName, String dirName) {
        log.info("File Delete : " + fileName);

        bucket = bucket + "/" + dirName;
        s3Client.deleteObject(bucket, fileName);
    }
}

