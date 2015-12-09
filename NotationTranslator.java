


import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * a class to translate algebraic chess notation
 */
public class NotationTranslator
{

	// regular expressions
	private static String pieceToMove = "(( )|([a-h])|( [a-h])|( [1-8]))?".replaceAll(" ", "[KQRNBS]");
	private static String castleKS    = "^(\\d+\\.)?O-O[\\?!#]*$";
	private static String castleQS   = "^(\\d+\\.)?O-O-O[\\?!#]*$";
        private static String simpleMove = "^(\\d+\\.)?"+pieceToMove+"[a-h][1-8]\\+?[\\?!#]*$";
	private static String capture = "^(\\d+\\.)?"+pieceToMove+"x[a-h][1-8]\\+?[\\?!#]*$";
	private static String promotion ="^(\\d+\\.)?[a-h][1-8]=[KQRNBS]\\+?[\\?!#]*$";
	private static String result = "(1-0)|(0-1)|(1/2-1/2)";

	//Patterns
	
	private static Pattern simpleRx = Pattern.compile(simpleMove);
	private static Pattern captureRx = Pattern.compile(capture);
	private static Pattern promotionRx = Pattern.compile(promotion);
	private static Pattern castleKSRx = Pattern.compile(castleKS);
	private static Pattern castleQSRx= Pattern.compile(castleQS);
	private static Pattern resultRx = Pattern.compile(result);


	/**
	 * takes algebraic notation and turns it into int[4] which program can understand as a move
	 * @param notation a move in algebraic notation
	 * @return 4 element int array representing move program can understand
	 */
	public static int[] translate(String notation) 
	{
		//Matchers
		Matcher matchSimple = simpleRx.matcher(notation);	
		Matcher matchCapture = captureRx.matcher(notation);	
		Matcher matchPromotion= promotionRx.matcher(notation);	
		Matcher matchCKS = castleKSRx.matcher(notation);
		Matcher matchCQS = castleQSRx.matcher(notation);
		Matcher matchResult = resultRx.matcher(notation);	

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

		int[] it = new int[4];
		return it;
	}
	

}
