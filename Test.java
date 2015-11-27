//sole purpose is to test

import java.util.Arrays;


public class Test
{
	final static boolean VERBOSE = true;

	public static void main(String[] args)
	{
		Game g = new Game();
		System.out.println("Testing Game.move()");
	        if (testMoves(g))
		{System.out.println("Tests Successful");}
		else
		{System.out.println("Problem with Game.move()");}

		

	}
	public static boolean testMove(Game g, int oldRow,int oldCol,int newRow,int newCol,TestBoard correctBoard)
	{
		if (VERBOSE)
		{
			System.out.println("Game board before:");
			g.display();
		}
		g.movePiece(oldRow,oldCol,newRow,newCol);
		if (VERBOSE)
		{
			System.out.println("Game board after move:");
			g.display();
			System.out.println("Correct board is:");
			System.out.print(correctBoard);
		}
		return Arrays.deepEquals(g.toStringArray(), correctBoard.array);
	} 
	public static boolean testMoves(Game g)
	{
		for (int i=0; i < TestData.testBoards.length; i++)
		{
				int[] params = TestData.testBoardMoves[i];
			        TestBoard board = TestData.testBoards[i];	
				if (!testMove(g,params[0],params[1],params[2],params[3],board))
				{ 
					g.display();
					System.out.println("should be");
					System.out.print(board);
					return false;
				}
		}
		return true;
		
	}

}
