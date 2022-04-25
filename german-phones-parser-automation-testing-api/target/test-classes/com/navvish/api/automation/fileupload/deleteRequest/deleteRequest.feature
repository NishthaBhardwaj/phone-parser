Feature: Delete All The Tasks Present in The System
  Background: Create the base url
    Given url _url

  Scenario: Delete All The Tasks When Task Is Present
    Given path '/german-phones-parser/upload-file'
    * def fileLocation = '../../data/phone_numbers_3.txt'
    * def taskIdFromLocation = function(str) {return str.split('/').pop();}

      # location of file, name of the file, content-type header value
    And multipart file file = { read:'#(fileLocation)', filename:'phone_numbers_3.txt', Content-Type:'text/plain' }
    When method post
    Then status 201
    * def id =  taskIdFromLocation(responseHeaders.Location[0])
    And match responseHeaders contains { 'Location': '#present' }
    Given path '/german-phones-parser/' + id
    When method delete
    Then status 200

  Scenario: Delete non-existing task
    Given path '/german-phones-parser/A1234566'
    When method delete
    Then status 404
    And match response.message == '#notnull'
