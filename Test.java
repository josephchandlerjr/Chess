
import java.util.Arrays;


/** 
 * holds tests to aid development
 * run main() to run tests
 * use -v switch to make verbose
 */ 
public class Test
{
	private static boolean VERBOSE = false;
	private static TestBoard[] testBoards = TestData.testBoards;
	private static int[][] testBoardMoves = TestData.testBoardMoves;

	public static void main(String[] args)
	{
		if ( args.length > 0 && args[0].equals("-v"))
		{ VERBOSE = true;}
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
		
		for (int i=0; i < testBoards.length; i++)
		{
				int[] params = testBoardMoves[i];
			        TestBoard board = testBoards[i];	
				if (!testMove(g,params[0],params[1],params[2],params[3],board))
				{ 
					g.display();
					System.out.println("should be");
					System.out.print(board);
					return false;
				}

		}

		int[][] scoreSheet = g.scoreSheet.toIntArray();
		if (VERBOSE)
		{
			System.out.println("The score sheet shows:");
			for (int i=0; i < scoreSheet.length; i++)
			{
				
				System.out.printf("from (%d,%d) to (%d,%d)\n",scoreSheet[i][0],
									      scoreSheet[i][1],
									      scoreSheet[i][2],
									      scoreSheet[i][3]);

			}
			System.out.println("Actual moves are:");
			for (int i=0; i < testBoardMoves.length; i++)
			{
				
				System.out.printf("from (%d,%d) to (%d,%d)\n",testBoardMoves[i][0],
									      testBoardMoves[i][1],
									      testBoardMoves[i][2],
									      testBoardMoves[i][3]);

			}
		}

		boolean correctScoreSheet = Arrays.deepEquals(testBoardMoves,scoreSheet);
		if (!correctScoreSheet)
		{ 
			System.out.println("Score sheet incorrect");
			return false;
		}

		return true;
		
	}

}
