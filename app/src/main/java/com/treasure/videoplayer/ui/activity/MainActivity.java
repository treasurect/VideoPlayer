package com.treasure.videoplayer.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.treasure.videoplayer.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }
    @OnClick({R.id.btn_click_play,R.id.btn_auto_play})
    public void onViewClick(View view){
        switch (view.getId()) {
            case R.id.btn_click_play:
                startActivity(new Intent(this,ClickPlayActivity.class));
                break;
            case R.id.btn_auto_play:
                startActivity(new Intent(this,AutoPlayActivity.class));
                break;
        }
    }
}
