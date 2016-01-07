package chess;

/**
 * represents a chess piece. This is superclass to all specific pieces: Pawn, Bishop, etc
 */
public class ChessPiece
{
	private String color; 
	private String strRep;
	private String ID;
	private String imageLocation;


	public static final ChessPiece WHITEPAWN = new Pawn("WHITE");
	public static final ChessPiece BLACKPAWN = new Pawn("BLACK");
	public static final ChessPiece WHITEROOK= new Rook("WHITE");
	public static final ChessPiece BLACKROOK= new Rook("BLACK");
	public static final ChessPiece WHITEKNIGHT= new Knight("WHITE");
	public static final ChessPiece BLACKKNIGHT= new Knight("BLACK");

        /** 
	 * constructor
	 * initializes chess piece with color and string representation
	 * @param color "WHITE" or "BLACK" 
	 * @param rep string representation to be used
	 */
	public ChessPiece(String color, String rep)
	{
		this.color = color;
		this.ID = rep;
		strRep = color.substring(0,1) + rep;


		String imageColor = "";
		String pieceName = "";
		String base = ".\\src\\chess\\resources\\";	
		if(color.equals("WHITE")){
			imageColor = "White";
		}
		else {
			imageColor = "Black";
		}
		
		if(rep.equals("P")){
			pieceName = "Pawn";
		}
		else if (rep.equals("R")){
			pieceName = "Rook";
		}
		else if (rep.equals("N")){
			pieceName = "Knight";
		}
		else if (rep.equals("B")){
			pieceName = "Bishop";
		}
		else if (rep.equals("Q")){
			pieceName = "Queen";
		}
		else if (rep.equals("K")){
			pieceName = "King";
		}
		imageLocation = base + imageColor + pieceName + ".png";
		
	}
	public int getDirection(){
	       return 0; // only here so pawns can be cast as ChessPiece and still call this method
	}	       
	public String getImageLocation(){
		return imageLocation;
	}
        /**
	 * gets the string representation of piece
	 * @return string representation of piece
	 */
	public String toString()
	{
		return strRep;
	}

	/**
	 * gets color of piece
	 * @return the color of piece, "WHITE" or "BLACK"
	 */
	public String getColor()
	{
		return color;
	}
	/**
	 * gets string id for given piece
	 * @return ID instance variable
	 */
	public String getID()
	{
		return this.ID;
	}

	/**
	 * tells you if a piece is a pawn or not
	 */
	public static boolean isPawn(ChessPiece piece){
		return piece instanceof Pawn;
	}
	public static boolean isRook(ChessPiece piece){
		return piece instanceof Rook;
	}
	public static boolean isKnight(ChessPiece piece){
		return piece instanceof Knight;
	}
}


/** 
 * represents a pawn chess piece
*/
class Pawn extends ChessPiece
{
	private int direction;

	/**
	 * constructor
	 * @param color either string "BLACK" or "WHITE"
	 */
	public Pawn(String color)
	{
		super(color, "P");
		if (color.equals("BLACK"))
		{
			direction = 1;
		}
		else 
		{
			direction = -1;
		}
	}
	/** 
	 * gets direction pawn moves
	 * @return 1 if pawn moves toward higher numbered rows or -1 if moving opposite direction
	 */
	public int getDirection()
	{
		return direction;
	}
	
}


/** 
 * represents a rook chess piece
 */
class Rook extends ChessPiece
{
	/**
	 * constructor
	 * @param color either string "BLACK" or "WHITE"
	 */
	public Rook(String color)
	{
		super(color, "R");
	}
}


/**
 * represents the knight chess piece
 */
class Knight extends ChessPiece
{
	/**
	 * constructor
	 * @param color either string "BLACK" or "WHITE"
	 */
	public Knight(String color)
	{
		super(color, "N");
	}
	
}

