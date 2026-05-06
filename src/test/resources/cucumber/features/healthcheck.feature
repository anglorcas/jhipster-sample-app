Feature: Health Check

  Scenario: Application health endpoint is reachable
    When I access "/management/health"
    Then status should be 200

  Scenario: Application info endpoint is reachable
    When I access "/management/info"
    Then status should be 200