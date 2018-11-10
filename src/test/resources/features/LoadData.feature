Feature: Load data from AWS to GCP

  Scenario Outline: Check file name and file contents are the same after load data from AWS to GCP
    Given Authorization in AWS
    When Upload file "<fileName>" in AWS "<bucketNameAWS>" bucket
    And Wait "1" second
    And Authorization in GCP
    And Upload file "<fileName>" in GCP "<bucketNameGCP>" bucket
    Then Check file name "<fileName>" is existed in GCP "<bucketNameGCP>" bucket true
    And Download file "<fileName>" from GCP "<bucketNameGCP>" bucket
    And File "<fileName>" contenta is the same
    And Delete file "<fileName>" from AWS "<bucketNameAWS>" bucket
    And Delete file "<fileName>" from GCP "<bucketNameGCP>" bucket
    And Check file name "<fileName>" is existed in GCP "<bucketNameGCP>" bucket false
    Examples:
      | fileName     | bucketNameGCP | bucketNameAWS |
      | LoadData.csv | GCP           | AWS           |