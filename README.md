# An implementation of the Noughts and Crosses Game Service
This project is an implementation of the Nought and Crosses Game Service.
Please find the details at https://github.com/michaeldfallen/coding-tests/blob/master/Noughts_and_crosses.md.

##Prerequisite
Requires Maven version 3.x and Java 8 JDK

##Installation
1. Download larryoke/noughts-and-crosses
2. Find downloaded noughts-and-crosses-master and change directory to noughts-and-crosses folder
3. From this folder build with command 'mvn clean install'


##Usage (A brief description of the RESTful API)
1.	

	Initialise a new game on the server
	
	Request
	GET /initialise 
	Accept: aplication/json
	
	Response
	Success
	 200 OK
	{gameID}
	
2. Process current mark
	
	Request
	POST /mark 
	Content-Type: application/json
	
	{
		"gameID":{gameID}
		"currentPlayer":{X|O}
		"selectedSquare":{1..9}
	}
	
	Response
	Success
	200 OK
	Content-Type: application/json
	
	{
		"markStatus":{CONTINUE|WIN|DRAW}
		"errorMessage":
		"scroreMessage":{scoreMessage}
	}
	
	Errors
	400 Bad Request
	Content-Type: application/json
	{
		"markStatus":ERROR
		"errorMessage":"This square is already marked"
		"scroreMessage":
	}
	If the mark square is already occupied
	
3. End game

  	Request
  	DELETE /end/{gameID} 


##Start Up
1. Go to noughts-and-crosses/GameService and run the command below to start the game server
	java -jar target/gameService-0.0.1-SNAPSHOT.jar
	
	
2. Go to noughts-and-crosses/GameClient and run the command below to start a game instance for Players X and O
	java -jar target/gameClient-0.0.1-SNAPSHOT.jar
	
	Simply follow further instructions

