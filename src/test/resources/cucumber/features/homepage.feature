Feature: Homepage

  Scenario: Access homepage
    When I access "/"
    Then the response status should be 200