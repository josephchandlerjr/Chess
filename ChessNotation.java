import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class ChessNotation
{
	private String notation;
	private boolean valid = false;
	private boolean isCastleKS = false;
	private boolean isCastleQS = false;
	private boolean endGameMarker = false;
	private String pieceToMove = "";
	private String fileToMove  = "";
	private String rankToMove  = "";
	private String fileDestination  = "";
	private String rankDestination  = "";

	public ChessNotation(String notation)
	{
		this.notation = notation;
		validateAndCapture(notation);

	}
	/**
	 * tells you if the notation fed to constructor was valid chess notation
	 * @return true if valid else false
	 */
	public boolean isValid()
	{
		return valid;
	}

	public void validateAndCapture(String token)
	{
		Matcher CASTLEKS = CASTLEKSRegex.matcher(token);
		Matcher CASTLEQS = CASTLEQSRegex.matcher(token);
		
		Matcher PIECEFILESQUARE = PIECEFILESQUARERegex.matcher(token);
		Matcher PIECESQUARE = PIECESQUARERegex.matcher(token);
		Matcher PIECERANKSQUARE = PIECERANKSQUARERegex.matcher(token);

		Matcher FILESQUARE = FILESQUARERegex.matcher(token);
		Matcher RANKSQUARE = RANKSQUARERegex.matcher(token);
		Matcher PAWNSQUARE = PAWNSQUARERegex.matcher(token);
		Matcher PIECECAPTURESQUARE = PIECECAPTURESQUARERegex.matcher(token);
		Matcher PIECEFILECAPTURESQUARE  = PIECEFILECAPTURESQUARERegex.matcher(token);
		Matcher PIECERANKCAPTURESQUARE = PIECERANKCAPTURESQUARERegex.matcher(token);
		Matcher PIECEFILERANKCAPTURESQUARE = PIECEFILERANKCAPTURESQUARERegex.matcher(token);
		Matcher RANKCAPTURESQUARE = RANKCAPTURESQUARERegex.matcher(token);
		Matcher FILECAPTURESQUARE = FILECAPTURESQUARERegex.matcher(token);
		Matcher SQUAREPROMOTION = SQUAREPROMOTIONRegex.matcher(token);
		Matcher FILEPROMOTION   = FILEPROMOTIONRegex.matcher(token);

		Matcher RESULT = RESULTRegex.matcher(token);

		if(PIECEFILESQUARE.find())
		{
			valid = true;
			pieceToMove     = PIECEFILESQUARE.group(1);
			fileToMove      = PIECEFILESQUARE.group(2);
			fileDestination = PIECEFILESQUARE.group(3);
			rankDestination = PIECEFILESQUARE.group(4);
			show(token,"PIECEFILESQUARE",PIECEFILESQUARE);

		}
		else if(PIECESQUARE.find())
		{
			valid = true;
			pieceToMove     = PIECESQUARE.group(1);
			fileDestination = PIECESQUARE.group(2);
			rankDestination = PIECESQUARE.group(3);
			show(token,"PIECESQUARE",PIECESQUARE);
		}
		else if(PIECERANKSQUARE.find())
		{
			valid = true;
			pieceToMove     = PIECERANKSQUARE.group(1);
			rankToMove      = PIECERANKSQUARE.group(2);
			fileDestination = PIECERANKSQUARE.group(3);
			rankDestination = PIECERANKSQUARE.group(4);
			show(token,"PIECERANKSQUARE",PIECERANKSQUARE);
		}
		else if(FILESQUARE.find())
		{
			valid = true;
			fileToMove      = FILESQUARE.group(1);
			fileDestination = PIECERANKSQUARE.group(2);
			rankDestination = PIECERANKSQUARE.group(3);
			show(token,"FILESQUARE",FILESQUARE);
		}
		else if(RANKSQUARE.find())
		{
			valid = true;
			show(token,"RANKSQUARE",RANKSQUARE);
		}
		else if(PAWNSQUARE.find())
		{
			valid = true;
			show(token,"PAWNSQUARE",PAWNSQUARE);
		}
		else if(PIECECAPTURESQUARE.find())
		{
			valid = true;
			show(token,"PIECECAPTURESQUARE",PIECECAPTURESQUARE);
		}
		else if(PIECEFILECAPTURESQUARE.find())
		{
			valid = true;
			show(token,"PIECEFILECAPTURESQUARE",PIECEFILECAPTURESQUARE);
		}
		else if(FILECAPTURESQUARE.find())
		{
			valid = true;
			show(token,"FILECAPTURESQUARE",FILECAPTURESQUARE);
		}
		else if(PIECERANKCAPTURESQUARE.find())
		{
			valid = true;
			show(token,"PIECERANKCAPTURESQUARE",PIECERANKCAPTURESQUARE);
		}
		else if(RANKCAPTURESQUARE.find())
		{
			valid = true;
			show(token,"RANKCAPTURESQUARE",RANKCAPTURESQUARE);
		}
		else if(PIECEFILERANKCAPTURESQUARE.find())
		{
			valid = true;
			show(token,"PIECEFILERANKCAPTURESQUARE",PIECEFILERANKCAPTURESQUARE);
		}
		else if(SQUAREPROMOTION.find())
		{
			valid = true;
			show(token,"SQUAREPROMOTION",SQUAREPROMOTION);
		}
		else if(FILEPROMOTION.find())
		{
			valid = true;
			show(token,"FILEPROMOTION",FILEPROMOTION);
		}
		else if (CASTLEKS.find())
		{
			valid = true;
			show(token,"CASTLEKS",CASTLEKS);
		}
		else if (CASTLEQS.find())
		{
			valid = true;
			show(token,"CASTLEQS",CASTLEQS);
		}
		else if (RESULT.find())
		{
			valid = true;
			endGameMarker = true;
			show(token,"CASTLEQS",CASTLEQS);
		}

	}
	private void show(String token, String ID, Matcher match)
	{
		System.out.printf("%s is a %s\n", token, ID);
		for (int group=0; group < match.groupCount()+1;group++)
		{
			System.out.printf("group#%d: %s\n",group,match.group(group));
		}
		System.out.printf("Piece:%s File:%s, Rank:%s, File:%s, Rank:%S\n", pieceToMove,
				                                                   fileToMove,
									           rankToMove,
									           fileDestination,
									           rankDestination
				                                                   );
	}
	public boolean isEndGameMarker()
	{
		return endGameMarker;
	}


	// regular expressions

        //these are for castling	
	private final String CASTLEKS    = "^(\\d+\\.)?O-O[\\?!#]*$";
	private final String CASTLEQS   = "^(\\d+\\.)?O-O-O[\\?!#]*$";
	// these are for moves to empty squares
	private final String RANK = "([1-8])";
	private final String FILE = "([a-h])";
	private final String PIECE = "([KQNBRS])";
	private final String SQUARE = "(?:"+FILE+RANK+")";
	private final String PIECEFILESQUARE = 
	      "^(?:\\d+\\.)?PF%S%[+!?#]*$".replace("P",PIECE).replace("F",FILE).replace("%S%",SQUARE);

	private final String PIECEFILE = 
	      "^(?:\\d+\\.)?PF[+!\\?#]*$".replace("P",PIECE).replace("F",FILE);
	private final String PIECERANK = 
	      "^(?:\\d+\\.)?P%R%[+!\\?#]*$".replace("P",PIECE).replace("%R%",RANK);
	private final String PIECERANKSQUARE = 
	      "^(?:\\d+\\.)?P%R%%S%[+!\\?#]*$".replace("P",PIECE).replace("%R%",RANK).replace("%S%",SQUARE);
	private final String PIECESQUARE = 
	      "^(?:\\d+\\.)?P%S%[+!\\?#]*$".replace("P",PIECE).replace("%S%",SQUARE);
	private final String FILESQUARE = 
	      "^(?:\\d+\\.)?F%S%[+!\\?#]*$".replace("F",FILE).replace("%S%",SQUARE);
	private final String RANKSQUARE = 
	      "^(?:\\d+\\.)?%R%%S%[+!\\?#]*$".replace("%R%",RANK).replace("%S%",SQUARE);
	private final String PAWNSQUARE = 
	      "^(?:\\d+\\.)?%S%[+!\\?#]*$".replace("%S%",SQUARE);

	//these are for captures
	private final String PIECECAPTURESQUARE = 
	      "^(?:\\d+\\.)?Px%S%[+!\\?#]*$".replace("P",PIECE).replace("%S%",SQUARE);
	private final String PIECEFILECAPTURESQUARE = 
	      "^(?:\\d+\\.)?PFx%S%[+!\\?#]*$".replace("P",PIECE).replace("F",FILE).replace("%S%",SQUARE);
	private final String PIECERANKCAPTURESQUARE = 
	      "^(?:\\d+\\.)?P%R%x%S%[+!\\?#]*$".replace("P",PIECE).replace("%R%",RANK).replace("%S%",SQUARE);
	private final String PIECEFILERANKCAPTURESQUARE = 
"^(?:\\d+\\.)?PF%R%x%S%[+!\\?#]*$".replace("P",PIECE).replace("F",FILE).replace("%R%",RANK).replace("%S%",SQUARE);
	private final String RANKCAPTURESQUARE= 
	      "^(?:\\d+\\.)?%R%x%S%[+!\\?#]*$".replace("%R%",RANK).replace("%S%",SQUARE);
	private final String FILECAPTURESQUARE= 
	      "^(?:\\d+\\.)?Fx%S%[+!\\?#]*$".replace("F",FILE).replace("%S%",SQUARE);
	private final String SQUAREPROMOTION= 
	      "^(?:\\d+\\.)?%S%=P[+!\\?#]*$".replace("%S%",SQUARE).replace("P",PIECE);
	private final String FILEPROMOTION= 
	      "^(?:\\d+\\.)?F=P[+!\\?#]*$".replace("F",FILE).replace("P",PIECE);

	private final String RESULT = "^(1-0)|(0-1)|(1/2-1/2)$";
	private final Pattern RESULTRegex = Pattern.compile(RESULT);
	//Patterns can be used elsewhere most notably in PGNFile
	
	private final Pattern CASTLEKSRegex = Pattern.compile(CASTLEKS);
	private final Pattern CASTLEQSRegex= Pattern.compile(CASTLEQS);
	private final Pattern PIECEFILESQUARERegex = Pattern.compile(PIECEFILESQUARE);
	private final Pattern PIECERANKSQUARERegex = Pattern.compile(PIECERANKSQUARE);
	private final Pattern PIECESQUARERegex = Pattern.compile(PIECESQUARE);
	private final Pattern FILESQUARERegex = Pattern.compile(FILESQUARE);
	private final Pattern RANKSQUARERegex = Pattern.compile(RANKSQUARE);
	private final Pattern PAWNSQUARERegex = Pattern.compile(PAWNSQUARE);
	private final Pattern PIECECAPTURESQUARERegex = Pattern.compile(PIECECAPTURESQUARE); 
	private final Pattern PIECEFILECAPTURESQUARERegex = Pattern.compile(PIECEFILECAPTURESQUARE); 
	private final Pattern PIECERANKCAPTURESQUARERegex = Pattern.compile(PIECERANKCAPTURESQUARE); 
	private final Pattern PIECEFILERANKCAPTURESQUARERegex = Pattern.compile(PIECEFILERANKCAPTURESQUARE); 
	private final Pattern RANKCAPTURESQUARERegex = Pattern.compile(RANKCAPTURESQUARE); 
	private final Pattern FILECAPTURESQUARERegex = Pattern.compile(FILECAPTURESQUARE); 
	private final Pattern SQUAREPROMOTIONRegex = Pattern.compile(SQUAREPROMOTION);
	private final Pattern FILEPROMOTIONRegex = Pattern.compile(FILEPROMOTION);

	private final Pattern[] algebraicNotations =  {CASTLEKSRegex,
						CASTLEQSRegex,
						PIECEFILESQUARERegex, 
						PIECERANKSQUARERegex, 
						PIECESQUARERegex, 
						FILESQUARERegex, 
						RANKSQUARERegex,
						PAWNSQUARERegex, 
						PIECECAPTURESQUARERegex, 
						PIECEFILECAPTURESQUARERegex, 
						PIECERANKCAPTURESQUARERegex, 
						PIECEFILERANKCAPTURESQUARERegex, 
						RANKCAPTURESQUARERegex, 
						FILECAPTURESQUARERegex, 
						SQUAREPROMOTIONRegex, 
						FILEPROMOTIONRegex
					        };	
}
