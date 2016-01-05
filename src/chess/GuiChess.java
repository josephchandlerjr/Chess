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
	String to = "";
	JFrame jframe;

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
		GuiBoard gui = new GuiBoard();
		gui.buildGui();
	}
	private void logFrom(String pieceID, String square) {
		if(!pieceID.equals("P")){
			from = pieceID;
		}
		from = from + square;

	}
	private void logTo(String square) {
		to = square;
		ChessNotation move = new ChessNotation(from+to);
		from = "";
		to = "";
		if(executeMove(player, move)){
			System.out.println("Good Move");
			String temp = player;
			player = opponent;
			opponent = temp;
			System.out.printf("it is %s's move\n",player);
		}
		else {System.out.printf("bad Move");}

	}
	private void logClick(String pieceID, String square) {
		System.out.println(pieceID);
		System.out.println(from);
		System.out.println(pieceID.equals(""));
		if(from.equals("") && !pieceID.equals("")){
			if(!pieceID.equals("P")){
				from = pieceID;
			}
			from = from + square;
			System.out.printf("from is set to %s\n",from);
			return;
		}
		to = square;
		System.out.printf("to is set to %s\n",to);
		System.out.printf("whole thing is set to %s\n",from+to);
		ChessNotation move = new ChessNotation(from+to);
		from = "";
		to = "";
		if(executeMove(player, move)){
			System.out.println("Good Move");
			String temp = player;
			player = opponent;
			opponent = temp;
			System.out.printf("it is %s's move\n",player);
		}
		else {System.out.printf("bad Move");}

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

	class GuiBoard implements MouseListener {
		MouseEvent lastEntered;

		public void buildGui(){
			JFrame frame = new JFrame();
			jframe = frame;
			GridLayout grid = new GridLayout(8,8);
			grid.setVgap(1);
			grid.setHgap(1);
			JPanel panel = new JPanel(grid);
			frame.setContentPane(panel);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			for (int row = 0; row < 8; row++){
				for (int col = 0; col < 8; col++){
					SquarePanel p = new SquarePanel(game.getSquare(row,col));
					frame.getContentPane().add(p);
					p.addMouseListener(this);
					
				}
			}

			frame.setSize(8*70,8*70);
			frame.setVisible(true);

		}
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
			jframe.repaint();
		}
		public void mouseReleased(MouseEvent e){
			String[] pieceFileRank = getSquareInfoFromMouseEvent(lastEntered);
			String file = pieceFileRank[1];
			String rank = pieceFileRank[2];

			System.out.printf("%s %s is to\n",file,rank);
			logTo(file+rank);
			jframe.repaint();
		}
	}//end inner class
}

class SquarePanel extends JPanel {
	Square square;

	public SquarePanel(Square s){
		square = s;
	}

	public void paintComponent(Graphics g) {
		if (square.getColor().equals("BLACK")){
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
