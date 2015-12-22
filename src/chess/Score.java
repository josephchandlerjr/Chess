package chess;

import java.util.ArrayList;
import java.util.List;

/**
 * represents score sheet
 */
public class Score
{
	List<Move> moves; 

	public Score()
	{
		moves = new ArrayList<Move>();
	}

	public void addMove(Move move)
	{
		moves.add(move);
	}

	/**
	 * searches score sheet for a given square
	 * @param square the square we are looking for
	 * @return true if square is anywhere in socre sheet else false
	 */
	public boolean contains(Square square)
	{
		for (Move move : moves)
		{
			if (move.getTo() == square || move.getFrom() == square){ return true;}
		}
		return false;
	}

	/**
	 * gets last move in score sheet
	 * @return last Move instance recorded or null if score sheet is empty
	 */
	public Move lastMove()
	{
		if (moves.size() == 0) { return null;}
		return moves.get(moves.size()-1);
	}
}

