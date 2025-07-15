package hooks;

import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.*;
import utils.ExtentManager;

public class CustomCucumberListener implements ConcurrentEventListener {

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestCaseFinished.class, this::handleTestCaseFinished);
    }

    private void handleTestCaseFinished(TestCaseFinished event) {
        Status resultStatus = event.getResult().getStatus();

        if (resultStatus == Status.UNDEFINED) {
            ExtentManager.getTest().fail("❌ Undefined Step Found. Implement the missing step definition.");
        } else if (resultStatus == Status.FAILED) {
            Throwable error = event.getResult().getError();
            String message = (error != null) ? error.getMessage() : "Unknown error";
            ExtentManager.getTest().fail("❌ Step Failed:\n" + message);
        }
    }
}
