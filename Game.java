

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
				{ result[r][c] = board[r][c].getStrRep();}
			}

		}
		return result;
		
	}
        /**
	 * display string representation of board
	 */
	public void display()
	{
		String horizontalLine = "\n -- -- -- -- -- -- -- -- \n";
		System.out.print(horizontalLine);
		for (int r=0; r < board.length;r++)
		{
			System.out.print("|");
			for (int c=0; c < board[0].length; c++)
			{
				if(board[r][c] == null) 
				{ 
					System.out.print("  |");
				}
				else 
				{ 
					System.out.print(board[r][c].getStrRep());
					System.out.print("|");
				}
			}
			System.out.print(horizontalLine);
		}
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
