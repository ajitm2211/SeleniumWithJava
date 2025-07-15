package stepdefinitions;

import io.cucumber.java.en.*;
import io.restassured.response.Response;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

public class RestApiStepDefinitions {

    private static Response response;
    private static String requestBody;
    private final String baseURL = "https://jsonplaceholder.typicode.com";

    @Given("I send a GET request to {string}")
    public void sendGET(String endpoint) {
        response = given()
                        .log().all()
                   .when()
                        .get(baseURL + endpoint)
                   .then()
                        .log().all()
                        .extract().response();
    }

    @When("I send a POST request to {string}")
    public void sendPOST(String endpoint) {
        response = given()
                        .log().all()
                        .header("Content-Type", "application/json")
                        .body(requestBody)
                   .when()
                        .post(baseURL + endpoint)
                   .then()
                        .log().all()
                        .extract().response();
    }

    @When("I send a PUT request to {string}")
    public void sendPUT(String endpoint) {
        response = given()
                        .log().all()
                        .header("Content-Type", "application/json")
                        .body(requestBody)
                   .when()
                        .put(baseURL + endpoint)
                   .then()
                        .log().all()
                        .extract().response();
    }

    @When("I send a DELETE request to {string}")
    public void sendDELETE(String endpoint) {
        response = given()
                        .log().all()
                   .when()
                        .delete(baseURL + endpoint)
                   .then()
                        .log().all()
                        .extract().response();
    }

    @Then("the response status code should be {int}")
    public void validateStatusCode(int statusCode) {
        assertEquals(response.getStatusCode(), statusCode, "Status code mismatch!");
    }

    @And("the response field {string} should be {string}")
    public void validateResponseField(String jsonPath, String expectedValue) {
        String actualValue = response.jsonPath().getString(jsonPath);
        assertEquals(actualValue, expectedValue, "Field value mismatch for " + jsonPath);
    }

    @Given("I set request body for POST with title {string}, body {string}, and userId {string}")
    public void setPostRequestBody(String title, String body, String userId) {
        requestBody = "{\n" +
                      "  \"title\": \"" + title + "\",\n" +
                      "  \"body\": \"" + body + "\",\n" +
                      "  \"userId\": " + userId + "\n" +
                      "}";
    }

    @Given("I set request body for PUT with title {string}, body {string}, and userId {string}")
    public void setPutRequestBody(String title, String body, String userId) {
        requestBody = "{\n" +
                      "  \"id\": 1,\n" +
                      "  \"title\": \"" + title + "\",\n" +
                      "  \"body\": \"" + body + "\",\n" +
                      "  \"userId\": " + userId + "\n" +
                      "}";
    }

    @Then("the array field {string} should contain {string} and {string}")
    public void validateArrayFieldContains(String path, String val1, String val2) {
        List<String> items = response.jsonPath().getList(path);

        assertNotNull(items, "Array path returned null: " + path);
        assertTrue(items.contains(val1), "Expected value not found: " + val1);
        assertTrue(items.contains(val2), "Expected value not found: " + val2);
    }
}
