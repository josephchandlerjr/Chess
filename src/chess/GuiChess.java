package chess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.io.Console;
import java.util.List;
import chess.lib.ListFilter;


public class GuiChess extends Chess{
	String player = "WHITE";
	String opponent = "BLACK";
	String fromNotation = "";
	Square fromSquare;
	Square toSquare;
	GuiBoard gui;


	/**
	 * main entry point which constructs a Game object and calls consolePlay()
	 * @param args is not used
	 */
	public static void main(String[] args) {
		//runs the show
		//constructs a game, whiteIO and blackIO
		GuiChess chess = new GuiChess();
	}

	/**
	 * constructor
	 */
	public GuiChess(){
		super();
		this.gui = new GuiBoard();
		this.gui.build();
		this.gui.initialize();
	}
	/**
	 * translates algebraic chess notation into game moves
	 * @param color color who's turn it is to move
	 * @param notation ChessNotation to translate
	 * @return true if move executed else false
	 */	
	public boolean executeMove(Move move) {
		boolean result = game.takeAction(move);
		return result;
	}
	private void logFrom(String pieceID, String square) {
		if(!pieceID.equals("P")){
			this.fromNotation = pieceID; 
		}
		this.fromNotation = this.fromNotation + square;
	}

	private void logTo(String toNotation) {
		ChessNotation notation = new ChessNotation(fromNotation+toNotation);
		if ("Ke1c1Ke8c8".contains(fromNotation+toNotation)){
			notation = new ChessNotation("O-O-O");
		}
		else if ("Ke1g1Ke8g8".contains(fromNotation+toNotation)){
			notation = new ChessNotation("O-O");
		}

		Move move = new Move(notation, player, fromSquare, toSquare);
		if(executeMove(move)){
			String temp = player;
			player = opponent;
			opponent = temp;
		}
		//reset instance variables on GuiChess, not sure if necessary...
		this.fromNotation = "";
		this.fromSquare = null;
		this.toSquare = null;
	}

	class NewGameListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			newGame();
			gui.initialize();
			player = "WHITE";
			opponent = "BLACK";
			gui.frame.repaint();
			gui.boardPanel.setEnabled(true);
		}
	}

	class BoardListener implements MouseListener {
		MouseEvent lastEntered;
		/**
		 * takes MouseEvent and returns square associated with SquarePanel from which it occured
		 */
		public Square squareFromEvent(MouseEvent e) {
			SquarePanel sp = (SquarePanel) (e.getComponent());
			return sp.square;
		}
		
		public String[] getNotationFromSquare(Square s) {
			int r = s.getRow();
			int c = s.getCol();
			
			String file = ChessNotation.columnToFile(c);
			String rank = ChessNotation.rowToRank(r);
			String pieceID = "";
			if (s.isOccupied()){
				pieceID = s.getPiece().getID();
			}
			return new String[]{pieceID, file, rank};
		}
		public void mouseClicked(MouseEvent e){}
		public void mouseEntered(MouseEvent e){
			lastEntered = e;
		}
		public void mouseExited(MouseEvent e){}
		public void mousePressed(MouseEvent e){
			Square from = squareFromEvent(e);
			fromSquare = from;// set instance variable on GuiChess

			String[] pieceFileRank = getNotationFromSquare(from);
			String pieceID = pieceFileRank[0];
			String file = pieceFileRank[1];
			String rank = pieceFileRank[2];

			logFrom(pieceID, file+rank);
			gui.frame.repaint();
		}
		public void mouseReleased(MouseEvent e){
			Square to = squareFromEvent(lastEntered);
			toSquare = to; // set instance variable on GuiChess

			String[] pieceFileRank = getNotationFromSquare(to);
			String file = pieceFileRank[1];
			String rank = pieceFileRank[2];

			logTo(file+rank);
			gui.frame.repaint();
			// for now if checkmate just disable board 
			if(game.hasWon("BLACK") || game.hasWon("WHITE")){
				gui.boardPanel.setEnabled(false);
			}
		}
	}//end inner class BoardListener

	class GuiBoard {
		SquarePanel[][] guiBoardSquares = new SquarePanel[8][8];
		JFrame frame;
		JPanel boardPanel;

		public void build(){
			frame = new JFrame("Look how far we've come!");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			GridLayout grid = new GridLayout(8,8);
			grid.setVgap(1);
			grid.setHgap(1);

			boardPanel = new JPanel(grid);
			boardPanel.setPreferredSize(new Dimension(70*8,70*8));
		        boardPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

			BoardListener listener = new BoardListener();
			for (int row = 0; row < 8; row++){
				for (int col = 0; col < 8; col++){
					SquarePanel p = new SquarePanel();
					p.addMouseListener(listener);
					boardPanel.add(p);
					guiBoardSquares[row][col] = p;
				}
			}
			frame.getContentPane().add(BorderLayout.CENTER, boardPanel);

			Box buttonBox = new Box(BoxLayout.Y_AXIS);

			JButton newGameButton = new JButton("New Game");
			newGameButton.addActionListener(new NewGameListener());
			buttonBox.add(newGameButton);

			JButton newButton = new JButton("Suprise me");
			newButton.addActionListener(new NewGameListener());
			buttonBox.add(newButton);

			StatusPanel statusPanel  = new StatusPanel(); 
			statusPanel.setPreferredSize(new Dimension(30,0));

			frame.getContentPane().add(BorderLayout.EAST, buttonBox);
			frame.getContentPane().add(BorderLayout.WEST, statusPanel);

			frame.pack();
			frame.setVisible(true);
		}
		public void initialize(){
			for (int row = 0; row < 8; row++){
				for (int col = 0; col < 8; col++){
					guiBoardSquares[row][col].setSquare(game.getSquare(row,col));
				}
			}
		}
	}//end inner class BuildGui
	class StatusPanel extends JPanel {
		public void paintComponent(Graphics g) {
			if (player.equals("BLACK")){
				g.setColor(Color.green);
				g.fillOval(0,10,15,15);
				g.setColor(Color.red);
				g.fillOval(0,getHeight()-15,15,15);
			}
			else{
				g.setColor(Color.green);
				g.fillOval(0,getHeight()-15,15,15);
				g.setColor(Color.red);
				g.fillOval(0,10,15,15);

			}
		}

	}//end inner class StatusPanel
}
/**
 * essentially a JPanel that points to a Square instance
 */
class SquarePanel extends JPanel {
	Square square;

	public void setSquare(Square s){
		square = s;
	}

	public void paintComponent(Graphics g) {
		if (square != null && square.getColor().equals("BLACK")){
			g.setColor(new Color(102,51,0));
		}
		else {
			g.setColor(Color.white);
		}
		g.fillRect(0,0,70,70);
		if (square.isOccupied()) {
			Image image = new ImageIcon(square.getPiece().getImageLocation()).getImage();	
			g.drawImage(image,10,10,this);
		}
	}

}
