Feature: Permissions
  Scenario: Company Admin cannot Edit User Profiles
    Given a User 'sdtxs01'
    And User has the 'FINDUSRAILCOMPADM' role
    Then User cannot 'UserEditProfile'
    
  Scenario: SSO Admin can Edit User Profiles
    Given a User 'sdtxs01'
    And User has the 'SSOADM' role
    Then User can 'UserEditProfile'
