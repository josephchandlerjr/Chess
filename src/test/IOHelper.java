package test;

import java.util.ArrayList;
import java.util.List;
import chess.ChessNotation;

public class IOHelper
{
	List<ChessNotation> notations;
	int currentIx = 0;
	
	public IOHelper(List<ChessNotation> notations)
	{
		this.notations = notations;
	}

	public ChessNotation next()
	{
		if (currentIx < notations.size())
		{
			ChessNotation result = notations.get(currentIx);
			currentIx ++;
			return result;
		}
		else
		{
			currentIx = 0;
			return null;
		}
	}

}

