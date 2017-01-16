# An implementation of the Noughts and Crosses Game Service
This project is an implementation of the Nought and Crosses Game Service.
Please find the details at https://github.com/michaeldfallen/coding-tests/blob/master/Noughts_and_crosses.md.

##Prerequisite
Requires Maven version 3.x and Java 8 JDK

##Installation
1. Download larryoke/noughts-and-crosses
2. Find downloaded noughts-and-crosses-master and change directory to noughts-and-crosses folder
3. From this folder build with command 'mvn clean package'


##Start Up
1. Go to noughts-and-crosses/GameService and run the command below to start the game server<br />
	java -jar target/gameService-0.0.1-SNAPSHOT.jar<br />
	
	
2. Go to noughts-and-crosses/GameClient and run the command below to start a game instance for Players X and O<br />
	java -jar target/gameClient-0.0.1-SNAPSHOT.jar<br />
	<br />
	Simply follow further instructions
	



##Usage (A brief description of the RESTful API)
1.	Initialise a new game on the server and return a new game ID
	
	**Request**<br />
	GET /initialise <br />
	Accept: aplication/json<br />
	
	**Response**<br />
	Success<br />
	 200 OK<br />
	{gameID}<br />
	
2. Process current mark and return a mark status to client
	 
	**Request**<br />
	POST /mark <br />
	Content-Type: application/json<br />
	```javascript
	{
		"gameID":{gameID}
		"currentPlayer":{X|O}
		"selectedSquare":{1..9}
	}
	```
	
	**Response**<br />
	Success<br />
	200 OK<br />
	Content-Type: application/json<br />
	
	{<br />
		"markStatus":{CONTINUE|WIN|DRAW}<br />
		"errorMessage":<br />
		"scroreMessage":{scoreMessage}<br />
	}<br />
	
	Errors<br />
	400 Bad Request<br />
	Content-Type: application/json<br />
	{<br />
		"markStatus":ERROR<br />
		"errorMessage":"This square is already marked"<br />
		"scroreMessage":<br />
	}<br />
	If the mark square is already occupied<br />
	

	
3. End the game (clean-up) to remove the game cache

  	**Request**<br />
  	DELETE /end/{gameID} <br />
