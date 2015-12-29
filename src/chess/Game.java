package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

/** 
 * represents a chess game
 * board is 2D array of  objects
*/ 
public class Game
{
	//instance variables
	private Board board;
	Score scoreSheet;
	// intial locations of pieces involved in castling
	Square initWK;
	Square initBK;
	Square initWKR;
	Square initWQR;
	Square initBKR;
	Square initBQR;
	private boolean blackCheck; //true if black king in check
	private boolean whiteCheck;
	private boolean blackCheckmate; //true if black king in checkmate
	private boolean whiteCheckmate;
	
	/**
	 * constructor
	 * creates board,scoresheet, initializes board and it's squares
	 */
	public Game()
	{
		board = new Board();
		scoreSheet = new Score();
		initializeBoard();
		board.setDirectionalVar();
	}

	public boolean isInCheck(String color)
	{	
		if(color.equals("BLACK"))
		{
			return blackCheck;
		}
		return whiteCheck;
	}

	public boolean hasWon(String color)
	{
		String opponent = board.otherColor(color);
		if (opponent.equals("BLACK"))
		{
			return blackCheckmate;
		}
		return whiteCheckmate;
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

	public Square[][] getBoard()
	{
		return board.getBoard();
	}

	/** controller for game, used by Chess object housing this game
	 * @param move Move object containing all info it needs
	 * @return true if move is executed else false
	 */
	public boolean takeAction(Move move)
	{
		if(move.isCastle())
		{
			boolean validMove = isValidCastle(move.getColor(), move.getCastleSide());
			if(!validMove){return false;}

			//ok let's go ahead and castle
			Square[] unMoveParams = castle(move.getColor(),move.getCastleSide());
			if(unMoveParams == null)
			{return false;}
			//now see if king is in check
			boolean invalid = kingInCheck(move.getColor());
			if(invalid)
			{
				Square initK = unMoveParams[0];
				Square newK =  unMoveParams[1];
				Square initR = unMoveParams[2];
				Square newR =  unMoveParams[3];
				unCastle(initK,newK,initR,newR);
				return false;
			}
		}
		else if(move.isEnPassant())
		{
			boolean validMove = isValidEnPassant(move);
                        if(!validMove){return false;}
			moveEnPassant(move);
			boolean invalid = kingInCheck(move.getColor());
			if(invalid)
			{
				unMoveEnPassant(move);
				return false;
			};
		}
		else if (move.isTwoRowPawnMove())
		{
			boolean validMove = isValidTwoRowPawnMove(move);
                        if(!validMove){return false;}
			movePawnTwoRows(move);
			boolean invalid = kingInCheck(move.getColor());
			if(invalid)
			{
				unMovePawnTwoRows(move);
				return false;
			};
		}
		else
		{
			boolean validMove = isValidMove(move);
                        if(!validMove){return false;}
			movePiece(move);
			boolean invalid = kingInCheck(move.getColor());
			if(invalid)
			{
				unMovePiece(move);
				return false;
			};
		}

		if(move.isPromotion())
		{
			int toRow = move.getTo().getRow();
			int toCol = move.getTo().getCol();
			board.setPiece(toRow,toCol,move.getPromoteTo());

		}
		
		scoreSheet.addMove(move);
		if(kingInCheck("BLACK")){blackCheck = true;}
		else                    {blackCheck = false;}
		if(kingInCheck("WHITE")){whiteCheck = true;}
		else                    {whiteCheck = false;}

		if(colorInCheckmate("BLACK")){blackCheckmate = true;}
		if(colorInCheckmate("WHITE")){whiteCheckmate = true;}

		return true;
	}
	/** determines if given player can castle on given side
	 * @param color the color of current player
	 * @param side the side she wishes to castle on
	 * @return true if is valid to castle else false
	 */
	private boolean isValidCastle(String color, String side)
	{
		 if (color.equals("WHITE"))
		 {
			 if (side.equals("KING"))
			 {
				 return whiteCanCastleKingSide();

			 }
			 else if(side.equals("QUEEN"))
			 {
				 return whiteCanCastleQueenSide();
			 }
		 }
		 else if (color.equals("BLACK"))
		 {
			 if (side.equals("KING"))
			 {
				 return blackCanCastleKingSide();
			 }
			 else if(side.equals("QUEEN"))
			 {
				 return blackCanCastleQueenSide();
			 }
		 }
		 return false;
	}
	/**moves a piece, used for 'normal' moves not en passant, two row pawn moves, or castling
	 * @param move move object
	 */
	private void movePiece(Move move)
	{
		Square from = move.getFrom();
		Square to = move.getTo();

		ChessPiece piece = from.getPiece();
		to.setPiece(piece);
		from.setPiece(null);
	}
	/** 
	 * undos a change made by movePiece method
	 * @param move Move object describing original move
	 */
	private void unMovePiece(Move move)
	{
		Square from = move.getFrom();
		Square to = move.getTo();
		from.setPiece(from.getPreviousPiece());
		to.setPiece(to.getPreviousPiece());

	}

	/**
	 * undos changes made by movePawnTwoRows method
	 * @param move Move object describing original move
	 */ 
	private void unMovePawnTwoRows(Move move)
	{
		unMovePiece(move);
	}

	/** makes two row pawn move
	 * @param move Move object describing move
	 */
	private void movePawnTwoRows(Move move)
	{
		Square from = move.getFrom();
		Square to = move.getTo();

		Pawn piece = (Pawn)(from.getPiece());
		board.setPiece(to.getRow(),to.getCol(), piece);
		from.setPiece(null);
	}
	/** 
	 * undos a change made by moveEnPassant method
	 * @param move Move object describing original move
	 */
	private void unMoveEnPassant(Move move)
	{
		Square from = move.getFrom();
		Square to = move.getTo();

		unMovePiece(move);
		Square captured = board.getSquare(from.getRow(),to.getCol());
		captured.setPiece(captured.getPreviousPiece());

	}

	/** makes en passant
	 * @param move Move object describing move
	 */
	private void moveEnPassant(Move move)
	{
		Square from = move.getFrom();
		Square to = move.getTo();
		
		Pawn piece = (Pawn)(from.getPiece());
		board.setPiece(to.getRow(),to.getCol(), piece);
		from.setPiece(null);
		Square squareToCapture = board.getSquare(from.getRow(),to.getCol());
		squareToCapture.setPiece(null);
	}
	/**
	 * determines if a two row pawn move is a valid move
	 * @param move Move object describing move
	 * @return true if is valid move else false
	 */
	private boolean isValidTwoRowPawnMove(Move move)
	{
		if(!(move.getFrom().getPiece() instanceof Pawn)){return false;}

		Square from = move.getFrom();
		Square to = move.getTo();

		Pawn piece = (Pawn)(from.getPiece());
		int direction = piece.getDirection();
		int fromRow = from.getRow();
		int fromCol = from.getCol();
		int toRow = to.getRow();
		int toCol = to.getCol();

		if(fromCol == toCol                 &&      // advance two rows, first move only
		   fromRow + 2 * direction == toRow &&
		   !to.isOccupied()                 &&
		   !to.isOccupied()                 &&
		   !board.piecesBetween(from,to)    &&
		   !scoreSheet.contains(from)         )
		 {
			 return true;
		 }
		return false;
	}

	/**
	 * determines if en passant is a valid move
	 * @param move Move object describing move
	 * @return true if is valid move else false
	 */
	private boolean isValidEnPassant(Move move)
	{
		// last move must be two square pawn move
		// last move must have ended next to from square
		// to move move be diagonal and 'behind' last move to square

		if(!(move.getFrom().getPiece() instanceof Pawn)){return false;}

		Square from = move.getFrom();
		Square to = move.getTo();

		 int fromRow = from.getRow();
		 int fromCol = from.getCol();
		 int toRow = to.getRow();
		 int toCol = to.getCol();

		 Pawn piece = (Pawn)(from.getPiece());
		 String myColor = piece.getColor();
		 int direction = piece.getDirection();

		 Move lastMove = scoreSheet.lastMove();

		 if (lastMove != null               &&
		     lastMove.isTwoRowPawnMove()    &&  //only after pawn jumps past another pawn
	             fromRow + direction == toRow   &&
	             Math.abs(toCol - fromCol) == 1 &&  // diagonal is row+direction and col+-1  
		     !to.isOccupied()               &&  // not true diagonal capture bc no piece there
		     lastMove.getTo() == board.getSquare(to.getRow() - direction, to.getCol())) //'behind' last move
		 {
			 return true;
		 }
		 return false;

	}
	/**
	 * determines if a move is a valid move
	 * @param move Move object describing move
	 * @return true if is valid move else false
	 */
	 private boolean isValidMove(Move move)
	 {
		 Square from = move.getFrom();
		 Square to = move.getTo();

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
		 if (p instanceof King){ return isValidKingMove(from, to);}
		 return false;
	 }
	/**
	 * determines if a pawn is a valid move
	 * @param from square moving from
	 * @param to square moving to
	 * @return true if is valid move else false
	 */
	 private boolean isValidPawnMove(Square from, Square to)
	 { 
		 if(!(from.getPiece() instanceof Pawn)){return false;}
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
			 board.isOccupiedByOpponent(to, myColor)       ) // capture diagnonal 
		 {
			 return true;
		 }

		 return false;
	 }
	/**
	 * determines if a rook move is a valid move
	 * @param from square moving from
	 * @param to square moving to
	 * @return true if is valid move else false
	 */
	 private boolean isValidRookMove(Square from, Square to)
	 {       

		 ChessPiece piece= from.getPiece();
		 String myColor = piece.getColor();
		 if (!board.areOnSameColumn(from,to) && !board.areOnSameRow(from,to)){ return false;}
		  
		 if (board.isOccupiedByPieceOfSameColor(to, myColor)) { return false;}
		 if (board.piecesBetween(from, to)){ return false;}
		 return true;

	 }
	/**
	 * determines if a Knight is a valid move
	 * @param from square moving from
	 * @param to square moving to
	 * @return true if is valid move else false
	 */
         private boolean isValidKnightMove(Square from, Square to)
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
	/**
	 * determines if a bishop is a valid move
	 * @param from square moving from
	 * @param to square moving to
	 * @return true if is valid move else false
	 */
	 private boolean isValidBishopMove(Square from, Square to)
	 { 
		 ChessPiece piece= from.getPiece();
		 String myColor = piece.getColor();
		 if (!board.areOnSameDiagonal(from,to)) { return false;}
		  
		 if (board.isOccupiedByPieceOfSameColor(to, myColor)) { return false;}
		 if (board.piecesBetween(from, to)){ return false;}
		 return true;
	 }

