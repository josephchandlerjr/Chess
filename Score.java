import java.util.ArrayList;

/**
 * represents score sheet
 */
public class Score
{
	ArrayList<Move> moves; 

	public Score()
	{
		moves = new ArrayList<Move>();
	}

	public void addMove(Square from, Square to)
	{
		Move move = new Move(from,to);
		moves.add(move);
	}

	public void addMove(Move move)
	{
		moves.add(move);
	}

	/**
	 * searches score sheet for a given square
	 * @param square the square we are looking for
	 * @return true if square is anywhere in socre sheet else false
	 */
	public boolean contains(Square square)
	{
		for (Move move : moves)
		{
			if (move.to == square || move.from == square){ return true;}
		}
		return false;
	}

	/**
	 * returns score sheet as integer array
	 * @return int[][] in form of {rowfrom, columnfrom, rowto, columnto}
	 */
	public int[][] toIntArray()
	{
		int[][] result = new int[moves.size()][4];
		for (int i=0; i < moves.size(); i++)
		{
			Square from = moves.get(i).from;
			Square to = moves.get(i).to;
			result[i][0] = from.getRow(); 
			result[i][1] = from.getCol(); 
			result[i][2] = to.getRow();
			result[i][3] = to.getCol(); 

		}
		return result;
	}
}

