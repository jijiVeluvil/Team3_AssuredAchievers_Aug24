@Patient_Module
Feature: Testing User Flow dietician

  @POST_Request_to_Create_Patient
  Scenario Outline: Check dietician able to create patient with valid datas 
	Given Dietician creates patient with request body for "<sheet>" and "<row>"
	When Dietician sends HTTP request with endpoint	
	Then Dietician recieves response code 
	Examples:
  |  sheet  | row | 
  | patient |  1  |
  | patient |  2  |
  | patient |  3  |
 
 @POST_Request_to_Create_Token
 Scenario: Check patient able to login
 Given Patient creates login Post request with request body 
 When Patient send POST HTTP request with endpoint	
 Then Patient recieves response code
 
 @POST_Request_to_Create_dietician_with_patient_Token
 Scenario Outline: Check admin able to create dietician with patient token
 Given Admin creates dietician with request body for "<sheet>" and "<row>"
 When Admin sends HTTP request with endpoint	
 Then Admin recieves response code 
 Examples:
 |   sheet   | row |
 | dietician |  5  |
 
 @PUT_Request_to_Update_dietician_With_Patient_Token 
 Scenario Outline: Check Admin able to update dietician with patient token
 Given Admin updates dietician details with request body for "<sheet>" and "<row>"
 When Admin sends HTTP request with endpoint	
 Then Admin recieves response code 
 Examples:
 |   sheet   | row |
 | dietician |  16  |
 @POST_Request
 Scenario Outline: Check dietician able to create patient with invalid datas or missing fields
 Given Dietician creates patient with request body for "<sheet>" and "<row>"
 When Dietician sends HTTP request with endpoint	
 Then Dietician recieves response code 
 Examples:
 |  sheet  | row |
 | patient |  4  |
 | patient |  5  |
 | patient |  6  |
 | patient |  7  |
 | patient |  8  |
 | patient |  9  |
 | patient |  10 |
 | patient |  11 |
 | patient |  12 |
 | patient |  13 |
 
 @PUT_Request_to_Update_Patient 
 Scenario Outline: Check dietician able to update patient with valid datas or invalid datas
 Given Dietician updates patient details with request body for "<sheet>" and "<row>"
 When Dietician sends HTTP request with endpoint	
 Then Dietician recieves response code 
  Examples:
   |  sheet  | row |
   | patient |  16 |
   | patient |  17 |
   | patient |  18 |
   | patient |  19 |
   | patient |  20 |
   | patient |  21 |
   | patient |  22 |
   | patient |  23 |
   | patient |  24 |
   | patient |  25 |
   | patient |  26 |
   | patient |  27 |
   | patient |  46 |
   | patient |  47 |
   | patient |  48 |
   
 @PUT_Request_to_Update_Patient_Report_And_Vitals 
 Scenario Outline: Check dietician able to update patient report with valid datas or invalid datas
 Given Dietician updates patient report and vitals with request body for "<sheet>" and "<row>"
 When Dietician sends HTTP request with endpoint	
 Then Dietician recieves response code
  Examples: 
   |   sheet | row |
   | patient |  30 |
   | patient |  31 |
   | patient |  32 |
   | patient |  33 |
   | patient |  34 |
   | patient |  35 |
   | patient |  36 |
   | patient |  37 |
   | patient |  38 |
   | patient |  39 | 
   | patient |  40 |
   | patient |  41 |  
   | patient |  42 |
   | patient |  43 |
   | patient |  50 |
  
 @Get_Request_to_get_all_dieticians_with_patient_token
 Scenario Outline: Check Admin able to retrieve all the Dietician details with patient token
 Given Admin retrieves all dietician details with "<token>","<method>","<endpoint>"
 When Admin sends HTTP request
 Then Admin recieves response status code "<statusCode>" with "<respMsg>" message
 Examples: 
 | token     | method | endpoint | statusCode | respMsg      |
 | patient   | GET    | valid    | 403        | HTTP/1.1 403 |  
 
 @Get_Req_to_get_dietician_details_by_dieticianId_with_patient_token
 Scenario Outline: Check Admin able to retrieve Dietician details with valid or invalid datas
 Given Admin retrieves Dietician details with "<token>","<method>","<id>","<endpoint>"
 When Admin sends HTTP request
 Then Admin recieves response status code "<statusCode>" with "<respMsg>" message
 Examples: 
 | token     | method | id        | endpoint | statusCode |   respMsg    |
 | patient   | GET    | validId   | valid    | 403        | HTTP/1.1 403 |
   
 @Get_Req_to_get_all_patient
 Scenario Outline: Check dietician able to retrieve patient details with valid or invalid datas
 Given Dietician retrieves all patient details with "<token>","<method>","<endpoint>"
 When Dietician sends HTTP request
 Then Dietician recieves response status code "<statusCode>" with "<respMsg>" message
 Examples: 
 |   token   | method | endpoint | statusCode |    respMsg   |
 | dietician |   GET  |  valid   |    200     | HTTP/1.1 200 | 
 |   admin   |   GET  |  valid   |    403     | HTTP/1.1 403 | 
 |  patient  |   GET  |  valid   |    403     | HTTP/1.1 403 |
 |  no auth  |   GET  |  valid   |    401     | HTTP/1.1 401 |
 | dietician |   PUT  |  valid   |    405     | HTTP/1.1 405 |
 | dietician |   GET  | invalid  |    404     | HTTP/1.1 404 |
 
 @Get_Req_to_get_patient_Morbidity_details
 Scenario Outline: Check dietician able to retrieve patient morbidity details with valid or invalid datas
 Given Dietician retrieves morbidity details with "<token>","<method>","<id>","<endpoint>"
 When Dietician sends HTTP request
 Then Dietician recieves response status code "<statusCode>" with "<respMsg>" message
 Examples: 
 |   token   | method |     id    | endpoint | statusCode |   respMsg    |
 | dietician |   GET  |  validId  |  valid   |    200     | HTTP/1.1 200 |
 |   admin   |   GET  |  validId  |  valid   |    403     | HTTP/1.1 403 |
 |  patient  |   GET  |  validId  |  valid   |    200     | HTTP/1.1 200 |
 |  no auth  |   GET  |  validId  |  valid   |    401     | HTTP/1.1 401 |
 | dietician |   POST |  validId  |  valid   |    405     | HTTP/1.1 405 |
 | dietician |   GET  | invalidId |  valid   |    404     | HTTP/1.1 404 |
 | dietician |   GET  |  validId  | invalid  |    404     | HTTP/1.1 404 |       
 
 @Get_Req_to_get_patient_file
 Scenario Outline: Check dietician able to retrieve patient file with valid or invalid datas
 Given Dietician gets patient file with "<token>","<method>","<id>","<endpoint>"
 When Dietician sends HTTP request
 Then Dietician recieves response status code "<statusCode>" with "<respMsg>" message
 Examples: 
 |   token   | method |    id     | endpoint | statusCode |   respMsg   |
 | dietician |   GET  |  validId  |   valid  |     200    | HTTP/1.1 200| 
 |   admin   |   GET  |  validId  |   valid  |     403    | HTTP/1.1 403 | 
 |  patient  |   GET  |  validId  |   valid  |     200    | HTTP/1.1 200 |
 |  no auth  |   GET  |  validId  |   valid  |     401    | HTTP/1.1 401 |
 | dietician |   POST |  validId  |   valid  |     405    | HTTP/1.1 405 | 
 | dietician |   GET  | invalidId |   valid  |     404    | HTTP/1.1 404 |
 | dietician |   GET  |  validId  |  invalid |     404    | HTTP/1.1 404 |
 
 
  
  
 
 
 
 
 

  
 





