	/**
	 * determines if a queen is a valid move
	 * @param from square moving from
	 * @param to square moving to
	 * @return true if is valid move else false
	 */
	 private boolean isValidQueenMove(Square from, Square to)
	 {
		 return isValidRookMove(from,to) || isValidBishopMove(from,to);
	 }

	
	/**
	 * determines if a pawn is a valid move
	 * @param from square moving from
	 * @param to square moving to
	 * @return true if is valid move else false
	 */
	 private boolean isValidKingMove(Square from, Square to)
	 {    

		 int fromRow = from.getRow();
		 int fromCol = from.getCol();
		 int toRow = to.getRow();
		 int toCol = to.getCol();

		 int colDiff = toCol - fromCol;
		 int rowDiff = toRow - fromRow;

		 return Math.abs(colDiff) <= 1 && Math.abs(rowDiff) <= 1; 
		 
		 
	 }

	/**
	 * determines if a king is a valid move
	 * @param from square moving from
	 * @param to square moving to
	 * @return true if is valid move else false
	 */
	 private Square findKing(String color)
	 {
		 Square result = null;
		 for (Square[] row : getBoard())
		 {
			 for (Square sqr : row)
			 {
				 if (sqr.isOccupied())
				 {
					
					 ChessPiece p = sqr.getPiece();
					 if(p instanceof King && p.getColor().equals(color))
					 {
						 result = sqr;
					 }	
				 }
			 }
		 }
		 return result;
	 }
	/**
	 * determines if a king is in check
	 * @param color color of king
	 * @return true if is valid move else false
	 */
	 private boolean kingInCheck(String color)
	 {
		 Square kingLoc = findKing(color);
		 for (Square opponentSquare : getSquaresByPieceColor(board.otherColor(color)))
		 {
			 ChessPiece piece = opponentSquare.getPiece();
			 boolean moveValid =isValidMove(new Move(color,opponentSquare, kingLoc));  
			 if(moveValid)
			 {
				 return true;
			 }
		 }
		 return false;
	 }

