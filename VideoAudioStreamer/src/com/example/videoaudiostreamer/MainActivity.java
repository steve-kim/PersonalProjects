package com.example.videoaudiostreamer;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.VideoView;

public class MainActivity extends Activity {
	private static final String TAG = "VideoViewDemo";
	private static Log debugLog;
	
	private static VideoView video;
	private ImageButton play;
	private ImageButton pause;
	private ImageButton restart;
	private ImageButton stop;
	
	private String videoPath = "http://daily3gp.com/vids/Funny women cannot understand.3gp";
	private int pauseLocation = 0;
	private boolean videoUrlSet = false;
	private boolean isPaused = false;
	private boolean isStopped = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		play = (ImageButton) findViewById(R.id.play);
		pause = (ImageButton) findViewById(R.id.pause);
		restart = (ImageButton) findViewById(R.id.restart);
		stop = (ImageButton) findViewById(R.id.stop);
		
		//get the context to play the video in
		video = (VideoView) findViewById(R.id.videoView1);
		
		
		
		play.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				playVideo();
			}

		});
		
		pause.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				pauseVideo();
			}
		});
		
		restart.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				restartVideo();
			}
		});
		
		stop.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				stopVideo();
			}
		});
		
		
		
		//Set the URL for the video to stream from
		video.setVideoURI(Uri.parse(videoPath));
		
	}

	//Starts the video playback
	private void playVideo() {
		//Set the URL for the video to stream from
		//We only need to set the URL if we have previously stopped the video
		if (video != null && isStopped)
			video.setVideoURI(Uri.parse(videoPath));
		
		if (video != null) {
			//We have paused this video before, start from the saved location
			if (pauseLocation > 0) {
				debugLog.v(TAG, "Resuming Playback");
				video.seekTo(pauseLocation);
				video.start();
				video.requestFocus();
				isPaused = false;
				return;
			}
			//We have not paused the video, start from the beginning
			else if (pauseLocation == 0) {
				debugLog.v(TAG, "Beginning Playback");
				//We need to reset the pause location so that subsequent plays start from the beginning
				pauseLocation = 0;
				isStopped = false;
				video.start();
				video.requestFocus();
				return;
			}
		}
	}
	
	//Pauses the video playback
	private void pauseVideo() {
		if (video != null && isPaused) {
			debugLog.v(TAG, "Resuming Playback");
			video.seekTo(pauseLocation);
			video.start();
			isPaused = false;
		}
		
		else if (video != null && !isPaused) {
			debugLog.v(TAG, "Pausing Playback");
			video.pause();
			pauseLocation = video.getCurrentPosition();
			isPaused = true;
		}
	}

	//Restarts the video playback
	private void restartVideo() {
		debugLog.v(TAG, "Restarting Playback");
		if (video != null)
			video.seekTo(0);
	}

	//Stops the video playback
	private void stopVideo() {
		debugLog.v(TAG, "Stopping Playback");
		if (video != null) {
			video.stopPlayback();
			pauseLocation = 0;
			isStopped = true;
		}
	}
}
