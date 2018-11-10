package steps;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import utils.ConfigFileReader;

import java.io.File;

public class AWSCloudSteps {

    private static ConfigFileReader config = new ConfigFileReader();
    private static AmazonS3Client s3Client;

    public static void authAWS() {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(config.getAWSAccessKeyId(), config.getAWSSecretAccessKey());
        s3Client = new AmazonS3Client(awsCredentials);
    }

    public static void uploadFileInAWSBucket(String fileName, String bucketName) {
        try {
            s3Client.putObject(bucketName, fileName, new File("src/test/resources/testData/" + fileName));
            System.out.println("File is uploaded in AWS Cloud\n");
        } catch (AmazonS3Exception e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
    }

    public static void deleteFileFromAWSBucket(String fileName, String bucketName) {
        try {
            s3Client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
            System.out.println("File was deleted in AWS Cloud\n");
        } catch (AmazonS3Exception e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
    }

}
