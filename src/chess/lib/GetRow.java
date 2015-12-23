package chess.lib;

import chess.Square;

public class GetRow implements Command<Square>
{
	public int execute(Square data)
	{
		return data.getRow();
	}	
}
