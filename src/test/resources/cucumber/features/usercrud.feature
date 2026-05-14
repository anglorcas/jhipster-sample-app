Feature: User CRUD

  Scenario: Admin can list all users
    When I login with user "admin" and password "admin"
    And I access protected "/api/admin/users"
    Then status should be 200
    And response body contains "admin"

  Scenario: Admin can get a specific user
    When I login with user "admin" and password "admin"
    And I access protected "/api/admin/users/admin"
    Then status should be 200
    And response body contains "admin"

  Scenario: Admin can create a new user
    When I login with user "admin" and password "admin"
    And I create a user with login "testuser" and email "testuser@example.com"
    Then status should be 201

  Scenario: Admin can delete a user
    When I login with user "admin" and password "admin"
    And I delete user "testuser"
    Then status should be 204

  Scenario: Non authenticated user cannot list users
    When I access protected "/api/admin/users"
    Then status should be 401