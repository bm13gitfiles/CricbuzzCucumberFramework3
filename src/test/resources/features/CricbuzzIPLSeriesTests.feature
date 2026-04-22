Feature: Validation of Cricbuzz - Indian Premier League 2026 Pages
  This feature validates the content of the Indian Premier League (IPL) 2026 pages,
  including navigation, news, statistics, points table, and team details.

  @IPLTests
  Scenario: Validate navigation to the IPL 2026 page
    Given The Cricbuzz website is launched for IPL
    When User navigates to the Indian Premier League 2026 page from Header > Series > Indian Premier League 2026
    Then The Indian Premier League 2026 page should be displayed to the user
    And It should display the IPL page Title as "IPL | Indian Premier League 2026 schedule, live scores, scorecards, points table, videos and statistics | Cricbuzz.com" in Browser Tab


  @IPLTests
  Scenario: Validate IPL 2026 Top News section
    Given Indian Premier League page is launched
    When User selects the IPL Top News section
    Then IPL top story should be displayed to the user
    And A relevant photograph for the IPL top story should be displayed with a caption
    And The first paragraph of the IPL top story should be displayed

  @IPLTests
  Scenario: Validate IPL 2026 Points Table
    Given Indian Premier League page is launched
    When User selects the IPL Points Table section
    Then IPL points table should be displayed to the user
    And The user should be able to identify the top team in IPL playoff contention

  @IPLTests
  Scenario: Validate IPL 2026 Most Runs statistics
    Given Indian Premier League page is launched
    When User selects the IPL Stats section
    Then The list of IPL batsmen with the most runs should be displayed to the user
    And The user should be able to identify the top IPL batsman in contention for the Orange Cap

  @IPLTests
  Scenario: Validate IPL 2026 Most Wickets statistics
    Given Indian Premier League page is launched
    When User selects the IPL Stats section
    And User selects IPL Stats > Most Wickets
    Then The list of IPL bowlers with the most wickets should be displayed to the user
    And The user should be able to identify the top IPL bowler in contention for the Purple Cap

  @IPLTests @CaptainTestIPL
  Scenario: Validate IPL 2026 Teams and Captains
    Given Indian Premier League page is launched
    When User selects the IPL Squads section
    Then The IPL teams should be displayed
    And The user should be able to identify the captain of each IPL team