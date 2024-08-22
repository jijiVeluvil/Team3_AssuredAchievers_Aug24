
@tag
Feature: Dietician Module E2E

Scenario Outline: Check user able to login
Given User creates login Post request with request body for "<sheet>" and "<row>"
When User send POST HTTP request with endpoint	
Then User recieves response code

Examples:
    | sheet 				| row | 
    |    LoginSheet |   1 | 
 @tag4   
 @SetAdminbearertoken
  Scenario Outline: Check admin able to create dietician with valid data and token
  Given Admin creates dietician Post request with request body for "<sheet>" and "<row>"
  When Admin send POST HTTP request with endpoint
  Then Admin recieves response code
  Then Admin generates Dietician Token and saves Dietician Token
 
  
  Examples:
    | sheet 			 | row | 
    |    dietician |   1 | 
    |dietician     |2    |
       
 
 
      
     #with Mandatory fields  5     
     #valid data with invalid method   6
     
     @SetAdminbearertoken
  Scenario Outline: Check admin able to create dietician with valid data and token
  Given Admin creates dietician Post request with request body for "<sheet>" and "<row>"
  When Admin send POST HTTP request with endpoint
  Then Admin recieves response code
  
  
  Examples:
    | sheet 			 | row | 
    |    dietician |   5 | 
    |dietician     |6   |
 
 #@tag1
  @POST(createdieticianWithoutAuth)
  Scenario Outline: Check admin able to create dietician with valid data
    Given Admin creates dietician Post request without Auth and with request body for "<sheet>" and "<row>" 
    When Admin send POST HTTP request with endpoint
    Then Admin recieves response code
       
       
 Examples:
    | sheet 				| row | 
    |    dietician |   3 | 
    
    
    
    #@tag2
  #@POST(createdieticianDieticianAuth)
  Scenario Outline: Check admin able to create dietician with valid data
    Given Admin creates dietician Post request with DieticianToken and with request body for "<sheet>" and "<row>" 
    When Admin send POST HTTP request with endpoint
    Then Admin recieves response code
       
       
 Examples:
    | sheet 				| row | 
    |    dietician |   4 | 
    
      
          
   
   #@tag2
   #@SetDieticianbearertoken 
  #Scenario:Check admin able to create dietician with valid data and dietician token
  #Given Admin creates POST request with valid data
  #When Admin send POST http request with endpoint
  #Then Admin recieves 403 forbidden
  #
  #@tag3
  #@SetPatientbearertoken
  #Scenario: Check admin able to create dietician with valid data and patient token  
  #When Admin creates POST request with valid data
  #Then Admin send POST http request with endpoint
  #When Admin recieves 403 forbidden
  #
  
  #@SetAdminbearertoken
  #Scenario Outline: Check admin able to create dietician with valid data and token
  #Given User creates dietician  "<scenario>" Post request with request body
  #When Admin send POST http request with endpoint
  #Then User recieves response code
  #Then Admin generates Dietician Token
  #Then User saves the dietician token
  #
  #Examples:
       #|scenario     |
       #|dietician1    |
       #
       
  
  #@tag5
  #Scenario:Check admin able to create dietician only with valid mandatory details
  #Given Admin creates POST request only with valid mandatory details
  #When Admin send POST http request with endpoint	
  #Then Admin recieves 201 created and with response body. # (Auto created dietician ID and login password)
  
  #@tag6  -------------------------------
  #Scenario: Check admin able to create dietician only with valid additional details
  #Given Admin creates POST request only with valid additional details
  #When Admin send POST http request with endpoint
  #Then Admin recieves 400 Bad request
  #
  #@tag7
  #Scenario: Check admin able to create dietician with invalid data
  #Given Admin creates POST request only with invalid additional details
  #When Admin send POST http request with endpoint
  #Then Admin recieves 400 Bad request
  #
  #@tag8------------working
  #Scenario: Check admin able to create dietician with valid data and invalid method
  #Given Admin creates PUT request only with valid details
  #When Admin send PUT http request with endpoint
  #Then Admin recieves 405 method not allowed
  #
  #@tag9
  #Scenario: Check admin able to create dietician with valid data and invalid endpoint
  #Given Admin creates POST request with valid data
  #When Admin sent POST http request with invalid endpoint
  #Then Admin recieves 404 not found
  #
  #@tag10
  #Scenario: Check admin able to create dietician with valid data and invalid content type
  #Given Admin creates POST request with valid data and invalid content type
  #When Admin send POST http request with endpoint
  #Then Admin recieves 415 unsupported media type
  #
  #
  #get all deitician Set no auth
  #@tag11
  #Scenario: Check admin able to retrieve all dietician
  #Given Admin create GET request 
  #When Admin send GET http request with endpoint
  #Then Admin recieves 401 unauthorized
  #
  #@tag12SetAuth
  #Scenario: Check admin able to retrieve all dietician
  #Given Admin create GET request 
  #When Admin send GET http request with endpoint
  #Then Admin recieves 200 ok with response body
  #
  #@tag13
  #Scenario: Check admin able to retrieve all dietician with invalid method
  #Given Admin create PUT request 
  #When Admin send PUT http request with endpoint
  #Then Admin send PUT http request with endpoint
  #
  #@tag14
  #Scenario: Check admin able to retrieve all dietician with invalid endpoint
  #Given Check admin able to retrieve all dietician with invalid endpoint
  #When Admin send GET http request with invalid endpoint
  #Then Admin recieves 404 not found
  #
  #
  #Get by dietician
  #@tag15Setnoauth
  #Scenario: Check admin able to retrieve dietician by ID
  #Given Admin create GET request 
  #When Admin send GET http request with endpoint
  #Then Admin recieves 401 unauthorized
  #
  #sets Auth
  #@tag16
  #Scenario: Admin recieves 401 unauthorized
  #Given Admin create GET request 
  #When Admin send GET http request with endpoint
  #Then Admin recieves 200 ok with details of the dietician id
  #
  #@tag17
  #Scenario: Check admin able to retrieve dietician by id with invalid method
  #Given Admin create POST request 
  #When Admin send POST http request with endpoint
  #Then Admin recieves 405 method not allowed
  #
  #@tag18
  #Scenario: Check admin able to retrieve dietician by invalid id
  #Given Admin create GET request 
  #When Admin send GET http request with endpoint
  #Then Admin recieves 404 not found
  #
  #@tag19
  #Scenario: Check admin able to retrieve dietician by id with invalid endpoint
  #Given Admin create GET request 
  #When Admin send GET http request with invalid endpoint
  #Then Admin recieves 404 not found
  #
  #
  #put(without Auth)
  #@tag20
  #Scenario: Check admin able to update dietician with valid data and dietician id
  #Given Admin creates PUT request with valid data
  #When Admin send PUT http request with endpoint
  #Then Admin recieves 401 unauthorized
  #
  #Set dietician bearer token
  #@tag21
  #Scenario: Check admin able to update dietician with valid data, dietician id and dietician token
  #Given Admin creates PUT request with valid data
  #When Admin send PUT http request with endpoint
  #Then Admin recieves 403 forbidden
  #
  #Set patient bearer token
  #@tag22
  #Scenario: Check admin able to update dietician with valid data , dietician id and patient token
  #Given Admin creates PUT request with valid data
  #When Admin send PUT http request with endpoint
  #Then Admin recieves 403 forbidden
  #
  #
  #Set admin bearer token
  #@tag23
  #Scenario: Check admin able to update dietician with valid data , dietician id and token
  #Given Admin creates PUT request with valid data. #( Mandatory and additional details) 
  #When Admin send PUT http request with endpoint
  #Then Admin recieves 200 ok and with updated response body. 
  #
  #@tag24
  #Scenario: Check admin able to update dietician only with valid mandatory details and dietician id
  #Given Admin creates PUT request only with valid mandatory details
  #When Admin send PUT http request with endpoint
  #Then Admin recieves 200 ok and with updated response body. 
  #
  #@tag25
  #Scenario: Check admin able to update dietician only with valid additional details
  #Given Admin creates PUT request only with valid additional details
  #When Admin send PUT http request with endpoint
  #Then Admin recieves 400 Bad request
  #
  #@tag26
  #Scenario: Check admin able to update dietician with invalid data and dietician id
  #Given Admin creates PUT request only with invalid additional details
  #When Admin send PUT http request with endpoint
  #Then Admin recieves 400 Bad request
  #
  #@tag27
  #Scenario: Check admin able to update dietician with valid data and invalid dietician id
  #Given  Admin creates PUT request only with valid mandatory details
  #When Admin send PUT http request with endpoint
  #Then Admin recieves 404 not found
  #
  #@tag28
  #Scenario: Check admin able to update dietician with valid data, dietician id and invalid method
  #Given Admin creates POST request only with valid details
  #When Admin send POST http request with endpoint
  #Then Admin recieves 405 method not allowed
  #
  #@tag29
  #Scenario:Check admin able to update dietician with valid data, dietician id and invalid endpoint
  #Given Admin creates PUT request with valid data
  #When Admin sent PUT http request with invalid endpoint
  #Then Admin recieves 404 not found
  #
  #@tag30
  #Scenario: Check admin able to update dietician with valid data, dietician id and invalid content type
  #Given Admin creates PUT request with valid data and invalid content type
  #When Admin send PUT http request with endpoint
  #Then Admin recieves 415 unsupported media type
  #
  #delete Set no auth
  #@tag31
  #Scenario: Check admin able to delete dietician by ID
  #Given Admin create DELETE request 
  #When Admin send DELETE http request with endpoint
  #Then Admin recieves 401 unauthorized
  #
  #delete Sets auth
  #@tag32
  #Scenario: Check admin able to delete dietician by ID
  #Given Admin create DELETE request 
  #When Admin send DELETE http request with endpoint
  #Then Admin recieves 200 ok with details of the dietician id
  #
  #@tag33
  #Scenario: Check admin able to delete dietician by id with invalid method
  #Given Admin create POST request 
  #When Admin send POST http request with endpoint
  #Then Admin recieves 405 method not allowed
  #
  #@tag34
  #Scenario: Check admin able to delete dietician by invalid id
  #Given Admin create DELETE request 
  #When Admin send DELETE http request with endpoint
  #Then Admin recieves 404 not found
  #
  #@tag35
  #Scenario: Check admin able to delete dietician by id with invalid endpoint
  #Given Admin create DELETE request 
  #When Admin send DELETE http request with invalid endpoint
  #Then Admin recieves 404 not found
  
  
  
  
  
  
  
  
  
  

   
