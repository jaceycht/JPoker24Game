COMP3402 System Architecture and Distributed Computing

# Overview

- A 24-game game system played with poker playing card, consists of:
  - A server that handle user login, user matching, and gaming support
  - A client that allow users to login and play games
- Assignements
  - Assignment 1: RMI – handles user registration and login
  - Assignment 2: JDBC – handles data storage and retrieval
  - Assignment 3: JMS – handle client-server communication and game logic

# Assignment 1 

User Data
- In assignment 1, the server must maintain two files
  - UserInfo.txt – storing registered user information
    - Persistence across server execution
    - Used to authenticate user
    - Updated in successful user registration
    - Checked to avoid duplicating user name during registration
   - OnlineUser.txt – keeping a list of online users
    - Cleared when server start
    - Updated when user login/logout
    - Checked to avoid multiple login
- Note that this will be replaced by the use of database in assignment 2
  - You may want to plan ahead

RMI
- The server must use RMI to support the following functions:
  - Login
    - validate user from UserInfo.txt
    - avoid repeated login using OnlineUser.txt
    - update OnlineUser.txt
  - Register
    - avoid duplicating user name with UserInfo.txt
    - login user (update OnlineUser.txt)
  - Logout
    - update OnlineUser.txt

# Assignment 2

- Setting up MySQL database
- Using MySQL JDBC driver
- Writing Java program
  - Connect to database
  - Create, read, update, delete

# Assingment 3 

Three major components:
- The game-play GUI (client)
- The game-play mechanism (server + client)
- Evaluating (and validating) answer

You must use JMS to support the game-play mechanism.

Game Initial Stage 
1. User logged-in client
2. Clicked “Play Game”
3. Clicked “New Game”

Game Joining Stage 
1. Inform server
2. Match player to game
3. Wait for game ready to start
4. Inform client

Game Playing Stage
1. Pick cards and inform client(s)
2. Wait client to submit answer
3. Validate answer
4. Update statistics
5. Inform client
- When to start a game?
  - When there are 4 players, or
  - When there are 2 players and 10 seconds has passed since the first player joined
- Rules:
  - 4 Cards of different values should be drawn.
  - Suit doesn’t matters
  - Goal: an expression that equals 24
  - Available operations: +,-,×,÷
  - Parenthesis can be used to override precedence
  - Fraction may appear in intermediate result

Game Over Stage
1. Clicked “Next Game”









