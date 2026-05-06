Feature: API Security

  Scenario: Access admin endpoint without authentication
    When I access protected "/api/admin/users"
    Then status should be 401

  Scenario: Access admin endpoint as admin
    When I login with user "admin" and password "admin"
    And I access protected "/api/admin/users"
    Then status should be 200