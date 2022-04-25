Feature: To Get The Phone Number By TaskId & All The Tasks
  Background: Create the base url
    Given url _url

  Scenario: Get The valid Phone Numbers By Task ID
    * def postRequest = call read("../../submitFile.feature")
    * def resourceName = "/german-phones-parser/" + postRequest.id
    Given path resourceName
    When method get
    Then status 200
    And match response.taskID == postRequest.id

  Scenario: Get All The Tasks
    Given path 'german-phones-parser/tasks'
    When method get
    Then status 200