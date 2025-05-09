Feature: Login to IOMS Web

  #make sure to keep this as fisrt file. This scenario login is done once to avoid time waste
  Scenario: User can access IOMS Web page URl
    Given user navigate to IOMS Web Application by entering correct username and password
    Then  user should be able to navigate to IOMS web successfully and see the IOMS logo





