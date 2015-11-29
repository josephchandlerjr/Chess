/**
 * holds data sets to be used in Test class
 */
public class TestData
{
	public static final int[][] testBoardMoves = { 
		                                          {1,0,2,0},
							  {6,1,5,1},
							  {0,1,2,2},
							  {7,2,5,0},
	                                                  };
	public static final TestBoard[] testBoards = { 

		        new TestBoard( new String[][] {
			{"BR","BN","BB","BQ","BK","BB","BN","BR"}, //(1,0) -> (2,0)
			{null,"BP","BP","BP","BP","BP","BP","BP"},
			{"BP",null,null,null,null,null,null,null},
			{null,null,null,null,null,null,null,null},
			{null,null,null,null,null,null,null,null},
			{null,null,null,null,null,null,null,null},
			{"WP","WP","WP","WP","WP","WP","WP","WP"},
			{"WR","WN","WB","WQ","WK","WB","WN","WR"}
		        }),
			new TestBoard( new String[][] {
			{"BR","BN","BB","BQ","BK","BB","BN","BR"}, //(6,1) -> (5,1)
			{null,"BP","BP","BP","BP","BP","BP","BP"},
			{"BP",null,null,null,null,null,null,null},
			{null,null,null,null,null,null,null,null},
			{null,null,null,null,null,null,null,null},
			{null,"WP",null,null,null,null,null,null},
			{"WP",null,"WP","WP","WP","WP","WP","WP"},
			{"WR","WN","WB","WQ","WK","WB","WN","WR"}
		        }),
			new TestBoard( new String[][] {
			{"BR",null,"BB","BQ","BK","BB","BN","BR"}, //(0,1) -> (2,2)
			{null,"BP","BP","BP","BP","BP","BP","BP"},
			{"BP",null,"BN",null,null,null,null,null},
			{null,null,null,null,null,null,null,null},
			{null,null,null,null,null,null,null,null},
			{null,"WP",null,null,null,null,null,null},
			{"WP",null,"WP","WP","WP","WP","WP","WP"},
			{"WR","WN","WB","WQ","WK","WB","WN","WR"}
		        }),
			new TestBoard( new String[][] {
			{"BR",null,"BB","BQ","BK","BB","BN","BR"}, //(7,2) -> (5,0)
			{null,"BP","BP","BP","BP","BP","BP","BP"},
			{"BP",null,"BN",null,null,null,null,null},
			{null,null,null,null,null,null,null,null},
			{null,null,null,null,null,null,null,null},
			{"WB","WP",null,null,null,null,null,null},
			{"WP",null,"WP","WP","WP","WP","WP","WP"},
			{"WR","WN",null,"WQ","WK","WB","WN","WR"}
		        })
			};
}
