package chess.lib;

import chess.Square;

public class GetPieceID implements Command<String, Square>
{
	/**
	 * gets the piece ID of a given square
	 * @param data a square
	 * @return ID of piece on give square
	 */
	public String execute(Square data)
	{
		return data.getPiece().getID();
	}	
}
