// for now just want to get moves for testing. later make this more robust. perhaps use ANTLR

import java.util.Scanner;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

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
		boolean inTagPair = false;
		boolean inComment = false;
		boolean white = true;
		int gamesIx = 0;
		games.add(gamesIx, new ArrayList<String>());
		while (in.hasNext())
		{
			String token = in.next();


			if (token.startsWith("["))  {inTagPair = true;}
			if (token.startsWith("{"))  {inComment = true;}
			
			if (!inTagPair && !inComment)
			{
			Matcher CASTLEKS = Chess.CASTLEKSRegex.matcher(token);
			Matcher CASTLEQS = Chess.CASTLEQSRegex.matcher(token);
			Matcher matchResult = Chess.resultRegex.matcher(token);
			
			// test
			
			Matcher PIECEFILESQUARE = Chess.PIECEFILESQUARERegex.matcher(token);
			Matcher PIECESQUARE = Chess.PIECESQUARERegex.matcher(token);
			Matcher PIECERANKSQUARE = Chess.PIECERANKSQUARERegex.matcher(token);

			Matcher FILESQUARE = Chess.FILESQUARERegex.matcher(token);
			Matcher RANKSQUARE = Chess.RANKSQUARERegex.matcher(token);
			Matcher PAWNSQUARE = Chess.PAWNSQUARERegex.matcher(token);
			Matcher PIECECAPTURESQUARE = Chess.PIECECAPTURESQUARERegex.matcher(token);
			Matcher PIECEFILECAPTURESQUARE  = Chess.PIECEFILECAPTURESQUARERegex.matcher(token);
			Matcher PIECERANKCAPTURESQUARE = Chess.PIECERANKCAPTURESQUARERegex.matcher(token);
			Matcher PIECEFILERANKCAPTURESQUARE = Chess.PIECEFILERANKCAPTURESQUARERegex.matcher(token);
			Matcher RANKCAPTURESQUARE = Chess.RANKCAPTURESQUARERegex.matcher(token);
			Matcher FILECAPTURESQUARE = Chess.FILECAPTURESQUARERegex.matcher(token);

			Matcher SQUAREPROMOTION = Chess.SQUAREPROMOTIONRegex.matcher(token);
			Matcher FILEPROMOTION   = Chess.FILEPROMOTIONRegex.matcher(token);

		// test

			if(PIECEFILESQUARE.find())
			{
				show(token,"PIECEFILESQUARE",PIECEFILESQUARE);
			}
			else if(PIECESQUARE.find())
			{
				show(token,"PIECESQUARE",PIECESQUARE);
			}
			else if(PIECERANKSQUARE.find())
			{
				show(token,"PIECERANKSQUARE",PIECERANKSQUARE);
			}
			else if(FILESQUARE.find())
			{
				show(token,"FILESQUARE",FILESQUARE);
			}
			else if(RANKSQUARE.find())
			{
				show(token,"RANKSQUARE",RANKSQUARE);
			}
			else if(PAWNSQUARE.find())
			{
				show(token,"PAWNSQUARE",PAWNSQUARE);
			}
			else if(PIECECAPTURESQUARE.find())
			{
				show(token,"PIECECAPTURESQUARE",PIECECAPTURESQUARE);
			}
			else if(PIECEFILECAPTURESQUARE.find())
			{
				show(token,"PIECEFILECAPTURESQUARE",PIECEFILECAPTURESQUARE);
			}
			else if(FILECAPTURESQUARE.find())
			{
				show(token,"FILECAPTURESQUARE",FILECAPTURESQUARE);
			}
			else if(PIECERANKCAPTURESQUARE.find())
			{
				show(token,"PIECERANKCAPTURESQUARE",PIECERANKCAPTURESQUARE);
			}
			else if(RANKCAPTURESQUARE.find())
			{
				show(token,"RANKCAPTURESQUARE",RANKCAPTURESQUARE);
			}
			else if(PIECEFILERANKCAPTURESQUARE.find())
			{
				show(token,"PIECEFILERANKCAPTURESQUARE",PIECEFILERANKCAPTURESQUARE);
			}
			else if(SQUAREPROMOTION.find())
			{
				show(token,"SQUAREPROMOTION",SQUAREPROMOTION);
			}
			else if(FILEPROMOTION.find())
			{
				show(token,"FILEPROMOTION",FILEPROMOTION);
			}
			else if (CASTLEKS.find())
			{
				show(token,"CASTLEKS",CASTLEKS);
			}
			else if (CASTLEQS.find())
			{
				show(token,"CASTLEQS",CASTLEQS);
			}
			else if (matchResult.find()){System.out.printf("%s: result\n",token);}
			else {System.out.printf("%s --> NO IDEA\n",token);}
	
		
				if ("1-00-11/2-1/2".contains(token))
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
					white = !white;
				}
			}
			if (token.endsWith("]"))    {inTagPair = false;}
			if (token.endsWith("}"))    {inComment = false;}
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

	private void show(String token, String ID, Matcher match)
	{
		System.out.printf("%s is a %s\n", token, ID);
		for (int group=0; group < match.groupCount()+1;group++)
		{
			System.out.printf("group#%d: %s\n",group,match.group(group));
		}
	}
}
