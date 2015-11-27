//

public class Pawn extends ChessPiece
{
	public Pawn(int row, int col, String color)
	{
		super(row, col, color, "P");
	}
	/**
	 * determines if a move is valid
	 * @param aSquare square number attempting to move to
	 * @return true if move is successful else false
	 */
	public boolean isValidMove(int row, int col, ChessPiece[][] board)
	{

		return true;


	}
	
	
}

