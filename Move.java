/** 
 * represents a move for the score sheet
 * a move is an array of Move objects
 */
public class Move
{
	Square from;
	Square to;

	public Move(Square from, Square to)
	{
		this.from = from;
		this.to = to;
	}
}
