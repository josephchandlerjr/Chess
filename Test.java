//sole purpose is to test

import java.util.Arrays;


public class Test
{
	public static void main(String[] args)
	{
		Game g = new Game();
		System.out.println("Testing Game.move()");
	        if (testMoves(g))
		{System.out.println("Tests Successful");}
		else
		{System.out.println("Problem with Game.move()");}

		

	}
	public static boolean testMove(Game g, int oldRow,int oldCol,int newRow,int newCol,String[][] correctBoard)
	{
		g.movePiece(oldRow,oldCol,newRow,newCol);
		return Arrays.deepEquals(g.getBoardStrRep(), correctBoard);
	} 
	public static boolean testMoves(Game g)
	{
		for (int i=0; i < TestData.testBoards.length; i++)
		{
				int[] params = TestData.testBoardMoves[i];
			        String[][] board = TestData.testBoards[i].array;	
				if (!testMove(g,params[0],params[1],params[2],params[3],board))
				{ 
					g.display();
					System.out.println("should be");
					System.out.print(TestData.testBoards[i]);
					return false;
				}
		}
		return true;
		
	}

}
