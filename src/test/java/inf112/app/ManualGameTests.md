##Game tests
####Controls:
Keybindings: 
Q       :   Checks the current position of the player
L       :   Shoots lasers from all the players in the direction they are facing
Player can be moved by cards or arrow keys on the keyboard.
When using arrow keys it will first check if the player is facing in the arrow direction,
and change direction if the player is not facing the same direction as the arrow key.

####Interacting
When moving towards the position of another player, the game will check if the player can be move and move
the other player in the same direction unless he is blocked by a wall.

####Player lasers
######L       :   Shoots lasers from all the players in the direction they are facing
A laser will continue until it hits a player, a wall or goes out of the board. If a laser hits another 
player the player will lose one hit point.