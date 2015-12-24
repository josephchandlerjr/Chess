
package chess.lib;

import java.util.List;
import java.util.ArrayList;
import chess.lib.Command;
import chess.Square;

/**
 * selects elements of a 2D array based on parameters given and 
 * returns flat List of elements selected
 * @param list 2D array to filter
 * @param command function used to retrieve value from element of list to be examined
 * @param value value to be compared to result of calling command.execute
 * @return List of selected items
 */
public class Arrays<T,S> 
{
	public List<S> selectFrom2D(S[][] array, Command<T, S> command, T value)
	{
		List<S> result = new ArrayList<S>();
		for (S[] row : array)
		{
			for (S element : row)
			{
				T toCompare = command.execute(element);
				if(toCompare.equals(value))
				{
					result.add(element);
				}
			}
		}
		return result;
	}
}	
