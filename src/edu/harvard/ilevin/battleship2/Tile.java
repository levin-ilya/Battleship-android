package edu.harvard.ilevin.battleship2;


public class Tile {
	TileType type;
	private boolean canChange;
	
	public Tile(){
		this.type = TileType.BLANK;
		this.canChange=true;
	}
	
	public Tile(TileType type){
		this.type = type;
		this.canChange=true;
	}
	
	public void setCanChange(boolean canchange){
		this.canChange=canchange;
	}
	
	public boolean canchange(){
		return canChange;
	}
	
	public void setType(TileType t){
		this.type=t;
	}
	
	public TileType getType(){
		return this.type;
	}
	
	public TileType rotateTile(){
		if(canchange()){
			if(type==TileType.WATER){
				type = TileType.SHIPBLOCK;	
			}else if(type==TileType.SHIPBLOCK){
				type = TileType.BLANK;
			}else if(type==TileType.BLANK){
				type = TileType.WATER;	
			}
		}
		return type;
	}
	
	
	 public boolean equals(Object obj){
		 Tile t = (Tile)obj;
		 return this.getType()==t.getType();
	 }

}
