package com.hpb.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    PlayingView playView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playView=(PlayingView)findViewById(R.id.channel_playing);
    }

    @Override
    protected void onResume() {
        super.onResume();
        playView.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        playView.hide();
    }
}
