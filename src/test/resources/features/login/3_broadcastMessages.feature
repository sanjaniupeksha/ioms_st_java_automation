Feature: BroadCastMessages


  Scenario Outline: User can create a new broadcast message
    Given user navigates to BroadcastMessages screen and click Create Broadcast Message
    When  user enter <Title> title and <Content> content and select users <Users> for the broadcast message
    Then  user should be able to see a success message
    Then  user should be receive a web push notification
    Examples:
      | Title           | Content                     | Users                                  |
      | Title_Automated | This is a Automated message | testuserabc7,testuserabc8,testuserabc4 |

  Scenario Outline: User can create a new broadcast message
    Given user navigates to BroadcastMessages screen and click Create Broadcast Message
    When  user enter <Title> title and <Content> content and select users <Users> for the broadcast message
    And  user should be able to see a success message
    Given user has created a broadcast message
    Then  user should see the sent message details added in the broadcast messages table
    Examples:
      | Title              | Content                       | Users                                               |
      | Title_BroadCastMsg | Message Content by Automation | testuserabc7,testuserabc8,testuserabc4,testuserabc2 |


  Scenario: User can create a new broadcast message
    Given user navigates to BroadcastMessages screen and click Create Broadcast Message
    Then  cancel button should take user back to previous page







