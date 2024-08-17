
@tag
Feature: Patient Module
 
  #Create Patient Without Auth
  @tag1
  Scenario: Check dietician able to create patient with valid data
    Given Dietician creates POST request with valid data 
    When Dietician sends POST request with endpoint
    Then Dietician recieves 401 unauthorized
  
  # Set Admin Brarer Token
  @tag2
  Scenario: Check Check admin able to create patient with valid data and admin token
  Given Admin creates POST request with valid data
  When Admin sends POST http request with endpoint
  Then Admin recieves 403 forbidden
  
  # Set Patient Bearer Token
  @tag3
  Scenario: Check patient able to create patient with valid data and patient token  
  When Patient creates POST request with valid data
  Then Patient sends POST http request with endpoint
  When Patient recieves 403 forbidden
  
  # Set Dietician Bearer Token
  @tag4
  Scenario: Check dietician able to create patient with valid data and token 
  Given Dietician creates POST request by entering valid data (Mandatory and additional details)
  When Dietician sends POST http request with endpoint
  Then Dietician recieves 201 created and with response body (Auto created patient ID and login password)
  
  @tag5
  Scenario: Check dietician able to create patient only with valid mandatory details
  Given Dietician creates POST request only by valid mandatory details
  When Dietician sends POST http request with endpoint
  Then Dietician recieves 201 created and with response body (Auto created patient ID and login password)
  
  @tag6
  Scenario: Check dietician able to create patient only with valid additional details
  Given Dietician creates POST request only by valid additional details
  When Dietician sends POST http request with endpoint
  Then Dietician recieves 400 Bad request
  
  @tag7
  Scenario: Check dietician able to create patient with invalid data (mandatory details)
  Given Dietician creates POST request only by invalid mandatory details
  When Dietician sends POST http request with endpoin
  Then Dietician recieves 400 Bad request
  
  @tag8
  Scenario: Check dietician able to create patient with valid mandatory fields and invalid data (additional details)
  Given Dietician creates POST request only by invalid additional details
  When Dietician sends POST http request with endpoint
  Then Dietician recieves 400 Bad request
  
  @tag9
  Scenario: Check dietician able to create patient with valid data and invalid method
  Given Dietician creates PUT request by entering valid data
  When Dietician sends PUT http request with endpoint
  Then Dietician recieves 405 method not allowed
  
  @tag10
  Scenario: Check dietician able to create patient with valid data and invalid endpoint
  Given Dietician creates POST request by entering valid data
  When Dietician sends POST http request with invalid endpoint
  Then Dietician recieves 404 not found
  
  @tag11
  Scenario: Check dietician able to create patient with valid data and invalid content type
  Given Dietician creates POST request by entering valid data and invalid content type
  When Dietician sends POST http request with valid endpoint
  Then Dietician recieves 415 unsupported media type
  
  #PUT without auth
  @tag12
  Scenario: Check dietician able to update patient with valid data
  Given Dietician creates PUT request by entering valid data 
  When Dietician sends PUT http request with endpoint
  Then Dietician recieves 401 unauthorized
  
  #Set Admin Bearer Token
  @tag13
  Scenario: Check admin able to update patient with valid data and admin token
  Given Admin creates PUT request by entering valid data
  When Admin sends PUT http request with endpoint
  Then Admin recieves 403 forbidden
  
  #Set Patient Bearer Token
  @tag14
  Scenario: Check patient able to update patient with valid data and patient token
  Given Patient creates PUT request by entering valid data 
  When Patient sends PUT http request with endpoint
  Then Patient recieves 403 forbidden
  
  #Set Dietician Bearer Token
  @tag15
  Scenario: Check Dietician able to update patient with valid data, patient id and token 
  Given Dietician creates PUT request by entering valid data (Mandatory and additional details)
  When Dietician sends PUT http request with endpoint
  Then Dietician recieves 200 ok and with updated response body 
  
  @tag16
  Scenario: Check dietician able to update patient only with valid mandatory details
  Given Dietician creates PUT request by entering only valid mandatory details
  When Dietician sends PUT http request with endpoint
  Then Dietician recieves 200 ok and with updated response body
  
  @tag17
  Scenario: Check dietician able to update patient with mandatory fields empty and only with valid additional details
  GivenDietician creates PUT request by entering only valid additional details
  When Dietician sends PUT http request with endpoint
  Then Dietician recieves 400 Bad request
  
  @tag18
  Scenario: Check dietician able to update patient with invalid data
  GivenDietician Dietician creates PUT request by entering only invalid additional details 
  When Dietician sends PUT http request with endpoint
  Then Dietician recieves 400 Bad request
  
  @tag19
  Scenario: Check dietician able to update patient with valid data and invalid patient id 
  GivenDietician Dietician creates PUT request by entering only valid mandatory details 
  When Dietician sends PUT http request with endpoint
  Then Dietician recieves 404 not found
  
  @tag20
  Scenario: Check dietician able to update patient with existing file by not attaching new file  
  GivenDietician Dietician creates PUT request by not attaching any file
  When Dietician sends PUT http request with endpoint
  Then Dietician recieves 200 ok and with updated response body
  
  @tag21
  Scenario: Check dietician able to update patient with valid data and invalid method  
  GivenDietician Dietician creates PUT request by entering valid data
  When Dietician sends POST http request with endpoint
  Then Dietician recieves 405 method not allowed
  
  @tag22
  Scenario: Check dietician able to update patient with valid data and invalid endpoint 
  GivenDietician Dietician creates PUT request by entering valid data
  When Dietician sends PUT http request with invalid endpoint
  Then Dietician recieves 404 not found
  
  @tag23
  Scenario: Check dietician able to update patient with valid data, patient id and invalid content type
  GivenDietician Dietician creates PUT request by entering valid data and invalid content type
  When Dietician sends PUT http request with endpoint
  Then Dietician recieves 415 unsupported media type
  
  # additional gherkins
  # update patient with invalid mandatory data 
  
  

  