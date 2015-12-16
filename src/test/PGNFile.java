// for now just want to get moves for testing. later make this more robust. perhaps use ANTLR

package test;

import java.util.Scanner;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

import chess.ChessNotation;

/**
 * class representation of a .pgn file
 */
public class PGNFile	
{
	Scanner in;
	ArrayList<ArrayList<ChessNotation>> games = new ArrayList<ArrayList<ChessNotation>>();
	int gamesIndex = 0;
	int moveIndex = 0;

	public PGNFile(String fileName) throws java.io.FileNotFoundException 
	{
		in = new Scanner(new FileReader(fileName));
		int gamesIx = 0;
		games.add(gamesIx, new ArrayList<ChessNotation>());
		boolean inTagOrComment = false;
		while (in.hasNext())
		{
			String token = in.next();
			ChessNotation notation = new ChessNotation(token);

			if(notation.isValid())
			{
				games.get(gamesIx).add(notation);
			}
			else if (notation.isEndGameMarker())
			{ 
				gamesIx++;
				games.add(gamesIx, new ArrayList<ChessNotation>());
			}
			else
			{
				String first = token.substring(0,1);
				String last = token.substring(token.length()-1,token.length());
				if(first.equals("[") | first.equals("{") )
				{
					inTagOrComment = true;
				}
				else if (last.equals("]") | last.equals("}"))
				{
					inTagOrComment = false;
				}
				else 
				{
					if (!inTagOrComment)
					{
						System.out.printf("%s is an unacceptable token\n",token);
					}
				}
			}



		}
	}
	/**
	 * returns ArrayList of ArrayLists of ChessNotation elements 
	 * @return ArrayList of games, each game is an ArrayList of ChessNotation elements
	 */
	public ArrayList<ArrayList<ChessNotation>> getGames()
	{
		return games;
	}

}
