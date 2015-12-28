package chess.lib;

import java.util.List;
import java.util.ArrayList;
import chess.lib.Command;
import chess.Square;

/**
 * filters elements out of a Array, essentially converts to list and uses ListFilter static methods
 */
public class ArrayFilter
{
	public static List<Square> flattenArray(Square[][] array)
	{
		List<Square> list = new ArrayList<Square>();

		for (Square[] row : array)
		{
			for (Square s : row)
			{
				list.add(s);
			}
		}
		return list;
	}

	public static List<Square> filterByColumn(Square[][] array, int col)
	{
		return ListFilter.filterByColumn(flattenArray(array),col);
	}

	public static List<Square> filterByPiece(Square[][] array, String piece)
	{
		return ListFilter.filterByPiece(flattenArray(array),piece);
	}

	public static List<Square> filterByPieceColor(Square[][] array, String color)
	{
		return ListFilter.filterByPieceColor(flattenArray(array),color);
	}
	public static List<Square> filterByRow(Square[][] array, int row)
	{
		return ListFilter.filterByRow(flattenArray(array),row);
	}

}


