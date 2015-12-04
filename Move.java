/** 
 * represents a move for the score sheet
 * a move is an array of Move objects
 */
public class Move
{
	Square from;
	Square to;
	boolean isCastle = false;
	String castleSide = "";
	Square from2 = null;
	Square to2 = null;
	

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
		from2 = f2;
		to2 = t2;
	        	


	}

}
