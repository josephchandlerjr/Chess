package chess;

import javax.swing.*;
import java.awt.*;

import java.io.Console;
import java.util.List;

public class Chess {
	Game game;

	/**
	 * main entry point which constructs a Game object and calls consolePlay()
	 * @param args is not used
	 */
	public static void main(String[] args) {
		//runs the show
		//constructs a game, whiteIO and blackIO
		Chess chess = new Chess();
		chess.play();
	}

	/**
	 * constructor
	 */
	public Chess(){
		newGame();
	}


	public void newGame(){
		game = new Game();
	}
	
	/**
	 * prints copy of game board to std out
	 */
	public void displayBoard() {
		game.display();
	}
	
	/**
	 * plays game in console
	 */
	private void play() {
		Console console = System.console();
		if (console == null) {
		    System.err.println("No console.");
		    System.exit(1);
		}
		String p1 = "WHITE";
		String p2 = "BLACK";
		while (true) {
			turn(p1,p2,console);
			String temp = p1;
			p1 = p2;
			p2 = temp;
		}
	}
	public void turn(String player, String opponent, Console console) {
		boolean hasMoved = false;
		displayBoard();
		while(!hasMoved) {
			String request = String.format("%nEnter your move, %s: ",player);
			String blackResponse = console.readLine(request);
			ChessNotation move = new ChessNotation(blackResponse);
			if(executeMove(player, move)){ 
				hasMoved = true;}
			displayBoard();
			if (game.hasWon(player)) {
				System.out.printf("Checkmate! %s wins!\n",player);
				System.exit(1);
			}
			else if(game.isInCheck(opponent)) {
				System.out.printf("%s in Check!\n",opponent);
			}
		}
	}

	/**
	 * translates algebraic chess notation into game moves
	 * @param color color who's turn it is to move
	 * @param notation ChessNotation to translate
	 * @return true if move executed else false
	 */	
	public boolean executeMove(String color, ChessNotation notation) {
		Square from = null;
		Square to = null;
		Move move;
		if(notation.isValid()) {       //if castle don't need to/from
			if (! (notation.isCastleKS() || notation.isCastleQS())) {       
				int destinationColumn = 
			        ChessNotation.fileToColumn(notation.getFileDestination());
				int destinationRow = 
				ChessNotation.rankToRow(notation.getRankDestination());
				
				//should always have to square in notation
				assert destinationColumn != -1;
				assert destinationRow != -1;

				to = game.getSquare(destinationRow,destinationColumn);
				from = getFromSquare(color, to, notation); 
				if (from == null) { 
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
	 * finds Square piece is to be moved from
	 * @param color color of piece to move
	 * @param toSquare square piece would be moved to
	 * @param notation ChessNotation object describing move
	 * @return Square object or null if no square found that matches notation aka an error
	 */
	private Square getFromSquare(String color, Square toSquare, ChessNotation notation) {

		int col = -1;
		int row = -1;
		String piece = notation.getPieceToMove();	

		if (!notation.getFileToMove().equals("")) {
			String file = notation.getFileToMove();
			col = ChessNotation.fileToColumn(file);
		}
		if (!notation.getRankToMove().equals("")) {
			String rank = notation.getRankToMove();
			row = ChessNotation.rankToRow(rank);
		}
		//filter by criteria
		List<Square> candidates = game.filterBoard(row, col, color, piece);
		
		//now filter by move
		for (int i=0; i < candidates.size();) {
			List<Square> possibleMoves = game.getAllMoves(candidates.get(i));
			boolean isPossibleMove = false;
			for (Square sqr : possibleMoves)
			{
				if (sqr == toSquare){ 
					isPossibleMove = true;
				}
			}
			if (!isPossibleMove) {
				candidates.remove(i);
			}
			else {
				i++;
			};
		}
		
		return candidates.get(0);
	}
}
