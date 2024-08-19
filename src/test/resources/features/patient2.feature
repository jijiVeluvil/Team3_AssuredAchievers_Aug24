@tag
Feature: Patient features

  @tag2
  Scenario Outline: Create Patient
    Given Dietician creates patient Post request with "<sheetname>","<scenario>","<row>" and request body
    When Dietician sends POST request with endpoint
    Then Dietician recieves response code
    Then Dietician generates Patient Token

    Examples: 
      | sheetname | scenario                     | row |
      | patient   | create_valid_patientId       |   2 |
      | patient   | invalid_method               |  11 |
      | patient   | valid_patient_mandatory_only |   7 |
