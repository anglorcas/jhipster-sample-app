package io.github.jhipster.sample.cucumber.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.*;
import org.springframework.http.*;
import org.springframework.web.client.*;

public class CucumberSteps {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private ResponseEntity<String> response;
    private String token;
    private final String BASE_URL = "http://localhost:8080";

    // -------------------------
    // LOGIN
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
            response = restTemplate.postForEntity(BASE_URL + "/api/authenticate", req, String.class);
            System.out.println("Login response: " + response.getStatusCode());
            token = objectMapper.readTree(response.getBody()).get("id_token").asText();
            System.out.println("Token extracted successfully");
        } catch (HttpClientErrorException e) {
            System.out.println("Login failed: " + e.getStatusCode());
            response = ResponseEntity.status(e.getStatusCode()).build();
            token = null;
        } catch (Exception e) {
            System.out.println("Login failed: " + e.getMessage());
            token = null;
        }
    }

    // -------------------------
    // REQUEST SIN AUTH
    // -------------------------
    @When("I access {string}")
    public void access(String path) {
        System.out.println("Accessing: " + path);
        try {
            response = restTemplate.getForEntity(BASE_URL + path, String.class);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            response = ResponseEntity.status(e.getStatusCode()).build();
        }
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
        if (response == null) {
            throw new AssertionError("Response is null");
        }
        System.out.println("Expected: " + expected + ", got: " + response.getStatusCode());
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

    // -------------------------
    // USER CRUD
    // -------------------------
    @When("I create a user with login {string} and email {string}")
    public void createUser(String login, String email) {
        System.out.println("Creating user: " + login);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (token != null) {
            headers.setBearerAuth(token);
        }
        String body =
            """
                {
                    "login": "%s",
                    "email": "%s",
                    "activated": true,
                    "authorities": ["ROLE_USER"]
                }
            """.formatted(login, email);
        HttpEntity<String> req = new HttpEntity<>(body, headers);
        try {
            response = restTemplate.exchange(BASE_URL + "/api/admin/users", HttpMethod.POST, req, String.class);
        } catch (HttpClientErrorException e) {
            System.out.println("HTTP error: " + e.getStatusCode());
            response = ResponseEntity.status(e.getStatusCode()).build();
        }
    }

    @When("I delete user {string}")
    public void deleteUser(String login) {
        System.out.println("Deleting user: " + login);
        HttpHeaders headers = new HttpHeaders();
        if (token != null) {
            headers.setBearerAuth(token);
        }
        HttpEntity<Void> req = new HttpEntity<>(headers);
        try {
            response = restTemplate.exchange(BASE_URL + "/api/admin/users/" + login, HttpMethod.DELETE, req, String.class);
        } catch (HttpClientErrorException e) {
            System.out.println("HTTP error: " + e.getStatusCode());
            response = ResponseEntity.status(e.getStatusCode()).build();
        }
    }

    // -------------------------
    // ASSERT BODY
    // -------------------------
    @Then("response body contains {string}")
    public void responseBodyContains(String expected) {
        if (response == null || response.getBody() == null) {
            throw new AssertionError("Response body is null");
        }
        if (!response.getBody().contains(expected)) {
            throw new AssertionError("Expected body to contain '" + expected + "' but was: " + response.getBody());
        }
    }

    @Then("response body is empty")
    public void responseBodyIsEmpty() {
        boolean empty = response == null || response.getBody() == null || response.getBody().isBlank();
        if (!empty) {
            throw new AssertionError("Expected empty body but got: " + response.getBody());
        }
    }

    // -------------------------
    // POST CON AUTH
    // -------------------------
    @When("I POST to protected {string} with body:")
    public void postProtected(String path, String body) {
        System.out.println("POST to protected: " + path);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (token != null) {
            headers.setBearerAuth(token);
        }
        HttpEntity<String> req = new HttpEntity<>(body, headers);
        try {
            response = restTemplate.exchange(BASE_URL + path, HttpMethod.POST, req, String.class);
        } catch (HttpClientErrorException e) {
            System.out.println("HTTP error: " + e.getStatusCode());
            response = ResponseEntity.status(e.getStatusCode()).build();
        }
    }

    @When("I change my password from {string} to {string}")
    public void changePassword(String currentPassword, String newPassword) {
        System.out.println("Changing password...");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (token != null) {
            headers.setBearerAuth(token);
        }
        String body =
            """
                {"currentPassword":"%s","newPassword":"%s"}
            """.formatted(currentPassword, newPassword);
        HttpEntity<String> req = new HttpEntity<>(body, headers);
        try {
            response = restTemplate.exchange(BASE_URL + "/api/account/change-password", HttpMethod.POST, req, String.class);
        } catch (HttpClientErrorException e) {
            System.out.println("HTTP error: " + e.getStatusCode());
            response = ResponseEntity.status(e.getStatusCode()).build();
        }
    }
}
