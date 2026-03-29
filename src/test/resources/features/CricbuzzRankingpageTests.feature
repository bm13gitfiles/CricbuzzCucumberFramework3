Feature: Validation of Cricbuzz Ranking page
  This Feature file is to validate the Content of Cricbuzz ranking page and to obtain the ranking list

  @Rankingpage @MenRankingTests
  Scenario: Validate Cricbuzz Mens Ranking page navigation
    Given The Cricbuzz website is launched
    When User navigates to Mens Ranking page from Header > Rankings > ICC Rankings - Men
    Then The Men's ranking page should be displayed to the user
    And It should display the Title as "ICC Player Ranking | Men's Ranking | Top 100 Batsmen | Cricbuzz.com" in Browser Tab

  @Rankingpage @MenRankingTests
  Scenario: Validate Cricbuzz Mens Ranking page Title
    Given The Cricbuzz Mens Ranking page is launched
    Then It should display the Title as "ICC Player Ranking | Men's Ranking | Top 100 Batsmen | Cricbuzz.com" in Browser Tab

  @Rankingpage @MenRankingTests
  Scenario: Verify the list of Top Test batters
    Given The Cricbuzz Mens Ranking page is launched
    Then By default it should display the list of Test Batters with player points
    And User should able to get Top 10 Test batters with player points
    And User should be able to get Best Test Batsman

  @Rankingpage @MenRankingTests
  Scenario: Verify the list of Top ODI batters
    Given The Cricbuzz Mens Ranking page is launched
    When User selects the ODI button
    Then It should display the list of ODI Batters with player points
    And User should able to get Top 10 ODI batters with player points
    And User should be able to get Top Batsman

  @Rankingpage @MenRankingTests
  Scenario: Verify the list of Top T20i batters
    Given The Cricbuzz Mens Ranking page is launched
    When User selects the T20i button
    Then It should display the list of T20i Batters with player points
    And User should able to get Top 10 T20i batters with player points
    And User should be able to get Top Batsman

  @Rankingpage @WomenRankingTests
  Scenario: Validate Cricbuzz Womens Ranking page navigation
    Given The Cricbuzz website is launched
    When User navigates to Womens Ranking page from Header > Rankings > ICC Rankings - Women
    Then The Womens ranking page should be displayed to the user
    And It should display the Title as "ICC Player Ranking | Women's Ranking | Top 100 Batting | Cricbuzz.com" in Browser Tab

  @Rankingpage @WomenRankingTests
  Scenario: Validate Cricbuzz Womens Ranking page Title
    Given The Cricbuzz Womens Ranking page is launched
    Then It should display the Title as "ICC Player Ranking | Women's Ranking | Top 100 Batting | Cricbuzz.com" in Browser Tab

  @Rankingpage @WomenRankingTests
  Scenario: Verify the list of Top ODI Women batters
    Given The Cricbuzz Womens Ranking page is launched
    Then By default it should display the list of Top ODI Women Batters with player points
    And User should able to get Top 10 ODI Women Batters with player points
    And User should be able to get Best Batsman

  @Rankingpage @WomenRankingTests
  Scenario: Verify the list of Top T20i Women batters
    Given The Cricbuzz Womens Ranking page is launched
    When User selects the T20i button
    Then It should display the list of Top T20i Women Batters with player points
    And User should able to get Top 10 T20i Women Batters with player points
    And User should be able to get Top Batsman