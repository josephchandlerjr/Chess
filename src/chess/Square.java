package chess;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

/** 
 * represents a square on the chess board
 */
public class Square extends JPanel implements Serializable
{
	private final String color; //the string BLACK or WHITE
	private final int ROW;
	private final int COL;
	private ChessPiece piece;
	private ChessPiece previousPiece;
	private Square NORTH;
	private Square SOUTH;
	private Square EAST;
	private Square WEST;


	/**
	 * create new square
	 * @param color color of square
	 * @param row row square resides in
	 * @param col col square resides in 
	 */
	public Square(String color, int row, int col)
	{
		this.color = color;
		this.ROW = row;
		this.COL = col;
		this.piece = null;
	}

	public void setNORTH(Square n){ NORTH = n;}
	public void setSOUTH(Square s){ SOUTH = s;}
	public void setEAST(Square e){ EAST = e;}
	public void setWEST(Square w){ WEST = w;}
	public Square north(){return NORTH;}
	public Square south(){return SOUTH;}
	public Square east(){return EAST;}
	public Square west(){return WEST;}
	public Square northEast(){
		if(NORTH == null){
			return null;
		}
		else{
			return NORTH.EAST;
		}
	}
	public Square northWest(){
		if(NORTH == null){
			return null;
		}
		else{
			return NORTH.WEST;
		}
	}
	public Square southWest(){
		if(SOUTH == null){
			return null;
		}
		else{
			return SOUTH.WEST;
		}
	}
	public Square southEast(){
		if(SOUTH == null){
			return null;
		}
		else{
			return SOUTH.EAST;
		}
	}

	public ChessPiece getPreviousPiece()
	{
		return previousPiece;
	}
	/**
	 * places piece on this square
	 * @param piece ChessPiece object to put on this square
	 */
	public void setPiece(ChessPiece piece)
	{
		this.previousPiece = this.piece;
		this.piece = piece;
	}

	public String getColor(){
		return color;
	}
	/**
	 * gets piece on this square
	 * @return piece on this square or null if there is none
	 */
	public ChessPiece getPiece()
	{
		return piece;
	}
	/**
	 * gets the string representation of piece on this square
	 * @return string representation of piece
	 */
	public String toString()
	{
		if (piece == null)
		{ return null;}
		return piece.toString();
	}

	/**
	 * gets row
	 * @return the row number of piece
	 */
	public int getRow()
	{
		return ROW;
	}

	/**
	 * gets column of piece
	 * @return the column number of piece
	 */
	public int getCol()
	{
		return COL;
	}

	/**
	 * gets color of square
	 * @return color of square
	 */
	public String getSquareColor()
	{
		return color;
	}
	/**
	 * gets color of piece sitting on square
	 * @return color of piece on square
	 */
	public String getPieceColor()
	{
		if (piece == null){
			return "";
		}
		return piece.getColor();
	}

	/**
	 * checks if given position is occupied by a piece 
	 * @param piece ChessPiece reference at given location on board
	 * @return true if parameter given is not a pointer to null
	 */
	public boolean isOccupied()
	{
		return piece != null;
	}

	/**
	 * get copy of this square
	 * @return copy of this square
	 */
	public Square copy()
	{
		Square newSquare = new Square(color, ROW, COL);
		newSquare.setPiece(piece);
		return newSquare;
	}
	/**
	 * for gui
	 */
	public void paintComponent(Graphics g) {
		if (getColor().equals("BLACK")){
			g.setColor(new Color(102,51,0));
		}
		else {
			g.setColor(Color.white);
		}
		g.fillRect(0,0,70,70);
		if (isOccupied()) {
			Image image = new ImageIcon(getPiece().getImageLocation()).getImage();	
			g.drawImage(image,10,10,this);
		}
	}	
}

