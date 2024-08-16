
@userLoginModule
Feature:  Testing User Flow dietician

Scenario Outline: Check user able to login
Given User creates login Post request with request body for "<sheet>" and "<row>"
When User send POST HTTP request with endpoint	
Then User recieves response code

Examples:
    | sheet 				| row | 
    |    LoginSheet |   1 |   
    |    LoginSheet |   2 |   
    |    LoginSheet |   3 |   
    |    LoginSheet |   4 |   
    |    LoginSheet |   5 | 
    |    LoginSheet |   6 |   
    |    LoginSheet |   7 |   
    |    LoginSheet |   8 |   
    |    LoginSheet |   9 |   
    |    LoginSheet |  10 | 
    |    LoginSheet |  11 |   
    |    LoginSheet |  12 |   
    |    LoginSheet |  13 |   
    |    LoginSheet |  14 |   
    |    LoginSheet |  15 |  
  
  #Scenario: Check user able to login as admin with valid credential	
#Given User creates login  Post request with request body
#When User send POST HTTP request with endpoint	
#Then User recieves 200 created with response body

#Scenario: Check user able to login as admin with invalid credential	
#Given User creates login  Post request with invalid credential
#When User send POST HTTP request with endpoint	
#Then User recieves 401 unauthorized

#Scenario: Check user able to login as admin with valid credential and invalid method	
#Given User creates login  GET request with request body
#When User send GET HTTP request with endpoint	
#Then User recieves 405 method not allowed

#Scenario: Check user able to login as admin with valid credential and invalid endpoint	
#Given User creates login  Post request with request body
#When User send POST HTTP request with invalid endpoint	
#Then User recieves 401 unauthorized

#Scenario: Check user able to login as admin with valid credential and invalid content type	
#Given User creates login  Post request with request body and invalid content type
#When User send POST HTTP request with endpoint	
#Then User recieves 415 unsupported media type
  