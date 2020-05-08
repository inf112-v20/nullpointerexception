# INF112 - RoboRally Project
HOW TO PLAY:
Start the game and choose 5 cards from the dealt cards. The order of the cards being picked determines the execution order of these cards. 
After picking 5 cards the round will begin.
Click on "Start" button to start each phase until the player used all of his cards.
When a player falls of the board/hole or is destroyed it stays out of the game until the next round starts.
To win, a player must walk onto the flags from number 1-3 respectively.
 

TESTING:
To test the movement and functionality use the arrow keys to move and "Q" while being on the conveyor belts/lasers/repairkits
You can find more information about testing in the ManualBoardTests and ManualGameTests md. files in the test directory.

## Known bugs
Currently throws "WARNING: An illegal reflective access operation has occurred", 
when the java version used is >8. This has no effect on function or performance, and is just a warning.

Powerdown functionality has not been implemented because of the shortage of time and our priority of other game functionalities.
Some bugs might appear when it comes to the player being shot/pushed by AI.

## Buttons
*   Use arrow keys to move the player
*   Q to test the tile under you 

## Requirements
*   java
*   git
*   maven

### Build
*   git clone <https://github.com/inf112-v20/nullpointerexception.git>
*   Open in yor preferred IDE. Navigate to Main and run it.

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/afeb6024171343a28fc70e9716c107b3)](https://app.codacy.com/gh/inf112-v20/nullpointerexception?utm_source=github.com&utm_medium=referral&utm_content=inf112-v20/nullpointerexception&utm_campaign=Badge_Grade_Settings)
[![Build Status](https://travis-ci.com/inf112-v20/nullpointerexception.svg?branch=master)](https://travis-ci.com/inf112-v20/nullpointerexception)
