Feature: Input validation

  Scenario: Login with empty username
    When I login with user "" and password "admin"
    Then status should be 400

  Scenario: Login with empty password
    When I login with user "admin" and password ""
    Then status should be 400

  Scenario: Change password with too short new password
    When I login with user "admin" and password "admin"
    And I change my password from "admin" to "123"
    Then status should be 400

  Scenario: Create user with invalid email
    When I login with user "admin" and password "admin"
    And I create a user with login "baduser" and email "not-an-email"
    Then status should be 400