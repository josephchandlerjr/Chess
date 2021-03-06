package test;


import java.util.ArrayList;
import java.util.List;
import java.io.File;
import chess.ConsoleChess;
import chess.ChessNotation;

/** 
 * holds tests to aid development
 * run main() to run tests
 * use -v switch to make verbose
 */ 
public class Test
{
	private static boolean VERBOSE = false;

	/**
	 * runs tests
	 */
	public static void main(String[] args) throws java.io.FileNotFoundException
	{
		File folder = new File("C:\\Users\\AGNESI\\Chess\\src\\test\\resources");
		File[] testFiles = folder.listFiles();

		IOHelper white;
		IOHelper black;
		for(File testFile : testFiles)
		{
			if (testFile.getName().endsWith(".pgn"))
			{
				System.out.printf("Testing from file %s...\n",testFile.getName());
				PGNFile file = new PGNFile(testFile.getPath());
				List<List<ChessNotation>> games = file.getGames();

				for (List<ChessNotation> game : games)
				{
					
					white = new IOHelper(game);
					black = white;

					ConsoleChess c = new ConsoleChess();
					while(true)
					{
						//c.displayBoard();
						ChessNotation whiteMove =  white.next();
						if(whiteMove == null) { break;}
						System.out.printf("MOVE IS %s\n",c.executeMove("WHITE",whiteMove));
						//c.displayBoard();
						ChessNotation blackMove =  black.next();
						if(blackMove == null) { break;}
						System.out.printf("MOVE IS %s\n",c.executeMove("BLACK",blackMove));
					}
				}
			}
		}
	}
}
