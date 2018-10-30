This project is based on the Dr.Nim classic game.
The aim is to get the opposite player to 'remove' the last stone.

Rules:
Players take turns in removing 'stones'. Each turn a player can remove between 1 and n stones, where n is specified with the 'startgame' function. The player that removes the last stones loses :(

Syntax: 
addplayer <username>,<family_name>,<given_name>: adds player
addaiplayer <username>,<family_name>,<given_name>: adds AI player that players can play against.
editplayer <username>,<new_family_name>,<new_given_name>: edit player names. NOTE: cannot edit username
removeplayer (<username>): if no username given, removes all players.
displayplayer (<username>): display statistics of all players if no username given.
resetstats (<username>): reset game statistics of all players if no username given.
rankings ([asc|desc]): shows player rankings in ascending or descending order. Default order is descending
startgame <number_initial_stones>,<upper_removal_limit>,<username1>,<username2>: Begins game with username1 moving first
exit: writes statistics to file and closes the program
