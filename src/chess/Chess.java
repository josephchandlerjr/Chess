package chess;

import java.io.Console;
import java.util.ArrayList;

public class Chess
{
	Game game;

	public static void main(String[] args) throws java.io.FileNotFoundException
	{
		//runs the show
		//constructs a game, whiteIO and blackIO
		Chess chess = new Chess();
		chess.play();
	}

	public Chess() throws java.io.FileNotFoundException
	{
		game = new Game();
	}
	public void displayBoard()
	{
		game.board.display();
	}
	public void play()
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
				if(move("WHITE",whiteMove))
				{ whiteHasMoved=true;}
	
			}

			game.board.display();
			while(!blackHasMoved)
			{
				whiteHasMoved=false;
				String blackResponse = console.readLine("%nEnter your move, black: ");
				ChessNotation blackMove =new ChessNotation(blackResponse);
				if(move("BLACK",blackMove))
				{ blackHasMoved=true;}
	
			}
		}

	}

	/**
	 * translates algebraic chess notation into an game moves
	 * @param color color who's turn it is to move
	 * @param notation ChessNotation to translate
	 * @return true if move executed else false
	 */	
	public boolean move(String color, ChessNotation notation)
	{
		System.out.println(notation.getNotation());
		if(notation.isValid())
		{
			if (notation.isCastleKS()  ){ return game.castle(color, "KING");}

			if (notation.isCastleQS()  ){ return game.castle(color, "QUEEN");}

			//so not a castle must be traditional move with from and to squares
			int destinationColumn = "abcdefgh".indexOf(notation.getFileDestination());
			//my index goes over and down not over and up
			int destinationRow = "87654321".indexOf(notation.getRankDestination());
			
			//should always have to square in notation
			assert destinationColumn != -1;
			assert destinationRow != -1;

			Square to = game.board.getSquare(destinationRow,destinationColumn);
			Square from = getFromSquare(color, to, notation); 
			if (from == null) 
			{ 
			if ((notation.getFileDestination().equals("c")) && 
			   (notation.getRankDestination().equals("2")))
			{
				System.out.println("from returned null");
			}
				return false;
			}
			
			boolean isValidMove = game.movePiece(from, to);
			if (isValidMove)
			{
				ChessPiece piece;
				if (notation.isPromotion() )
				{
					if (notation.getPromoteTo().equals("Q"))
					{ piece = new Queen(color);} 
					else if (notation.getPromoteTo().equals("R"))
					{ piece = new Rook(color);} 
					else if (notation.getPromoteTo().equals("N"))
					{ piece =  new Knight(color);} 
					else if (notation.getPromoteTo().equals("B"))
					{ piece = new Bishop(color);} 
					else 
					{ return false;}
					
					game.board.setPiece(to.getRow(),to.getCol(), piece);
				}
				return true;
			}
			else
			{
				return false;
			}

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
	public Square getFromSquare(String color, Square toSquare, ChessNotation notation)
	{
		//ArrayList of potentials, idea is to wittle it down by given criteria
		//initially is every square on board that is occupied by piece of players same color
		ArrayList<Square> candidates = new ArrayList<Square>();
		for (Square[] row : game.board.board)
		{
			for (Square s : row)
			{
				if (s.isOccupied() && s.getPieceColor().equals(color))
				{
					candidates.add(s);
				}
			}	
		}
			if ((notation.getFileDestination().equals("c")) && 
			   (notation.getRankDestination().equals("2")))
			{
				System.out.println("after initial purge");
				for (Square c : candidates)
				{System.out.println(c);}
			}

		if (candidates.size() == 1) { return candidates.get(0);} // maybe we're done
	        else if (candidates.size() == 0) 
		{ 
			return null;
		}	

		//if given the file of piece to move lets reduce candidates further
		if (!notation.getFileToMove().equals(""))
		{
			int col = "abcdefgh".indexOf(notation.getFileToMove());
			for (int i=0; i < candidates.size();)
			{
				Square s = candidates.get(i);
				if (s.getCol() != col)
				{
					candidates.remove(i);
				}
				else
				{
					i++;
				}
			}
		}
			if ((notation.getFileDestination().equals("c")) && 
			   (notation.getRankDestination().equals("2")))
			{
				System.out.println("after file purge");
				for (Square c : candidates)
				{System.out.println(c);}
			}

		if (candidates.size() == 1) { return candidates.get(0);} // maybe we're done
	        else if (candidates.size() == 0) { return null;}	
		
		//if given the rank of piece to move lets reduce candidates further
		if (!notation.getRankToMove().equals(""))
		{
			int row = "87654321".indexOf(notation.getRankToMove());
			if ((notation.getFileDestination().equals("d")) && 
			   (notation.getRankDestination().equals("5")))
			{
				System.out.printf("row of notation is %s\n", row);
			}
			for (int i=0; i < candidates.size();)
			{
				Square s = candidates.get(i);
				if (s.getRow() != row)
				{
					candidates.remove(i);
				}
				else
				{
					i++;
				}
			}
		}
			if ((notation.getFileDestination().equals("c")) && 
			   (notation.getRankDestination().equals("2")))
			{
				System.out.println("after rank purge");
				for (Square c : candidates)
				{System.out.println(c);}
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
			if ((notation.getFileDestination().equals("c")) && 
			   (notation.getRankDestination().equals("2")))
			{
				System.out.println("after piece  purge");
				for (Square c : candidates)
				{System.out.println(c);}
			}
		
		//remove candidates who's piece can't move to the toSquare
		for (int i=0; i < candidates.size();)
		{
			ArrayList<Square> possibleMoves = game.getAllMoves(candidates.get(i));
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
			if ((notation.getFileDestination().equals("c")) && 
			   (notation.getRankDestination().equals("2")))
			{
				System.out.println("after get all moves purge");
				for (Square c : candidates)
				{System.out.println(c);}
			}
		
		//if not just one candidate now then is invalid move request
		if (candidates.size() == 1) { return candidates.get(0);}
		else 
		{ 
			return null;
		}
		
	}

}