	 /** 
	  * finds all squares that are valid moves from a given starting square, normal
	  * moves only not en passant, two row piece moves, or castling
	  * @param from Square we are moving from
	  * @return List of Square elements which are valid moves from starting point
	  */
	 public List<Square> getAllMoves(Square from)
	 {
		 String color = from.getPiece().getColor();
		 List<Square> result = new ArrayList<Square>();
		 for (Square[] row : getBoard())
		 {
			 for (Square to : row)
			 {
				 Move move = new Move(color,from, to);
				 if (isValidTwoRowPawnMove(move)||isValidEnPassant(move)) 
				 {
					 result.add(to);
				 }
				 if(isValidMove(move))
				 {
					 movePiece(move);
					 boolean invalid = kingInCheck(move.getColor());
					 unMovePiece(move);
					 if(!invalid)
					 {result.add(to);} 
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
	 private boolean colorInCheckmate(String color)
	 {
		 if (!kingInCheck(color)){return false;}
		 //find player pieces

		 List<Square> squares = getSquaresByPieceColor(color);
		 for (Square from : squares)
		 {
			 for (Square[] row : getBoard())
			 {
				 for (Square to : row)
				 {
					 Move move = new Move(color, from, to);
					 if(isValidMove(move))
					 {
						 movePiece(move);
						 boolean inCheck = kingInCheck(color);
						 unMovePiece(move);
						 if(!inCheck){return false;}
					 }
					 else if(isValidEnPassant(move))
					 {
						 moveEnPassant(move);
						 boolean inCheck  = kingInCheck(color);
						 unMoveEnPassant(move);
						 if(!inCheck){return false;}
					 }
					 else if(isValidTwoRowPawnMove(move))
					 {
						 movePawnTwoRows(move);
						 boolean inCheck = kingInCheck(color);
						 unMovePawnTwoRows(move);
						 if(!inCheck){return false;}
					 }
				 } 
			 }
		 }
		 return true;
	 }

	 /**
	  * determines if white can castle king side
	  * @return true if white can castle king side else false
	  */
	 private boolean whiteCanCastleKingSide()
	 {
		 return canCastle(initWK, initWKR, "KING");

	 }
	 /**
	  * determines if black can castle king side
	  * @return true if black can castle king side else false
	  */
	 private boolean blackCanCastleKingSide()
	 {

		 return canCastle(initBK, initBKR, "KING");

	 } 

	 /**
	  * determines if white can castle queen side
	  * @return true if white can castle queen side else false
	  */
	 private boolean whiteCanCastleQueenSide()
	 {
		 return canCastle(initWK, initWQR, "QUEEN");
	 }
	 /**
	  * determines if black can castle queen side
	  * @return true if black can castle queen side else false
	  */
	 private boolean blackCanCastleQueenSide()
	 {

		 return canCastle(initBK, initBQR, "QUEEN");

	 } 
	 /**
	  * determines if given side can castle side
	  * @param initK initial Square of king
	  * @param initR initial Square of rook
	  * @return true if side can castle  side else false
	  */
	 private boolean canCastle(Square initK, Square initR, String side)
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

		 return true;

	 } 
	 /**
	  * castles
	  * @param color of side to castle, is either "BLACK" or "WHITE"
	  * @param side to castle on, is either "KING" or "QUEEN"
	  * @return 4 square array of [initial king's pos, new king's pos, initial rook's position, new rook pos]
	  * @return null if is no possible to castle because king would pass through check
	  */
	 private Square[] castle(String color, String side)
	 {
		 // to make compiler happy
		 Square initK = null;
		 Square initR = null;
		 Square newKingSqr = null;
		 Square newRookSqr = null;
		 Square intermediarySquare = null; //the square king must pass through

		 if (color.equals("WHITE"))
		 {
			 initK = initWK;

			 if (side.equals("KING"))
			 {
				 initR = initWKR;
				 newKingSqr = initK.East.East;
				 newRookSqr = initR.West.West;
				 intermediarySquare = initK.East; 
			 }
			 else if(side.equals("QUEEN"))
			 {
				 initR = initWQR;
				 newKingSqr = initK.West.West;
				 newRookSqr = initR.East.East.East;
				 intermediarySquare = initK.West; 
			 }
		 }
		 else if (color.equals("BLACK"))
		 {
			 initK = initBK;

			 if (side.equals("KING"))
			 {
				 initR = initBKR;
				 newKingSqr = initK.East.East;
				 newRookSqr = initR.West.West;
				 intermediarySquare = initK.East; 
 
			 }
			 else if(side.equals("QUEEN"))
			 {
				 initR = initBQR;
				 newKingSqr = initK.West.West;
				 newRookSqr = initR.East.East.East;
				 intermediarySquare = initK.West; 
			 }
		 }

		 //before castling let's see if king will pass through check on way to destination	
		 Move oneSqr = new Move(color, initK, intermediarySquare);
		 movePiece(oneSqr);
		 boolean invalid = kingInCheck(color);
		 unMovePiece(oneSqr);
		 if (invalid)
		 {
			 return null;
		 }
			

		 ChessPiece king = initK.getPiece();
		 ChessPiece rook = initR.getPiece();
		 initK.setPiece(null);
		 initR.setPiece(null);
		 newKingSqr.setPiece(king);
		 newRookSqr.setPiece(rook);
		 
		 Square[] result = {initK, newKingSqr, initR, newRookSqr};
		 return result;
	 }
	 /** reverses a castle
	  * @param initK initial position of king prior to castling
	  * @param newKingSqr position of king after castle
	  * @param initR initial position of rook prior to castling
	  * @param newRookSqr position of rook after castle
	  */
	 private void unCastle(Square initK, Square newKingSqr,Square initR,Square newRookSqr)
	 {
		 newKingSqr.setPiece(null);
		 newRookSqr.setPiece(null);
		 initK.setPiece(initK.getPreviousPiece());
		 initR.setPiece(initR.getPreviousPiece());
	 }

	 public List<Square> getSquaresByPieceColor(String color)
	 {
		 return board.getSquaresByPieceColor(color);
	 }

	 public Square getSquare(int row, int col)
	 {
		 return board.getSquare(row, col);
	 }

	 public void display()
	 {
		 board.display();
	 }

}
