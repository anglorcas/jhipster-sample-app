Feature: Role based security

  Scenario: Admin role can access admin endpoints
    When I login with user "admin" and password "admin"
    And I access protected "/api/admin/users"
    Then status should be 200

  Scenario: Admin role can access authority list
    When I login with user "admin" and password "admin"
    And I access protected "/api/authorities"
    Then status should be 200
    And response body contains "ROLE_ADMIN"
    And response body contains "ROLE_USER"

  Scenario: Unauthenticated user cannot access authority list
    When I access protected "/api/authorities"
    Then status should be 401