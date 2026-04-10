package io.github.jhipster.sample.cucumber.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class HomepageSteps {

    private ResponseEntity<String> response;
    private final RestTemplate restTemplate = new RestTemplate();

    @When("I access {string}")
    public void i_access(String path) {
        System.out.println("Trying to access: " + path);
        response = restTemplate.getForEntity("http://localhost:8080" + path, String.class);
    }

    @Then("the response status should be {int}")
    public void status_should_be(int status) {
        System.out.println("Expected status: " + status + ", got: " + response.getStatusCode().value());
        assertEquals(status, response.getStatusCode().value());
    }
}
