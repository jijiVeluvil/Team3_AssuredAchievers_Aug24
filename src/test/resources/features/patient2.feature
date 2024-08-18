@tag
Feature: Patient features

  @tag2
  Scenario Outline: Create Patient
    Given Dietician creates patient Post request with "<sheetname>","<scenario>","<row>" and request body
    When Dietician sends POST request with endpoint
    Then Dietician recieves response code

    Examples: 
      | sheetname | scenario                     | row |
      | patient   | no_auth                      |   4 |
      | patient   | invalid_method               |  11 |
      | patient   | valid_patient_mandatory_only |   7 |
