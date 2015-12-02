/**
 * a string array representing a board to be used in testing
 */
public class TestBoard
{
	String[][] array;

	public TestBoard(String[][] array)
	{
		this.array = array; 
	}

	public String toString()
	{
		String res = " ";
		for (int row=0; row < array.length; row++)
		{
			for(int col=0; col < array[0].length; col++)
			{
				res = res + array[row][col] + " ";
			}
			res = res + "\n";

		}
		return Board.toString(array);
	}
	public void display()
	{
		System.out.print(toString());
	}
}
