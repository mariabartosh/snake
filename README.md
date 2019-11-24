## Description
 **Snake** is a [libGDX](https://libgdx.badlogicgames.com/)-based game which is a copy of the famous game "snake" and [slither.io](http://slither.io/) in particular.
*****
## Building and playing
>***Note:*** [Gradle](https://gradle.org/) is used as build system.
>Java 1.7+ is required for building and running the project.

Building the project should be very straightforward:

1. `git clone https://github.com/mariabartosh/snake.git`
2. `cd snake`
3. To build and start the server, use `./gradlew server:run`
4. *(optional)* To build and run the error servlet, to which client sends information about errors, which then are stored to the database, use `./gradlew appRun` 
>[Gretty](https://github.com/gretty-gradle-plugin/gretty) Gradle plugin is used for running the servlet. Gretty **requires JDK8 or JDK9**.
5. To build and run the desktop client, use `./gradlew desktop:run`

*****
## Download
If you only want to play game and connect to an already running server, client can be [downloaded](https://github.com/mariabartosh/snake/releases/latest/download/snake.jar) in binary form and run on Windows, Mac or Linux. Java 1.7+ is required. To run Snake, double click the `snake.jar` file or run it from the command line:

`java -jar snake.jar`

*****
## Some rules
The game is multiplayer. Your goal is to eat donuts and dodge enemies.
The more donuts you eat, the longer the snake becomes. The more enemies run into you, the bigger your snake becomes. Head-on collision kills two players. Collision with borders of the world will also kill you. 

Have a nice game!
