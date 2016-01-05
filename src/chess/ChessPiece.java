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

}

