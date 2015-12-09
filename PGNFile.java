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
	int gamesIndex = -1;
	int moveIndex = 0;

	ArrayList<ArrayList<String[][]>> gameParseTrees = new ArrayList<ArrayList<String[][]>>();

	// regular expressions for parsing
	String pieceToMove = "(( )|([a-h])|( [a-h])|( [1-8]))?".replaceAll(" ", "[KQRNBS]");
	String castleKS    = "^(\\d+\\.)?O-O[\\?!#]*$";
	String castleQS   = "^(\\d+\\.)?O-O-O[\\?!#]*$";
        String simpleMove = "^(\\d+\\.)?"+pieceToMove+"[a-h][1-8]\\+?[\\?!#]*$";
	String capture = "^(\\d+\\.)?"+pieceToMove+"x[a-h][1-8]\\+?[\\?!#]*$";
	String promotion ="^(\\d+\\.)?[a-h][1-8]=[KQRNBS]\\+?[\\?!#]*$";
	String result = "(1-0)|(0-1)|(1/2-1/2)";
	

	

	
	//new instance variables


	public PGNFile(String fileName) throws java.io.FileNotFoundException 
	{
		Pattern simpleRx = Pattern.compile(simpleMove);
		Pattern captureRx = Pattern.compile(capture);
		Pattern promotionRx = Pattern.compile(promotion);
		Pattern castleKSRx = Pattern.compile(castleKS);
		Pattern castleQSRx= Pattern.compile(castleQS);
		Pattern resultRx = Pattern.compile(result);
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
			Matcher matchSimple = simpleRx.matcher(token);	
			Matcher matchCapture = captureRx.matcher(token);	
			Matcher matchPromotion= promotionRx.matcher(token);	
			Matcher matchCKS = castleKSRx.matcher(token);
			Matcher matchCQS = castleQSRx.matcher(token);
			Matcher matchResult = resultRx.matcher(token);
			if (matchSimple.find()){System.out.printf("%s is simple\n",token);}
			else if (matchCapture.find()){System.out.printf("%s is capture\n",token);}
			else if (matchPromotion.find()){System.out.printf("%s is promotion\n",token);}
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
