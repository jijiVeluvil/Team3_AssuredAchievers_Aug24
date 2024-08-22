
Feature:  Testing delete Operation

Scenario Outline: Check dietician or patient able to logout
Given User creates Logout Get request with request body for "<sheet>" and "<row>"
When User send POST HTTP request with logout endpoint	
Then User recieves response code for logout

Examples:
    |  	 sheet 	  | row |   
    | LogOutSheet |  2  |   
    | LogOutSheet |  3  |   
    
@Delete_Req_to_delete_patientId
 Scenario Outline: Check dietician able to delete patient Id with valid or invalid datas
 Given Dietician deletes patient Id with "<token>","<method>","<id>","<endpoint>"
 When Dietician sends HTTP request
 Then Dietician recieves response status code "<statusCode>" with "<respMsg>" message
 Examples: 
 |   token   | method |      id   | endpoint | statusCode |   respMsg    |
 |   admin   | DELETE |  validId  |   valid  |     403    | HTTP/1.1 403 |
 |  patient  | DELETE |  validId  |   valid  |     403    | HTTP/1.1 403 |
 |  no auth  | DELETE |  validId  |   valid  |     401    | HTTP/1.1 401 |
 | dietician |  POST  |  validId  |   valid  |     405    | HTTP/1.1 405 |
 | dietician | DELETE | invalidId |   valid  |     404    | HTTP/1.1 404 |
 | dietician | DELETE |  validId  |  invalid |     404    | HTTP/1.1 404 |
 | dietician | DELETE |  validId  |   valid  |     200    | HTTP/1.1 200 |
 | dietician | DELETE |  validId1 |   valid  |     200    | HTTP/1.1 200 |
 | dietician | DELETE |  validId2 |   valid  |     200    | HTTP/1.1 200 |


@Delete_Req_to_delete_dieticianId
 Scenario Outline: Check Admin able to delete dietician Id with valid or invalid datas
 Given Admin deletes dietician Id with "<token>","<method>","<id>","<endpoint>"
 When Admin sends HTTP request
 Then Admin recieves response status code "<statusCode>" with "<respMsg>" message
 Examples: 
 |  token    | method |    id     | endpoint | statusCode |   respMsg    |
 | no auth   | DELETE |  validId  |  valid   |    401     | HTTP/1.1 401 |
 |  admin    |  POST  |  validId  |  valid   |    405     | HTTP/1.1 405 |    
 |  admin    | DELETE | invalidId |  valid   |    404     | HTTP/1.1 404 |
 |  admin    | DELETE |  validId  | invalid  |    404     | HTTP/1.1 404 |
 |  admin    | DELETE |  validId  |  valid   |    200     | HTTP/1.1 200 |
 |  admin    | DELETE |  validId1 |  valid   |    200     | HTTP/1.1 200 |
 