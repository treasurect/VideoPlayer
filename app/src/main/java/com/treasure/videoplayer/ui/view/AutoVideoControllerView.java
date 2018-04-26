package com.treasure.videoplayer.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.treasure.videoplayer.R;
import com.treasure.videoplayer.adapter.AutoListAdapter;
import com.treasure.videoplayer.helper.MediaHelper;
import com.treasure.videoplayer.ui.activity.AutoPlayActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ============================================================
 * Copyright：treasure_ct和他的朋友们有限公司版权所有 (c) 2018
 * Author：   treasure_ct
 * time：2018/04/07 18:05
 * ============================================================
 */

public class AutoVideoControllerView extends RelativeLayout {
    @BindView(R.id.video_first)
    ImageView videoFirst;
    @BindView(R.id.progress)
    ProgressBar progressBar;

    private Context context;

    public AutoVideoControllerView(Context context) {
        this(context, null);
    }

    public AutoVideoControllerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoVideoControllerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
        ((AutoPlayActivity) context).setOnStartVideo(new AutoPlayActivity.OnStartVideo() {
            @Override
            public void startVideo(int pos) {
                startVideo1(pos);
            }
        });
    }

    //初始化控件
    private void initView() {
        View view = View.inflate(getContext(), R.layout.layout_autovideo_controller, this);
        ButterKnife.bind(this, view);

    }

    private AutoListAdapter adapter;

    public void setAdapter(AutoListAdapter mediaListAdapter) {
        this.adapter = mediaListAdapter;
    }

    //初始化控件的显示状态
    public void hindVideoView() {
        videoFirst.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
//        progress.setVisibility(View.VISIBLE);
    }

    //初始化控件的显示状态
    public void showVideoView() {
        videoFirst.postDelayed(new Runnable() {
            @Override
            public void run() {
                videoFirst.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
//                progress.setVisibility(View.GONE);
            }
        },1000);
    }

    public void reStartVideo() {
        //把媒体播放器的位置移动到开始的位置
        MediaHelper.getInstance().seekTo(0);
        //开始播放
        MediaHelper.play();
    }

    public void startVideo1(int pos) {
        //点击一个新的条目进行播放
        //点击的条目下标是否是之前播放的条目下标
        if (adapter == null)
            return;


        //让其他的条目停止播放(还原条目开始的状态)
        MediaHelper.release();
        //把播放条目的下标设置给适配器
        adapter.setPlayPosition(pos);
    }
}
