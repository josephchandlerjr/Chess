package chess;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

import java.io.Console;
import java.util.*;


public class Chess {
	GuiBoard gui;

	String GAMELOGFILE = "GameLogs";
	Date date = new Date();

	JList<String> promotionList;
	JFrame promotionSelector;

	Game game;
	boolean localGame = true;
	String myColor = "WHITE";


	/**
	 * main entry point which constructs a Game object and calls consolePlay()
	 * @param args is not used
	 */
	public static void main(String[] args) {
		//runs the show
		//constructs a game, whiteIO and blackIO
		Chess chess = new Chess();
	}

	/**
	 * constructor
	 */
	public Chess(){
		game = new Game();
		this.gui = new GuiBoard();
		this.gui.build();
		this.gui.initialize();
	}
	public void newGame(){
		game = new Game();
	}
	/**
	 * executes move
	 */	
	public void executeMove(Square fromSquare, String fromNotation, String toNotation, Square toSquare) {
		// make sure correct player is moving
		if(!fromSquare.isOccupied()){ return;} //must be moving a piece
		String pieceColor = fromSquare.getPieceColor();
		
		// must be piece of my color and must be my turn
	        if(!pieceColor.equals(myColor) || !pieceColor.equals(game.player)){ return;} 
		
		ChessNotation notation = new ChessNotation(fromNotation+toNotation);
		if ("Ke1c1Ke8c8".contains(fromNotation+toNotation)){
			notation = new ChessNotation("O-O-O");
		}
		else if ("Ke1g1Ke8g8".contains(fromNotation+toNotation)){
			notation = new ChessNotation("O-O");
		}

		Move move = new Move(notation, game.player, fromSquare, toSquare);

		boolean isPawn = ChessPiece.isPawn(fromSquare.getPiece());
		boolean executed = game.takeAction(move);
 
		// check if we need to promote pawn
		if (executed){
			if(localGame){
				myColor = game.player;
			}
			if(isPawn){
				int row = toSquare.getRow(); 
				if( row == 0 || row == 7){
					promotePawn(toSquare);
					game.updateCheckStatus();
				}
			}
			gui.frame.repaint();
			setStatus();
		}
	}
	/**
	 * sets status label on gui
	 */
	public void setStatus(){
		String message = "";
		if(game.hasWon("BLACK")){
			gui.status.setText("CHECKMATE! BLACK WINS!");
			game.addResultToScoreSheet("BLACK");
			writeLog();

		}
		else if(game.hasWon("WHITE")){
			gui.status.setText("CHECKMATE! WHITE WINS!");
			game.addResultToScoreSheet("WHITE");
			writeLog();
		}
		else{
			if (game.isInCheck("WHITE") || game.isInCheck("BLACK")){
				message = message + "CHECK!";
			}
			gui.status.setText(String.format("%s %s'S MOVE",message, game.player));
		}
		
	}

	/**
	 * attempts to connect to play remote game
	 */
	public void requestRemoteGame(){
		Thread thread = new Thread(new RemoteGame());
		thread.run();
	}

	/**
	 * promotes a pawn
	 */
	private void promotePawn(Square toSquare){	
		promotionSelector = new JFrame("choose promotion");
		String[] pieces = {"Queen","Rook","Knight","Bishop"};

		promotionList = new JList<String>(pieces);
		promotionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		PromotionListener listener = new PromotionListener(toSquare);
		promotionList.addListSelectionListener(listener);
		
		JScrollPane scroller = new JScrollPane(promotionList);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		JButton selectPromotion = new JButton("Promote");
		selectPromotion.addActionListener(new PromoteButtonListener());
		
		promotionSelector.getContentPane().add(BorderLayout.CENTER,scroller);
		promotionSelector.getContentPane().add(BorderLayout.SOUTH,selectPromotion);

		promotionSelector.pack();
		promotionSelector.setVisible(true);

		gui.frame.setEnabled(false);
	}

	/**
	 * appends game results as algebraic notation to a file
	 */
	public void writeLog(){
		BufferedWriter writer = null;
		try{
			String gameLog = game.getGameLog();
			writer = new BufferedWriter(new FileWriter(GAMELOGFILE,true));
			writer.write(String.format("%tc\n",date));
			writer.write(gameLog);
		}catch(Exception ex){
			System.out.println("failed to write to gamelog");
			ex.printStackTrace();
		}finally{
			try{
			writer.close();
			}catch (Exception ex){
			}
		}
	}

