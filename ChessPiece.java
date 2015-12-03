/**
 * represents a chess piece. This is superclass to all specific pieces: Pawn, Bishop, etc
 */
public class ChessPiece
{
	//instance variables
	private String color; 
	private String strRep;

        /** initializes chess piece with row, column, color and string representation
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
	 * @return the color of piece, 0=white, 1=black
	 */
	public String getColor()
	{
		return color;
	}

}

