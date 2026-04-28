Feature: Authentication

  Scenario: Login with valid credentials
    When I login with user "admin" and password "admin"
    Then status should be 200

  Scenario: Login with invalid credentials
    When I login with user "admin" and password "wrongpassword"
    Then status should be 401

  Scenario: Access protected endpoint after login
    When I login with user "admin" and password "admin"
    And I access protected "/api/account"
    Then status should be 200

  Scenario: Access protected endpoint after logout
    When I login with user "admin" and password "admin"
    And I logout
    And I access protected "/api/account"
    Then status should be 401