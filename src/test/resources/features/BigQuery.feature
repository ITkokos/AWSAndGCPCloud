Feature: Run query in BigQuery

  Scenario Outline: Check that the number of rows corresponds to the expectations
    Given Authorization in GCP
    Then Check number of rows equal "1460" in "<tableName>" table
    Examples:
      | tableName                                    |
      | fine-cycling-220511.load_data_table.my_table |