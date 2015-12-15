import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Chess
{
	Game game;
	public static void main(String[] args)
	{
		//runs the show
		//constructs a game, whiteIO and blackIO
		//
		Game game = new Game();
	}

	/**
	 * translates algebraic chess notation into an game moves
	 * @param color color who's turn it is to move
	 * @param notation ChessNotation to translate
	 * @return true if move executed else false
	 */	
	public boolean move(String color, ChessNotation notation)
	{
		if(notation.isValid())
		{
			if (notation.isCastleKS()  ){ return game.castle(color, "KING");}
			if (notation.isCastleQS()  ){ return game.castle(color, "QUEEN");}
			int destinationColumn = "abcdefgh".indexOf(notation.getRankDestination());
			int destinationRow = "12345678".indesOf(notation.getFileDestination());
			assert destinationColumn != -1;
			assert destinationRow != -1;
			Square to = game.board.getSquare(destinationRow,destinationColumn);
			Square from = getFromSquare(notation); 
			if (from == null) { return false;}


			if (notation.isPromotion() )
			{
				sqr.setPiece(piece);
			}
		}
		return false;
	}
	/**
	 * finds Square piece to be moved from
	 * @param notation ChessNotation object describing move
	 * @return Square object or null if no square found that matches notation aka an error
	 */
	public Square getFromSquare(ChessNotation notation)
	{
	}
}
