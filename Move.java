/** 
 * represents a move for the score sheet
 * a move is an array of Move objects
 */
public class Move
{
	Square from;
	Square to;
	Square enPassantCapture;
	boolean isCastle = false;
	boolean enPassant = false;
	boolean twoRowPawnMove = false;
	String castleSide;
	Move move2; // castling has second move
	

	public Move(Square from, Square to)
	{
		this.from = from;
		this.to = to;
	}
	/**
	 * second constructor to be used for castling
	 * @param side king side or queen side, is "KING" or "QUEEN"
	 * @param f from Square in first move
	 * @param t to Square in first move 
	 * @param f2 from Square in second move
	 * @param t2 to Square in second move
	 */
	public Move(String side, Square f, Square t, Square f2, Square t2)
	{
		isCastle = true;
		castleSide = side;
		from = f;
		to = t;
		move2 = new Move(f2,t2);
	        	


	}
	/**
	 * third constructor to be used for en passant 
	 * @param f from Square in first move
	 * @param t to Square in first move 
	 * @param enPassantCapture square captured in en passant move
	 */
	public Move (Square f, Square t, Square enPassantCapture)
	{
		enPassant = true;
		this.from = from;
		this.to = to;
		this.enPassantCapture = enPassantCapture;
	}

	public void markAsTwoRowPawnMove()
	{
		twoRowPawnMove = true;
	}

	public boolean isTwoRowPawnMove()
	{
		return twoRowPawnMove;
	}

}
