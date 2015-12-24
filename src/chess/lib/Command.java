package chess.lib;

import chess.Square;


public interface Command<T,S>
{
	public T execute(S data);
}
