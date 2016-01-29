package chess;

import java.net.*;
import java.io.*;
import java.awt.*;

/**
 * simple server to pair two Chess instances and orchestrate a game
 * essentially passes move objects back and forth
 */
public class ChessServer {
	final int PORTNUM = 5000;
	ObjectOutputStream whiteOos;
	ObjectOutputStream blackOos;
	ObjectInputStream whiteOis;
	ObjectInputStream blackOis;

	public static void main(String[] args){
		ChessServer server = new ChessServer();
		server.go();
	}

	public void go(){
		try{
			ServerSocket serverSocket = new ServerSocket(PORTNUM);

			while(true){

				Socket whiteSocket = serverSocket.accept();
				whiteOos = new ObjectOutputStream(whiteSocket.getOutputStream());
				whiteOis = new ObjectInputStream(whiteSocket.getInputStream()); 
				String whiteName = (String)(whiteOis.readObject());
				Thread whiteListener = new Thread(new ClientHandler(whiteOis));
				whiteListener.setName("WHITE");

				Socket blackSocket = serverSocket.accept();
				blackOos = new ObjectOutputStream(blackSocket.getOutputStream());
				blackOis = new ObjectInputStream(blackSocket.getInputStream());
				String blackName = (String)(blackOis.readObject());
				Thread blackListener = new Thread(new ClientHandler(blackOis));
				blackListener.setName("BLACK");


				whiteOos.writeObject(Color.WHITE);
				whiteOos.writeObject(blackName);

				blackOos.writeObject(Color.BLACK);
				blackOos.writeObject(whiteName);

				whiteListener.start();
				blackListener.start();

			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public synchronized void passMove(int[] coord, String[] notations, String color){
		try{
			if(color.equals("WHITE")){
			       blackOos.writeObject(coord);
			       blackOos.writeObject(notations);
			}		
			else{
			       whiteOos.writeObject(coord);
			       whiteOos.writeObject(notations);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	class ClientHandler implements Runnable {
		ObjectInputStream is;

		public ClientHandler(ObjectInputStream ois){
			is = ois;
		}
		public void run(){
			while(true){
				try{
					int[] coords = (int[])(is.readObject());
					String[] notations = (String[])(is.readObject());
					passMove(coords, notations, Thread.currentThread().getName());
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		}
	}
}


