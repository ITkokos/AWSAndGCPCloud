package steps;

import com.google.api.gax.paging.Page;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.bigquery.*;
import com.google.cloud.storage.*;
import utils.ConfigFileReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;

public class GCPCloudSteps {

    private static ConfigFileReader config = new ConfigFileReader();
    private static Storage storage;
    private static BigQuery bigquery;

    public static void authGCP() throws IOException {
        GoogleCredentials gcpCredentials = GoogleCredentials.fromStream(new FileInputStream(config.getGCPKey()));
        storage = StorageOptions.newBuilder().setCredentials(gcpCredentials).build().getService();
        bigquery = BigQueryOptions.newBuilder().setCredentials(gcpCredentials).build().getService();
    }

    public static void uploadFileInGCPBucket(String fileName, String bucketName) throws FileNotFoundException {
        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("text/plain").build();
        Blob blob = storage.create(blobInfo, new FileInputStream("src/test/resources/testData/" + fileName));
    }

    public static boolean isFileNameInGCPExisted(String fileName, String bucketName) {
        Page<Blob> blobs = storage.get(bucketName).list();
        for (Blob blob : blobs.getValues()) {
            if (fileName.equals(blob.getName())) {
                return true;
            }
        }
        return false;
    }

    public static void downloadFileFromGCPBucket(String fileName, String bucketName) {
        Blob blob = storage.get(BlobId.of(bucketName, fileName));
        blob.downloadTo(Paths.get("src/test/resources/testData/downloadedFiles/" + fileName));
    }

    public static void deleteFileFromGCPBucket(String fileName, String bucketName) {
        boolean deleted = storage.delete(BlobId.of(bucketName, fileName));
        if (deleted) {
            System.out.println("File was deleted from GCP Cloud\n");
        } else {
            System.out.println("File was not deleted from GCP Cloud\n");
        }
    }

    public static String getTotalCountRecordsFromGCPTable(String totalCount, String tableName) throws InterruptedException {
        QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder("SELECT COUNT(*) as total_count FROM `"
                + tableName + "`").setUseLegacySql(false).build();
        JobId jobId = JobId.of(UUID.randomUUID().toString());
        Job queryJob = bigquery.create(JobInfo.newBuilder(queryConfig).setJobId(jobId).build());
        queryJob = queryJob.waitFor();
        if (queryJob == null) {
            throw new RuntimeException("Jonb no longer exists");
        } else if (queryJob.getStatus().getError() != null) {
            throw new RuntimeException(queryJob.getStatus().getError().toString());
        }
        for (FieldValueList row : bigquery.query(queryConfig).iterateAll()) {
            totalCount = row.get("total_count").getStringValue();
        }
        return totalCount;
    }

}
