@DieticianModule
Feature:  Testing User Flow dietician

@Post_Request_To_create_dietician_with_valid_datas
Scenario Outline: Check admin able to create dietician with valid datas
Given Admin creates dietician with request body for "<sheet>" and "<row>"
When Admin sends HTTP request with endpoint	
Then Admin recieves response code 
Examples:
    | sheet 				| row | 
    | dietician     | 1 |
    | dietician     | 2 |
    
 @POST_Request_to_Create_Token
 Scenario: Check dietician able to login
 Given Dietician creates login Post request with request body 
 When Dietician send POST HTTP request with endpoint	
 Then Dietician recieves login response code 
 
 @POST_Request_to_Create_dietician
 Scenario Outline: Check admin able to create dietician with invalid datas or missing fields
 Given Admin creates dietician with request body for "<sheet>" and "<row>"
 When Admin sends HTTP request with endpoint	
 Then Admin recieves response code 
  Examples:
   | sheet | row |
   | dietician | 3 |
   | dietician | 4 |
   | dietician | 5 |
   | dietician | 6 |
   | dietician | 7 | 
   | dietician | 8 |
   | dietician | 9 |
   | dietician | 10 |
   | dietician | 11 |
   
  @PUT_Request_to_Update_dietician 
 Scenario Outline: Check Admin able to update dietician with valid datas or invalid datas
 Given Admin updates dietician details with request body for "<sheet>" and "<row>"
 When Admin sends HTTP request with endpoint	
 Then Admin recieves response code 
  Examples:
   | sheet | row |
   | dietician | 14 |
   | dietician | 15 |
   | dietician | 16 |
   | dietician | 17 |
   | dietician | 18 |
   | dietician | 19 |
   | dietician | 20 |
   | dietician | 21 |
   | dietician | 22 |
   | dietician | 23 |
   | dietician | 24 |
   
 @Get_Request_to_get_all_dieticians
 Scenario Outline: Check Admin able to retrieve all the Dietician details with valid or invalid datas
 Given Admin retrieves all dietician details with "<token>","<method>","<endpoint>"
 When Admin sends HTTP request
 Then Admin recieves response status code "<statusCode>" with "<respMsg>" message
 Examples: 
 | token     | method | endpoint | statusCode | respMsg |
 | no auth   | GET    | valid    | 401       | HTTP/1.1 401 | 
 | admin     | GET    | valid    | 200       | HTTP/1.1 200 | 
 | admin   | PUT    | valid    | 405       | HTTP/1.1 405 |
 | admin | GET    | invalid  | 404        | HTTP/1.1 404 |
 
 
 @Get_Req_to_get_dietician_details_by_dieticianId
 Scenario Outline: Check Admin able to retrieve Dietician details with valid or invalid datas
 Given Admin retrieves Dietician details with "<token>","<method>","<id>","<endpoint>"
 When Admin sends HTTP request
 Then Admin recieves response status code "<statusCode>" with "<respMsg>" message
 Examples: 
 | token     | method | id      | endpoint | statusCode | respMsg |
 | no auth   | GET    | validId | valid    | 401        | HTTP/1.1 401 |
 | admin     | GET    | validId | valid    | 200        | HTTP/1.1 200 |
 | admin     | PUT    | validId | valid    | 405       | HTTP/1.1 405|
 | admin     | GET    | invalidId | valid    | 404        | HTTP/1.1 404 |
 | admin    |  GET    | validId | invalid    | 404        | HTTP/1.1 404 |
   
   
    