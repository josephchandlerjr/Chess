package chess;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class ChessNotation
{
	private String notation;
	private boolean valid = false;
	private boolean castleKS = false;
	private boolean castleQS = false;
	private boolean promotion = false;
	private String promoteTo = "";
	private boolean endGameMarker = false;
	private String pieceToMove = "";
	private String fileToMove  = "";
	private String rankToMove  = "";
	private String fileDestination  = "";
	private String rankDestination  = "";
	private String label = "";

	/**
	 * constructor
	 * @param notation algebraic chess notation
	 */
	public ChessNotation(String notation)
	{
		this.notation = notation;
		validateAndCapture(notation);

	}
	
	/**
	 * static method that converts file notation of a-h to a a column number 0-7
	 * @param file file to convert to integer
	 * @return int value in range 0-7 representing column number
	 */
	public static int fileToColumn(String file)
	{
		int column = "abcdefgh".indexOf(file);
		return column;
	}

	/**
	 * static method that converts rank notation of 1-8 to a row number 0-7
	 * @param rank rank to convert to integer
	 * @return int value in range 0-7 representing row number
	 */
	public static int rankToRow(String rank)
	{
		int row = "87654321".indexOf(rank);
		return row;
	}

	/**
	 * static method that converts column number to file notation
	 * @param column column number between 0-7
	 * @return letter of corresponding file in range a-h
	 */
	public static String columnToFile(int column)
	{
		String file = "abcdefgh".substring(column,column+1);
		return file;
	}
	/**
	 * static method that converts row number to rank notation
	 * @param row row number between 0-7
	 * @return number of corresponding file in range 1-8
	 */
	public static String rowToRank(int row)
	{
		String file = "87654321".substring(row ,row+1);
		return file;
	}

	//getters
	public String getNotation() { return notation;}
	public String getPieceToMove() { return pieceToMove;}
	public String getFileToMove() { return fileToMove;}
	public String getRankToMove() { return rankToMove;}
	public String getFileDestination() { return fileDestination;}
	public String getRankDestination() { return rankDestination;}
	public String getPromoteTo() { return promoteTo;}

	/**
	 * tells you if the notation fed to constructor was valid chess notation
	 * @return true if valid else false
	 */
	public boolean isValid()
	{
		return valid;
	}
	/** identifies token and sets appropriate flags as well as logging capturing groups from regex
	 * @param token algebraic chess notation
	 */
	private void validateAndCapture(String token)
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


	        Matcher FILECAPTURESQUAREPROMOTION = FILECAPTURESQUAREPROMOTIONRegex.matcher(token);  
		Matcher RANKCAPTURESQUAREPROMOTION =RANKCAPTURESQUAREPROMOTIONRegex.matcher(token);  
		Matcher FILERANKCAPTURESQUAREPROMOTION =FILERANKCAPTURESQUAREPROMOTIONRegex.matcher(token);  


		if(PIECEFILESQUARE.find())
		{
			valid = true;
			pieceToMove     = PIECEFILESQUARE.group(1);
			fileToMove      = PIECEFILESQUARE.group(2);
			fileDestination = PIECEFILESQUARE.group(3);
			rankDestination = PIECEFILESQUARE.group(4);
			label = "PIECEFILESQUARE";

		}
		else if(PIECESQUARE.find())
		{
			valid = true;
			pieceToMove     = PIECESQUARE.group(1);
			fileDestination = PIECESQUARE.group(2);
			rankDestination = PIECESQUARE.group(3);
			label = "PIECESQUARE";
		}
		else if(PIECERANKSQUARE.find())
		{
			valid = true;
			pieceToMove     = PIECERANKSQUARE.group(1);
			rankToMove      = PIECERANKSQUARE.group(2);
			fileDestination = PIECERANKSQUARE.group(3);
			rankDestination = PIECERANKSQUARE.group(4);
			label = "PIECERANKSQUARE";
		}
		else if(FILESQUARE.find())
		{
			valid = true;
			fileToMove      = FILESQUARE.group(1);
			fileDestination = PIECERANKSQUARE.group(2);
			rankDestination = PIECERANKSQUARE.group(3);
			label = "FILESQUARE";
		}
		else if(RANKSQUARE.find())
		{
			valid = true;
			rankToMove =      RANKSQUARE.group(1);
			fileDestination = RANKSQUARE.group(2);
			rankDestination = RANKSQUARE.group(3);
			label = "RANKSQUARE";
		}
		else if(PAWNSQUARE.find())
		{
			valid = true;
			pieceToMove = "P";
			fileDestination = PAWNSQUARE.group(1);
			rankDestination = PAWNSQUARE.group(2);
			label = "PAWNSQUARE";
		}
		else if(PIECECAPTURESQUARE.find())
		{
			valid = true;
			pieceToMove =     PIECECAPTURESQUARE.group(1);
			fileDestination = PIECECAPTURESQUARE.group(2);
			rankDestination = PIECECAPTURESQUARE.group(3);
			label = "PIECECAPTURESQUARE";
		}
		else if(PIECEFILECAPTURESQUARE.find())
		{
			valid = true;
			pieceToMove =     PIECEFILECAPTURESQUARE.group(1);
			fileToMove =      PIECEFILECAPTURESQUARE.group(2);
			fileDestination = PIECEFILECAPTURESQUARE.group(3);
			rankDestination = PIECEFILECAPTURESQUARE.group(4);
			label = "PIECEFILECAPTURESQUARE";
		}
		else if(FILECAPTURESQUARE.find())
		{
			valid = true;
			pieceToMove = "P";
			fileToMove =      FILECAPTURESQUARE.group(1);
			fileDestination = FILECAPTURESQUARE.group(2);
			rankDestination = FILECAPTURESQUARE.group(3);
			label = "FILECAPTURESQUARE";
		}
		else if(PIECERANKCAPTURESQUARE.find())
		{
			valid = true;
			pieceToMove =     PIECERANKCAPTURESQUARE.group(1);
			rankToMove =      PIECERANKCAPTURESQUARE.group(2);
			fileDestination = PIECERANKCAPTURESQUARE.group(3);
			rankDestination = PIECERANKCAPTURESQUARE.group(4);
			label = "PIECERANKCAPTURESQUARE";
		}
		else if(RANKCAPTURESQUARE.find())
		{
			valid = true;
			pieceToMove = "P";
			rankToMove =      RANKCAPTURESQUARE.group(1);
			fileDestination = RANKCAPTURESQUARE.group(2);
			rankDestination = RANKCAPTURESQUARE.group(3);
			label = "RANKCAPTURESQUARE";
		}
		else if(PIECEFILERANKCAPTURESQUARE.find())
		{
			valid = true;
			pieceToMove =     PIECEFILERANKCAPTURESQUARE.group(1);
			fileToMove =      PIECEFILERANKCAPTURESQUARE.group(2);
			rankToMove =      PIECEFILERANKCAPTURESQUARE.group(3);
			fileDestination = PIECEFILERANKCAPTURESQUARE.group(4);
			rankDestination = PIECEFILERANKCAPTURESQUARE.group(5);
			label = "PIECEFILERANKCAPTURESQUARE";
		}
		else if(SQUAREPROMOTION.find())
		{
			valid = true;
			promotion = true;
			pieceToMove = "P";
			fileDestination = SQUAREPROMOTION.group(1);
			rankDestination = SQUAREPROMOTION.group(2);
			promoteTo = SQUAREPROMOTION.group(3);
			label = "SQUAREPROMOTION";
		}
		else if(FILEPROMOTION.find())
		{
			valid = true;
			promotion = true;
			pieceToMove = "P";
			fileToMove =      FILEPROMOTION.group(1);
			fileDestination = FILEPROMOTION.group(2);
			rankDestination = FILEPROMOTION.group(3);
			promoteTo =       FILEPROMOTION.group(4);
			label = "FILEPROMOTION";
		}
		else if (FILECAPTURESQUAREPROMOTION.find())
		{
			valid = true;
			promotion = true;
			pieceToMove = "P";
			fileToMove =      FILECAPTURESQUAREPROMOTION.group(1);
			fileDestination = FILECAPTURESQUAREPROMOTION.group(2);
			rankDestination = FILECAPTURESQUAREPROMOTION.group(3);
			promoteTo =       FILECAPTURESQUAREPROMOTION.group(4);
			label = "FILEPROMOTION";
					
		}
		else if (RANKCAPTURESQUAREPROMOTION.find()) 
		{
			valid = true;
			promotion = true;
			pieceToMove = "P";
			rankToMove =      RANKCAPTURESQUAREPROMOTION.group(1);
			fileDestination = RANKCAPTURESQUAREPROMOTION.group(2);
			rankDestination = RANKCAPTURESQUAREPROMOTION.group(3);
			promoteTo =       RANKCAPTURESQUAREPROMOTION.group(4);
			label = "FILEPROMOTION";

		}
		else if (FILERANKCAPTURESQUAREPROMOTION.find()) 
		{
			valid = true;
			promotion = true;
			pieceToMove = "P";
			fileToMove =      FILERANKCAPTURESQUAREPROMOTION.group(1);
			rankToMove =      FILERANKCAPTURESQUAREPROMOTION.group(2);
			fileDestination = FILERANKCAPTURESQUAREPROMOTION.group(3);
			rankDestination = FILERANKCAPTURESQUAREPROMOTION.group(4);
			promoteTo =       FILERANKCAPTURESQUAREPROMOTION.group(5);
			label = "FILEPROMOTION";

		}
		else if (CASTLEKS.find())
		{
			valid = true;
			castleKS = true;
			label = "CASTLEKS";
		}
		else if (CASTLEQS.find())
		{
			valid = true;
			castleQS = true;
			label = "CASTLEQS";
		}
		else if (RESULT.find())
		{
			endGameMarker = true;
			label = "RESULT";
		}
	}

	public boolean isEndGameMarker()
	{
		return endGameMarker;
	}
	public boolean isCastleKS()
	{
		return castleKS;
	}
	public boolean isCastleQS()
	{
		return castleQS;
	}
	public boolean isPromotion()
	{
		return promotion;
	}
	public String toString()
	{
		return this.notation;
	}



	// regular expressions
	private final String NUM = "(?:(?:\\d+\\s?\\.\\s?)|\\.?)";
	private final String END = "[\\+\\?!#]*";

        //these are for castling	
	private final String CASTLEKS    = String.format("^%s?O-O%s$",NUM,END);
	private final String CASTLEQS   =  String.format("^%s?O-O-O%s$",NUM,END);
	// these are for moves to empty squares
	private final String RANK = "([1-8])";
	private final String FILE = "([a-h])";
	private final String PIECE = "([KQNBRS])";
	private final String SQUARE = "(?:"+FILE+RANK+")";
	private final String PIECEFILESQUARE = String.format("^%s?%s%s%s%s$",NUM,PIECE,FILE,SQUARE,END);
	private final String PIECEFILE = String.format("^%s?%s%s%s$",NUM,PIECE,FILE,END);
	private final String PIECERANK = String.format("^%s?%s%s%s$",NUM,PIECE,RANK,END);
	private final String PIECERANKSQUARE = String.format("^%s?%s%s%s%s$",NUM,PIECE,RANK,SQUARE,END);
	private final String PIECESQUARE = String.format("^%s?%s%s%s$",NUM,PIECE,SQUARE,END);
	private final String FILESQUARE = String.format("^%s?%s%s%s$",NUM,FILE,SQUARE,END);
	private final String RANKSQUARE = String.format("^%s?%s%s%s$",NUM,RANK,SQUARE,END);
	private final String PAWNSQUARE = String.format("^%s?%s%s$",NUM,SQUARE,END);

	//these are for captures
	private final String PIECECAPTURESQUARE = String.format("^%s?%sx%s%s$",NUM,PIECE,SQUARE,END);
	private final String PIECEFILECAPTURESQUARE = String.format("^%s?%s%sx%s%s$",NUM,PIECE,FILE,SQUARE,END);
	private final String PIECERANKCAPTURESQUARE = String.format("^%s?%s%sx%s%s$",NUM,PIECE,RANK,SQUARE,END);
	private final String PIECEFILERANKCAPTURESQUARE = String.format("^%s?%s%s%sx%s%s$",NUM,PIECE,FILE,RANK,SQUARE,END);
	private final String RANKCAPTURESQUARE= String.format("^%s?%sx%s%s$",NUM,RANK,SQUARE,END);
	private final String FILECAPTURESQUARE= String.format("^%s?%sx%s%s$",NUM,FILE,SQUARE,END);
	private final String SQUAREPROMOTION= String.format("^%s?%s=%s%s$",NUM,SQUARE,PIECE,END);
	private final String FILEPROMOTION= String.format("^%s?%s=%s%s$",NUM,FILE,PIECE,END);
	private final String FILECAPTURESQUAREPROMOTION =
		             String.format("^%s?%sx%s=%s%s$",NUM,FILE,SQUARE,PIECE,END);
	private final String RANKCAPTURESQUAREPROMOTION =
		             String.format("^%s?%sx%s=%s%s$",NUM,RANK,SQUARE,PIECE,END);
	private final String FILERANKCAPTURESQUAREPROMOTION =
		             String.format("^%s?%s%sx%s=%s%s$",NUM,FILE,RANK,SQUARE,PIECE,END);


	private final String RESULT = "(^1-0$)|(^0-1$)|(^1/2-1/2$)";
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

	private final Pattern FILECAPTURESQUAREPROMOTIONRegex = Pattern.compile(FILECAPTURESQUAREPROMOTION);
	private final Pattern RANKCAPTURESQUAREPROMOTIONRegex = Pattern.compile(RANKCAPTURESQUAREPROMOTION);
	private final Pattern FILERANKCAPTURESQUAREPROMOTIONRegex = Pattern.compile(FILERANKCAPTURESQUAREPROMOTION);
		             
}
