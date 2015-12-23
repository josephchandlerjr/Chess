package chess.lib;

import chess.Square;

public class GetCol implements Command<Square>
{
	public int execute(Square data)
	{
		return data.getCol();
	}	
}
