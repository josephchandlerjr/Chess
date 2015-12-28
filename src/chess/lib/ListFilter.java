package chess.lib;

import java.util.List;
import java.util.ArrayList;
import chess.lib.Command;
import chess.Square;

/**
 * filters elements out of a List
 * @param <T> type of values to be compared in order to determine if list element should be removed
 * @param <S> type of elements contained in List
 */
public class ListFilter<T,S>
{
	List<S> newList;
 
	/**
	 * filters out elements of a List based on parameters given
	 * @param list List to filter
	 * @param command function used to retrieve value from element of list to be examined
	 * @param value value to be compared to result of calling command.execute
	 */
	public ListFilter(List<S> list, Command<T, S> command, T value) 
	{
		newList = new ArrayList<S>();
		for (S element : list)
		{
			T toCompare = command.execute(element);	
			if (toCompare.equals(value))
			{newList.add(element);}
		}
	}
	public List<S> getNewList()
	{
		return newList;
	}

	public static List<Square> filterByColumn(List<Square> candidates, int col)
	{
		ListFilter<Integer,Square> LF = new ListFilter<Integer,Square>(candidates,
				                                               new GetCol(),
					                                       new Integer(col));
		return LF.getNewList();
	}

	public static List<Square> filterByPiece(List<Square> candidates, String piece)
	{

		ListFilter<String, Square> LF = new ListFilter<String,Square>(candidates,
				                                              new GetPieceID(),
					                                      piece); 
		return LF.getNewList();
	}

	public static List<Square> filterByPieceColor(List<Square> candidates, String color)
	{

		ListFilter<String, Square> LF = new ListFilter<String,Square>(candidates,
				                                              new GetPieceColor(),
					                                      color); 
		return LF.getNewList();
	}
	public static List<Square> filterByRow(List<Square> candidates, int row)
	{
		Integer rowAsInteger = new Integer(row);
		Command<Integer,Square> C = new GetRow();

		ListFilter<Integer,Square> LF= 
		new ListFilter<Integer,Square>(candidates,C,rowAsInteger);
		
		return LF.getNewList();
	}

}


