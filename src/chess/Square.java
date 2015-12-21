package chess;

/** 
 * represents a square on the chess board
 */
public class Square
{
	private String color; //the string BLACK or WHITE
	private int row;
	private int col;
	private ChessPiece piece;
	private ChessPiece previousPiece;
	/**
	 * create new square
	 * @param color color of square
	 * @param row row square resides in
	 * @param col col square resides in 
	 */
	public Square(String color, int row, int col)
	{
		this.color = color;
		this.row = row;
		this.col = col;
		this.piece = null;
	}
	public ChessPiece getPreviousPiece()
	{
		return previousPiece;
	}
	/**
	 * places piece on this square
	 * @param piece ChessPiece object to put on this square
	 */
	public void setPiece(ChessPiece piece)
	{
		this.previousPiece = this.piece;
		this.piece = piece;
	}
	/**
	 * gets piece on this square
	 * @return piece on this square or null if there is none
	 */
	public ChessPiece getPiece()
	{
		return piece;
	}
	/**
	 * gets the string representation of piece on this square
	 * @return string representation of piece
	 */
	public String toString()
	{
		if (piece == null)
		{ return null;}
		return piece.toString();
	}

	/**
	 * gets row
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
	 * gets color of square
	 * @return color of square
	 */
	public String getSquareColor()
	{
		return color;
	}
	/**
	 * gets color of piece sitting on square
	 * @return color of piece on square
	 */
	public String getPieceColor()
	{
		return piece.getColor();
	}

	/**
	 * checks if given position is occupied by a piece 
	 * @param piece ChessPiece reference at given location on board
	 * @return true if parameter given is not a pointer to null
	 */
	public boolean isOccupied()
	{
		return piece != null;
	}

	/**
	 * get copy of this square
	 * @return copy of this square
	 */
	public Square copy()
	{
		Square newSquare = new Square(color, row, col);
		newSquare.setPiece(piece);
		return newSquare;
	}

	
}
