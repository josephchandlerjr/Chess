package chess.lib;

import chess.Square;

public class GetCol implements Command<Integer, Square>
{
	/**
	 * gets the column of a given square
	 * @param data a square
	 * @return column number wrapped in an Integer type
	 */
	public Integer execute(Square data)
	{
		return new Integer(data.getCol());
	}	
}
