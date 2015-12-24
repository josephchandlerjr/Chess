package chess.lib;

import chess.Square;

public class GetPieceColor implements Command<String, Square>
{
	/**
	 * gets color of piece on given square
	 * @param data a square
	 * @return piece color if square is occupied else empty string
	 */
	public String execute(Square data)
	{
		if (data.isOccupied())
		{
			return data.getPiece().getColor();
		}
		return "";
	}	
}
