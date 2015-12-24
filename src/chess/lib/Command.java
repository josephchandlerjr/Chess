package chess.lib;

import chess.Square;

/**
 * interface for passing around methods, only has one method called execute
 * @param <T> type that is returned by method execute
 * @param <S> type that is parameter fed to method execute
 */
public interface Command<T,S>
{
	public T execute(S data);
}
