# Treasure Thieves
## Treasure Thieves Game Overview
Contents

- 1.1 : (game name) Overview
- 1.2 Introduction
- 1.3 Tutorial
- 1.4 Importing Project
- 1.5 Run Project, Jar Project, Test Project
- 1.6 Credits 
- 1.7 An Aside on AI + Disclaimer

## Authors
- msh254
- nsr36

## Prerequisites
- JDK >= 17 [click here to get the latest stable OpenJDK release (as of writing this README)](https://jdk.java.net/18/)
- *(optional)* Gradle [Download](https://gradle.org/releases/) and [Install](https://gradle.org/install/)

## Introduction

This project is a demonstation of skills gained throughout the last few months. This game is being developed as a part of the SENG201 (Software Engineering 201) Course at UC (University of Canterbury).
This game, designed off specifications and guidelines outlined in the project specifications is heavily inspired of traditional 'Tower Defense' Games such as 'Bloons Tower Defense 6', 'Kingdom Rush' and other titles. However, this game offers a speciality in where the player will aim for a balance of strategy and efficiency in order to complete the game.

## Tutorial

Upon opening the game, the player will be have to complete the 'Game Setup' screen, which consists of entering a player name, a specified round number and a difficulty. In addition, the player will also be given a choice of three starting towers.

After the game setup, the player will have an opportunity to buy towers from the starting cash they recieve (depends on difficulty), and will also be given an opportunity to rearrange their inventory.

After selecting a modifier, the player will commence the first round.

The 'game' is a culmination of a set of rules, which are;

- The player start the game with 3-5 towers and any upgrades they have bought using in-game currency. Each tower has certain strengths and weaknesses
- 'Carts' are spawned depending on the round ranging from 5-12 Carts. Each cart will have a 'resource type' and a 'fill amount' (health)- The goal of the 'Carts' is to make it through the entire map without being sunk
- Destroying a cart with your towers before it reaches the end of the map will result in the player recieving money and 'score'.
- It is up to the player to choose the towers and the position of the towers to best finish a round.
- If the player fails to sink a Cart before it reaches the end of the map, a life will be lost for every Cart which is not sunk, and 
the player must also retry the round until they beat all of the carts.

However, there is more nuance to be game than this. Specifically;

Towers:
- Each tower will have a 'Fill Amount' , 'Damage', 'Cost', and 'Resource Type'.
- It is the Resource Type and Damage which are the most important, as the damage dealt to a cart is dependent on the Towers' resource type.
- Specifically, if the Tower and the Cart share the same Resource Type, then the Tower will deal FULL DAMAGE to the Cart.
- Otherwise, the tower will only deal 40% of it's 'Damage' to the Cart.

Upgrades:
- Throughout the game, the player will be given opprotunities to buy 'Upgrades' from the Shop. Upgrades are 'Resource Type' Dependent and offer boosts and bonuses to towers of the same type.
- Additionally, if three upgrades of the same type are bought, there will be a major upgrade to all towers of the same resource type (as the upgrade). Towers will get a 10% damage upgrade.

Carts (Boats):
- Carts are spawned with parameters of 'Fill Amount' (Health), 'Resource Type', and 'Speed'. The Resource Type of the Cart influences the image of the cart, and also infleunces which Towers will deal max damage to the Cart. The speed of the Cart is usually set to 1 (time to travel 1 grid is 1 second), but carts which have more 'Fill Amount' (Health), may have slower speed.
- Interestingly, Carts of the same Resource Type may actually have different Healths. For example, two 'Bronze Carts' spawned in a round may have 'Fill Amounts' of 100 and 200, respectively. This adds another layer of intricacy into the game, where the player will likely have to use context clues (speed of cart / what round they are on) to determine the Health of a Cart. (Although they could also see the Health Bar decreasing slower).

Random Events:
- Throughout the game several random events may occur each round. Specifically, after each round, their is a change that either one of the Players' towers will break, and their is also a chance that the next round they play, their towers may be buffed or nerfed.

## Importing Project (Using IntelliJ)
IntelliJ has built-in support for Gradle. To import this project:

- Launch IntelliJ and choose `Open` from the start-up window.
- Select the project and click open
- At this point in the bottom right notifications you may be prompted to 'load gradle scripts', If so, click load

**Note:** *If you run into dependency issues when running the app or the Gradle pop up doesn't appear then open the Gradle sidebar and click the Refresh icon.*

Alternatively
- Launch IntelliJ and choose `Open` from the start-up window.
- Select `Get from Version Control`, and paste in the GitLab repository link
- Clone the repository

## Run Project 
1. Open a command line interface inside the project directory and run `./gradlew run` to run the app.
2. The game should then be run, and a seperate window will be opened.

## Build and Run Jar
1. Open a command line interface inside the project directory and run `./gradlew jar` to create a packaged Jar. The Jar file is located at build/libs/seng201_team35-1.0-SNAPSHOT.jar
2. Navigate to the build/libs/ directory (you can do this with `cd build/libs`)
3. Run the command `java -jar seng201_team35-1.0-SNAPSHOT.jar` to open the application.

Alternatively, the jar for the game can be run by running the command
`java -jar msh254_nsr36_TreasureThieves.jar` to open the game

## Run Tests
1. Open a command line interface inside the project directory and run `./gradlew test` to run the tests.
2. Test results should be printed to the command line


## Credits

Image Credits: 
mini world sprites:
Author: 'Shade'
[Link](https://merchant-shade.itch.io/16x16-mini-world-sprites)

Additionally, function in the game also were heavily influenced/inspired from many sources.
All of the functions which used external sources have been mentioned in their respective JavaDoc.
For more information about each specific function;
(Look for the corresponding JavaDoc which has the number) (8 in GameManager, 1 in SpriteSheet)

(1) loadBuildingAssets()  -> (GameManager)
[Link](https://www.youtube.com/watch?v=UdGiuDDi7Rg&ab_channel=BroCode)
This function was inspired by the youtube video 'JavaFX animations' by 'Bro Code'
Specifically, the video showed how to use 'ImageView', which was used to load the building assets in the game.

(2,3) animateTower() -> (GameManager)
[Link](https://www.youtube.com/watch?v=_RY9VafiHq4&ab_channel=SoumyashreeSahoo)
This function used the AnimationTimer function, which was influenced by the video 'JavaFX Animation Timer', which outlined how to imlement a AnimationTimer function in our code.
Specifically, the video outlined how to override the handle of the AnimationTimer function.

(4) spawnCart() -> (GameManager) 
[Link1](https://www.youtube.com/watch?v=UdGiuDDi7Rg&ab_channel=BroCode) [Link2](https://www.youtube.com/watch?v=D0jbo58mYoo&ab_channel=Randomcode)
This function used ImageView objects to display png files. This was inspired off the two videos 'JavaFx - Moving sprite animation' by 'Random Code' and 'JavaFX animations' by 'Bro Code'

(5) rotateTowerTowardsTarget() -> (GameManager)
This code was developed with AI (ChatGPT).
AI was used in understanding the intricacy of this function.
Specifically, ChatGPT was used in order to determine how to calculate the angle between the tower, and the target.

(6) checkTowersTargeting() -> (GameManager)
This code was developed with AI (ChatGPT).
AI was used in understanding the intricacy of this function.
Specifically, ChatGPT was used in determining how to obtain the 'center' of the Cart in terms of x and y co-ordinates.

(7) launchProjectile() -> (GameManager)
[Link](AI was used in understanding the intricacy of this function.) [Link2](https://www.youtube.com/watch?v=-WfyzkDodlI&t=26s&ab_channel=GenuineCoder) [Link3](https://www.youtube.com/watch?v=D0jbo58mYoo&ab_channel=Randomcode)
This function used ImageView objects to display png files. Additionally, this function used the 'TranslateTransition' function which was infleunced by the youtube videos linked.

(8) moveCarts() -> (GameManager)
[Link](AI was used in understanding the intricacy of this function.) [Link2](https://www.youtube.com/watch?v=-WfyzkDodlI&t=26s&ab_channel=GenuineCoder) [Link3](https://www.youtube.com/watch?v=D0jbo58mYoo&ab_channel=Randomcode)
This function used ImageView objects to display png files. Additionally, this function used the 'TranslateTransition' function which was infleunced by the youtube videos linked.

(9) SpriteSheet -> (SpriteSheet)
[Link](https://www.youtube.com/watch?v=UdGiuDDi7Rg&ab_channel=BroCode)
This function used ImageView objects to display png files, influenced by the linked youtube video.
This code was developed with AI (ChatGPT).
AI was used in understanding the intricacy of this function.
Specifically, ChatGPT was used to seperate the png file of (any) Tower into specific 'animation frames'

## An Aside on AI + Disclaimer

Throughout this project, ChatGPT was used to understand the intricacies of specific functions, such as AnimationTimer, and TranslateTransitions. Additionally, ChatGPT was used to troubleshoot and understand complex bugs in the programs' system.
All (if any) AI generated code has been fully understood and as a rule, no code has been directly copied from ChatGPT, only used as a guideline for how to better structure our own code.

This sentiment applies to code influenced from youtube videos aswell. Youtube videos (similar to AI) were used only to gain a better understanding of how the nature of a game works, and in times, the intricate details of complex features.