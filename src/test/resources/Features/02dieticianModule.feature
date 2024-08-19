@DieticianModule
Feature:  DieticianModule

Scenario Outline: Check admin able to create dietician with valid datas
Given Admin creates dietician Post request with request body for "<sheet>" and "<row>"
When Admin send POST HTTP request with endpoint	
Then Admin recieves response code 
Examples:
    | sheet 				| row | 
    | dietician     | 1 |
    | dietician     | 2 |