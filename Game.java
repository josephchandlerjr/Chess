

// board is 2D array, black stars on rows 0 and 1 facing "down", white starts
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
			board[1][i] = new Pawn(1, i, 1);
			board[6][i] = new Pawn(6, i, 0);
		}
	        	
		//rooks
		board[0][0] = new Rook(0,0,1);
		board[0][7] = new Rook(0,7,1);
		board[7][0] = new Rook(7,0,0);
		board[7][7] = new Rook(7,7,0);

		//knights
		board[0][1] = new Knight(0,1,1);
		board[0][6] = new Knight(0,6,1);
		board[7][1] = new Knight(7,1,0);
		board[7][6] = new Knight(7,6,0);

		//Bishops
		board[0][2] = new Bishop(0,2,1);
		board[0][5] = new Bishop(0,5,1);
		board[7][2] = new Bishop(7,2,0);
		board[7][5] = new Bishop(7,5,0);

		//Queens
		board[0][3] = new Queen(0,3,1);
		board[7][3] = new Queen(7,3,0);

		//Kings
		board[0][4] = new King(0,4,1);
		board[7][4] = new King(7,4,0);	
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
}
