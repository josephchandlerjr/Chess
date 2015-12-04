
import java.util.Arrays;


/** 
 * holds tests to aid development
 * run main() to run tests
 * use -v switch to make verbose
 */ 
public class Test
{
	private static boolean VERBOSE = false;
	private static TestBoard[] testBoards = TestData.testBoards1;
	private static int[][] testBoardMoves = TestData.testBoardMoves1;
	private static boolean[] moveIsValid = TestData.moveIsValid1;

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

	        if (testMoves(g, testBoards, testBoardMoves))
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
		}
		boolean moveResult = g.movePiece(oldRow,oldCol,newRow,newCol);
		if (VERBOSE)
		{
			System.out.printf("movePiece returns %s\n", moveResult);
			System.out.println("Game board after move:");
			g.board.display();
			if (g.blackKingInCheck()){ System.out.println("Black king in check!");}
			if (g.whiteKingInCheck()){ System.out.println("White king in check!");}
			if (g.blackInCheckmate()){ System.out.println("Black king in checkmate!!");}
			if (g.whiteInCheckmate()){ System.out.println("White king in checkmate!!");}
			if (g.whiteCanCastleKingSide()){ System.out.println("White can castle king side");}
			if (g.whiteCanCastleQueenSide()){ System.out.println("White can castle queen side");}
			if (g.blackCanCastleKingSide()){ System.out.println("Black can castle king side");}
			if (g.blackCanCastleQueenSide()){ System.out.println("Black can castle queen side");}
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
		System.out.println("Initial Game board:");
		if (VERBOSE){g.board.display();}
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
