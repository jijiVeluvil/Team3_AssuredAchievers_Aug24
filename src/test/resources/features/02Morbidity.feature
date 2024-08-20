Feature: Get all morbidity
 Get all morbidity by admin token

#No auth
Scenario: Check dietician able to retrieve all morbidities details
Given Dietician create GET request with no auth
When Dietician send GET request 
Then Dietician recieves 401 unauthorized

Scenario: Check pateint is able to retrieve all morbidities details
Given Patient create GET request 
When Patient send GET http request 
Then Patient recieves 403 Forbidden

#Positive 
Scenario: Check admin is able to retrieve all morbidities details
Given admin creates a GET request 
When admin send GET request 
Then admin recieves 200 ok with details of the patient id

Scenario: Check admin able to retrieve all morbidities details with invalid method
Given admin create POST request 
When admin send POST http request 
Then admin recieves 405 method not allowed

Scenario: Check admin able to retrieve all morbidities details with invalid endpoint
Given admin create GET request 
When admin send GET request 
Then admin recieves 404 not found
 
 Scenario: Check dietician able to retrieve all morbidities details
Given Dietician create GET request 
When Dietician send GET request 
Then admin recieves 200 ok with details of the patient id

Scenario: Check dietician able to retrieve all morbidities details with invalid method
Given Dietician create POST request 
When Dietician send POST http request
Then Dietician recieves 405 method not allowed

Scenario: Check dietician able to retrieve all morbidities details with invalid endpoint
Given Dietician create GET request with invalid endpoint
When Dietician send GET request
Then Dietician recieves 404 not found

#Get Operation [Retrieve Morbidity condition by Test name ]

Scenario Outline: Check dietician able to get morbidity condition by test name
    Given Dietician creates GET request to check morbidity by "<sheet>" and "<row>" with no auth
    When Dietician sends GET HTTP request to morbidity endpoint
    Then Dietician receives response code for morbidity request
    Examples: 
    | sheet          | row |
    | MorbidityTests | 4  |
    

Scenario Outline: Check patient is able to retrieve morbidity condition by test name 
    Given Patient creates GET request to check morbidity by "<sheet>" and "<row>"
    When Patient send GET http request
    Then Patient recieves 403 Forbidden
    Examples: 
    | sheet          | row |
    | MorbidityTests | 5  |
    

Scenario Outline: Check admin able to retrieve morbidity condition by test name 
    Given Admin creates GET request to check morbidity by "<sheet>" and "<row>"
    When Admin send http request with endpoint
    Then admin recieves 200 ok with details of the patient id
Examples: 
    | sheet          | row |
    | MorbidityTests | 1   |
    | MorbidityTests | 2   |
    | MorbidityTests | 3   |
    | MorbidityTests | 4   |
    | MorbidityTests | 5   |
    | MorbidityTests | 6  |
    | MorbidityTests |7   |
    
    
    
Scenario Outline: Check admin able to retrieve morbidity condition by test name with invalid method
    Given Admin creates POST request to check morbidity by "<sheet>" and "<row>"
    When admin send POST http request
    Then admin recieves 405 method not allowed
 Examples: 
    | sheet          | row |
    | MorbidityTests | 1   |
    | MorbidityTests | 2   |
     

Scenario Outline: Check admin able to retrieve morbidity condition by invalid test name 
    Given Admin creates GET request with invalid test name by "<sheet>" and "<row>"
    When Admin send http request with endpoint
    Then admin recieves 404 not found
Examples:
    | sheet          | row |
    | MorbidityTests | 8 |    

Scenario: Check admin able to retrieve morbidity condition by test name with invalid endpoint
    Given Admin creates GET request with an invalid endpoint
    When Admin send http request with endpoint
    Then admin recieves 404 not found

Scenario Outline: Check dietician able to retrieve morbidity condition by test name 
    Given Dietician creates GET request to check morbidity by "<sheet>" and "<row>" 
    When Dietician sends GET HTTP request to morbidity endpoint
    Then Dietician receives response code for morbidity request
    Examples: 
    | sheet          | row |
    | MorbidityTests | 1   |
    | MorbidityTests | 2   |
    | MorbidityTests | 3   |
    | MorbidityTests | 4   |
    | MorbidityTests | 5   |
    |MOrbidityTests|6|

Scenario Outline: Check dietician able to retrieve morbidity condition by test name  with invalid method
Given Dietician create POST request to check morbidity by "<sheet>" and "<row>" 
When Dietician send POST http request
Then Dietician recieves 405 method not allowed
Examples: 
    | sheet          | row |
    | MorbidityTests | 1   |
   

Scenario Outline: Check dietician able to retrieve morbidity condition by invalid test name 
Given Dietician creates GET request to check morbidity by "<sheet>" and "<row>" invalid test name
When Dietician sends GET HTTP request to morbidity endpoint
Then Dietician recieves 404 not found
Examples: 
    | sheet          | row |
    | MorbidityTests | 8|  

    
Scenario: Check dietician able to retrieve morbidity condition by test name with invalid endpoint
Given Dietician creates GET request with an invalid endpoint 
When Dietician send GET request
Then Dietician recieves 404 not found  
