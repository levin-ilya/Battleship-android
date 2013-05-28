package edu.harvard.ilevin.battleship2;

import android.graphics.Point;

public class Utils {
	

	
	/*
	 * pos on the board is:
	 * 1 2 3 4 5
	 * 6 7 8 9 10
	 * 
	 * From pos we need the X and Y for the 2D array 'tiles'
	 * example pos=8 = [1,2] = row 1, column 2
	 */
	public static Point posToXY(int pos,int boardsize){
		int column = pos % boardsize;
		int row = (pos/boardsize);
		return new Point(row,column);
	}
	
	public static int XYtoPos(int row,int col,int boardsize){
		return (row*boardsize)+col;
	}

}
