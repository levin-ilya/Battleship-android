package edu.harvard.ilevin.battleship2;

import java.util.Arrays;

import android.graphics.Point;


public class Board {
	private int boardsize;
	private Tile tiles[][];
	private int[] outsideRowNumbers;
	private int[] outsideColumnNumbers;

	
	public Board(int size){
		this.boardsize = size;
		this.tiles = new Tile[size][size];
		outsideColumnNumbers = new int[size] ;
		outsideRowNumbers = new int[size];
		Arrays.fill(this.outsideColumnNumbers, 0);
		Arrays.fill(this.outsideRowNumbers,0);
		for(int row=0;row<size;row++){
			for(int column=0;column<size;column++){
				tiles[row][column]= new Tile();
			}
		}

	}
	
	public void setTile(int pos,Tile t){
		int row = Utils.posToXY(pos,boardsize).x;
		int column = Utils.posToXY(pos,boardsize).y;
		tiles[row][column] = t;
		if(t.type==TileType.SHIPBLOCK){
			outsideRowNumbers[row] = outsideRowNumbers[row]+1;
			outsideColumnNumbers[column] = outsideColumnNumbers[column]+1;
		}
		
	}
	
	public Tile getTile(int pos){
		int row = Utils.posToXY(pos,boardsize).x;
		int column = Utils.posToXY(pos,boardsize).y;
		return tiles[row][column];
	}
	
	public TileType rotateTile(int pos) throws Exception{
		int row = Utils.posToXY(pos,boardsize).x;
		int column = Utils.posToXY(pos,boardsize).y;
		TileType newState = tiles[row][column].rotateTile();
		if((newState==TileType.BLANK||newState==TileType.WATER) && outsideRowNumbers[row]>0 && outsideColumnNumbers[column]>0){
			outsideRowNumbers[row] = outsideRowNumbers[row]-1;
			outsideColumnNumbers[column] = outsideColumnNumbers[column]-1;
		}else if(newState==TileType.SHIPBLOCK){
			outsideRowNumbers[row] = outsideRowNumbers[row]+1;
			outsideColumnNumbers[column] = outsideColumnNumbers[column]+1;
		}
		return newState;
		
	}

	


	public int getOutsideRowNumber(int row) {
		return outsideRowNumbers[row];
	}

	public int getOutsideColumnNumber(int column) {
		return outsideColumnNumbers[column];
	}
	
	public Point posToXY(int pos){
		return Utils.posToXY(pos, boardsize);
	}
	
	public void setAllBlocks(TileType t){
		for(Tile[] row:tiles){
			for(Tile tile:row){
				tile.setType(t);
			}
		}
	}
	
	public boolean equals(Board v){
		boolean results = true;
		for(int x=0;x<tiles.length;x++){
			for(int y=0;y<tiles[x].length;y++){
				if(this.tiles[x][y].getType()!=v.tiles[x][y].getType()){
					results=false;
					break;
				}
			}
			
		}
		return results;
	}
	
	public int size(){
		return boardsize;
	}
	
}
