 # Description
 **Snake** is a libGDX-based game which is a copy, my version of the implementation of the famous game "snake" and [slither.io](http://slither.io/) in particular.
*****
# Building and playing
Building the project should be very straight forward:

1. git clone `https://github.com/mariabartosh/snake.git`
2. cd `snake`
3. To build the client, use `gradlew desktop:dist`
4. To build and start the server, use `gradlew server:run`
5. *(optional)* To build and run the servlet, which sends information about client errors to the database for storage, use `gradlew appRun`
6. Client launch 
`cd desktop\build\libs\`
`java -jar desktop-1.0.jar`

If you only want to start the client and connect to an already running server, use `some magic`
*****
# Some rules
The game is multiplayer, the purpose of which is to eat donuts and dodge enemies.
The more donuts, the longer the snake. The more enemies crash into you, the bigger your snake becomes. Head-on collision kills two players. Collision with borders of the world will also kill you. 

Have a nice game!
