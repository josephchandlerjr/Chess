/**
 * holds data sets to be used in Test class
 */
public class TestData
{
	public static final int[][] testBoardMoves1 = { 
		                                          {1,0,2,0,0},
							  {6,1,5,1,0},
							  {0,1,2,2,0},
							  {7,2,5,0,0},
							  {1,4,3,4,0},
							  {6,4,4,4,0},
							  {0,6,2,5,0},
							  {6,3,5,3,0},
							  {1,3,3,3,0},
							  {4,4,3,4,0},
							  {4,4,3,3,0},
							  {3,4,4,5,0},
							  {3,4,5,4,0},
							  {2,0,3,0,0},
							  {0,0,2,0,0},
							  {2,0,2,2,0},
							  {6,7,4,7,0},
							  {7,7,5,7,0},
							  {5,7,5,2,0},
							  {5,7,5,4,0},
							  {5,4,4,5,0},
							  {5,0,4,0,0},
							  {7,5,4,2,0},
							  {7,5,6,4,0},
							  {0,3,1,3,0},
							  {1,3,3,5,0},
							  {0,4,1,4,0},
							  {3,5,6,5,0},
							  {7,4,6,5,0},
							  {0,5,2,3,0},
							  {0,4,0,6,9},
	                                                  };
	public static final boolean[] moveIsValid1 =       { 
		                                          true,
							  true,
							  true,
							  true,
							  true,
							  true,
							  true,
							  true,
							  true,
							  false,
							  true, 
							  false, 
							  false, 
							  true, 
							  true, 
							  false,
							  true, 
							  true, 
							  false, 
							  true,
							  false,
							  false,
							  false,
							  true, 
							  true, 
							  true, 
							  false,
							  true, 
							  true,
							  true,
							  true
	                                                  };
	public static final TestBoard[] testBoards1= { 

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
		        }),
			new TestBoard( new String[][] {
			{"BR",null,"BB","BQ","BK","BB","BN","BR"}, //(1,4) -> (3,4)
			{null,"BP","BP","BP",null,"BP","BP","BP"},
			{"BP",null,"BN",null,null,null,null,null},
			{null,null,null,null,"BP",null,null,null},
			{null,null,null,null,null,null,null,null},
			{"WB","WP",null,null,null,null,null,null},
			{"WP",null,"WP","WP","WP","WP","WP","WP"},
			{"WR","WN",null,"WQ","WK","WB","WN","WR"}
		        }),
			new TestBoard( new String[][] {
			{"BR",null,"BB","BQ","BK","BB","BN","BR"}, //(6,4) -> (4,4)
			{null,"BP","BP","BP",null,"BP","BP","BP"},
			{"BP",null,"BN",null,null,null,null,null},
			{null,null,null,null,"BP",null,null,null},
			{null,null,null,null,"WP",null,null,null},
			{"WB","WP",null,null,null,null,null,null},
			{"WP",null,"WP","WP",null,"WP","WP","WP"},
			{"WR","WN",null,"WQ","WK","WB","WN","WR"}
		        }),
			new TestBoard( new String[][] {
			{"BR",null,"BB","BQ","BK","BB",null,"BR"}, //(0,6) -> (2,5)
			{null,"BP","BP","BP",null,"BP","BP","BP"},
			{"BP",null,"BN",null,null,"BN",null,null},
			{null,null,null,null,"BP",null,null,null},
			{null,null,null,null,"WP",null,null,null},
			{"WB","WP",null,null,null,null,null,null},
			{"WP",null,"WP","WP",null,"WP","WP","WP"},
			{"WR","WN",null,"WQ","WK","WB","WN","WR"}
		        }),
			new TestBoard( new String[][] {
			{"BR",null,"BB","BQ","BK","BB",null,"BR"}, //(6,3) -> (5,3)
			{null,"BP","BP","BP",null,"BP","BP","BP"},
			{"BP",null,"BN",null,null,"BN",null,null},
			{null,null,null,null,"BP",null,null,null},
			{null,null,null,null,"WP",null,null,null},
			{"WB","WP",null,"WP",null,null,null,null},
			{"WP",null,"WP",null,null,"WP","WP","WP"},
			{"WR","WN",null,"WQ","WK","WB","WN","WR"}
		        }),
			new TestBoard( new String[][] {
			{"BR",null,"BB","BQ","BK","BB",null,"BR"}, //(1,3) -> (3,3)
			{null,"BP","BP",null,null,"BP","BP","BP"},
			{"BP",null,"BN",null,null,"BN",null,null},
			{null,null,null,"BP","BP",null,null,null},
			{null,null,null,null,"WP",null,null,null},
			{"WB","WP",null,"WP",null,null,null,null},
			{"WP",null,"WP",null,null,"WP","WP","WP"},
			{"WR","WN",null,"WQ","WK","WB","WN","WR"}
		        }),
			new TestBoard( new String[][] {
			{"BR",null,"BB","BQ","BK","BB",null,"BR"}, //(4,4) -> (3,4) bad move
			{null,"BP","BP",null,null,"BP","BP","BP"},
			{"BP",null,"BN",null,null,"BN",null,null},
			{null,null,null,"BP","BP",null,null,null},
			{null,null,null,null,"WP",null,null,null},
			{"WB","WP",null,"WP",null,null,null,null},
			{"WP",null,"WP",null,null,"WP","WP","WP"},
			{"WR","WN",null,"WQ","WK","WB","WN","WR"}
		        }),
			new TestBoard( new String[][] {
			{"BR",null,"BB","BQ","BK","BB",null,"BR"}, //(4,4) -> (3,3) 
			{null,"BP","BP",null,null,"BP","BP","BP"},
			{"BP",null,"BN",null,null,"BN",null,null},
			{null,null,null,"WP","BP",null,null,null},
			{null,null,null,null,null,null,null,null},
			{"WB","WP",null,"WP",null,null,null,null},
			{"WP",null,"WP",null,null,"WP","WP","WP"},
			{"WR","WN",null,"WQ","WK","WB","WN","WR"}
		        }),
			new TestBoard( new String[][] {
			{"BR",null,"BB","BQ","BK","BB",null,"BR"}, //(3,4) -> (4,5) bad move 
			{null,"BP","BP",null,null,"BP","BP","BP"},
			{"BP",null,"BN",null,null,"BN",null,null},
			{null,null,null,"WP","BP",null,null,null},
			{null,null,null,null,null,null,null,null},
			{"WB","WP",null,"WP",null,null,null,null},
			{"WP",null,"WP",null,null,"WP","WP","WP"},
			{"WR","WN",null,"WQ","WK","WB","WN","WR"}
		        }),
			new TestBoard( new String[][] {
			{"BR",null,"BB","BQ","BK","BB",null,"BR"}, //(3,4) -> (5,4) bad move 
			{null,"BP","BP",null,null,"BP","BP","BP"},
			{"BP",null,"BN",null,null,"BN",null,null},
			{null,null,null,"WP","BP",null,null,null},
			{null,null,null,null,null,null,null,null},
			{"WB","WP",null,"WP",null,null,null,null},
			{"WP",null,"WP",null,null,"WP","WP","WP"},
			{"WR","WN",null,"WQ","WK","WB","WN","WR"}
		        }),
			new TestBoard( new String[][] {
			{"BR",null,"BB","BQ","BK","BB",null,"BR"}, //(2,0) -> (3,0)  
			{null,"BP","BP",null,null,"BP","BP","BP"},
			{null,null,"BN",null,null,"BN",null,null},
			{"BP",null,null,"WP","BP",null,null,null},
			{null,null,null,null,null,null,null,null},
			{"WB","WP",null,"WP",null,null,null,null},
			{"WP",null,"WP",null,null,"WP","WP","WP"},
			{"WR","WN",null,"WQ","WK","WB","WN","WR"}
		        }),
			new TestBoard( new String[][] {
			{null,null,"BB","BQ","BK","BB",null,"BR"}, //(0,0) -> (2,0)  
			{null,"BP","BP",null,null,"BP","BP","BP"},
			{"BR",null,"BN",null,null,"BN",null,null},
			{"BP",null,null,"WP","BP",null,null,null},
			{null,null,null,null,null,null,null,null},
			{"WB","WP",null,"WP",null,null,null,null},
			{"WP",null,"WP",null,null,"WP","WP","WP"},
			{"WR","WN",null,"WQ","WK","WB","WN","WR"}
		        }),
			new TestBoard( new String[][] {
			{null,null,"BB","BQ","BK","BB",null,"BR"}, //(2,0) -> (2,2) bad move  
			{null,"BP","BP",null,null,"BP","BP","BP"},
			{"BR",null,"BN",null,null,"BN",null,null},
			{"BP",null,null,"WP","BP",null,null,null},
			{null,null,null,null,null,null,null,null},
			{"WB","WP",null,"WP",null,null,null,null},
			{"WP",null,"WP",null,null,"WP","WP","WP"},
			{"WR","WN",null,"WQ","WK","WB","WN","WR"}
		        }),
			new TestBoard( new String[][] {
			{null,null,"BB","BQ","BK","BB",null,"BR"}, //(6,7) -> (4,7)   
			{null,"BP","BP",null,null,"BP","BP","BP"},
			{"BR",null,"BN",null,null,"BN",null,null},
			{"BP",null,null,"WP","BP",null,null,null},
			{null,null,null,null,null,null,null,"WP"},
			{"WB","WP",null,"WP",null,null,null,null},
			{"WP",null,"WP",null,null,"WP","WP",null},
			{"WR","WN",null,"WQ","WK","WB","WN","WR"}
		        }),
			new TestBoard( new String[][] {
			{null,null,"BB","BQ","BK","BB",null,"BR"}, //(7,7) -> (5,7)   
			{null,"BP","BP",null,null,"BP","BP","BP"},
			{"BR",null,"BN",null,null,"BN",null,null},
			{"BP",null,null,"WP","BP",null,null,null},
			{null,null,null,null,null,null,null,"WP"},
			{"WB","WP",null,"WP",null,null,null,"WR"},
			{"WP",null,"WP",null,null,"WP","WP",null},
			{"WR","WN",null,"WQ","WK","WB","WN",null}
		        }),
			new TestBoard( new String[][] {
			{null,null,"BB","BQ","BK","BB",null,"BR"}, //(5,7) -> (5,2) bad move  
			{null,"BP","BP",null,null,"BP","BP","BP"},
			{"BR",null,"BN",null,null,"BN",null,null},
			{"BP",null,null,"WP","BP",null,null,null},
			{null,null,null,null,null,null,null,"WP"},
			{"WB","WP",null,"WP",null,null,null,"WR"},
			{"WP",null,"WP",null,null,"WP","WP",null},
			{"WR","WN",null,"WQ","WK","WB","WN",null}
		        }),
			new TestBoard( new String[][] {
			{null,null,"BB","BQ","BK","BB",null,"BR"}, //(5,7) -> (5,4)   
			{null,"BP","BP",null,null,"BP","BP","BP"},
			{"BR",null,"BN",null,null,"BN",null,null},
			{"BP",null,null,"WP","BP",null,null,null},
			{null,null,null,null,null,null,null,"WP"},
			{"WB","WP",null,"WP","WR",null,null,null},
			{"WP",null,"WP",null,null,"WP","WP",null},
			{"WR","WN",null,"WQ","WK","WB","WN",null}
		        }),
			new TestBoard( new String[][] {
			{null,null,"BB","BQ","BK","BB",null,"BR"}, //(5,4) -> (4,5) bad move  
			{null,"BP","BP",null,null,"BP","BP","BP"},
			{"BR",null,"BN",null,null,"BN",null,null},
			{"BP",null,null,"WP","BP",null,null,null},
			{null,null,null,null,null,null,null,"WP"},
			{"WB","WP",null,"WP","WR",null,null,null},
			{"WP",null,"WP",null,null,"WP","WP",null},
			{"WR","WN",null,"WQ","WK","WB","WN",null}
		        }),
			new TestBoard( new String[][] {
			{null,null,"BB","BQ","BK","BB",null,"BR"}, //(5,0) -> (4,0) bad move  
			{null,"BP","BP",null,null,"BP","BP","BP"},
			{"BR",null,"BN",null,null,"BN",null,null},
			{"BP",null,null,"WP","BP",null,null,null},
			{null,null,null,null,null,null,null,"WP"},
			{"WB","WP",null,"WP","WR",null,null,null},
			{"WP",null,"WP",null,null,"WP","WP",null},
			{"WR","WN",null,"WQ","WK","WB","WN",null}
		        }),
			new TestBoard( new String[][] {
			{null,null,"BB","BQ","BK","BB",null,"BR"}, //(7,5) -> (4,2) bad move  
			{null,"BP","BP",null,null,"BP","BP","BP"},
			{"BR",null,"BN",null,null,"BN",null,null},
			{"BP",null,null,"WP","BP",null,null,null},
			{null,null,null,null,null,null,null,"WP"},
			{"WB","WP",null,"WP","WR",null,null,null},
			{"WP",null,"WP",null,null,"WP","WP",null},
			{"WR","WN",null,"WQ","WK","WB","WN",null}
		        }),
			new TestBoard( new String[][] {
			{null,null,"BB","BQ","BK","BB",null,"BR"}, //(7,5) -> (6,4)   
			{null,"BP","BP",null,null,"BP","BP","BP"},
			{"BR",null,"BN",null,null,"BN",null,null},
			{"BP",null,null,"WP","BP",null,null,null},
			{null,null,null,null,null,null,null,"WP"},
			{"WB","WP",null,"WP","WR",null,null,null},
			{"WP",null,"WP",null,"WB","WP","WP",null},
			{"WR","WN",null,"WQ","WK",null,"WN",null}
		        }),
			new TestBoard( new String[][] {
			{null,null,"BB",null,"BK","BB",null,"BR"}, //(0,3) -> (1,3)   
			{null,"BP","BP","BQ",null,"BP","BP","BP"},
			{"BR",null,"BN",null,null,"BN",null,null},
			{"BP",null,null,"WP","BP",null,null,null},
			{null,null,null,null,null,null,null,"WP"},
			{"WB","WP",null,"WP","WR",null,null,null},
			{"WP",null,"WP",null,"WB","WP","WP",null},
			{"WR","WN",null,"WQ","WK",null,"WN",null}
		        }),
			new TestBoard( new String[][] {
			{null,null,"BB",null,"BK","BB",null,"BR"}, //(1,3) -> (3,5)   
			{null,"BP","BP",null,null,"BP","BP","BP"},
			{"BR",null,"BN",null,null,"BN",null,null},
			{"BP",null,null,"WP","BP","BQ",null,null},
			{null,null,null,null,null,null,null,"WP"},
			{"WB","WP",null,"WP","WR",null,null,null},
			{"WP",null,"WP",null,"WB","WP","WP",null},
			{"WR","WN",null,"WQ","WK",null,"WN",null}
		        }),
			new TestBoard( new String[][] {
			{null,null,"BB",null,"BK","BB",null,"BR"}, //(0,4) -> (1,4) bad move   
			{null,"BP","BP",null,null,"BP","BP","BP"},
			{"BR",null,"BN",null,null,"BN",null,null},
			{"BP",null,null,"WP","BP","BQ",null,null},
			{null,null,null,null,null,null,null,"WP"},
			{"WB","WP",null,"WP","WR",null,null,null},
			{"WP",null,"WP",null,"WB","WP","WP",null},
			{"WR","WN",null,"WQ","WK",null,"WN",null}
		        }),
			new TestBoard( new String[][] {
			{null,null,"BB",null,"BK","BB",null,"BR"}, //(3,5) -> (6,5)    
			{null,"BP","BP",null,null,"BP","BP","BP"},
			{"BR",null,"BN",null,null,"BN",null,null},
			{"BP",null,null,"WP","BP",null,null,null},
			{null,null,null,null,null,null,null,"WP"},
			{"WB","WP",null,"WP","WR",null,null,null},
			{"WP",null,"WP",null,"WB","BQ","WP",null},
			{"WR","WN",null,"WQ","WK",null,"WN",null}
		        }),
			new TestBoard( new String[][] {
			{null,null,"BB",null,"BK","BB",null,"BR"}, //(7,4) -> (6,5)    
			{null,"BP","BP",null,null,"BP","BP","BP"},
			{"BR",null,"BN",null,null,"BN",null,null},
			{"BP",null,null,"WP","BP",null,null,null},
			{null,null,null,null,null,null,null,"WP"},
			{"WB","WP",null,"WP","WR",null,null,null},
			{"WP",null,"WP",null,"WB","WK","WP",null},
			{"WR","WN",null,"WQ",null,null,"WN",null}
		        }),
			new TestBoard( new String[][] {
			{null,null,"BB",null,"BK",null,null,"BR"}, //(0,5) -> (2,3)    
			{null,"BP","BP",null,null,"BP","BP","BP"},
			{"BR",null,"BN","BB",null,"BN",null,null},
			{"BP",null,null,"WP","BP",null,null,null},
			{null,null,null,null,null,null,null,"WP"},
			{"WB","WP",null,"WP","WR",null,null,null},
			{"WP",null,"WP",null,"WB","WK","WP",null},
			{"WR","WN",null,"WQ",null,null,"WN",null},
			}),
			new TestBoard( new String[][] {
			{null,null,"BB",null,null,"BR","BK",null}, //(9,9) -> (9,9)    
			{null,"BP","BP",null,null,"BP","BP","BP"},
			{"BR",null,"BN","BB",null,"BN",null,null},
			{"BP",null,null,"WP","BP",null,null,null},
			{null,null,null,null,null,null,null,"WP"},
			{"WB","WP",null,"WP","WR",null,null,null},
			{"WP",null,"WP",null,"WB","WK","WP",null},
			{"WR","WN",null,"WQ",null,null,"WN",null}
		        })
			};
}
