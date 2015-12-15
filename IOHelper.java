import java.util.ArrayList;

public class IOHelper
{
	ArrayList<ChessNotation> notations;
	int currentIx = 0;
	
	public IOHelper(ArrayList<ChessNotation> notations)
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

