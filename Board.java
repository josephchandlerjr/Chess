

/**
 * represents a chess game board
 */
public class Board
{
	private Square[][] board;

	public Board()
	{
		board = new Square[8][8];
	}

	public Square getSquare(int row, int col)
	{
		return board[row][col];
	}

	/** gets a 2D array representing board
	 * @return String array representing pieces on the board
	 */
	public String[][] toStringArray()
	{
		String[][] res = new String[board.length][board[0].length];
		for (int r=0; r < board.length;r++)
		{
			for (int c=0; c < board[0].length; c++)
			{
				res[r][c] = board[r][c].toString();
			}
		} 
		return res;
	}

	 /** returns string representation of board
	 * @return string representing current state of board
	 */
	public String toString()
	{
		String[][] asStringArray = toStringArray();

		return toString(asStringArray);
	}

	/**
	 * returns string representation of board given
	 * is static method to be used by any 2D string array
	 * @param board a 2D array of String elements
	 * @return string representing current state of board
	 */
	public String toString(String[][] board)
	{
		String horizontalLine = "-- -- -- -- -- -- -- --";
		String res = horizontalLine + "\n";
		for (int r=0; r < board.length;r++)
		{
			res = res + "|";
			for (int c=0; c < board[0].length; c++)
			{
				if(board[r][c] == null) 
				{ 
					res = res + "  |";
				}
				else 
				{ 
					res = res + board[r][c];
					res = res + "|";
				}
			}
			res = res + "\n" + horizontalLine + "\n";
		}
		return res;
	}

        /**
	 * display string representation of board
	 */
	public void display()
	{
		System.out.print(this);

	}

	 /**
	  * checks if a row,column combination is off of the 8x8 board
	  * @return if row,column combination if off board else false
	  */
	 public static boolean isOffBoard(int row, int col)
	 {
		 return row > 7 || row < 0 || col > 7 || row < 0;
	 }

	/**
	 * return opposite color
	 * @param myColor can be String BLACK or WHITE
	 * @return if WHITE is give as param return BLACK else WHITE
	 */
	public String otherColor(String myColor)
	{
		if (myColor == "WHITE"){ return "BLACK";}
		return "WHITE";
	} 

	/**
	 * tells you if a square is occupied by your opponents color
	 * @param s Square instance you are inquiring about
	 * @param myColor a string either "BLACK" or "WHITE" which is your color
	 * @return true if square given has an opponents piece on it else false
	 */
	public boolean isOccupiedByOpponent(Square s, String myColor)
	{
		String opponentsColor = otherColor(myColor);
		return s.isOccupied() && s.getPiece().getColor().equals(opponentsColor);
	}

	/**
	 * tells yoiu if a square is occupied by one of your pieces 
	 * @param s Square instance you are inquiring about
	 * @param myColor a string either "BLACK" or "WHITE" which is your color
	 * @return true if square given has one of your pieces on it else false
	 */
	public boolean isOccupiedByPieceOfSameColor(Square s, String myColor)
	{
		return s.isOccupied() && s.getPiece().getColor().equals(myColor);

	}

}
