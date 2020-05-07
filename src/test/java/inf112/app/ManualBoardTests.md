# Manual tests
####Controls:
Keybinds: 
Q       :   Checks the current position of the player

Player can be moved by cards or arrow keys on the keyboard.
When using arrow keys it will first check if the player is facing in the arrow direction,
and change direction if the player is not facing the same direction as the arrow key.

###Board
If the player moves outside of the board in any of the direction, the player will lose one life and 
be re spawned on his last spawn point. Spawn points are either the initial spawn, the last visited repair kit, 
or the last visited flag.

###Board objects
By pressing Q the player will interact with the board.

#####Walls:
Walls stop lasers and players from going through them. If a player tries to move through a wall
he will not change position.

#####Laser: 
If a player is standing on a tile with a laser, the player will take one damage. Lasers
should be stopped by walls.

#####Black holes:
If a player steps in a black hole he will die, lose a life point and be re spawned later if he has more life points.

#####Conveyor belts: 
If a player is standing on a tile with a conveyor belt, the player will be moved 
one square on single arrow conveyors and two squares on double arrowed conveyors.
If the conveyor moves a player into a conveyor turn, the player will be turned in the 
direction the conveyor is turning.

#####Turn wheels: 
If a player is standing on a tile with a turn wheel, he will be turned in the same direction
of the turn wheel.

#####Repair kit:
If a player is standing on a repair kit, his health will increase by one hit point, up till max hit points.

#####Flags:
If a player stands on a flag, the player's re spawn point will change to the flag's position, 
and the player may be repaired by one hit point up till max hit points.
If a player visits all flags on the board he will win. Flags have different numbers, and must be picked up in the 
correct order. 