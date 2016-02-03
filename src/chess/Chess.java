package chess;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

import java.util.*;
import javax.imageio.*;

public class Chess {
	GuiBoard gui;

	String GAMELOGFILE = "GameLogs";
	Date date = new Date();

	JList<String> promotionList;
	JFrame promotionSelector;

	JFrame remoteInformationFrame;
	JTextField serverLocationField;
	JTextField playerNameField;
	final int PORTNUM = 5000;

	Game game;
	boolean localGame = true;
	private String myColor;

	ObjectOutputStream oos;
	ObjectInputStream ois;

	public static void main(String[] args) {
		Chess chess = new Chess();
	}

	/**
	 * constructor
	 */
	public Chess(){
		game = new Game();
		myColor = "WHITE";
		this.gui = new GuiBoard();
		this.gui.build();
		this.gui.initialize();
	}
	/**
	 * creates new game
	 */ 
	public void newGame(){
		game = new Game();
	}

	public void getRemoteInformation(){
		remoteInformationFrame = new JFrame();

		JLabel locationLabel = new JLabel("Enter Server Location");
		JLabel nameLabel = new JLabel("Enter Player Name");

		serverLocationField = new JTextField("127.0.0.1");
		serverLocationField.setMaximumSize(serverLocationField.getPreferredSize());
		playerNameField = new JTextField("username");
		serverLocationField.setMaximumSize(playerNameField.getPreferredSize());

		JButton enterButton = new JButton("Enter");
		enterButton.addActionListener(new RemoteInformationListener());

	        Box labelBox = new Box(BoxLayout.Y_AXIS);
		labelBox.add(locationLabel);
		labelBox.add(nameLabel);


		Box fieldBox = new Box(BoxLayout.Y_AXIS);
		fieldBox.add(serverLocationField);
		fieldBox.add(playerNameField);

		Container contentPane = remoteInformationFrame.getContentPane();
		contentPane.add(BorderLayout.WEST, labelBox);
		contentPane.add(BorderLayout.EAST, fieldBox);
		contentPane.add(BorderLayout.SOUTH, enterButton);

		remoteInformationFrame.pack();
		remoteInformationFrame.setVisible(true);

	}
	/**
	 * sets up networking for remote game, reads Color object from server to set player's color,
	 * creates thread to listen for moves coming from other player via server
	 */
	public void remoteSetup(String ServerAddr, String userName){

		newGame();
		localGame = false;
		String msg = "WAITING FOR OPPONENT TO ARRIVE";
		gui.setWhiteLabel(msg,false);
		gui.setBlackLabel(msg,false);

		gui.initialize();
		gui.frame.repaint();
		
		try{
			Socket sock = new Socket(ServerAddr,PORTNUM);
			oos = new ObjectOutputStream(sock.getOutputStream());
			ois = new ObjectInputStream(sock.getInputStream());  
			oos.writeObject(userName);
			Color color = (Color)(ois.readObject());
			if(color.equals(Color.WHITE)) {	
				myColor = "WHITE";
				String opponent = (String)(ois.readObject());
			        gui.whitePlayer = userName;
				gui.blackPlayer = opponent;
			}
			else{
				myColor = "BLACK";
				String opponent = (String)(ois.readObject());
				gui.blackPlayer = userName;
				gui.whitePlayer = opponent;
			}
			setStatus(); 
			Thread remoteMoveListener = new Thread(new RemoteMoveHandler());
			remoteMoveListener.start();


		}catch(ConnectException ex){
			JOptionPane.showMessageDialog( null,
					              "Cannot connect to server",
					              "Server Unreachable",
					              JOptionPane.WARNING_MESSAGE);
			gui.frame.dispose();
		}catch(SocketException ex){
			JOptionPane.showMessageDialog( null,
					              "Connection to server lost",
					              "Bad Connection",
					              JOptionPane.WARNING_MESSAGE);
			gui.frame.dispose();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
	/**
	 * executes a move
	 */	
	public synchronized void executeMove(String myColor, Square fromSquare, String fromNotation, String toNotation, Square toSquare) {
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

		Move move = new Move(notation, myColor, fromSquare, toSquare);

		boolean isPawn = ChessPiece.isPawn(fromSquare.getPiece());
		boolean executed = game.takeAction(move);
 
		// check if we need to promote pawn and if remote game send move made over socket
		if (executed){
			if(localGame){
				this.myColor = game.player; //was a bug, need to specifically set instance var
			}
			else {
				try{
					//fromRow,fromCol, fromNotion, toNotation, toSquare,fromSquare
					int[] coord = {fromSquare.getRow(),fromSquare.getCol(),
						       toSquare.getRow(), toSquare.getCol()};
					String[] notations = {fromNotation,toNotation};
					oos.writeObject(coord);
					oos.writeObject(notations);
				}catch(Exception ex){
					JOptionPane.showMessageDialog( null,
								      "Connection to server lost",
								      "Bad Connection",
								      JOptionPane.WARNING_MESSAGE);
					gui.frame.dispose();
				}
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
	public void remoteExecuteMove(int[] coord, String[] notations){
		executeMove(game.otherColor(myColor),
			    game.getSquare(coord[0],coord[1]),
	                    notations[0],
			    notations[1],
			    game.getSquare(coord[2],coord[3]));
					   
	}
	/**
	 * sets status label on gui
	 */
	public void setStatus(){
		String message = "";
		if(game.hasWon("BLACK")){
			gui.setBlackLabel("CHECKMATE! YOU WIN!",true);
			gui.setWhiteLabel("",true);
			game.addResultToScoreSheet("BLACK");
			writeLog();
		}
		else if(game.hasWon("WHITE")){
			gui.setWhiteLabel("CHECKMATE! YOU WIN!",true);
			gui.setBlackLabel("",true);
			game.addResultToScoreSheet("WHITE");
			writeLog();
		}
		else if (game.isInCheck("WHITE")){
			gui.setWhiteLabel("In Check!",false);
			gui.setBlackLabel("",false);
			}
			
		else if (game.isInCheck("BLACK")){
			gui.setBlackLabel("In Check!",false);
			gui.setWhiteLabel("",false);
			}
		else{
			gui.setBlackLabel("",false);
			gui.setWhiteLabel("",false);
		}
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
			JOptionPane.showMessageDialog( null,
					              "Failed to same game",
					              "Couldn't write game to game log",
					              JOptionPane.WARNING_MESSAGE);
			ex.printStackTrace();
		}finally{
			try{
			writer.close();
			}catch (Exception ex){
			}
		}
	}
	/**
	 * listens on button for new game requests and when button is pressed starts new game
	 */
	class NewGameListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			newGame();
			gui.initialize();
			setStatus();
			myColor = game.player;
			gui.frame.repaint();
		}
	}// end inner class NewGameListener

	/**
	 * listens for changes to list of piece names, dynamically updating promotion of pawn
	 */
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

	/**
	 * listens for button push that signals the promotion of pawn and then closes window
	 * no need to do more as pawn is is updated dynamically when selection on list is changed
	 */
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
				setStatus();
				gui.frame.repaint();

			}catch(FileNotFoundException ex){
				JOptionPane.showMessageDialog( null,
							      "Couldn't resume game",
							      "No game saved",
							      JOptionPane.WARNING_MESSAGE);
			}catch(Exception ex){
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
				JOptionPane.showMessageDialog( null,
							      "Couldn't save game",
							      "Game not saved",
							      JOptionPane.WARNING_MESSAGE);
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
			}catch(FileNotFoundException ex){
				JOptionPane.showMessageDialog( null,
							      "Couldn't read game log",
							      "Game log not found",
							      JOptionPane.WARNING_MESSAGE);
				ex.printStackTrace();
			}catch(Exception ex){
				ex.printStackTrace();
			}
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

			executeMove(myColor, fromSquare, fromNotation, file+rank, toSquare);

			this.fromNotation = "";
			this.fromSquare = null;
			this.toSquare = null;

		}  
	}//end inner class BoardListener

	class RemoteGameListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			getRemoteInformation();
		}
	}//end inner class RemoteGameListener

	class RemoteMoveHandler implements Runnable {
		int[] coord = new int[2];
		String[] notations = new String[2];
		public void run(){
			while(true){
				try{
					coord = (int[])(ois.readObject());
					notations = (String[])(ois.readObject());
					remoteExecuteMove(coord,notations);
				}catch(SocketException ex){
					JOptionPane.showMessageDialog( null,
								      "Connection to server lost",
								      "Bad Connection",
								      JOptionPane.WARNING_MESSAGE);
					gui.frame.dispose();
				}catch(Exception ex){
					gui.frame.dispose();
					ex.printStackTrace();
				}
			}
		}
	}

	class RemoteInformationListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			remoteInformationFrame.dispose();
			//gui.frame.setEnabled(true);
			String serverLocation = serverLocationField.getText();
			String playerName = playerNameField.getText();
			Thread thread = new Thread(new RemoteThread(serverLocation, playerName));
			thread.start();
		}
	}

	class GuiBoard {
		SquarePanel[][] guiBoardSquares = new SquarePanel[8][8];
		JFrame frame;
		JPanel board;
		JLabel blackStatus;
		JLabel whiteStatus;

		String blackPlayer = "Player 2:";
		String whitePlayer = "Player 1:";

		public void setWhiteLabel(String msg, boolean gameOver){
			String msg2 = "";
			if (!gameOver){ msg2 = String.format("%s'S MOVE",game.player);}
			String message = String.format("%s White %s %s",whitePlayer,msg,msg2);
			whiteStatus.setText(message);
		}
		public void setBlackLabel(String msg, boolean gameOver){
			String msg2 = "";
			if (!gameOver){ msg2 = String.format("%s'S MOVE",game.player);}
			String message = String.format("%s Black %s %s",blackPlayer,msg,msg2);
			blackStatus.setText(message);
		}

		public void build(){
			frame = new JFrame("CHESS");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			blackStatus = new JLabel();
			whiteStatus = new JLabel();

			setStatus();

			frame.getContentPane().add(BorderLayout.NORTH, blackStatus);
			frame.getContentPane().add(BorderLayout.SOUTH, whiteStatus);

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

			JButton newGameButton = new JButton("New Local Game");
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

			JButton remoteButton = new JButton("Remote Game");
			remoteButton.addActionListener(new RemoteGameListener());
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
			
			Image image = 
		        new ImageIcon(square.getPiece().getImageLocation()).getImage(); 

			g.drawImage(image,10,10,this);
		}
	}

}


class RemoteThread implements Runnable{
	String location;
	String name;

	public RemoteThread(String loc, String name){
		this.location = loc;
		this.name = name;
	}
	public void run(){
		Chess c = new Chess();
		c.remoteSetup(location,name);
		
	}
}
