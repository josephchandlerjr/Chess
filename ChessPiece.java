//


public class ChessPiece
{
	//instance variables
	private int row;
	private int col;
	private String color; 
	private String strRep;

        /** initializes chess piece with row, column, color and string representation
	 * @param row row number 
	 * @param col column number
	 * @param color set to 0 for white and 1 for black
	 * @param rep string representation to be used
	 */
	public ChessPiece(int row, int col, String color, String rep)
	{
		this.row = row;
		this.col = col;
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
	 * moves piece to new col, row
	 * @param row new row
	 * @param col new column
	 */
	public void move(int row, int col)
	{
		setRow(row);
		setCol(col);
	}
	
	/**
	 * sets row of piece
	 * @param row row to set piece to
	 */
	public void setRow(int row)
	{
		this.row = row;
	}

	/**
	 * sets column of piece
	 * @param col the column to set piece to
	 */
	public void setCol(int col)
	{
		this.col = col;
	}/**
	 * gets color of piece
	 * @return the color of piece, 0=white, 1=black
	 */
	public String getColor()
	{
		return color;
	}
	
	/**
	 * determines if a move is valid
	 * @param row row number attempting to move to
	 * @param col column number attempting to move to
	 * @return true if move is successful else false
	 */
	public boolean isValidMove(int row, int col, ChessPiece[][] board)
	{
		//to be implemented in subclasses, here just return false and print
		//warning in case something goes awry
		//
		System.out.println("Error: validMove() should be called only from subclass of ChessPiece");
		return false;	
	}

	/**
	 * tests if a given position would be of the board
	 * @return true if off board else false
	 */
	public boolean isOffBoard(int row, int col)
	{
		return row < 0 || row > 7 || col < 0 || col > 7;
	}
	/**
	 * checks if given position is occupied by a piece 
	 * @param piece ChessPiece reference at given location on board
	 * @return true if parameter given is not a pointer to null
	 */
	public boolean isOccupied(ChessPiece piece)
	{
		return piece != null;
	}
	/**
	 * gets opponents color
	 * @return BLACK if this piece is white and WHITE if this piece is black
	 */
	public String otherColor()
	{
		return Game.OpponentsColor(getColor());
	}

}

