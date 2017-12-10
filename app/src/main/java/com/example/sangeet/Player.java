package com.example.sangeet;

import java.io.File;
import java.util.ArrayList;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;

public class Player extends Activity implements View.OnClickListener{
	static MediaPlayer mp;
	ArrayList<File> mySongs;
	SeekBar sb;
	Button btplay,btff,btnxt,btprev,btfb;
	int position;
	Uri u;
	Thread UpdateSeekBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player);
		btplay=(Button) findViewById(R.id.btplay);
		btff=(Button) findViewById(R.id.btff);
		btnxt=(Button) findViewById(R.id.btnxt);
		btprev=(Button) findViewById(R.id.btprv);
		btfb=(Button) findViewById(R.id.btfb);
		
		btplay.setOnClickListener(this);
		btplay.setClickable(true);
		btff.setOnClickListener(this);
		btnxt.setOnClickListener(this);
		btprev.setOnClickListener(this);
		btfb.setOnClickListener(this);
		
		if(mp!=null){
			mp.stop();
			mp.release();
			
		}
		
		sb =(SeekBar)findViewById(R.id.seekBar1);
		UpdateSeekBar=new Thread(){
			@Override
			public void run(){
				int totalDuration=mp.getDuration();
				int currentPosition=0;
				sb.setMax(totalDuration);
				while(currentPosition<totalDuration){
					try {
						sleep(1000);
						currentPosition = mp.getCurrentPosition();
						sb.setProgress(currentPosition);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		};
		
		
		
		Intent i=getIntent();
		Bundle b= i.getExtras();
		mySongs= (ArrayList) b.getParcelableArrayList("songlist");
		position = b.getInt("pos", 0);
		
		
		u = Uri.parse(mySongs.get(position).toString());
		mp= MediaPlayer.create(getApplicationContext(),u);
		mp.start();
		sb.setMax(mp.getDuration());
		UpdateSeekBar.start();
		sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
			
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				// TODO Auto-generated method stub
				
				
			}
		});
		
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.player, menu);
		return true;
	}


	@Override
	public void onClick(View v) {
	int id = v.getId();
	switch(id){
	case R.id.btplay:
		if(mp.isPlaying()){
			mp.pause();
			btplay.setBackgroundResource(R.drawable.ipl);

		}
		else{
			mp.start();
			btplay.setBackgroundResource(R.drawable.ipa);
		}
		break;
		
	case R.id.btff:
		mp.seekTo(mp.getCurrentPosition()+10000);
		break;
		
	case R.id.btfb:
		mp.seekTo(mp.getCurrentPosition()-10000);
		break;
	case R.id.btnxt:
		mp.stop();
		mp.release();
		position=(position+1)%mySongs.size();
		u = Uri.parse(mySongs.get((position+1)).toString());
		mp= MediaPlayer.create(getApplicationContext(),u);
		mp.start();
		sb.setMax(mp.getDuration());
		break;
		
	case R.id.btprv:
		mp.stop();
		mp.release();
		position=(position-1<0)?mySongs.size()-1:position-1;
		/*if(position-1<0){
			position = mySongs.size()-1;
		}
		else{
			position=position-1;
		}*/
		u = Uri.parse(mySongs.get((position-1)).toString());
		mp= MediaPlayer.create(getApplicationContext(),u);
		mp.start();
		sb.setMax(mp.getDuration());
		break;
		
		
		
	}
	
		
	}

}
