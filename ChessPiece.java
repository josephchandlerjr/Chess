//


public class ChessPiece
{
	//instance variables
	private String color; 
	private String strRep;

        /** initializes chess piece with row, column, color and string representation
	 * @param row row number 
	 * @param col column number
	 * @param color set to 0 for white and 1 for black
	 * @param rep string representation to be used
	 */
	public ChessPiece(String color, String rep)
	{
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

