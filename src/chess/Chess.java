package chess;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

import chess.lib.*;

public class Chess
{
	Game game;

	/**
	 * main entry point which constructs a Game object and calls consolePlay()
	 * @param args is not used
	 */
	public static void main(String[] args) throws java.io.FileNotFoundException
	{
		//runs the show
		//constructs a game, whiteIO and blackIO
		Chess chess = new Chess();
		chess.consolePlay();
	}

	/**
	 * constructor
	 */
	public Chess() throws java.io.FileNotFoundException
	{
		game = new Game();
	}
	
	/**
	 * prints copy of game to std out
	 */
	public void displayBoard()
	{
		game.board.display();
	}
	
	/**
	 * plays game in console
	 */
	private void consolePlay()
	{
		Console console = System.console();
		if (console == null) {
		    System.err.println("No console.");
		    System.exit(1);
		}
		boolean whiteHasMoved = false;
		boolean blackHasMoved = false;
		while (true) 
		{
			
			game.board.display();
			while(!whiteHasMoved)
			{
				blackHasMoved=false;
				game.board.display();
				String whiteResponse = console.readLine("%nEnter your move, white: ");
				ChessNotation whiteMove =new ChessNotation(whiteResponse);
				if(executeMove("WHITE",whiteMove))
				{ whiteHasMoved=true;}
	
			}

			game.board.display();
			while(!blackHasMoved)
			{
				whiteHasMoved=false;
				String blackResponse = console.readLine("%nEnter your move, black: ");
				ChessNotation blackMove =new ChessNotation(blackResponse);
				if(executeMove("BLACK",blackMove))
				{ blackHasMoved=true;}
	
			}
		}

	}

	/**
	 * translates algebraic chess notation into game moves
	 * @param color color who's turn it is to move
	 * @param notation ChessNotation to translate
	 * @return true if move executed else false
	 */	
	public boolean executeMove(String color, ChessNotation notation)
	{
		Square from = null;
		Square to = null;
		Move move;
		if(notation.isValid())
		{
			if (! (notation.isCastleKS() || notation.isCastleQS())) //if castle don't need to/from
			{       
				int destinationColumn = ChessNotation.fileToColumn(notation.getFileDestination());
				int destinationRow = ChessNotation.rankToRow(notation.getRankDestination());
				
				//should always have to square in notation
				assert destinationColumn != -1;
				assert destinationRow != -1;

				to = game.board.getSquare(destinationRow,destinationColumn);
				from = getFromSquare(color, to, notation); 
				if (from == null) 
				{ 

					return false;
			        }
			}
			move = new Move(notation, color, from, to);
			boolean result = game.takeAction(move);
			return result;
		}
		return false;

	}
	/**
	 * finds Square piece to be moved from
	 * @param color color of piece to move
	 * @param toSquare square piece would be moved to
	 * @param notation ChessNotation object describing move
	 * @return Square object or null if no square found that matches notation aka an error
	 */
	private Square getFromSquare(String color, Square toSquare, ChessNotation notation)
	{
		//List of potentials, idea is to wittle it down by given criteria
		//initially is every square on board that is occupied by piece of players same color

		List<Square> candidates = game.getSquaresByPieceColor(color);
		int ix = 0;
		while (ix < candidates.size())
		{
			Square s = candidates.get(ix);
			if (!s.isOccupied())
			{
				candidates.remove(s);
			}
			else
			{
				ix++;
			}
		}

		if (candidates.size() == 1) { return candidates.get(0);} // maybe we're done
	        else if (candidates.size() == 0) 
		{ 
			return null;
		}	

		//if given the file of piece to move lets reduce candidates further
		if (!notation.getFileToMove().equals(""))
		{
			int col = ChessNotation.fileToColumn(notation.getFileToMove());
			Command<Square> command = new GetCol();
			ListFilter.filter(candidates, command, col);
		}


		if (candidates.size() == 1) { return candidates.get(0);} // maybe we're done
	        else if (candidates.size() == 0) { return null;}	
		
		//if given the rank of piece to move lets reduce candidates further
		if (!notation.getRankToMove().equals(""))
		{
			int row = ChessNotation.rankToRow(notation.getRankToMove());
			Command<Square> command = new GetRow();
			ListFilter.filter(candidates, command, row);
	
		}

		if (candidates.size() == 1) { return candidates.get(0);} // maybe we're done
	        else if (candidates.size() == 0) 
		{ 
			return null;
		}	


		if (!notation.getPieceToMove().equals(""))
		{	
			//remove candidates that don't match piece
			for (int i=0; i < candidates.size();)
			{
				Square s = candidates.get(i);
				ChessPiece piece = s.getPiece();
				if (!piece.getID().equals(notation.getPieceToMove()))
				{
					candidates.remove(i);
				}
				else
				{
					i++;
				}
			}	
		}

		
		//remove candidates who's piece can't move to the toSquare
		for (int i=0; i < candidates.size();)
		{
			List<Square> possibleMoves = game.getAllMoves(candidates.get(i));
			boolean isPossibleMove = false;
			for (Square sqr : possibleMoves)
			{
				if (sqr == toSquare){ isPossibleMove = true;}
			}
			if (!isPossibleMove)
			{
				candidates.remove(i);
			}
			else
			{
				i++;
			};
		}

		
		//if not just one candidate now then is invalid move request
		if (candidates.size() == 1) { return candidates.get(0);}
		else 
		{ 
			return null;
		}
		
	}

}
