# API test

@API
Feature: Complete REST API automation with basic and advanced examples

  Scenario: GET request - Fetch user by ID
    Given I send a GET request to "/users/1"
    Then the response status code should be 200
    And the response field "address.city" should be "Gwenborough"

  Scenario: POST request - Create a new post
    Given I set request body for POST with title "My Post", body "Learning API", and userId "101"
    When I send a POST request to "/posts"
    Then the response status code should be 201
    And the response field "title" should be "My Post"

  Scenario: PUT request - Update an existing post
    Given I set request body for PUT with title "Updated Post", body "Updated Body", and userId "101"
    When I send a PUT request to "/posts/1"
    Then the response status code should be 200
    And the response field "title" should be "Updated Post"

  Scenario: DELETE request - Delete a post
    When I send a DELETE request to "/posts/1"
    Then the response status code should be 200

  Scenario: GET request - Handle JSON array and validate multiple items
    Given I send a GET request to "/users"
    Then the array field "address.city" should contain "Gwenborough" and "Wisokyburgh"
