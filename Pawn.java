/** 
 * represents a pawn chess piece
*/
public class Pawn extends ChessPiece
{
	private int direction;

	public Pawn(String color)
	{
		super(color, "P");
		if (color.equals("BLACK"))
		{
			direction = 1;
		}
		else 
		{
			direction = -1;
		}
	}
	/** 
	 * gets direction pawn moves
	 * @return 1 if pawn moves toward higher numbered rows or -1 if moving opposite direction
	 */
	public int getDirection()
	{
		return direction;
	}
	
}

