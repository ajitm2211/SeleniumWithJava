package stepdefinitions;

import io.cucumber.java.en.Given;
import utils.DriverFactory;

public class LoginSteps {

    @Given("user navigates to login page")
    public void user_navigates_to_login_page() {
        DriverFactory.getDriver().get("https://google.com");
    }

    @Given("user navigates to logic page")
    public void user_navigates_to_logic_page() {
        DriverFactory.getDriver().get("https://google.com");
    }
}
