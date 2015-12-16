package chess;
import java.util.ArrayList;

/**
 * represents a chess game board
 */
public class Board
{
	Square[][] board;
	Square whiteKingsSquare;
	Square blackKingsSquare;
	

	public Board()
	{
		board = new Square[8][8];
	}

	public Board(Square[][] board)
	{
		this.board = board;
	}

	private Square[] getRow(int row)
	{
		return board[row];
	}
	private Square[] getColumn(int col)
	{
		return board[col];
	}

	/**
	 * gets a square from the board
	 * @param row row square you want lies on
	 * @param col column square you want lies on
	 * @return Square instance you asked for
	 */
	public Square getSquare(int row, int col)
	{
		return board[row][col];
	}
        /**
	 * assigns a given Square with a give ChessPiece
	 * @param row row of square
	 * @param col column of square
	 * @param piece ChessPiece to assign
	 */
	public void setPiece(int row, int col, ChessPiece piece)
	{
		Square square = board[row][col];
		square.setPiece(piece);
		if (piece instanceof King)
		{ 
			String color = piece.getColor();
			if (color.equals("BLACK"))      { blackKingsSquare = square;}
			else if (color.equals("WHITE")) { whiteKingsSquare = square;}
		}

	}
	/**
	 * gets a square from the board
	 * @param row row square you want lies on
	 * @param col column square you want lies on
	 * @return Square instance you asked for
	 */
	public void setSquare(String color, int row, int col)
	{
		board[row][col] = new Square(color,row,col);
	}
	
	/** gets a 2D array representing board
	 * @return String array representing pieces on the board
	 */
	public String[][] toStringArray()
	{
		String[][] res = new String[board.length][board[0].length];
		for (int r=0; r < board.length;r++)
		{
			for (int c=0; c < board[0].length; c++)
			{
				res[r][c] = board[r][c].toString();
			}
		} 
		return res;
	}

	 /** returns string representation of board
	 * @return string representing current state of board
	 */
	public String toString()
	{
		String[][] asStringArray = toStringArray();

		return toString(asStringArray);
	}

	/**
	 * returns string representation of board given
	 * is static method to be used by any 2D string array
	 * @param board a 2D array of String elements
	 * @return string representing current state of board
	 */
	public static String toString(String[][] board)
	{
		String horizontalLine = "-- -- -- -- -- -- -- --";
		String res = horizontalLine + "\n";
		for (int r=0; r < board.length;r++)
		{
			res = res + "|";
			for (int c=0; c < board[0].length; c++)
			{
				if(board[r][c] == null) 
				{ 
					res = res + "  |";
				}
				else 
				{ 
					res = res + board[r][c];
					res = res + "|";
				}
			}
			res = res + "\n" + horizontalLine + "\n";
		}
		return res;
	}

        /**
	 * display string representation of board
	 */
	public void display()
	{
		System.out.print(this);

	}

	 /**
	  * checks if a row,column combination is off of the 8x8 board
	  * @return if row,column combination if off board else false
	  */
	 public boolean isOffBoard(int row, int col)
	 {
		 return row > 7 || row < 0 || col > 7 || row < 0;
	 }

	/**
	 * return opposite color
	 * @param myColor can be String BLACK or WHITE
	 * @return if WHITE is give as param return BLACK else WHITE
	 */
	public String otherColor(String myColor)
	{
		if (myColor == "WHITE"){ return "BLACK";}
		return "WHITE";
	} 

	/**
	 * tells you if a square is occupied by your opponents color
	 * @param s Square instance you are inquiring about
	 * @param myColor a string either "BLACK" or "WHITE" which is your color
	 * @return true if square given has an opponents piece on it else false
	 */
	public boolean isOccupiedByOpponent(Square s, String myColor)
	{
		String opponentsColor = otherColor(myColor);
		return s.isOccupied() && s.getPiece().getColor().equals(opponentsColor);
	}

