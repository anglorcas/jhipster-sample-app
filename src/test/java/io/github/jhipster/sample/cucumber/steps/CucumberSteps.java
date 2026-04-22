package io.github.jhipster.sample.cucumber.steps;

import io.cucumber.java.en.*;
import org.springframework.http.*;
import org.springframework.web.client.*;

public class CucumberSteps {

    private final RestTemplate restTemplate = new RestTemplate();

    private ResponseEntity<String> response;
    private String token;

    private final String BASE_URL = "http://localhost:8080";

    // -------------------------
    // LOGIN (si lo necesitas)
    // -------------------------
    @When("I login with user {string} and password {string}")
    public void login(String user, String pass) {
        System.out.println("Logging in...");

        try {
            String body =
                """
                    {"username":"%s","password":"%s"}
                """.formatted(user, pass);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> req = new HttpEntity<>(body, headers);

            ResponseEntity<String> resp = restTemplate.postForEntity(BASE_URL + "/api/authenticate", req, String.class);

            System.out.println("Login response: " + resp.getStatusCode());

            // aquí normalmente extraerías JWT
            token = "FAKE_OR_PARSE_IT";
        } catch (Exception e) {
            System.out.println("Login failed: " + e.getMessage());
        }
    }

    // -------------------------
    // REQUEST SIN AUTH
    // -------------------------
    @When("I access {string}")
    public void access(String path) {
        System.out.println("Accessing: " + path);

        response = restTemplate.getForEntity(BASE_URL + path, String.class);

        System.out.println("Response: " + response.getStatusCode());
    }

    // -------------------------
    // REQUEST CON AUTH
    // -------------------------
    @When("I access protected {string}")
    public void accessProtected(String path) {
        System.out.println("Accessing protected: " + path);

        HttpHeaders headers = new HttpHeaders();

        if (token != null) {
            headers.setBearerAuth(token);
        }

        HttpEntity<Void> req = new HttpEntity<>(headers);

        try {
            response = restTemplate.exchange(BASE_URL + path, HttpMethod.GET, req, String.class);
        } catch (HttpClientErrorException e) {
            System.out.println("HTTP error: " + e.getStatusCode());
            response = ResponseEntity.status(e.getStatusCode()).build();
        }
    }

    // -------------------------
    // ASSERTS
    // -------------------------
    @Then("status should be {int}")
    public void status(int expected) {
        System.out.println("Expected: " + expected + ", got: " + response.getStatusCode());

        if (response == null) {
            throw new AssertionError("Response is null");
        }

        if (response.getStatusCode().value() != expected) {
            throw new AssertionError("Expected " + expected + " but got " + response.getStatusCode());
        }
    }

    // -------------------------
    // LOGOUT
    // -------------------------
    @When("I logout")
    public void logout() {
        System.out.println("Logging out");
        token = null;
    }
}
