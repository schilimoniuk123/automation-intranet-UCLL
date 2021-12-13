Feature: search news item

  As a student
  I want to be able to search for a specific news item
  In order to quickly find a news item

  # Persona
  # Yannick - student

  Background:
    Given Yannick is logged in

  Rule: Search a specific news item on news feed

    @UI
    Scenario: Search a specific news item on news feed
      Given the news item "Verstuur je kerst- en nieuwjaarswensen" is on the news feed
      When Yannick searches the news item "Verstuur je kerst- en nieuwjaarswensen"
      Then the news item "Verstuur je kerst- en nieuwjaarswensen" is found

  Rule: Search a news item based on keywords

    @UI
    Scenario: Search a news item based on keywords
      Given the news item "Verstuur je kerst- en nieuwjaarswensen" is on the news feed
      When Yannick searches "Verstuur je" to search the news item "Verstuur je kerst- en nieuwjaarswensen"
      Then the news item "Verstuur je kerst- en nieuwjaarswensen" is found
