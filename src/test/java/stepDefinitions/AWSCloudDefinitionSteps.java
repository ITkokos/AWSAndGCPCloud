package stepDefinitions;

import cucumber.api.java8.En;
import steps.AWSCloudSteps;

public class AWSCloudDefinitionSteps implements En {

    public AWSCloudDefinitionSteps() {
        Given("^Authorization in AWS$", AWSCloudSteps::authAWS);

        When("^Upload file \"([^\"]*)\" in AWS \"([^\"]*)\" bucket$", AWSCloudSteps::uploadFileInAWSBucket);

        And("^Delete file \"([^\"]*)\" from AWS \"([^\"]*)\" bucket$", AWSCloudSteps::deleteFileFromAWSBucket);
    }

}
