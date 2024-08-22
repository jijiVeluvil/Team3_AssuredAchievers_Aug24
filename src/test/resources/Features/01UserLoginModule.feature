
@userLoginModule
Feature:  Testing User Flow dietician

Scenario Outline: Check user able to login
Given User creates login Post request with request body for "<sheet>" and "<row>"
When User send POST HTTP request with endpoint	
Then User recieves response code

Examples:
    |     sheet   | row | 
    |  LoginSheet |   1 |   
    |  LoginSheet |   2 |   
    |  LoginSheet |   3 |   
    |  LoginSheet |   4 |   
    |  LoginSheet |   5 | 
    |  LoginSheet |   6 |   
    |  LoginSheet |   7 |   
    |  LoginSheet |   8 |   
    |  LoginSheet |   9 |   
    |  LoginSheet |  10 |   
    |  LoginSheet |  11 |   
    |  LoginSheet |  12 |   
    |  LoginSheet |  13 |  
  
 
  