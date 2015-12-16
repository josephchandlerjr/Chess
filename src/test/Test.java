package test;


import java.util.Arrays;
import java.util.ArrayList;
import java.io.EOFException;
import java.util.ArrayList;

import chess.Chess;
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
		IOHelper white;
		IOHelper black;
		PGNFile file = new PGNFile("C:\\Users\\AGNESI\\Chess\\src\\test\\resources\\Paderborn (1991).pgn");
		ArrayList<ArrayList<ChessNotation>> games = file.getGames();

		for (ArrayList<ChessNotation> game : games)
		{
			
			white = new IOHelper(game);
			black = white;

			Chess c = new Chess();
			while(true)
			{
				ChessNotation whiteMove =  white.next();
				if(whiteMove == null) { break;}
				System.out.println(c.move("WHITE",whiteMove));
				ChessNotation blackMove =  black.next();
				if(blackMove == null) { break;}
				System.out.println(c.move("BLACK",blackMove));
			}
		}
	}
}
