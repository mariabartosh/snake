 # Description
 **Snake** is a [libGDX](https://libgdx.badlogicgames.com/)-based game which is a copy of the famous game "snake" and [slither.io](http://slither.io/) in particular.
*****
# Building and playing
>***Note:*** [Gradle](https://gradle.org/) is used as build system.
>Java 1.7+ is required for building and running the project.

Building the project should be very straightforward:

1. `git clone https://github.com/mariabartosh/snake.git`
2. `cd snake`
3. To build the desktop client, use `./gradlew desktop:dist`
4. To build and start the server, use `./gradlew server:run`
5. *(optional)* To build and run the error servlet, to which client sends information about errors, which then are stored to the database, use `./gradlew appRun`, 
>[Gretty](https://github.com/gretty-gradle-plugin/gretty) Gradle plugin is used for running the servlet. Gretty **requires JDK8 or JDK9**.

6. Client launch 
`cd desktop\build\libs\`
`java -jar desktop-1.0.jar`

If you only want to start the client and connect to an already running server, use `some magic`
*****
# Some rules
The game is multiplayer. Your goal is to eat donuts and dodge enemies.
The more donuts you eat, the longer the snake becomes. The more enemies run into you, the bigger your snake becomes. Head-on collision kills two players. Collision with borders of the world will also kill you. 

Have a nice game!
