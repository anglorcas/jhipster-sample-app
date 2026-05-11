Feature: Homepage

  Scenario: Access homepage
    When I access "/"
    Then status should be 200