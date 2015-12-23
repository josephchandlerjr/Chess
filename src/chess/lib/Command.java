package chess.lib;

import chess.Square;


public interface Command<S>
{
	public int execute(S data);
}
