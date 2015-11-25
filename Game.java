

// board is 2D array, black starts on rows 0 and 1 facing "down", white starts
// on rows 6 and 7 facing "up" 
public class Game
{
	//instance variables
	ChessPiece[][] board;
	
	public Game()
	{
		board = new ChessPiece[8][8];
		initializeBoard();
		
	}
	/**
	 * creates all ChessPiece objects and inserts them in board array
	 */
	private void initializeBoard()
	{
		//place pawns
		for (int i=0; i < 8; i++)
		{
			board[1][i] = new Pawn(1, i, "BLACK");
			board[6][i] = new Pawn(6, i, "WHITE");
		}
	        	
		//rooks
		board[0][0] = new Rook(0,0,"BLACK");
		board[0][7] = new Rook(0,7,"BLACK");
		board[7][0] = new Rook(7,0,"WHITE");
		board[7][7] = new Rook(7,7,"WHITE");

		//knights
		board[0][1] = new Knight(0,1,"BLACK");
		board[0][6] = new Knight(0,6,"BLACK");
		board[7][1] = new Knight(7,1,"WHITE");
		board[7][6] = new Knight(7,6,"WHITE");

		//Bishops
		board[0][2] = new Bishop(0,2,"BLACK");
		board[0][5] = new Bishop(0,5,"BLACK");
		board[7][2] = new Bishop(7,2,"WHITE");
		board[7][5] = new Bishop(7,5,"WHITE");

		//Queens
		board[0][3] = new Queen(0,3,"BLACK");
		board[7][3] = new Queen(7,3,"WHITE");

		//Kings
		board[0][4] = new King(0,4,"BLACK");
		board[7][4] = new King(7,4,"WHITE");	
	}
	/**
	 * gets a 2D array of string represenation of current board array
	 * @return 2D array of strings representing current board 
	 */
	public String[][] getBoardStrRep()
	{
		String[][] result = new String[8][8];
		for (int r=0; r < board.length; r++)
		{
			for (int c=0; c < board[0].length; c++)
			{
				if (board[r][c] != null)
				{ result[r][c] = board[r][c].toString();}
			}

		}
		return result;
		
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
				if (board[r][c] == null)
				{
					res[r][c] = null;
				}
				else
				{
					res[r][c] = board[r][c].toString();
				}
			}
		} 
		return res;
	}
	 /** returns string representation of board
	 * @return string representing current state of board
	 */
	public String toString()
	{
		String[][] asStringArray = this.toStringArray();

		return toString(this.board);
	}
	/**
	 * returns string representation of board given
	 * @param board a 2D array of ChessPiece objects
	 * @return string representing current state of board
	 */
	public String toString(ChessPiece[][] board)
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
					res = res + board[r][c].toString();
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
	/**moves a piece
	 * @param curRow current row of piece to be moved
	 * @param curCol current column of piece to be moved
	 * @param newRow row of new position
	 * @param newCol column of new position
	 * @return true if move is made, false if not a valid move
	 */
	public boolean movePiece(int curRow, int curCol, int newRow, int newCol)
	{
		ChessPiece piece = board[curRow][curCol];
		if (piece == null) { return false;} // can't move a piece that isn't there
		if (true) //later will call obj.isValidMove() here
		{
			if (curRow == newRow && curCol == newCol)
			{ return false;}
			board[newRow][newCol] = board[curRow][curCol];
			board[curRow][curCol] = null;
			board[newRow][newCol].move(newRow,newCol);
			
		}
		return false;
	}
}
