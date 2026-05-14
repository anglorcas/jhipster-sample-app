Feature: Password change

  Scenario: Change password with valid data
    When I login with user "admin" and password "admin"
    And I change my password from "admin" to "newPassword123!"
    Then status should be 200

  Scenario: Change password back
    When I login with user "admin" and password "newPassword123!"
    And I change my password from "newPassword123!" to "admin"
    Then status should be 200