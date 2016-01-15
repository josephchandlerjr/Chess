package chess;
import java.io.*;

/** 
 * represents a move for the score sheet
 * a move is an array of Move objects
 */
public class Move implements Serializable {
	private Square from;
	private Square to;
	private Square enPassantCapture;
	private boolean castle;
	private boolean enPassant;
	private boolean twoRowPawnMove;
	private boolean promotion;
	private String castleSide = "";
	private ChessPiece promoteTo;
	private String color;
	private String notation;

	public Move(String color, Square from, Square to)
	{
		this.from = from;
		this.to = to;
		this.color = color;
	}

	/**
	 * constructor primarily for adding result, a lot let overhead so to speak
	 */
	public Move(ChessNotation notation) {
		assert notation.isValid();
		this.notation = notation.getNotation();
	}
	
	/** new move constructor
	 */
	public Move(ChessNotation notation, String color, Square from, Square to)
	{
		assert notation.isValid();

		this.notation = notation.getNotation();
		this.from = from;
		this.to = to;
		this.color = color;
		
		if (notation.isCastleKS())
		{
			this.castleSide = "KING";
			this.castle = true;
		}
		else if (notation.isCastleQS())
		{
			this.castleSide = "QUEEN";
			this.castle = true;
		}
		else if(notation.getPieceToMove().equals("P"))
		{
			if(rowDifference(from,to) == 2)
			{
				this.twoRowPawnMove = true;
			}
			else if(isDiagonal(from, to) &&
				to.getPiece() == null)
			{
				this.enPassant = true;
			}

			else if(notation.isPromotion() )
			{
				this.promotion = true;
				if (notation.getPromoteTo().equals("Q"))
				{ this.promoteTo = new Queen(color);} 
				else if (notation.getPromoteTo().equals("R"))
				{ this.promoteTo = new Rook(color);} 
				else if (notation.getPromoteTo().equals("N"))
				{ this.promoteTo =  new Knight(color);} 
				else if (notation.getPromoteTo().equals("B"))
				{ this.promoteTo = new Bishop(color);} 

			}	
		}
	}

	//getters
	public boolean isCastle(){return castle;}
	public boolean isEnPassant(){return enPassant;}
	public boolean isTwoRowPawnMove(){return twoRowPawnMove;}
	public boolean isPromotion(){return promotion;}
	public Square getFrom(){return from;}
	public Square getTo(){return to;}
	public Square getEnPassantCapture(){return enPassantCapture;}
	public ChessPiece getPromoteTo(){return promoteTo;}
	public String getColor(){return color;}
	public String getCastleSide(){return castleSide;}
	public String getNotation(){return notation;}

	//setters
	
	public void setFrom(Square s){ this.from = s;}
	public void setTo(Square s)  { this.to = s;}

	private int rowDifference(Square A, Square B)
	{
		int rowA = A.getRow();
		int rowB = B.getRow();
		return Math.abs(rowA - rowB);
	}
	private int colDifference(Square A, Square B)
	{
		int colA = A.getCol();
		int colB = B.getCol();
		return Math.abs(colA - colB);
	}
	private boolean isDiagonal(Square A, Square B)
	{
		int r = rowDifference(A,B);
		int c = colDifference(A,B);
		return r != 0 && r==c;
	}
	public String toString()
	{
		String res =String.format("{ Move(%s)\n",getNotation());
		res = res +String.format("castle=%s\n",isCastle());
		res = res +String.format("enPassant=%s\n",isEnPassant());
		res = res +String.format("twoRowPawnMove=%s\n",isTwoRowPawnMove());
		res = res +String.format("promotion=%s\n",isPromotion());
		res = res +String.format("from=%s\n",getFrom());
		res = res +String.format("to=%s\n",getTo());
		res = res +String.format("enPassantCapture=%s\n",getEnPassantCapture());
		res = res +String.format("promoteTo%s\n",getPromoteTo());
		res = res +String.format("color=%s\n",getColor());
		res = res +String.format("castleSide=%s\n",getCastleSide());
		return res + "} ";
	}
}
