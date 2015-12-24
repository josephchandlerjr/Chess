package chess.lib;

import java.util.List;
import chess.lib.Command;
import chess.Square;

/**
 * filters elements out of a List
 * @param <T> type of values to be compared in order to determine if list element should be removed
 * @param <S> type of elements contained in List
 */
public class ListFilter<T,S>
{
	/**
	 * filters out elements of a List based on parameters given
	 * @param list List to filter
	 * @param command function used to retrieve value from element of list to be examined
	 * @param value value to be compared to result of calling command.execute
	 */
	public void filter(List<S> list, Command<T, S> command, T value) 
	{
		int i = 0;
		while (i < list.size())
		{
			T toCompare = command.execute(list.get(i));	
			if (!toCompare.equals(value))
			{list.remove(i);}
			else
			{i++;}
		}

	}
}


