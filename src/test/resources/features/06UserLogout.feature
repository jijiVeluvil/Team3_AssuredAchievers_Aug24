
@userLogoutModule
Feature:  Testing User LogOut Flow dietician

Scenario Outline: Check user able to logout
Given User creates Logout Get request with request body for "<sheet>" and "<row>"
When User send POST HTTP request with logout endpoint	
Then User recieves response code for logout

Examples:
    |  			sheet 	 | row | 
    |    LogOutSheet |   1 |   
    |    LogOutSheet |   2 |   
    |    LogOutSheet |   3 |   
    |    LogOutSheet |   4 |   
    |    LogOutSheet |   5 | 
    |    LogOutSheet |   6 |  
		|    LogOutSheet |   8 |  