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

	public void turn(String player, String opponent, Console console) {
		boolean hasMoved = false;
		while(!hasMoved) {
			String request = String.format("%nEnter your move, %s: ",player);
			String blackResponse = console.readLine(request);
			ChessNotation move = new ChessNotation(blackResponse);
			if(executeMove(player, move)){ 
				hasMoved = true;}
			if (game.hasWon(player)) {
				System.out.printf("Checkmate! %s wins!\n",player);
				System.exit(1);
			}
			else if(game.isInCheck(opponent)) {
				System.out.printf("%s in Check!\n",opponent);
			}
		}
	}
	class NewGameListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			System.out.println("here i am");
			newGame();
			gui.initialize();
			player = "WHITE";
			opponent = "BLACK";
			guiFrame.repaint();
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

			System.out.printf("%s %s is from\n",file,rank);
			logFrom(pieceID, file+rank);
			guiFrame.repaint();

		}
		public void mouseReleased(MouseEvent e){
			String[] pieceFileRank = getSquareInfoFromMouseEvent(lastEntered);
			String file = pieceFileRank[1];
			String rank = pieceFileRank[2];

			System.out.printf("%s %s is to\n",file,rank);
			logTo(file+rank);
			guiFrame.repaint();
			// for now if checkmate just start over
			if(game.hasWon("BLACK") || game.hasWon("WHITE")){
				newGame();
				gui.initialize();
			}
		}
	}//end inner class BoardListener

	class GuiBoard {
		SquarePanel[][] panels = new SquarePanel[8][8];
		public void build(){
			JFrame frame = new JFrame();
			guiFrame = frame;
			GridLayout grid = new GridLayout(8,8);
			grid.setVgap(1);
			grid.setHgap(1);
			JPanel panel = new JPanel(grid);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			BoardListener listener = new BoardListener();
			for (int row = 0; row < 8; row++){
				for (int col = 0; col < 8; col++){
					SquarePanel p = new SquarePanel();
					panel.add(p);
					p.addMouseListener(listener);
					panels[row][col] = p;
				}
			}
			frame.getContentPane().add(BorderLayout.CENTER, panel);

			JButton button = new JButton("New Game");
			button.addActionListener(new NewGameListener());
			frame.getContentPane().add(BorderLayout.EAST, button);

			frame.setSize(8*70,8*70);
			frame.setVisible(true);
		}
		public void initialize(){
			for (int row = 0; row < 8; row++){
				for (int col = 0; col < 8; col++){
					panels[row][col].setSquare(game.getSquare(row,col));
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
