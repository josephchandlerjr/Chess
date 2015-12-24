package chess.lib;

import chess.Square;

public class GetRow implements Command<Integer, Square>
{
	public Integer execute(Square data)
	{
		return new Integer(data.getRow());
	}	
}
