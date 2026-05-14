Feature: Management endpoints

  Scenario: Health endpoint returns UP status
    When I access "/management/health"
    Then status should be 200
    And response body contains "UP"

  Scenario: Info endpoint is publicly accessible
    When I access "/management/info"
    Then status should be 200

  Scenario: Health details require authentication
    When I access protected "/management/health"
    Then status should be 200