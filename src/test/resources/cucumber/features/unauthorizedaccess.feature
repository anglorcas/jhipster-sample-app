Feature: Unauthorized access

  Scenario: Access protected endpoint without authentication
    When I access protected "/api/account"
    Then status should be 401