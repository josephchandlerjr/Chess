//


public class Rook extends ChessPiece
{
	public Rook(int row, int col, String color)
	{
		super(row, col, color, "R");
	}
	/**
	 * determines if a move is valide
	 * @param aSquare square number attempting to move to
	 * @return true if move is successful else false
	 */
	public boolean isValidMove(int row, int col, ChessPiece[][] board)
	{
		//to be implemented
		return true;
	}
	
}

