/** 
 * represents a chess game
 * board is 2D array of  objects
*/ 
public class Game
{
	//instance variables
	Square[][] board;
	Score scoreSheet;
	
	public Game()
	{
		board = new Square[8][8];
		scoreSheet = new Score();
		initializeBoard();
		
	}
	/**
	 * creates all ChessPiece objects and inserts them in board array
	 */
	private void initializeBoard()
	{
		String squareColor = "WHITE";
		for (int r=0; r < 8; r++)
		{
			for (int c=0; c < 8; c++)
			{
				board[r][c] = new Square(squareColor, r, c);
				squareColor = Game.OpponentsColor(squareColor);
			}
		}
		//place pawns
		for (int i=0; i < 8; i++)
		{
			board[1][i].setPiece(new Pawn("BLACK"));
			board[6][i].setPiece(new Pawn("WHITE"));
		}
	        	
		//rooks
		board[0][0].setPiece(new Rook("BLACK"));
		board[0][7].setPiece(new Rook("BLACK"));
		board[7][0].setPiece(new Rook("WHITE"));
		board[7][7].setPiece(new Rook("WHITE"));

		//knights
		board[0][1].setPiece(new Knight("BLACK"));
		board[0][6].setPiece(new Knight("BLACK"));
		board[7][1].setPiece(new Knight("WHITE"));
		board[7][6].setPiece(new Knight("WHITE"));

		//Bishops
		board[0][2].setPiece(new Bishop("BLACK"));
		board[0][5].setPiece(new Bishop("BLACK"));
		board[7][2].setPiece(new Bishop("WHITE"));
		board[7][5].setPiece(new Bishop("WHITE"));

		//Queens
		board[0][3].setPiece(new Queen("BLACK"));
		board[7][3].setPiece(new Queen("WHITE"));

		//Kings
		board[0][4].setPiece(new King("BLACK"));
		board[7][4].setPiece(new King("WHITE"));	
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
		String[][] asStringArray = this.toStringArray();

		return Game.toString(asStringArray);
	}
	/**
	 * returns string representation of board given
	 * is static method to be used by any 2D string array
	 * @param board a 2D array of String elements
	 * @return string representing current state of board
	 */
	public static String toString(String[][] board)
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
	/**moves a piece
	 * @param curRow current row of piece to be moved
	 * @param curCol current column of piece to be moved
	 * @param newRow row of new position
	 * @param newCol column of new position
	 * @return true if move is made, false if not a valid move
	 */
	public boolean movePiece(int curRow, int curCol, int newRow, int newCol)
	{ 
		if (isOffBoard(newRow,newCol))
		{ return false;}
		Square fromSquare = board[curRow][curCol];
		Square toSquare = board[newRow][newCol];
		
		return movePiece(fromSquare,toSquare);
		
	}
	/**moves a piece
	 * @param from  piece to be moved is on
	 * @param to  piece is to be moved to 
	 * @return true if move is made, false if not a valid move
	 */
	public boolean movePiece(Square from, Square to)
	{

                if (from == to){ return false;}
	        	
		if (!from.isOccupied()) // can't move a piece that isn't there
		{ return false;} 
		if (isValidMove(from,to)) 
		{
			to.setPiece(from.getPiece());
			from.setPiece(null);
			scoreSheet.addMove(from,to);
			return true;
			
		}
		return false;
	}
	
	/**
	 * checks if a move is valid
	 * @param from  piece to be moved is on
	 * @param to  piece will be moved to
	 * @return true if move is valid else false
	 */
	 public boolean isValidMove(Square from, Square to)
	 {
		 ChessPiece p = from.getPiece();
		 if (p instanceof Pawn){ return isValidPawnMove(from, to);}
		 if (p instanceof Rook){ return isValidRookMove(from, to);}
		 if (p instanceof Knight){ return isValidKnightMove(from, to);}
		 if (p instanceof Bishop){ return isValidBishopMove(from, to);}
		 if (p instanceof Queen){ return isValidQueenMove(from, to);}
		 if (p instanceof King){ return isValidKingMove(from, to);}
		 return false;
	 }
	 public boolean isValidPawnMove(Square from, Square to)
	 { 
		 int fromRow = from.getRow();
		 int fromCol = from.getCol();
		 int toRow = to.getRow();
		 int toCol = to.getCol();

		 Pawn piece= (Pawn)(from.getPiece());
		 String myColor = piece.getColor();
		 int direction = piece.getDirection();


		 if (fromCol == toCol         &&
	         fromRow + direction == toRow &&
		 !to.isOccupied()                       )// advance one row
		 { 
			 return true;
		 }
		 else if(fromCol-1 == toCol           &&
		         fromRow + direction == toRow &&
			 isOccupiedByOpponent(to, myColor)       ) // capture diagnonal
		 {
			 return true;
		 }
		 else if(fromCol+1 == toCol           &&
		         fromRow + direction == toRow &&
			 isOccupiedByOpponent(to, myColor)       ) // capture diagnonal // capture diagonal
		 {
			 return true;
		 }
		 else if(fromCol == toCol                 &&      // advance two rows, first move only
	                 fromRow + 2 * direction == toRow &&
		         !to.isOccupied()                 &&
		       	 !scoreSheet.contains(from)         )
		 {
			 return true;
		 }
		 return false;
	 }
	 public boolean isValidRookMove(Square from, Square to)
	 {       
		 int fromRow = from.getRow();
		 int fromCol = from.getCol();
		 int toRow = to.getRow();
		 int toCol = to.getCol();
		 ChessPiece piece= from.getPiece();
		 String myColor = piece.getColor();
		 int step;
		 
		 if (isOccupiedByPieceOfSameColor(to, myColor)) { return false;}

		 if(fromRow == toRow)//left-to-right
		 {
			 // determine what direction we are heading
			 if (toCol > fromCol){step = 1;}
			 else{ step = -1;}

			 // check if pieces lie in between squares
			 int col = fromCol + step;
			 int row = toRow; 
			 while (col != toCol)
			 {	
				 if (board[row][col].isOccupied())
				 {
					 return false;
				 }
				 col = col + step;
			 }
			 return true;
			 
		 }
		 else if(fromCol == toCol)//up-and-down
		 {
			 if (toRow > fromRow){ step = 1;}
			 else { step = -1;}

			 // check if pieces lie in between squares
			 int row = fromRow + step;
			 int col = toCol;
			 while (row != toRow)
			 {	
				 if (board[row][col].isOccupied()){return false;}
				 row = row + step;
			 }
			 return true;
			 
		 }
		 return false;
	 }
         public boolean isValidKnightMove(Square from, Square to){ return true;}
	 public boolean isValidBishopMove(Square from, Square to){ return true;}
	 public boolean isValidQueenMove(Square from, Square to){ return true;}
	 public boolean isValidKingMove(Square from, Square to){ return true;}

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
	public static String OpponentsColor(String myColor)
	{
		if (myColor == "WHITE"){ return "BLACK";}
		return "WHITE";
	} 

	public boolean isOccupiedByOpponent(Square s, String myColor)
	{
		String opponentsColor = Game.OpponentsColor(myColor);
		return s.isOccupied() && s.getPiece().getColor().equals(opponentsColor);
	}

	public boolean isOccupiedByPieceOfSameColor(Square s, String myColor)
	{
		return s.isOccupied() && s.getPiece().getColor().equals(myColor);

	}
}
