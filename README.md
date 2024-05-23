# game name (to decide soon)
Welcome to the template project for SENG201 which you will transform into your own.
This README file includes some useful information to help you get started.
However, we expect that this README becomes your own

## (game name) Game Overview
Remember you are required to commit your code to the **main** branch of your repository before the deadline.

Contents
1.1 : (game name) Overview
1.2 Introduction
1.3 Tutorial
1.4 Discussion
1.5 Intricate Details / Mechanics
1.6 Credits + Disclaimer
1.7 Features which could have been added 

## Authors
- msh254
- nsr36

## Prerequisites
- JDK >= 17 [click here to get the latest stable OpenJDK release (as of writing this README)](https://jdk.java.net/18/)
- *(optional)* Gradle [Download](https://gradle.org/releases/) and [Install](https://gradle.org/install/)

## Introduction

This project is a demonstation of skills gained throughout the last few months. This game is being developed as a part of the SENG201 (Software Engineering 201) Course at UC (University of Canterbury).
This game, designed off specifications and guidelines outlined in the project specifications is heavily inspired of traditional 'Tower Defense' Games such as 'Bloons Tower Defense 6', 'Kingdom Rush' and other titles. However, this game offers a speciality in where the player will aim for a balance of strategy and efficiency in order to complete the game.

# Tutorial

Upon opening the game, the player will be have to complete the 'Game Setup' screen, which consists of entering a player name, a specified round number and a difficulty. In addition, the player will also be given a choice of three starting towers.

After the game setup, the player will have an opportunity to buy towers from the starting cash they recieve (depends on difficulty), and will also be given an opportunity to rearrange their inventory.

After selecting a modifier, the player will commence the first round.

The 'game' is a culmination of a set of rules, which are;



















## What's Included
This project comes with some basic examples of the following (including dependencies in the build.gradle file):
- JavaFX
- Junit 5

We have also included a basic setup of the Gradle project and Tasks required for the course including:
- Required dependencies for the functionality above
- Test Coverage with JaCoCo
- Build plugins:
    - JavaFX Gradle plugin for working with (and packaging) JavaFX applications easily

You are expected to understand the content provided and build your application on top of it. If there is anything you
would like more information about please reach out to the tutors.

## Importing Project (Using IntelliJ)
IntelliJ has built-in support for Gradle. To import your project:

- Launch IntelliJ and choose `Open` from the start-up window.
- Select the project and click open
- At this point in the bottom right notifications you may be prompted to 'load gradle scripts', If so, click load

**Note:** *If you run into dependency issues when running the app or the Gradle pop up doesn't appear then open the Gradle sidebar and click the Refresh icon.*

## Run Project 
1. Open a command line interface inside the project directory and run `./gradlew run` to run the app.
2. The app should then open a new window, this may not be displayed over IntelliJ but can be easily selected from the taskbar

## Build and Run Jar
1. Open a command line interface inside the project directory and run `./gradlew jar` to create a packaged Jar. The Jar file is located at build/libs/seng201_team35-1.0-SNAPSHOT.jar
2. Navigate to the build/libs/ directory (you can do this with `cd build/libs`)
3. Run the command `java -jar seng201_team35-1.0-SNAPSHOT.jar` to open the application.

## Run Tests
1. Open a command line interface inside the project directory and run `./gradlew test` to run the tests.
2. Test results should be printed to the command line

**Note:** *This Jar is **NOT** cross-platform, so you **must** build the jar you submit on Linux.* 
