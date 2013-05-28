package edu.harvard.ilevin.battleship2;

import java.io.IOException;
import java.io.InputStream;

import android.os.Bundle;
import android.os.Handler.Callback;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GameActivity extends Activity implements Callback {
	private GameModel game;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AssetManager assetManager = getAssets();
		try {
			InputStream ims = assetManager.open("level1.txt");
			game = new GameModel(ims);
			setContentView(R.layout.activity_game);
			createUIBoard();
			updateOutsideNumbers(R.id.ColumnNumbers,"column");
			updateOutsideNumbers(R.id.RowNumbers,"row");
			game.registerForTimeMessage(this);
			game.startTimer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void createUIBoard(){
		int rowsAndColsToBeAdded = game.getBoardSize();
		LinearLayout board = (LinearLayout) findViewById(R.id.boardLinearLayout);
		LinearLayout colNums = (LinearLayout) findViewById(R.id.ColumnNumbers);
		LinearLayout rowNums = (LinearLayout) findViewById(R.id.RowNumbers);
		
		// update the id bug fix

		for(int i=0;i<rowsAndColsToBeAdded;i++){
		
			// add more rows
			LinearLayout newRow = new LinearLayout(this);
			// add tiles to the new row
			for(int x=0;x<game.getBoardSize();x++){
				newRow.addView(tileFactory(Utils.XYtoPos(i, x,game.getBoardSize())));
			}
			// add row to the board
			board.addView(newRow);
			
			// update outside numbers
			LayoutParams textLayout = new ViewGroup.LayoutParams(100,100);
			float textSize = 40;
			
			TextView newRowNum = new TextView(this);
			newRowNum.setText("0");
			newRowNum.setLayoutParams(textLayout);
			newRowNum.setTextSize(textSize);
			rowNums.addView(newRowNum);
			
			TextView newColNum = new TextView(this);
			newColNum.setLayoutParams(textLayout);
			newColNum.setText("0");
			newColNum.setTextSize(textSize);
			colNums.addView(newColNum);
			
     	}
		
	}
	
	private ImageButton tileFactory(int position){
		ImageButton tile = new ImageButton(this);
		tile.setLayoutParams(new ViewGroup.LayoutParams(100,100));
		tile.setId(position);
		tile.setImageResource(getTileImage(game.getTileState(position)));
		tile.setOnClickListener(new View.OnClickListener() {
			
	        @Override
	        public void onClick(View v) {
				try {
		    		ImageButton button = (ImageButton)v;
		    		GameActivity gameUI = (GameActivity)button.getContext();
		    		GameModel game = gameUI.getGameModel();
		    		TileType t;
					t = game.rotateTile(button.getId());
		    		updateTile(button,t);
		    		checkRowColNumbers(button.getId());
		    		isWinner();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

	        }
	    });
		return tile;
	}
	
	private int getTileImage(TileType t){
		int results=0; 
		if(t==TileType.BLANK){
			results = R.drawable.playfield;
		}else if(t==TileType.SHIPBLOCK){
			results = R.drawable.tdbig_pencil;
		}else if(t==TileType.WATER){
			results = R.drawable.water2;
		  }
		
		return results; 
	}

	private void addHoverlisten() {
//		/ImageButton btest =(ImageButton)findViewById(R.id.ImageButton0);
//		btest.setOnDragListener(new View.OnDragListener() {
//			
//			@Override
//			public boolean onDrag(View paramView, DragEvent paramDragEvent) {
//				Log.v("Drag",game.toString());
//				return false;
//			}
//		});
		
	}

	private void updateOutsideNumbers(int viewID,String type) {
		LinearLayout colNums = (LinearLayout) findViewById(viewID);
		String newText = "N/A";
		for(int count=0;count<colNums.getChildCount();count++){
			TextView t = (TextView)colNums.getChildAt(count);
			if(type=="column"){
				newText = String.valueOf(game.getOutsideColumnNumber(count));
			}else if(type=="row"){
				newText = String.valueOf(game.getOutsideRowNumber(count));
			}
			t.setText(newText);
		}
	}
	


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}
	

	
	private void isWinner() {
		if(game.isWinner()){
			game.stopTimer();
			Intent winner = new Intent(this,Winner.class);
			winner.putExtra("Score",game.getTime());
			startActivity(winner);
		}
	}

	private void checkRowColNumbers(Integer pos) {
		boolean[] isOver = game.isRowColNumberOver(pos);
		int rowNum = game.getRowNum(pos);
		LinearLayout rowGroup = (LinearLayout)findViewById(R.id.RowNumbers);
		TextView rowText = (TextView)rowGroup.getChildAt(rowNum);
		
		
		int colNum = game.getColNum(pos);
		LinearLayout colgroup = (LinearLayout)findViewById(R.id.ColumnNumbers);
		TextView  colText = (TextView)colgroup.getChildAt(colNum);
		
		// update row
		if(isOver[0]){
			rowText.setTypeface(null, Typeface.BOLD);
			rowText.setTextColor(Color.RED);
		}else{
			rowText.setTypeface(null, Typeface.NORMAL);
			rowText.setTextColor(Color.BLACK);
		}
		
		// update column
		if(isOver[1]){
			colText.setTypeface(null, Typeface.BOLD);
			colText.setTextColor(Color.RED);
		}else{
			colText.setTypeface(null, Typeface.NORMAL);
			colText.setTextColor(Color.BLACK);
		}

	}
	
	

	private void updateTile(ImageButton button,TileType t){
		if(t==TileType.BLANK){
			button.setImageResource(R.drawable.playfield);
		}else if(t==TileType.SHIPBLOCK){
			button.setImageResource(R.drawable.tdbig_pencil);
		}else if(t==TileType.WATER){
			button.setImageResource(R.drawable.water2);
		  }
	}
	
	public GameModel getGameModel(){
		return this.game;
	}


	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		TextView time = (TextView)findViewById(R.id.clock);
		time.setText(String.valueOf(msg.arg1));;
		return true;
	}
	
	public void backToHome(View v){
		Intent home = new Intent(this,MainActivity.class);
		startActivity(home);
	}
}
