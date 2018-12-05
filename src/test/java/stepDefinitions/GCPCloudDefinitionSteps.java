package stepDefinitions;

import cucumber.api.java8.En;
import steps.GCPCloudSteps;

import java.util.concurrent.Callable;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.awaitility.Awaitility.await;
import static steps.GCPCloudSteps.*;

public class GCPCloudDefinitionSteps implements En {

    public GCPCloudDefinitionSteps() {
        Given("^Authorization in GCP$", GCPCloudSteps::authGCP);

        And("^Upload file \"([^\"]*)\" in GCP \"([^\"]*)\" bucket$", GCPCloudSteps::uploadFileInGCPBucket);

        Then("^Check file name \"([^\"]*)\" is existed in GCP \"([^\"]*)\" bucket (.*)$", (String fileName, String bucketName, String state) -> {
            assertThat(Boolean.valueOf(state)).as("No such file in GCP bucket")
                    .isEqualTo(isFileNameInGCPExisted(fileName, bucketName));
        });

        And("^Wait for \"([^\"]*)\" file to appear in GCP \"([^\"]*)\" bucket", (String fileName, String bucketName) -> {
            await("Awaiting for " + fileName + " file in GCP " + bucketName + " bucket").atMost(60, SECONDS)
                    .until(() -> isFileNameInGCPExisted(fileName, bucketName));
        });

        And("^Download file \"([^\"]*)\" from GCP \"([^\"]*)\" bucket$", GCPCloudSteps::downloadFileFromGCPBucket);

        And("^Delete file \"([^\"]*)\" from GCP \"([^\"]*)\" bucket$", GCPCloudSteps::deleteFileFromGCPBucket);

        Then("^Check number of rows equal \"([^\"]*)\" in \"([^\"]*)\" table$", (String totalCount, String tableName) -> {
            assertThat(totalCount).as("Number of records are different")
                    .isEqualTo(getTotalCountRecordsFromGCPTable(totalCount, tableName));
        });

    }
}
