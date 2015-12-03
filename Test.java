
import java.util.Arrays;


/** 
 * holds tests to aid development
 * run main() to run tests
 * use -v switch to make verbose
 */ 
public class Test
{
	private static boolean VERBOSE = false;
	private static TestBoard[] goodTestBoards = TestData.goodTestBoards;
	private static int[][] testBoardMoves = TestData.goodTestBoardMoves;
	private static boolean[] moveIsValid = TestData.moveIsValid;

	/**
	 * runs tests
	 */
	public static void main(String[] args)
	{
		if ( args.length > 0 && args[0].equals("-v"))
		{ 
			VERBOSE = true;
		}
		Game g = new Game();
		System.out.println("Testing");

	        if (testMoves(g, goodTestBoards, testBoardMoves))
		{
			System.out.println("Tests Successful");
		}
		else
		{
			System.out.println("Tests Failed");
		}

		

	}
	/**
	 * tests a move by comparing its string array representation to the known correct string array 
	 * @return true if the string arrays match else false
	 */
	public static boolean testMove(Game g, int oldRow,int oldCol,int newRow,int newCol,TestBoard correctBoard, boolean result)
	{
		if (VERBOSE)
		{
			System.out.printf("testing move: %d %d to %d %d\n",oldRow,oldCol,newRow,newCol);
			System.out.println("Game board before:");
			g.board.display();
		}
		boolean moveResult = g.movePiece(oldRow,oldCol,newRow,newCol);
		if (VERBOSE)
		{
			System.out.printf("movePiece returns %s\n", moveResult);
			System.out.println("Game board after move:");
			g.board.display();
			System.out.println("Correct board is:");
			System.out.print(correctBoard);
			if (g.blackKingInCheck()){ System.out.println("Black king in check!");}
			if (g.whiteKingInCheck()){ System.out.println("White king in check!");}
		}
		
		return moveResult == result && Arrays.deepEquals(g.board.toStringArray(), correctBoard.array);
	} 
	/** performs a series of moves and ensures they are handled correctly
	 * @param g Game instance
	 * @param boards an array of TestBoards to test results against
	 * @param moves 2D array; each row is a a move in form of a 4-tuple {fromRow,fromCol,toRow,toCol}
	 * @return true if all tests successful else false
	 */
	public static boolean testMoves(Game g, TestBoard[] boards, int[][] moves)
	{
		for (int i=0; i < boards.length; i++)
		{
				int[] params = moves[i];
			        TestBoard board = boards[i];	
				boolean moveResult = testMove(g,params[0],params[1],params[2],params[3],
						     board, moveIsValid[i]);
				if (!moveResult)
				{ 
					System.out.printf("error moving %d %d to %d %d",params[0],
							                                params[1],
											params[2],
											params[3]);
					g.board.display();
					System.out.println("should be");
					System.out.print(board);
					return false;
				}

		}

		System.out.println("All moves correct");

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
			for (int i=0; i < moves.length; i++)
			{
				
				System.out.printf("from (%d,%d) to (%d,%d)\n",moves[i][0],
									      moves[i][1],
									      moves[i][2],
									      moves[i][3]);

			}
		}
		moves = removeBadMoves();

		boolean correctScoreSheet = Arrays.deepEquals(moves,scoreSheet);
		if (!correctScoreSheet)
		{ 
			System.out.println("Score sheet incorrect");
			return false;
		}

		System.out.println("Score sheet correct");
		return true;
		
	}
	/** removes bad moves from test moves so can compare to score sheet from game
	 * @return new moves integer array minus the moves that should have been invalid
	 */
	public static int[][] removeBadMoves()
	{
		int numGoodMoves = 0;
		for (int i=0; i < moveIsValid.length; i++)
		{
			if (moveIsValid[i] == true) 
			{ numGoodMoves ++;}
		}

		int[][] newScoreSheet = new int[numGoodMoves][4];

		int newIndex=0;
		for (int j=0; j < testBoardMoves.length; j++)
		{
			if (moveIsValid[j])
			{
				newScoreSheet[newIndex] = testBoardMoves[j];
				newIndex++;
			}
		}
		return newScoreSheet;

	} 
}
