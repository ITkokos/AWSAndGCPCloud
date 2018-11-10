package stepDefinitions;

import cucumber.api.java8.En;

public class WaitingDefinitionSteps implements En {

    public WaitingDefinitionSteps() {
        And("^Wait \"(\\d+)\" second$", (Integer second) -> {
            Thread.sleep(second * 1000);
        });
    }

}
