//


public class ChessPiece
{
	//instance variables
	private int row;
	private int col;
	private int color; //0=white, 1=black
	private String strRep;

        /** initializes chess piece with row, column, color and string representation
	 * @param row row number 
	 * @param col column number
	 * @param color set to 0 for white and 1 for black
	 * @param rep string representation to be used
	 */
	public ChessPiece(int row, int col, int color, String rep)
	{
		this.row = row;
		this.col = col;
		this.color = color;
		if (color == 0){ rep = rep + "W";}
		if (color == 1){ rep = rep + "B";}
		strRep = rep;
	}
	
        /**
	 * gets the string representation of piece
	 * @return string representation of piece
	 */
	public String getStrRep()
	{
		return strRep;
	}


	/**
	 * gets row of piece
	 * @return the row number of piece
	 */
	public int getRow()
	{
		return row;
	}

	/**
	 * gets column of piece
	 * @return the column number of piece
	 */
	public int getCol()
	{
		return col;
	}
	
	/**
	 * gets color of piece
	 * @return the color of piece, 0=white, 1=black
	 */
	public int getColor()
	{
		return color;
	}
	/**
	 * sets piece to row,column if it's a valid move and returns true
	 * else returns false
	 * @param row row number
	 * @param col column number
	 * @return true if move was made, false if not because is invalid move
	 */
	public boolean setSquare(int row, int col)
	{
		if (validMove(row, col))
		{
			this.row = row;
			this.col = col;
			return true;
		}
		else { return false;}

	}
	/**
	 * determines if a move is valid
	 * @param row row number attempting to move to
	 * @param col column number attempting to move to
	 * @return true if move is successful else false
	 */
	public boolean validMove(int row, int col)
	{
		//to be implemented in subclasses, here just return false and print
		//warning in case something goes awry
		//
		System.out.println("Error: validMove() should be called only from subclass of ChessPiece");
		return false;	
	}
}

