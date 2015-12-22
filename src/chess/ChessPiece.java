package chess;

/**
 * represents a chess piece. This is superclass to all specific pieces: Pawn, Bishop, etc
 */
public class ChessPiece
{
	private String color; 
	private String strRep;
	private String ID;

        /** 
	 * constructor
	 * initializes chess piece with color and string representation
	 * @param color "WHITE" or "BLACK" 
	 * @param rep string representation to be used
	 */
	public ChessPiece(String color, String rep)
	{
		if (color.equals("BLACK") && color.equals("WHITE"))
		{
			System.out.printf("%s is an invalid color\n", color);
		}
		this.color = color;
		this.ID = rep;
		strRep = color.substring(0,1) + rep;
		
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

