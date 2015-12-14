// for now just want to get moves for testing. later make this more robust. perhaps use ANTLR

import java.util.Scanner;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * class representation of a .pgn file
 */
public class PGNFile	
{
	Scanner in;
	ArrayList<ArrayList<String>> games = new ArrayList<ArrayList<String>>();
	int gamesIndex = 0;
	int moveIndex = 0;

	public PGNFile(String fileName) throws java.io.FileNotFoundException 
	{
		in = new Scanner(new FileReader(fileName));
		int gamesIx = 0;
		games.add(gamesIx, new ArrayList<String>());
		while (in.hasNext())
		{
			String token = in.next();
			ChessNotation notation = new ChessNotation(token);

			if(notation.isValid())
			{
				if (notation.isEndGameMarker())
				{ 
					gamesIx++;
					games.add(gamesIx, new ArrayList<String>());
				}
				else
				{
					int dot = token.indexOf(".");
					if(dot != -1 )
					{
						token = token.substring(dot+1,token.length());
					}
					games.get(gamesIx).add(token);
				}
			}


		}
	}
	/**
	 * returns ArrayList of ArrayLists of String elements, each a game from this file
	 * @return ArrayList<ArrayList<string>> , each a game
	 */
	public ArrayList<ArrayList<String>> getGames()
	{
		return games;
	}

}
