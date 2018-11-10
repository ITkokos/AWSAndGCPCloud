package stepDefinitions;

import cucumber.api.java8.En;

import java.io.File;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static steps.FileServiceSteps.compareFileContents;

public class FileServiceDefinitionSteps implements En {

    public FileServiceDefinitionSteps() {
        And("^File \"([^\"]*)\" contenta is the same$", (String fileName) -> {
            assertThat(true).as("File contents is not the same")
                    .isEqualTo(compareFileContents(new File("src/test/resources/testData/" + fileName),
                            new File("src/test/resources/testData/downloadedFiles/" + fileName)));
        });
    }

}
