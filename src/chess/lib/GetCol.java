package chess.lib;

import chess.Square;

public class GetCol implements Command 
{
	public int execute(Square sqr)
	{
		return sqr.getCol();
	}	
}
