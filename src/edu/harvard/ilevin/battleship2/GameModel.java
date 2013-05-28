package edu.harvard.ilevin.battleship2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.Timer;


import android.content.res.AssetManager;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;

public class GameModel implements Callback{
	//TODO: make boards private
	Board currentBoard;
	Board answer;
	private final static int OneSecondinMilisec = 1000;
	private Handler ticker;
	private Message timeTracker;
	private Callback timerListen;
	private int savedTime;
	
	
	public GameModel(InputStream levelData){
		try {
			setuplevel(levelData);
			revealAnswers();
			timeTracker = Message.obtain();
			// arg1 will kept track of the time
			timeTracker.arg1 = 00;
			// just an ID for the message so we can remove it 
			timeTracker.what = 100;
			ticker = new Handler(this);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private void revealAnswers() {
		// TODO Auto-generated method stub
	    
		int blocksToReveal = 1 + (int)(Math.random() * ((4 - 1) + 1));
		for(int i=0;i<blocksToReveal;i++){
			int randomPos = (int) (Math.random() * 100 % answer.size());
			Tile answerTile = answer.getTile(randomPos);
			answerTile.setCanChange(false);
			currentBoard.setTile(randomPos, answerTile);

		}
	}
	
	public TileType getTileState(int pos){
		return currentBoard.getTile(pos).type;
	}


	private void setuplevel(InputStream levelData) throws IOException {
		// TODO Auto-generated method stub
		  InputStreamReader levelReader = new InputStreamReader(levelData);
		  BufferedReader levelString = new BufferedReader(levelReader);
		  
		  // set up size first
		  String line = levelString.readLine();
			this.currentBoard = new Board(Integer.parseInt(line));
			this.answer = new Board(Integer.parseInt(line));
			this.answer.setAllBlocks(TileType.WATER);
		 // skip one line
		 levelString.readLine();
		 // add ship blocks to answer board
		  while ((line = levelString.readLine()) != null) {
	            int pos = Integer.parseInt(line);
	            answer.setTile(pos, new Tile(TileType.SHIPBLOCK));
	        }
	}


	/*
	 * Returns the next state of the Tile
	 */
	public TileType rotateTile(int pos) throws Exception{
			return this.currentBoard.rotateTile(pos);
	
	}
	
	private void setupAnswerBoard(){
		answer.setAllBlocks(TileType.WATER);
		answer.setTile(1, new Tile(TileType.SHIPBLOCK));
	}
	
	public int getOutsideRowNumber(int row){
		return answer.getOutsideRowNumber(row);
	}
	
	public int getOutsideColumnNumber(int column){
		return answer.getOutsideColumnNumber(column);
	}
	
	public boolean[] isRowColNumberOver(int pos){
		int row = currentBoard.posToXY(pos).x;
		int column = currentBoard.posToXY(pos).y;
		boolean isRow = false;
		boolean isCol = false;
		if(currentBoard.getOutsideRowNumber(row)>answer.getOutsideRowNumber(row)){
			isRow=true;
		}
		if(currentBoard.getOutsideColumnNumber(column)>answer.getOutsideColumnNumber(column)){
			isCol = true;
		}
		return new boolean[]{isRow,isCol};
	}
	
	public int getRowNum(int pos){
		return currentBoard.posToXY(pos).x;
	}
	
	public int getColNum(int pos){
		return currentBoard.posToXY(pos).y;
	}
	
	public boolean isWinner() {
		return answer.equals(currentBoard);
		
	}
	
	public int getBoardSize(){
		return currentBoard.size();
	}
	

	@Override
	public boolean handleMessage(Message timer) {
		Message nextMessage = Message.obtain();
		// update the time
		timer.arg1++;
		nextMessage.arg1=timer.arg1;
		savedTime=timer.arg1;
		nextMessage.what=100;
		// send to any listens
		if(timerListen!=null){
			timerListen.handleMessage(timer);
		}
		
		ticker.sendMessageDelayed(nextMessage,OneSecondinMilisec);
		return true;
	}
	
	public void startTimer(){
		ticker.sendMessageDelayed(timeTracker, OneSecondinMilisec);
	}
	
	public void stopTimer(){
		ticker.removeMessages(100);
	}
	
	public void registerForTimeMessage(Callback i){
		timerListen = i;
	}
	
	public int getTime(){
		return savedTime;
	}
	
	

}