	/**
	 * tells yoiu if a square is occupied by one of your pieces 
	 * @param s Square instance you are inquiring about
	 * @param myColor a string either "BLACK" or "WHITE" which is your color
	 * @return true if square given has one of your pieces on it else false
	 */
	public boolean isOccupiedByPieceOfSameColor(Square s, String myColor)
	{
		return s.isOccupied() && s.getPiece().getColor().equals(myColor);

	}	
	/**
	 * finds if a particular square is occupied by piece of give color and type
	 * @param s square we are examining
	 * @param color color of piece we are looking for 
	 * @param rep string rep of piece per PGN, for example "N" is a knight, "K" a king
	 * @return true if square has such a piece on it else false
	 */
	public boolean isOccupiedByPiece(Square s, String color, String rep)
	{
		String fullRep = color.substring(0,1) + rep;
		return s.isOccupied() && s.getPiece().toString().equals(fullRep);
	}
	/**
	 * takes two pieces on same row, column, or diagonal and tells if there are pieces between them
	 * @param from a Square on the board
	 * @param to another Square on board
	 * @return true if there are occupied squares between Squares given else false 
	 */
	public boolean piecesBetween(Square from, Square to)
	{
		ArrayList<Square> squares = squaresBetween(from, to);
		for (Square square : squares)
		{
			if (square.isOccupied())
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * returns squares that lie between two squares on same row, column or diagonal
	 * @param from a Square on the board
	 * @param to another Square on board
	 * @return array of Square objects between the two give Squares
	 */
	public ArrayList<Square> squaresBetween(Square from, Square to)
	 {       
		 ArrayList<Square> result = new ArrayList<Square>();
		 int fromRow = from.getRow();
		 int fromCol = from.getCol();
		 int toRow = to.getRow();
		 int toCol = to.getCol();

		 int rowStep;
		 int colStep;
		 
		 if     (toRow > fromRow){ rowStep = 1;}
		 else if(toRow < fromRow){ rowStep = -1;}
		 else                    { rowStep = 0;}
                 
		 if     (toCol > fromCol){ colStep = 1;}
		 else if(toCol < fromCol){ colStep = -1;}
		 else                    { colStep = 0;}	 

		 int col = fromCol + colStep;
		 int row = fromRow + rowStep;

		 while (col != toCol || row != toRow)
		 {	
			 result.add(getSquare(row,col));
			 col = col + colStep;
			 row = row + rowStep;

		 }
		 
		 return result;
			 
	 }
	public boolean areOnSameColumn(Square A, Square B)
	 {       
		 int fromCol = A.getCol();
		 int toCol = B.getCol();

		 return fromCol == toCol;
	 }
	public boolean areOnSameRow(Square A, Square B)
	 {       
		 int fromRow = A.getRow();
		 int toRow = B.getRow();

		 return fromRow == toRow;
	 }
	/**
	 * tests if two pieces are on same diagonal
	 * @param A a Square
	 * @param B a Square
	 * @return true if A and B on the same diagonal else false
	 */
	public boolean areOnSameDiagonal(Square A, Square B)
	{
	         int fromCol = A.getCol();
		 int toCol = B.getCol();
		 int fromRow = A.getRow();
		 int toRow = B.getRow();

		 int colDiff = toCol - fromCol;
		 int rowDiff = toRow - fromRow;

		 return Math.abs(colDiff) == Math.abs(rowDiff);
	}
	/** 
	 * makes copy of this board object
	 * @return new Board object which is copy of this board object
	 */
	public Board copy()
	{
		Square[][] newArray = new Square[8][8];
		for (int row=0; row < 8; row++)
		{
			for (int col=0; col < 8; col++)
			{
				newArray[row][col] = board[row][col].copy();
			}
		}
		
	        Board newBoard = new Board(newArray);	
		int whiteKingRow = whiteKingsSquare.getRow();
		int whiteKingCol = whiteKingsSquare.getCol();

		int blackKingRow = blackKingsSquare.getRow();
		int blackKingCol = blackKingsSquare.getCol();

		newBoard.whiteKingsSquare = newBoard.getSquare(whiteKingRow,whiteKingCol);
		newBoard.blackKingsSquare = newBoard.getSquare(blackKingRow,blackKingCol);

		return newBoard;

	}
}
