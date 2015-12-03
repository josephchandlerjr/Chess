import java.util.ArrayList;
import java.util.Arrays;

/** 
 * represents a chess game
 * board is 2D array of  objects
*/ 
public class Game
{
	//instance variables
	Board board;
	Score scoreSheet;
	// intial locations of pieces involved in castling
	Square initWK;
	Square initBK;
	Square initWKR;
	Square initWQR;
	Square initBKR;
	Square initBQR;
	
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
		initBQR = board.getSquare(0,0);
		board.setPiece(0, 7, new Rook("BLACK"));
		initBKR = board.getSquare(0,7);
		board.setPiece(7, 0, new Rook("WHITE"));
		initWQR = board.getSquare(7,0);
		board.setPiece(7, 7, new Rook("WHITE"));
		initWKR = board.getSquare(7,7);

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
		initBK = board.getSquare(0,4);
		board.setPiece(7, 4, new King("WHITE"));	
		initWK = board.getSquare(7,4);
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

                
		if (isValidMove(from,to)) 
		{
			ChessPiece piece = from.getPiece();
			board.setPiece(to.getRow(),to.getCol(), piece);
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
		 if (from == to){ return false;}
	        	
		 if (!from.isOccupied()) // can't move a piece that isn't there
		 { return false;} 

		 if (board.isOccupiedByPieceOfSameColor(to, from.getPiece().getColor()))
		 { return false;}

		 ChessPiece p = from.getPiece();
		 if (p instanceof Pawn){ return isValidPawnMove(from, to);}
		 if (p instanceof Rook){ return isValidRookMove(from, to);}
		 if (p instanceof Knight){ return isValidKnightMove(from, to);}
		 if (p instanceof Bishop){ return isValidBishopMove(from, to);}
		 if (p instanceof Queen){ return isValidQueenMove(from, to);}
		 if (p instanceof King){ return isValidKingMove(from, to, false);}
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
         public boolean isValidKnightMove(Square from, Square to)
	 { 
		 int fromRow = from.getRow();
		 int fromCol = from.getCol();
		 int toRow = to.getRow();
		 int toCol = to.getCol();

		 int rowDiff = Math.abs(toRow - fromRow);
		 int colDiff = Math.abs(toCol - fromCol);

		 if (rowDiff == 2 && colDiff == 1) { return true;}
		 if (rowDiff == 1 && colDiff == 2) { return true;}
		 
		 return false;
		 
	 }
	 public boolean isValidBishopMove(Square from, Square to)
	 { 
		 ChessPiece piece= from.getPiece();
		 String myColor = piece.getColor();
		 if (!board.areOnSameDiagonal(from,to)) { return false;}
		  
		 if (board.isOccupiedByPieceOfSameColor(to, myColor)) { return false;}
		 if (board.piecesBetween(from, to)){ return false;}
		 return true;
	 }
	 public boolean isValidQueenMove(Square from, Square to)
	 {
		 return isValidRookMove(from,to) || isValidBishopMove(from,to);
	 }
	 /**
	  * determines if a given move is a valid move for a king
	  * @param from Square you are moving from
	  * @param to Square you are moving to
	  * @param ignoreCheckRule if this is true it doesn't ensure move doesn't put king in check
	  * @return true if move is valid else false;
	  */
	 public boolean isValidKingMove(Square from, Square to, boolean ignoreCheckRule)
	 {    

		 // need duplicate logic here when called directly from kingInCheck
		 if (from == to){ return false;}
	        	
		 if (!from.isOccupied()) // can't move a piece that isn't there
		 { return false;} 

		 if (board.isOccupiedByPieceOfSameColor(to, from.getPiece().getColor()))
		 { return false;}

		 //ignoreCheckRule only set to false when called from within KingInCheck to prevent recursion
		 Move move1 = new Move(from,to);
		 Move move2 = new Move(null, from);
		 Move[] moves = {move1, move2};
		 Board boardAfterMove = altBoard(moves);
		 if (!ignoreCheckRule && kingInCheck(to,boardAfterMove)) 
		 { 
			 return false;} 
		 
		 int fromRow = from.getRow();
		 int fromCol = from.getCol();
		 int toRow = to.getRow();
		 int toCol = to.getCol();

		 int colDiff = toCol - fromCol;
		 int rowDiff = toRow - fromRow;

		 
		 
		 return Math.abs(colDiff) <= 1 && Math.abs(rowDiff) <= 1; 
		 
		 
	 }
	 /** tests if king is in check, if explicit board given will use it
	  * @param kingsLoc kings Square, if board given will get Square of same row/col on it instead implicitly
	  * @param board if Board object given will use it instead of game board, if null will ignore param
	  * @return true if king is on kingsLoc is in check else false 
	  */
	 public boolean kingInCheck(Square kingsLoc, Board board)
	 {

		 if (board == null){ board = this.board;}
		 Square kingsLocation = board.getSquare(kingsLoc.getRow(),kingsLoc.getCol());
		 for (int row=0; row < 8; row++)
		 {
			 for (Square from : board.board[row])
			 {
				 if (from.isOccupied())
				 {
					 ChessPiece piece = from.getPiece();
					 if (piece instanceof King)
					 {
						 if(isValidKingMove(from,kingsLocation,true))
						 {
							 return true;
						 }

					 }
					 else if(isValidMove(from, kingsLocation))
				         {

						 return true;
					 }
				 }
			 }
		 }

		 return false;
	 }
	 /** finds out if black king is in check
	  * @return true if black king is in check else false
	  */
	 public boolean blackKingInCheck()
	 {
		 return kingInCheck(board.blackKingsSquare, null);
	 }
	 /** finds out if white king is in check
	  * @return true if white king is in check else false
	  */
	 public boolean whiteKingInCheck()
	 {
		 return kingInCheck(board.whiteKingsSquare, null);
	 }
	 /** 
	  * finds all squares that are valid moves from a given starting square
	  * @param from Square we are moving from
	  * @return ArrayList of Square elements which are valid moves from starting point
	  */
	 public ArrayList<Square> getAllMoves(Square from)
	 {
		 ArrayList<Square> result = new ArrayList<Square>();
		 for (int row=0; row < 8; row++)
		 {
			 for (Square to : board.board[row])
			 {
				 if (isValidMove(from, to))
				 {
					 result.add(to);
				 }
			 }
		 }
		 return result; 
	 }

	 /**
	  * finds out if given color is in checkmate
	  * @param color color of side we are testing
	  * @return true if given colors king is in checkmate else false
	  */
	 public boolean colorInCheckmate(String color)
	 {
		 if (color.equals("WHITE"))
		 {
			 if(!whiteKingInCheck()){return false;}
		 }
		 else
		 {
			 if(!blackKingInCheck()){return false;}
		 }
		 Square kingsLocation = board.whiteKingsSquare;
		 if (color.equals("BLACK"))
		 { kingsLocation = board.blackKingsSquare; }
		 ArrayList<Square> validMoves = getAllMoves(kingsLocation);

		 for (int row=0; row < 8; row++)
		 {
			 for (Square to : board.board[row])
			 {
				 Move move1 = new Move(kingsLocation, to);
				 Move move2 = new Move(null, kingsLocation);
				 Move[] moves = {move1, move2};
				 Board boardAfter = altBoard(moves);
				 if (isValidMove(kingsLocation, to) && !kingInCheck(to, boardAfter));
				 { return false;}
			 }
		 }
		 return true;

	 }
	 /**
	  * finds out if black king is in checkmate
	  * @return true if black king is in checkmate else false
	  */
	 public boolean blackInCheckmate()
	 {
		 return colorInCheckmate("BLACK");
	 }
	 /**
	  * finds out if white king is in checkmate
	  * @return true if white king is in checkmate else false
	  */
	 public boolean whiteInCheckmate()
	 {
		 return colorInCheckmate("WHITE");
	 }

	/**
	 * gets a copy of the current board with addition of moves given
	 * @param moves array of Moves objects, if element contains null Square reference implies to us null piece
	 * @return copy of current board object with moves applied
	 */ 
	 public Board altBoard(Move[] moves)
	 {
	         Board newBoard = board.copy();
		 for (Move move : moves)
		 { 
			 ChessPiece piece;
			 //direct access to newBoard here, maybe change later
			 if (move.from == null)
			 {
				 piece = null;
			 }
			 else
			 {
				 piece = move.from.getPiece();
			 }

			 newBoard.setPiece(move.to.getRow(),move.to.getCol(), piece);
		 } 
		 return newBoard;
	 } 
	 /**
	  * determines if white can castle king side
	  * @return true if white can castle king side else false
	  */
	 public boolean whiteCanCastleKingSide()
	 {
		 return canCastle(initWK, initWKR, "KING");

	 }
	 /**
	  * determines if black can castle king side
	  * @return true if black can castle king side else false
	  */
	 public boolean blackCanCastleKingSide()
	 {

		 return canCastle(initBK, initBKR, "KING");

	 } 

	 /**
	  * determines if white can castle queen side
	  * @return true if white can castle queen side else false
	  */
	 public boolean whiteCanCastleQueenSide()
	 {
		 return canCastle(initWK, initWQR, "QUEEN");
	 }
	 /**
	  * determines if black can castle queen side
	  * @return true if black can castle queen side else false
	  */
	 public boolean blackCanCastleQueenSide()
	 {

		 return canCastle(initBK, initBQR, "QUEEN");

	 } 
	 /**
	  * determines if given side can castle side
	  * @param initK initial Square of king
	  * @param initR initial Square of rook
	  * @return true if side can castle  side else false
	  */
	 public boolean canCastle(Square initK, Square initR, String side)
	 {
		 int kingShift = 8;
		 int rookShift = 8; 
		 if (side.equals("KING"))
		 {
			 kingShift = 2;
			 rookShift = -2;
		 }
		 else if (side.equals("QUEEN"))
		 {
			 kingShift = -2;
			 rookShift = 3;
		 }
		 //rook and king in original positions?
		 if (scoreSheet.contains(initR) || scoreSheet.contains(initK))
		 { return false;}
		 //no pieces between
		 if (board.piecesBetween(initR,initK))
		 { return false;}

		 //does not put king in check
		 Square newKingSqr = board.getSquare(initK.getRow(), initK.getCol() + kingShift);
		 Square newRookSqr = board.getSquare(initR.getRow(), initR.getCol() + rookShift);

		 Move moveKing = new Move(initK, newKingSqr);
                 Move makeInitKingNull = new Move(null, initK);
		 Move moveRook = new Move(initR,newRookSqr);
		 Move makeInitRookNull = new Move(null, initR);
		 Move[] moves = {moveKing,makeInitKingNull,moveRook,makeInitRookNull};

		 Board newBoard = altBoard(moves);

		 if (kingInCheck(newKingSqr, newBoard))
		 { return false;}


		 return true;

	 } 

	 
}
