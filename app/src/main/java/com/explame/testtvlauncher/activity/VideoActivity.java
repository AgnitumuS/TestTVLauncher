package com.explame.testtvlauncher.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.explame.testtvlauncher.R;
import com.explame.testtvlauncher.domain.MediaModel;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by admin on 2018/5/10.
 */

public class VideoActivity extends AppCompatActivity {

    private JCVideoPlayerStandard jcVideoPlayerStandard;
    public static final String VIDEO = "Video";
    private MediaModel mMediaModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        mMediaModel = getIntent().getParcelableExtra(VideoActivity.VIDEO);

        JCVideoPlayer.TOOL_BAR_EXIST = false;
        JCVideoPlayer.ACTION_BAR_EXIST = false;

        jcVideoPlayerStandard = (JCVideoPlayerStandard) findViewById(R.id.video_player);
        jcVideoPlayerStandard.setUp(mMediaModel.getVideoUrl()
                , JCVideoPlayerStandard.SCREEN_WINDOW_FULLSCREEN, mMediaModel.getTitle());
        jcVideoPlayerStandard.startPlayLocic();
    }


    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
}
