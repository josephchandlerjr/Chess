package chess.lib;

import chess.Square;

public class GetRow implements Command<Integer, Square>
{
	/**
	 * gets the row of a given square
	 * @param data a square
	 * @return row of square wrapped in an integer type
	 */
	public Integer execute(Square data)
	{
		return new Integer(data.getRow());
	}	
}
