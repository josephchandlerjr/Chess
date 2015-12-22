// for now just want to get moves for testing. later make this more robust. perhaps use ANTLR

package test;

import java.util.Scanner;
import java.io.File;
//import java.io.FileReader;
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
		String content = new Scanner(new File(fileName)).useDelimiter("\\Z").next();
		content = content.replaceAll("\\[[^\\]]*\\]","");
		content = content.replaceAll("\\{[^\\}]*\\}","");
		content = content.replaceAll("\\([^\\)]*\\)","");
		in = new Scanner(content);
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
