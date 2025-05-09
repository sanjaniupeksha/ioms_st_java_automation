Feature: Participants Absence Requests


  Scenario: Verify cancel button on create a new Participants Absence Request
    Given user navigates to Participants Absence Request page
    And   click on create new Participants Absence Request button
    And   verify Mandatory fields marked properly
    Then  user navigated back to previous page on click of button cancel

  Scenario Outline: User can create a new Participants Absence Request
    Given user navigates to Participants Absence Request page
    And   click on create new Participants Absence Request button
    And   fill <CourseName> course  <Watch> watchName and <Participant> participant  details
    And   user select reason <Reason> and  incident <Incident> if related and remarks <Remarks>
    When  user save Absence Request
    Then  user should see a record added with status Pending and <CourseCode> course code
    Examples:
      | CourseName     | Watch    | Participant       | Reason   | Remarks                  | Incident      | CourseCode  |
      | SJU_21_04_2025 | Leonidas | PARTICIANT SJU262 | Personal | Added By Test Automation | Med - Bob Lee | SJU21042025 |


  Scenario Outline: User cannot create a request for same user
    Given user navigates to Participants Absence Request page
    And   click on create new Participants Absence Request button
    And   fill <CourseName> course  <Watch> watchName and <Participant> participant  details
    And   user select reason <Reason> and  incident <Incident> if related and remarks <Remarks>
    When  user save Absence Request
    Then  user should see an error message for participant
    Examples:
      | CourseName     | Watch    | Participant       | Reason   | Remarks                  | Incident      |
      | SJU_21_04_2025 | Leonidas | PARTICIANT SJU262 | Personal | Added By Test Automation | Med - Bob Lee |

  Scenario: Delete an Absence request
    Given user navigates to Participants Absence Request page
    And   user filter created Absence request with participant name and click delete button



  Scenario Outline: Approve an Absence request
    Given user navigates to Participants Absence Request page
    And   click on create new Participants Absence Request button
    And   fill <CourseName> course  <Watch> watchName and <Participant> participant  details
    And   user select reason <Reason> and  incident <Incident> if related and remarks <Remarks>
    Then  user save Absence Request
    And   user filter created Absence request with participant name
    Then  user approves created Absence request
    Examples:
      | CourseName     | Watch    | Participant       | Reason   | Remarks                  | Incident      | CourseCode  |
      | SJU_21_04_2025 | Leonidas | PARTICIANT SJU262 | Personal | Added By Test Automation | Med - Bob Lee | SJU21042025 |








