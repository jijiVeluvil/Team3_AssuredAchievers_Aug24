Feature:  Testing User Flow dietician

@Get_Request_to_get_all_morbidity_details
 Scenario Outline: Check user able to retrieve morbidity details with valid or invalid datas
 Given User gets morbidity details with "<token>","<method>","<endpoint>"
 When User sends HTTP request
 Then User recieves response status code "<statusCode>" with "<respMsg>" message
 Examples: 
 | token | method | endpoint | statusCode | respMsg |
 | no auth | GET  | valid | 401 | HTTP/1.1 401 |
 | patient | GET | valid | 403 | HTTP/1.1 403 |
 | admin | GET | valid | 200 | HTTP/1.1 200 |
 | admin | POST | valid | 405 | HTTP/1.1 405 |
 | admin | GET | invalid | 404 | HTTP/1.1 404 |
 | dietician | GET | valid | 200 | HTTP/1.1 200 |
 | dietician | POST | valid | 405 | HTTP/1.1 405 |
 | dietician | GET | invalid | 404 | HTTP/1.1 404 |
 
 @Get_Request_to_get_morbidity_by_testname_by_admin
 Scenario Outline: Check admin able to retrieve morbidity details by testName
 Given Admin gets morbidity details by "<testName>"
 When Admin sends HTTP get request
 Then Admin recieves response status code 
 Examples: 
 | testName |
 | Fasting Glucose |
 | Average Glucose |
 | Plasma Glucose |
 | HBA1C |
 | TSH |
 | T4 |
 | T3 |
 
 @Get_Request_to_get_morbidity_by_testname_by_dietician
 Scenario Outline: Check dietician able to retrieve morbidity details by testName
 Given Dietician gets morbidity details by "<testName>"
 When Dietician sends HTTP get request
 Then Dietician recieves response status code 
 Examples: 
 | testName |
 | Fasting Glucose |
 | Average Glucose |
 | Plasma Glucose |
 | HBA1C |
 | T3 |
 | T4 |
 | TSH |
 
 #@Get_Request_to_get_morbidity_details_by_test_name_with_invalid_values
 #Scenario Outline: Check user able to get by testname with valid or invalid datas
 #Given Creates request for the user to get morbidity by testname with "<token>","<method>","<endpoint>","<tastName>"
 #When User sends HTTP get request
 #Then User recieves response status code "<statusCode>" with "<respMsg>" message
 #Examples: 
 #| token     | method | endpoint | testName | statusCode | respMsg |
 #| no auth   | GET    | valid    | valid    | 401        | HTTP/1.1 401 |
 #| patient   | GET    | valid    | valid    | 403        | HTTP/1.1 403 |
 #| admin     | POST   | valid    | valid    | 405        | HTTP/1.1 405 |
 #| admin     | GET    | invalid  | valid    | 404        | HTTP/1.1 404 |
 #| admin     | GET    | valid    | invalid  | 404        | HTTP/1.1 404 |
 #| dietician | POST   | valid    | valid    | 405        | HTTP/1.1 405 |
 #| dietician | GET    | invalid  | valid    | 404        | HTTP/1.1 404 |
 #| dietician | GET    | valid    | invalid  | 404        | HTTP/1.1 404 |
 
 @Get_Request_to_get_morbidity_details_by_test_name_with_invalid_values
 Scenario Outline: Check user able to get morbidity by testname with valid or invalid datas
 Given Creates request for the user to get morbidity by testname with "<sheet>" and "<row>"
 When User sends HTTP get request
 Then User recieves response status code for getting morbidity by testname
 Examples:
 | sheet     | row |
 | Morbidity | 1 |
 | Morbidity | 2 |
 | Morbidity | 3 |
 | Morbidity | 4 |
 | Morbidity | 5 |
 | Morbidity | 6 |
 | Morbidity | 7 |
 | Morbidity | 8 |
  
 