	class NewGameListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			newGame();
			gui.initialize();
			gui.status.setText(String.format("%s'S MOVE",game.player));
			gui.frame.repaint();
		}
	}// end inner class NewGameListener

	class PromotionListener implements ListSelectionListener {
		Square square;
		public PromotionListener(Square square)
		{
			super();
			this.square = square;
		}
		public void valueChanged(ListSelectionEvent lse){
			if( !lse.getValueIsAdjusting()){
				String promoteTo = (String) promotionList.getSelectedValue(); 
				if(square.getPieceColor().equals("WHITE")){	
					if(promoteTo.equals("Queen")) { 
						square.setPiece(ChessPiece.WHITEQUEEN);}
					if(promoteTo.equals("Rook")) { 
						square.setPiece(ChessPiece.WHITEROOK);}
					if(promoteTo.equals("Bishop")) { 
						square.setPiece(ChessPiece.WHITEBISHOP);}
					if(promoteTo.equals("Knight")) { 
						square.setPiece(ChessPiece.WHITEKNIGHT);}
				}
				else{
					if(promoteTo.equals("Queen")) { 
						square.setPiece(ChessPiece.BLACKQUEEN);}
					if(promoteTo.equals("Rook")) { 
						square.setPiece(ChessPiece.BLACKROOK);}
					if(promoteTo.equals("Bishop")) { 
						square.setPiece(ChessPiece.BLACKBISHOP);}
					if(promoteTo.equals("Knight")) { 
						square.setPiece(ChessPiece.BLACKKNIGHT);}
				}
				gui.frame.repaint();
			}
		}
	}//end inner class PromotionListener

	class PromoteButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			gui.frame.setEnabled(true);
		        promotionSelector.dispose();	
		}
	}// end inner class PromoteButtonListener

	class ResumeGameListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			ObjectInputStream is = null;
			try{
				is = new ObjectInputStream(new FileInputStream(new File("SavedGame.ser")));
				game = (Game) is.readObject();
				gui.initialize();
				myColor = game.player;
				gui.status.setText(String.format("%s'S MOVE",game.player));
				gui.frame.repaint();

			}catch(Exception ex){
				System.out.println("couldn't resume game");
				ex.printStackTrace();
			}finally{
				try{
					is.close();
				}catch(Exception ex){
				}
			}
		}
	}//end inner class ResumeGameListener
	
	class SaveGameListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			ObjectOutputStream os = null;
			try{
				os = new ObjectOutputStream(new FileOutputStream(new File("SavedGame.ser")));
				os.writeObject(game);
			}catch(Exception ex){
				System.out.println("couldn't save game");
				ex.printStackTrace();
			}finally{
				try{
					os.close();
				}catch(Exception ex){
				}
			}
		}
	}//end inner class ResumeGameListener

	class ViewGameLogListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			String text = "\n";
			BufferedReader reader = null;
			try{
				reader = new BufferedReader(new FileReader(GAMELOGFILE));
				String line = null;
				while ((line = reader.readLine()) != null){
					text = text + line + "\n";
				}
			}catch(Exception ex){
				text = "couldn't read game log file";
				ex.printStackTrace();
			}finally{
				try{
					reader.close();
				}catch (Exception ex){
				}

				JFrame frame = new JFrame("Game logs");
				JTextArea textArea = new JTextArea(10,20);
				textArea.setText(text);
				textArea.setLineWrap(true);

				JScrollPane scrollPane = new JScrollPane(textArea);
				scrollPane.setVerticalScrollBarPolicy(
						ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
				scrollPane.setHorizontalScrollBarPolicy(
						ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

				frame.getContentPane().add(BorderLayout.CENTER,scrollPane);
				frame.pack();
				
				frame.setVisible(true);
			}

				
		}
	}//end inner class ViewGameLogListener

	class RequestRemoteGameListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			requestRemoteGame();
		}
	}

	class BoardListener implements MouseListener {
		MouseEvent lastEntered;
		String fromNotation = "";
		Square fromSquare;
		Square toSquare;
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

			//logFrom(pieceID, file+rank);

			if(!pieceID.equals("P")){
				this.fromNotation = pieceID; 
			}
			this.fromNotation = this.fromNotation + file+rank;
		}
		public void mouseReleased(MouseEvent e){
			Square to = squareFromEvent(lastEntered);
			toSquare = to; // set instance variable on GuiChess

			String[] pieceFileRank = getNotationFromSquare(to);
			String file = pieceFileRank[1];
			String rank = pieceFileRank[2];

			executeMove(fromSquare, fromNotation, file+rank, toSquare);

			this.fromNotation = "";
			this.fromSquare = null;
			this.toSquare = null;

		}  
	}//end inner class BoardListener

	class GuiBoard {
		SquarePanel[][] guiBoardSquares = new SquarePanel[8][8];
		JFrame frame;
		JPanel board;
		JLabel status;

		public void build(){
			frame = new JFrame("CHESS");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			status = new JLabel();
			status.setText(String.format("%s'S MOVE",game.player));

			frame.getContentPane().add(BorderLayout.NORTH, status);

			GridLayout grid = new GridLayout(8,8);
			grid.setVgap(1);
			grid.setHgap(1);

			board = new JPanel(grid);
			board.setPreferredSize(new Dimension(70*8,70*8));
		        board.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

			BoardListener listener = new BoardListener();
			for (int row = 0; row < 8; row++){
				for (int col = 0; col < 8; col++){
					SquarePanel p = new SquarePanel();
					p.addMouseListener(listener);
					board.add(p);
					guiBoardSquares[row][col] = p;
				}
			}
			frame.getContentPane().add(BorderLayout.CENTER, board);

			Box buttonBox = new Box(BoxLayout.Y_AXIS);

			JButton newGameButton = new JButton("New Game");
			newGameButton.addActionListener(new NewGameListener());
			buttonBox.add(newGameButton);

			JButton viewGameLogButton = new JButton("View Game Log");
			viewGameLogButton.addActionListener(new ViewGameLogListener());
			buttonBox.add(viewGameLogButton);

			JButton saveGameButton = new JButton("Save Game");
			saveGameButton.addActionListener(new SaveGameListener());
			buttonBox.add(saveGameButton);

			JButton resumeGameButton = new JButton("Resume Game");
			resumeGameButton.addActionListener(new ResumeGameListener());
			buttonBox.add(resumeGameButton );

			JButton remoteButton = new JButton("Request Remote Game");
			remoteButton.addActionListener(new RequestRemoteGameListener());
			buttonBox.add(remoteButton);

			frame.getContentPane().add(BorderLayout.EAST, buttonBox);

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
	}//end inner class GuiBoard

	class RemoteGame implements Runnable {
		public void run(){
			Chess remoteGame = new Chess();
			remoteGame.gui.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}
	}
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
