@tag
Feature: Patient features

  @tag2
  Scenario Outline: Create Patient
    Given Dietician creates patient Post request with "<sheetname>","<scenario>","<row>" and request body
    When Dietician sends POST request with endpoint
    Then Dietician recieves response code
    Then Dietician generates Patient Token

    Examples: 
      | sheetname | scenario                          | row |
      | patient   | create_valid_patientId            |   2 |
      | patient   | create_valid_patientId            |   3 |
      | patient   | create_patient_without_report     |   4 |
      | patient   | create_no_auth                    |   5 |
      | patient   | create_invalid_token_admin        |   6 |
      | patient   | create_invalid_token_patient      |   7 |
      | patient   | create_patient_without_report     |   8 |
      | patient   | create_no_mandatory_details       |   9 |
      | patient   | create_invalid_mandatory_details  |  10 |
      | patient   | create_invalid_additional_details |  11 |
      | patient   | create_invalid_method             |  12 |
      | patient   | create_invalid_endpoint           |  13 |
      | patient   | create_invalid_content_type       |  14 |
