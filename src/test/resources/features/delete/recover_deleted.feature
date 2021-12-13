Feature: recover deleted

  As a student
  I want to be able to recover a deleted news item
  In order to show these news items in my news feed again

  # Persona
  # Yannick - student

  Background:
    Given Yannick is logged in

  Rule: Undeleted news items are added to the news feed

    @UI
    Scenario: Undeleted news items are added to the news feed
      Given the news item "Verstuur je kerst- en nieuwjaarswensen" is a deleted news item
      When Yannick undeletes the news item "Verstuur je kerst- en nieuwjaarswensen"
      Then the news item "Verstuur je kerst- en nieuwjaarswensen" should be on the news feed again

  Rule: Undeleted news items are removed from the list of deleted news items

    @UI
    Scenario: Undeleted news items are removed from the list of deleted news items
      Given the news item "14 december WEBINAR Pitch Please: starten als student ondernemer!" is a deleted news item
      When Yannick undeletes the news item "14 december WEBINAR Pitch Please: starten als student ondernemer!"
      Then the news item "14 december WEBINAR Pitch Please: starten als student ondernemer!" should be removed from the list of deleted news items

  Rule: Undeleted news items are marked as undeleted news items

    @UI
    Scenario: Undeleted news items are marked as undeleted news items
      Given the news item "Stem dinsdag mee op de beste VolunteerFest-pitch" is a deleted news item
      When Yannick undeletes the news item "Stem dinsdag mee op de beste VolunteerFest-pitch"
      Then the news item "Stem dinsdag mee op de beste VolunteerFest-pitch" should be marked as undeleted