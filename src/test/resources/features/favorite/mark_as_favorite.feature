Feature: Mark as Favorite

  As a student
  I want to be able to mark a news item as favorite
  In order to keep a collection of favorite news items

  # Persona
  # Yannick - student

  Background:
    Given Yannick is logged in

  Rule: Favorite news items are marked as favorite
  @UI
  Scenario: Favorite news items are marked as favorite
    Given the news item "stem dinsdag mee op de beste volunteerfest-pitch" is not a favorite news item
    When Yannick marks the news item "stem dinsdag mee op de beste volunteerfest-pitch" as favorite
    Then the news item "stem dinsdag mee op de beste volunteerfest-pitch" should be marked as a favorite news item

  Rule: Favorite news items are added to the list of favorite news items
  @UI
  Scenario: Favorite news items are added to the list of favorite news items
    Given the news item "stem dinsdag mee op de beste volunteerfest-pitch" is not a favorite news item
    When Yannick marks the news item "stem dinsdag mee op de beste volunteerfest-pitch" as favorite
    Then the news item "stem dinsdag mee op de beste volunteerfest-pitch" should be added to the list of favorite news items