package chess;

import java.net.*;
import java.io.*;
import java.awt.*;

/**
 * simple server to pair two Chess instances and orchestrate a game
 * essentially passes move objects back and forth
 */
public class ChessServer {
	ObjectOutputStream[] oos = new ObjectOutputStream[2];

	public static void main(String[] args){
		ChessServer server = new ChessServer();
		server.go();
	}

	public void go(){
		try{
			ServerSocket serverSocket = new ServerSocket(5000);

			while(true){

				Socket whiteSocket = serverSocket.accept();
				System.out.println("GOT ONE");
				oos[0] = new ObjectOutputStream(whiteSocket.getOutputStream());
				Thread white = new Thread(new ClientHandler(whiteSocket));
				white.setName("WHITE");
				System.out.printf("ONE CONNECTION ESTABLISHED port=%s", white);

				Socket blackSocket = serverSocket.accept();
				oos[1] = new ObjectOutputStream(blackSocket.getOutputStream());
				Thread black = new Thread(new ClientHandler(blackSocket));
				System.out.printf("ONE CONNECTION ESTABLISHED port=%s", black);
				black.setName("BLACK");
				System.out.println("BOTH CONNECTIONS ESTABLISHED");

				oos[0].writeObject(Color.WHITE);
				System.out.println("JUST WROTE TO OOS 0");
				white.start();
				System.out.println("BACK FROM white.run");
				oos[1].writeObject(Color.BLACK);
				black.start();
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public synchronized void passMove(int[] coord, String[] notations, String color){
		try{
			if(color.equals("WHITE")){
			       oos[1].writeObject(coord);
			       oos[1].writeObject(notations);
			}		
			else{
			       oos[0].writeObject(coord);
			       oos[0].writeObject(notations);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	class ClientHandler implements Runnable {
		ObjectInputStream is;

		public ClientHandler(Socket sock){
			try{
				is = new ObjectInputStream(sock.getInputStream());
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		public void run(){
			System.out.println("HERE IN RUN");
			while(true){
				try{
					int[] coords = (int[])(is.readObject());
					String[] notations = (String[])(is.readObject());
					System.out.println("server got move");
					passMove(coords, notations, Thread.currentThread().getName());
					System.out.println("server wrote move");
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		}
	}
}


