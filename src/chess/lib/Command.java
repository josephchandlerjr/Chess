package chess.lib;

import chess.Square;


public interface Command
{
	public int execute(Square sqr);
}
