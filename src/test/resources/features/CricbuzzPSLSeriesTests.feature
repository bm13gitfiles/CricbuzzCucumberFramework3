Feature: Validation of Cricbuzz - Pakistan Super League 2026 Pages
  This feature validates the content of the Pakistan Super League (PSL) 2026 pages,
  including navigation, news, statistics, points table, and team details.

  @PSLTests
  Scenario: Validate navigation to the PSL 2026 page
    Given The Cricbuzz website is launched for PSL
    When User navigates to the Pakistan Super League 2026 page from Header > Series > Pakistan Super League 2026
    Then The Pakistan Super League 2026 page should be displayed to the user
    And It should display the PSL page Title as "Pakistan Super League 2026 schedule, live scores, scorecards, points table, videos and statistics | Cricbuzz.com" in Browser Tab


  @PSLTests @NewsTests
  Scenario: Validate PSL 2026 Top News section
    Given The Cricbuzz - Pakistan Super League 2026 page is launched
    When User selects the Top News section
    Then The top story should be displayed to the user
    And A relevant photograph for the top story should be displayed with a caption
    And The first paragraph of the top story should be displayed


  @PSLTests @PointsTableTests
  Scenario: Validate PSL 2026 Points Table
    Given The Cricbuzz - Pakistan Super League 2026 page is launched
    When User selects the Points Table section
    Then The points table should be displayed to the user
    And The user should be able to identify the top team in playoff contention


  @PSLTests @StatsTests
  Scenario: Validate PSL 2026 Most Runs statistics
    Given The Cricbuzz - Pakistan Super League 2026 page is launched
    When User selects the Stats section
    Then The list of batsmen with the most runs should be displayed to the user
    And The user should be able to identify the top batsman in contention for the Orange Cap


  @PSLTests @StatsTests
  Scenario: Validate PSL 2026 Most Wickets statistics
    Given The Cricbuzz - Pakistan Super League 2026 page is launched
    When User selects the Stats section
    And User selects Stats > Most Wickets
    Then The list of bowlers with the most wickets should be displayed to the user
    And The user should be able to identify the top bowler in contention for the Purple Cap


  @PSLTests @SquadsTests
  Scenario: Validate PSL 2026 Teams and Captains
    Given The Cricbuzz - Pakistan Super League 2026 page is launched
    When User selects the Squads section
    Then The PSL teams should be displayed
    And The user should be able to identify the captain of each team