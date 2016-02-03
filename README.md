# Chess 

This started out as a console chess game. Now it has a GUI using swing components, the ability
to play remote games via the ChessServer class, and other features. 
You'll see additional classes suchs as PGNFile under \src\test which were used by the original console game but
are not longer relevant to the GUI version.

## Build

This was coded in a Windows 7 environment and has yet to be tested elsewhere.
Clone the repository, cd into the root director \chess and enter
javac -d .\classes .\src\chess\\*.java
 
Run the GUI board by entering

cd classes

java chess.GuiChess 

## Packages

### chess

Contains classes which run gui game. Chess class is essentially the control center. Game class represent instances of games. Board class models a game board. ChessPiece class holds static references to specific game piece objects.

### test

Contains testing classes related to original ConsoleChess class. Deprecated but keeping it around for future reference. Includes PGNFile class which I used to parse PGN format chess games to use in testing.


