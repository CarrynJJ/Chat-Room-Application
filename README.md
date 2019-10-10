# Chat-Room-Application
This Chat Room Application was a networking assignment to demonstrate our understanding of the Client/Server connection and the usage of 
sockets.

## How to run
In order to run the application the server has to be running. Therefore, first run Server before running the Client file.

```bash
javac *.java
java Server
java Client
```

## Usage
The application includes limited functionality.
To broadcast a message, just input a message and press enter.
The system will send the message to all connect clients. For example:
```java
//input by user
Hello World

//received by clients
User: Hello World
```
To send a directed message. Begin the message with '@' and the username of the recipient(s). For example:
```java
//input sent by user
@John how are you

//received by John
User: how are you
```
To logout of the application, enter 'logout', the connection to the server will close and the program will terminate.
