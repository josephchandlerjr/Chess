package chess;

import javax.swing.*;
import java.awt.*;

import java.io.Console;
import java.util.List;
import chess.lib.ListFilter;


public class GuiChess extends Chess{

	/**
	 * main entry point which constructs a Game object and calls consolePlay()
	 * @param args is not used
	 */
	public static void main(String[] args) {
		//runs the show
		//constructs a game, whiteIO and blackIO
		GuiChess chess = new GuiChess();
		chess.play();
	}

	/**
	 * constructor
	 */
	public GuiChess(){
		super();
		GuiBoard gui = new GuiBoard();
		gui.buildGui();
	}

	public void play(){
	}



	class GuiBoard {

		public void buildGui(){
			JFrame frame = new JFrame();
			GridLayout grid = new GridLayout(8,8);
			grid.setVgap(1);
			grid.setHgap(1);
			JPanel panel = new JPanel(grid);
			frame.setContentPane(panel);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			for (int row = 0; row < 8; row++){
				for (int col = 0; col < 8; col++){
					ButtonSquare button = new ButtonSquare(game.getSquare(row,col));
					frame.getContentPane().add(button);
				}
			}

			frame.setSize(8*70,8*70);
			frame.setVisible(true);

		}
	}//end inner class
}

class ButtonSquare extends JPanel {
	Square square;

	public ButtonSquare(Square s){
		square = s;
	}

	public void paintComponent(Graphics g) {
		if (square.getColor().equals("BLACK")){
			g.setColor(Color.cyan);
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
