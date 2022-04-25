Feature: To upload the file in German Phone Parser
  Background: Create the base url
    Given url _url

  Scenario: Submit Good File

    Given path '/german-phones-parser/upload-file'
    * def fileLocation = '../data/phone_numbers_3.txt'
    And multipart file file = { read:'#(fileLocation)', filename:'phone_numbers_3.txt', Content-Type:'text/plain' }
    When method post
    Then status 201
    And match responseHeaders contains { 'Location': '#present' }

  Scenario: Submit Bad File
    Given path '/german-phones-parser/upload-file'
    * def fileLocation = '../data/uploadBadFile.txt'
      # location of file, name of the file, content-type header value
    And multipart file file = { read:'#(fileLocation)', filename:'uploadBadFile.txt', Content-Type:'text/plain' }
    When method post
    Then status 400
    And match response.message == '#notnull'
    And match response.details == '#notnull'
    And match responseHeaders contains { 'Location': '#notpresent' }

