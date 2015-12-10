import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Chess
{
	// regular expressions
	private static String pieceToMove = "(( )|([a-h])|( [a-h])|( [1-8]))?".replaceAll(" ", "[KQRNBS]");
	private static String castleKS    = "^(\\d+\\.)?O-O[\\?!#]*$";
	private static String castleQS   = "^(\\d+\\.)?O-O-O[\\?!#]*$";
        private static String simpleMove = "^(\\d+\\.)?"+pieceToMove+"[a-h][1-8]\\+?[\\?!#]*$";
	private static String capture = "^(\\d+\\.)?"+pieceToMove+"x[a-h][1-8]\\+?[\\?!#]*$";
	private static String promotion ="^(\\d+\\.)?[a-h][1-8]=[KQRNBS]\\+?[\\?!#]*$";
	private static String result = "(1-0)|(0-1)|(1/2-1/2)";

	//Patterns can be used elsewhere most notably in PGNFile
	
	static Pattern simpleRegex = Pattern.compile(simpleMove);
	static Pattern captureRegex = Pattern.compile(capture);
	static Pattern promotionRegex = Pattern.compile(promotion);
	static Pattern castleKSRegex = Pattern.compile(castleKS);
	static Pattern castleQSRegex= Pattern.compile(castleQS);
	static Pattern resultRegex = Pattern.compile(result);


	public static void main(String[] args)
	{
		//runs the show
		//constructs a game, whiteIO and blackIO
		//
	}

	/**
	 * translates algebraic chess notation into an int[] that the game instance can act on
	 * @param notation algebraic chess notation to translate
	 * @param color color who's turn it is to move
	 * @return array of 4 integers that can be interpreted by game instance and acted upon
	 */	
	public int[] translateNotation(String notation, String color)
	{
		//Matchers
		Matcher matchSimple = simpleRegex.matcher(notation);	
		Matcher matchCapture = captureRegex.matcher(notation);	
		Matcher matchPromotion= promotionRegex.matcher(notation);	
		Matcher matchCKS = castleKSRegex.matcher(notation);
		Matcher matchCQS = castleQSRegex.matcher(notation);
		Matcher matchResult = resultRegex.matcher(notation);	

		if (matchSimple.find())
		{System.out.printf("%s is simple\n",notation);}
		else if (matchCapture.find())
		{System.out.printf("%s is capture\n",notation);}
		else if (matchPromotion.find())
		{System.out.printf("%s is promotion\n",notation);}
		else if (matchCKS.find())
		{System.out.printf("%s is CKS\n",notation);}
		else if (matchCQS.find())
		{System.out.printf("%s is CQS\n",notation);}
		else if (matchResult.find())
		{System.out.printf("%s is result\n",notation);}
		else 
		{System.out.printf("%s --> NO IDEA\n",notation);}
		return new int[4];
	}
}
