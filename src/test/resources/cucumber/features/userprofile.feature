Feature: User profile

  Scenario: Get current user info as admin
    When I login with user "admin" and password "admin"
    And I access protected "/api/account"
    Then status should be 200
    And response body contains "admin"

  Scenario: Get current user info without auth
    When I access protected "/api/account"
    Then status should be 401