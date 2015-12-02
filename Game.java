/** 
 * represents a chess game
 * board is 2D array of  objects
*/ 
public class Game
{
	//instance variables
	Board board;
	Score scoreSheet;
	
	public Game()
	{
		board = new Board();
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
				board.setSquare(squareColor, r, c);
				squareColor = board.otherColor(squareColor);
			}
		}
		//place pawns
		for (int i=0; i < 8; i++)
		{
			board.setPiece(1, i, new Pawn("BLACK"));
			board.setPiece(6, i, new Pawn("WHITE"));
		}
	        	
		//rooks
		board.setPiece(0, 0, new Rook("BLACK"));
		board.setPiece(0, 7, new Rook("BLACK"));
		board.setPiece(7, 0, new Rook("WHITE"));
		board.setPiece(7, 7, new Rook("WHITE"));

		//knights
		board.setPiece(0, 1, new Knight("BLACK"));
		board.setPiece(0, 6, new Knight("BLACK"));
		board.setPiece(7, 1, new Knight("WHITE"));
		board.setPiece(7, 6, new Knight("WHITE"));

		//Bishops
		board.setPiece(0, 2, new Bishop("BLACK"));
		board.setPiece(0, 5, new Bishop("BLACK"));
		board.setPiece(7, 2, new Bishop("WHITE"));
		board.setPiece(7, 5, new Bishop("WHITE"));

		//Queens
		board.setPiece(0, 3, new Queen("BLACK"));
		board.setPiece(7, 3, new Queen("WHITE"));

		//Kings
		board.setPiece(0, 4, new King("BLACK"));
		board.setPiece(7, 4, new King("WHITE"));	
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
		if (board.isOffBoard(newRow,newCol))
		{ return false;}
		Square fromSquare = board.getSquare(curRow,curCol);
		Square toSquare = board.getSquare(newRow,newCol);
		
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
			 board.isOccupiedByOpponent(to, myColor)       ) // capture diagnonal
		 {
			 return true;
		 }
		 else if(fromCol+1 == toCol           &&
		         fromRow + direction == toRow &&
			 board.isOccupiedByOpponent(to, myColor)       ) // capture diagnonal // capture diagonal
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

		 ChessPiece piece= from.getPiece();
		 String myColor = piece.getColor();
		 if (!board.areOnSameColumn(from,to) && !board.areOnSameRow(from,to)){ return false;}
		  
		 if (board.isOccupiedByPieceOfSameColor(to, myColor)) { return false;}
		 if (board.piecesBetween(from, to)){ return false;}
		 return true;

	 }
         public boolean isValidKnightMove(Square from, Square to){ return true;}
	 public boolean isValidBishopMove(Square from, Square to)
	 { 
		 ChessPiece piece= from.getPiece();
		 String myColor = piece.getColor();
		 if (!board.areOnSameDiagonal(from,to)) { return false;}
		  
		 if (board.isOccupiedByPieceOfSameColor(to, myColor)) { return false;}
		 if (board.piecesBetween(from, to)){ return false;}
		 return true;
	 }
	 public boolean isValidQueenMove(Square from, Square to){ return true;}
	 public boolean isValidKingMove(Square from, Square to){ return true;}

}
