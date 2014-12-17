package com.android.pennaed.InstructionVideo;

import android.app.Fragment;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import com.android.pennaed.R;

/**
 * Created by anand_000 on 2014-11-08.
 */
public class InstructionVideo extends Fragment {
	VideoView videoview;
	String SrcPath = "rtsp://r1---sn-jc47eu7e.c.youtube.com/CiILENy73wIaGQkOdxPwhfjKmRMYESARFEgGUgZ2aWRlb3MM/0/0/0/video.3gp";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_instructionvideo_main, container,
				false);
		videoview = (VideoView) view.findViewById(R.id.VideoView);

		try {
			// Start the MediaController
			MediaController mediacontroller = new MediaController(
					this.getActivity());
			mediacontroller.setAnchorView(videoview);
			// Get the URL from String VideoURL
			Uri video = Uri.parse(SrcPath);
			videoview.setMediaController(mediacontroller);
			videoview.setVideoURI(video);

		} catch (Exception e) {
			Log.e("Error", e.getMessage());
			e.printStackTrace();
		}
		videoview.requestFocus();
		videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
			// Close the progress bar and play the video
			public void onPrepared(MediaPlayer mp) {
				//	pDialog.dismiss();
				videoview.start();
			}
		});


		return view;
	}


	public String getSrcPath() {
		return SrcPath;
	}

	public VideoView getVideoview(){
		return videoview;
	}
}
