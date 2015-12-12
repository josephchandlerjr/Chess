import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Chess
{
	// regular expressions
	private static String pieceToMove = "(( )|([a-h])|( [a-h])|( [1-8]))?".replaceAll(" ", "[KQRNBS]");
	private static String result = "^(1-0)|(0-1)|(1/2-1/2)$";

	// new regex versions
	// these are for moves to empty squares
	
	private static String CASTLEKS    = "^(\\d+\\.)?O-O[\\?!#]*$";
	private static String CASTLEQS   = "^(\\d+\\.)?O-O-O[\\?!#]*$";
	private static String RANK = "([1-8])";
	private static String FILE = "([a-h])";
	private static String PIECE = "([KQNBRS])";
	private static String SQUARE = "("+FILE+RANK+")";
	private static String PIECEFILESQUARE = 
		              "^(?:\\d+\\.)?PF%S%[+!?#]*$".replace("P",PIECE).replace("F",FILE).replace("%S%",SQUARE);

	private static String PIECEFILE = 
		              "^(?:\\d+\\.)?PF[+!\\?#]*$".replace("P",PIECE).replace("F",FILE);
	private static String PIECERANK = 
		              "^(?:\\d+\\.)?P%R%[+!\\?#]*$".replace("P",PIECE).replace("%R%",RANK);
	private static String PIECERANKSQUARE = 
		              "^(?:\\d+\\.)?P%R%%S%[+!\\?#]*$".replace("P",PIECE).replace("%R%",RANK).replace("%S%",SQUARE);
	private static String PIECESQUARE = 
		              "^(?:\\d+\\.)?P%S%[+!\\?#]*$".replace("P",PIECE).replace("%S%",SQUARE);

	private static String FILESQUARE = 
		              "^(?:\\d+\\.)?F%S%[+!\\?#]*$".replace("F",FILE).replace("%S%",SQUARE);
	private static String RANKSQUARE = 
		              "^(?:\\d+\\.)?%R%%S%[+!\\?#]*$".replace("%R%",RANK).replace("%S%",SQUARE);
	private static String PAWNSQUARE = 
		              "^(?:\\d+\\.)?%S%[+!\\?#]*$".replace("%S%",SQUARE);
	//these are for captures
	private static String PIECECAPTURESQUARE = 
		              "^(?:\\d+\\.)?Px%S%[+!\\?#]*$".replace("P",PIECE).replace("%S%",SQUARE);
	private static String PIECEFILECAPTURESQUARE = 
		              "^(?:\\d+\\.)?PFx%S%[+!\\?#]*$".replace("P",PIECE).replace("F",FILE).replace("%S%",SQUARE);
	private static String PIECERANKCAPTURESQUARE = 
		              "^(?:\\d+\\.)?P%R%x%S%[+!\\?#]*$".replace("P",PIECE).replace("%R%",RANK).replace("%S%",SQUARE);
	private static String PIECEFILERANKCAPTURESQUARE = 
		"^(?:\\d+\\.)?PF%R%x%S%[+!\\?#]*$".replace("P",PIECE).replace("F",FILE).replace("%R%",RANK).replace("%S%",SQUARE);
	private static String RANKCAPTURESQUARE= 
		              "^(?:\\d+\\.)?%R%x%S%[+!\\?#]*$".replace("%R%",RANK).replace("%S%",SQUARE);
	private static String FILECAPTURESQUARE= 
		              "^(?:\\d+\\.)?Fx%S%[+!\\?#]*$".replace("F",FILE).replace("%S%",SQUARE);
	private static String SQUAREPROMOTION= 
		              "^(?:\\d+\\.)?%S%=P[+!\\?#]*$".replace("%S%",SQUARE).replace("P",PIECE);
	private static String FILEPROMOTION= 
		              "^(?:\\d+\\.)?F=P[+!\\?#]*$".replace("F",FILE).replace("P",PIECE);

	//Patterns can be used elsewhere most notably in PGNFile
	
	static Pattern CASTLEKSRegex = Pattern.compile(CASTLEKS);
	static Pattern CASTLEQSRegex= Pattern.compile(CASTLEQS);
	static Pattern resultRegex = Pattern.compile(result);
	//new Pattern objs
	static Pattern PIECEFILESQUARERegex = Pattern.compile(PIECEFILESQUARE);
	static Pattern PIECERANKSQUARERegex = Pattern.compile(PIECERANKSQUARE);
	static Pattern PIECESQUARERegex = Pattern.compile(PIECESQUARE);
	static Pattern FILESQUARERegex = Pattern.compile(FILESQUARE);
	static Pattern RANKSQUARERegex = Pattern.compile(RANKSQUARE);
	static Pattern PAWNSQUARERegex = Pattern.compile(PAWNSQUARE);
	static Pattern PIECECAPTURESQUARERegex = Pattern.compile(PIECECAPTURESQUARE); 
	static Pattern PIECEFILECAPTURESQUARERegex = Pattern.compile(PIECEFILECAPTURESQUARE); 
	static Pattern PIECERANKCAPTURESQUARERegex = Pattern.compile(PIECERANKCAPTURESQUARE); 
	static Pattern PIECEFILERANKCAPTURESQUARERegex = Pattern.compile(PIECEFILERANKCAPTURESQUARE); 
	static Pattern RANKCAPTURESQUARERegex = Pattern.compile(RANKCAPTURESQUARE); 
	static Pattern FILECAPTURESQUARERegex = Pattern.compile(FILECAPTURESQUARE); 
	static Pattern SQUAREPROMOTIONRegex = Pattern.compile(SQUAREPROMOTION);
	static Pattern FILEPROMOTIONRegex = Pattern.compile(FILEPROMOTION);

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
		return new int[4];
	}
}
