package chess.lib;

import java.util.List;
import chess.lib.Command;
import chess.Square;




public class ListFilter
{
	/**
	 * removes elements from List if int derived from each element not equal to given int
	 * @param value value to compare
	 * @param command command obj to get value from object
	 * @return 
	 */
	public void filter(List<Square> list, Command command, int value) 
	{
		int i = 0;
		while (i < list.size())
		{
			int toCompare = command.execute(list.get(i));	
			if (toCompare != value)
			{list.remove(i);}
			else
			{i++;}
		}

	}
}


