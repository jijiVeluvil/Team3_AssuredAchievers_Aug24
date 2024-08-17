#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
@userLoginModule
Feature:  Testing User Flow dietician

Scenario Outline: Check user able to login
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
