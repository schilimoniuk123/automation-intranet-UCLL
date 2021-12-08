Feature: delete

  As a student
  I want to be able to recover a delete news item
  In order to show these news items in my news feed again

  # Persona
  # Yannick - student

  Background:
    Given Yannick is logged in

  Rule: Deleted news items are marked as deleted news items

  @UI
  Scenario: Deleted news items are marked as deleted news items
    Given the news item "Verstuur je kerst- en nieuwjaarswensen" is not a deleted news item
    When Yannick deletes the news item "Verstuur je kerst- en nieuwjaarswensen"
    Then the news item "Verstuur je kerst- en nieuwjaarswensen" should be marked as deleted

  Rule: Deleted news items are removed from the overview of all news items

  @UI
  Scenario: Deleted news items are removed from the overview of all news items
    Given the news item "Verstuur je kerst- en nieuwjaarswensen" is not a deleted news item
    When Yannick deletes the news item "Verstuur je kerst- en nieuwjaarswensen"
    Then the news item "Verstuur je kerst- en nieuwjaarswensen" should be removed from the overview of all news items

  Rule: Deleted news items are added to the list of deleted news items

  @UI
  Scenario: Deleted news items are added to the list of deleted news items
    Given the news item "Verstuur je kerst- en nieuwjaarswensen" is not a deleted news item
    When Yannick deletes the news item "Verstuur je kerst- en nieuwjaarswensen"
    Then the news item "Verstuur je kerst- en nieuwjaarswensen" is added to the list of deleted news items