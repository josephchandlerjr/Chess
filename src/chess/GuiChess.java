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
	String from = "";
	JFrame guiFrame;
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
	private void logFrom(String pieceID, String square) {
		if(!pieceID.equals("P")){
			from = pieceID;
		}
		from = from + square;
	}

	private void logTo(String to) {
		ChessNotation move = new ChessNotation(from+to);
		if ("Ke1c1Ke8c8".contains(from+to)){
			move = new ChessNotation("O-O-O");
		}
		else if ("Ke1g1Ke8g8".contains(from+to)){
			move = new ChessNotation("O-O");
		}
		from = "";
		if(executeMove(player, move)){
			String temp = player;
			player = opponent;
			opponent = temp;
		}
	}

	class NewGameListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			newGame();
			gui.initialize();
			player = "WHITE";
			opponent = "BLACK";
			guiFrame.repaint();
			gui.boardPanel.setEnabled(true);
		}
	}

	class BoardListener implements MouseListener {
		MouseEvent lastEntered;
		public String[] getSquareInfoFromMouseEvent(MouseEvent e) {
			SquarePanel sp = (SquarePanel) (e.getComponent());
			Square s = sp.square;
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
			String[] pieceFileRank = getSquareInfoFromMouseEvent(e);
			String pieceID = pieceFileRank[0];
			String file = pieceFileRank[1];
			String rank = pieceFileRank[2];

			logFrom(pieceID, file+rank);
			guiFrame.repaint();

		}
		public void mouseReleased(MouseEvent e){
			String[] pieceFileRank = getSquareInfoFromMouseEvent(lastEntered);
			String file = pieceFileRank[1];
			String rank = pieceFileRank[2];

			logTo(file+rank);
			guiFrame.repaint();
			// for now if checkmate just disable board 
			if(game.hasWon("BLACK") || game.hasWon("WHITE")){
				gui.boardPanel.setEnabled(false);
			}
		}
	}//end inner class BoardListener

	class GuiBoard {
		SquarePanel[][] guiBoardSquares = new SquarePanel[8][8];
		JPanel boardPanel;

		public void build(){
			guiFrame = new JFrame();
			guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			GridLayout grid = new GridLayout(8,8);
			grid.setVgap(1);
			grid.setHgap(1);

			boardPanel = new JPanel(grid);
			boardPanel.setPreferredSize(new Dimension(70*8,70*8));

			BoardListener listener = new BoardListener();
			for (int row = 0; row < 8; row++){
				for (int col = 0; col < 8; col++){
					SquarePanel p = new SquarePanel();
					p.addMouseListener(listener);
					boardPanel.add(p);
					guiBoardSquares[row][col] = p;
				}
			}
			guiFrame.getContentPane().add(BorderLayout.CENTER, boardPanel);

			JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

			JButton newGameButton = new JButton("New Game");
			newGameButton.addActionListener(new NewGameListener());
			buttonPanel.add(newGameButton);
			guiFrame.getContentPane().add(BorderLayout.EAST, buttonPanel);

			//frame.setSize(9*70,9*70);
			guiFrame.pack();
			guiFrame.setVisible(true);
		}
		public void initialize(){
			for (int row = 0; row < 8; row++){
				for (int col = 0; col < 8; col++){
					guiBoardSquares[row][col].setSquare(game.getSquare(row,col));
				}
			}
		}
	}//end inner class BuildGui
}

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
