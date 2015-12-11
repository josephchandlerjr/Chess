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
			Matcher matchCKS = Chess.castleKSRegex.matcher(token);
			Matcher matchCQS = Chess.castleQSRegex.matcher(token);
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
				System.out.printf("%s IS PIECEFILESQUARE\n",token);
				System.out.printf("group 1 is %s\n",PIECEFILESQUARE.group(1));
				System.out.printf("group 2 is %s\n",PIECEFILESQUARE.group(2));
				System.out.printf("group 3 is %s\n",PIECEFILESQUARE.group(3));
				System.out.printf("group 4 is %s\n",PIECEFILESQUARE.group(4));
				System.out.printf("group 5 is %s\n",PIECEFILESQUARE.group(5));
			}
			else if(PIECESQUARE.find())
			{
				System.out.printf("%s IS PIECESQUARE\n",token);
				System.out.printf("group 1 is %s\n",PIECESQUARE.group(1));
				System.out.printf("group 2 is %s\n",PIECESQUARE.group(2));
				System.out.printf("group 3 is %s\n",PIECESQUARE.group(3));
				System.out.printf("group 4 is %s\n",PIECESQUARE.group(4));
			}
			else if(PIECERANKSQUARE.find())
			{
				System.out.printf("%s IS PIECERANKSQUARE\n", token);
				System.out.printf("group 1 is %s\n",PIECERANKSQUARE.group(1));
				System.out.printf("group 2 is %s\n",PIECERANKSQUARE.group(2));
				System.out.printf("group 3 is %s\n",PIECERANKSQUARE.group(3));
				System.out.printf("group 4 is %s\n",PIECERANKSQUARE.group(4));
				System.out.printf("group 5 is %s\n",PIECERANKSQUARE.group(5));
			}
			else if(FILESQUARE.find())
			{
				System.out.printf("%s IS FILESQUARE\n", token);
				System.out.printf("group 1 is %s\n",FILESQUARE.group(1));
				System.out.printf("group 2 is %s\n",FILESQUARE.group(2));
				System.out.printf("group 3 is %s\n",FILESQUARE.group(3));
				System.out.printf("group 4 is %s\n",FILESQUARE.group(4));
			}
			else if(RANKSQUARE.find())
			{
				System.out.printf("%s IS RANKSQUARE\n",token);
				System.out.printf("group 1 is %s\n",RANKSQUARE.group(1));
				System.out.printf("group 2 is %s\n",RANKSQUARE.group(2));
				System.out.printf("group 3 is %s\n",RANKSQUARE.group(3));
				System.out.printf("group 4 is %s\n",RANKSQUARE.group(4));
			}
			else if(PAWNSQUARE.find())
			{
				System.out.printf("%s IS PAWNSQUARE\n",token);
				System.out.printf("group 1 is %s\n",PAWNSQUARE.group(1));
				System.out.printf("group 2 is %s\n",PAWNSQUARE.group(2));
				System.out.printf("group 3 is %s\n",PAWNSQUARE.group(3));
			}

			else if(PIECECAPTURESQUARE.find())
			{
				System.out.printf("%s IS PIECECAPTURESQUARE\n",token);
				for (int group=0; group < PIECECAPTURESQUARE.groupCount();group++)
				{
					System.out.printf("group %d is %s\n",group+1,PIECECAPTURESQUARE.group(group+1));
				}
			}
			else if(PIECEFILECAPTURESQUARE.find())
			{
				System.out.printf("%s IS PIECEFILECAPTURESQUARE\n",token);
				for (int group=0; group < PIECEFILECAPTURESQUARE.groupCount();group++)
				{
					System.out.printf("group %d is %s\n",group+1,PIECEFILECAPTURESQUARE.group(group+1));
				}
			}
			else if(FILECAPTURESQUARE.find())
			{
				System.out.printf("%s IS FILECAPTURESQUARE \n", token);
				for (int group=0; group < FILECAPTURESQUARE.groupCount();group++)
				{
					System.out.printf("group %d is %s\n",group+1,FILECAPTURESQUARE.group(group+1));
				}
			}
			else if(PIECERANKCAPTURESQUARE.find())
			{
				System.out.printf("%s IS PIECERANKCAPTURESQUARE \n",token);
				for (int group=0; group < PIECERANKCAPTURESQUARE.groupCount();group++)
				{
					System.out.printf("group %d is %s\n",group+1,PIECERANKCAPTURESQUARE.group(group+1));
				}
			}
			else if(RANKCAPTURESQUARE.find())
			{
				System.out.printf("%s IS RANKCAPTURESQUARE \n", token);
				for (int group=0; group < RANKCAPTURESQUARE.groupCount();group++)
				{
					System.out.printf("group %d is %s\n",group+1,RANKCAPTURESQUARE.group(group+1));
				}
			}
			else if(PIECEFILERANKCAPTURESQUARE.find())
			{
				System.out.printf("%s IS PIECEFILERANKCAPTURESQUARE \n",token);
				for (int group=0; group < PIECEFILERANKCAPTURESQUARE.groupCount();group++)
				{
					System.out.printf("group %d is %s\n",group+1,PIECEFILERANKCAPTURESQUARE.group(group+1));
				}
			}
			else if (SQUAREPROMOTION.find())
			{
				System.out.printf("%s IS SQUAREPROMOTION\n", token);
				for (int group=0; group < SQUAREPROMOTION.groupCount();group++)
				{
					System.out.printf("group %d is %s\n",group+1,SQUAREPROMOTION.group(group+1));
				}
			}
			else if (FILEPROMOTION.find())
			{
				System.out.printf("%s IS FILEPROMOTION\n", token);
				for (int group=0; group < FILEPROMOTION.groupCount();group++)
				{
					System.out.printf("group %d is %s\n",group+1,FILEPROMOTION.group(group+1));
				}
			}
			else if (matchCKS.find()){System.out.printf("%s is CKS\n",token);}
			else if (matchCQS.find()){System.out.printf("%s is CQS\n",token);}
			else if (matchResult.find()){System.out.printf("%s is result\n",token);}
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
}
