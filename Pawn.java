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
	public boolean validMove(int row, int col, ChessPiece[][] board)
	{
		// first check if move is off the 8x8 board
		if (isOffBoard(row, col)){ return false;}

		// now make sure white pieces are moving toward lower columns and black toward higher columns
		// also just make sure piece is only asking to be moved on column forward
		String color = getColor();
		int colDiff = col - getCol();
		if (colDiff == 1)
		{
			if (color.equals("WHITE")){ return false;}

		}
		else if (colDiff == -1)
		{
			if (color.equals("BLACK")){ return false;}
		}
		else 
		{ 
			return false;
		}

		// we know it's heading the right direction column-wise but is it straight ahead or diagonal
		int rowDiff = Math.abs(row - getRow());
		if (rowDiff == 1)
		{
			if (isOccupied(board[row][col]) && board[row][col].getColor() == otherColor())
			{ return true;}
			else
			{ return false;}
		}
		else if (rowDiff == 0)
		{
			if (isOccupied(board[row][col]))
			{ return false;}
			else
			{ return true;}
		}
		else 
		{
			return false;
		}


	}
	
	
}

