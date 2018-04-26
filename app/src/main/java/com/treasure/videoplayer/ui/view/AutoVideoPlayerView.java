package com.treasure.videoplayer.ui.view;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.RelativeLayout;

import com.treasure.videoplayer.R;
import com.treasure.videoplayer.helper.MediaHelper;
import com.treasure.videoplayer.utils.ScreenUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ============================================================
 * Copyright：treasure_ct和他的朋友们有限公司版权所有 (c) 2018
 * Author：   treasure_ct
 * time：2018/04/07 18:05
 * ============================================================
 */

public class AutoVideoPlayerView extends RelativeLayout {
    @BindView(R.id.texture_view)
     TextureView videoView;
    @BindView(R.id.media_controller)
    public AutoVideoControllerView mediaController;

    public MediaPlayer mMediaPlayer;
    private Surface mSurface;

    public boolean hasPlay;//是否播放了

    private int mScreenWidth, mScreenHeight;
    private Context context;

    public AutoVideoPlayerView(Context context) {
        this(context, null);
    }

    public AutoVideoPlayerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoVideoPlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        mScreenWidth = context.getResources().getDisplayMetrics().widthPixels;
        mScreenHeight = context.getResources().getDisplayMetrics().heightPixels;
        initView();
    }

    //初始化布局
    private void initView() {
        View view = View.inflate(getContext(), R.layout.layout_autoplayer_view, this);
        ButterKnife.bind(this, view);
        LayoutParams layoutParams = (LayoutParams) videoView.getLayoutParams();
        layoutParams.width = mScreenWidth - ScreenUtils.dip2px(context, 32);
        layoutParams.height = mScreenHeight - ScreenUtils.dip2px(context, 131);
        videoView.setLayoutParams(layoutParams);
        mediaController.setLayoutParams(layoutParams);
        //设置视频圆角
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            videoView.setOutlineProvider(new TextureVideoViewOutlineProvider(context, ScreenUtils.dip2px(context, 3)));
            videoView.setClipToOutline(true);
        }

        //进行TextureView控件创建的监听
        videoView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            //创建完成  TextureView才可以进行视频画面的显示
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                // Log.i(TAG,"onSurfaceTextureAvailable");
                mSurface = new Surface(surface);//连接对象（MediaPlayer和TextureView）
                play(url);
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
                // Log.i(TAG,"onSurfaceTextureSizeChanged");
            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                // Log.i(TAG,"onSurfaceTextureDestroyed");
//                videoView = null;
//                mSurface = null;
//                mMediaPlayer.stop();
//                mMediaPlayer.release();
                return true;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {
                // Log.i(TAG,"onSurfaceTextureUpdated");
            }
        });

    }

    //视频播放（视频的初始化）
    private void play(String url) {
        try {
            mMediaPlayer = MediaHelper.getInstance();
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(url);
            //让MediaPlayer和TextureView进行视频画面的结合
            mMediaPlayer.setSurface(mSurface);
            //设置监听
            mMediaPlayer.setOnBufferingUpdateListener(onBufferingUpdateListener);
            mMediaPlayer.setOnCompletionListener(onCompletionListener);
            mMediaPlayer.setOnErrorListener(onErrorListener);
            mMediaPlayer.setOnPreparedListener(onPreparedListener);
            mMediaPlayer.setScreenOnWhilePlaying(true);//在视频播放的时候保持屏幕的高亮
            //异步准备
            mMediaPlayer.prepareAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //准备完成监听
    private MediaPlayer.OnPreparedListener onPreparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            //隐藏视频加载进度条
//            mediaController.showVideoView();
            //进行视频的播放
            MediaHelper.play();
            hasPlay = true;
//            hindVideoView();
//            videoView.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    showVideoView();
//                }
//            },2000);
        }
    };

    //错误监听
    private MediaPlayer.OnErrorListener onErrorListener = new MediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            return true;
        }
    };

    //完成监听
    private MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            //视频播放完成
            mediaController.reStartVideo();

        }
    };

    //缓冲的监听
    private MediaPlayer.OnBufferingUpdateListener onBufferingUpdateListener = new MediaPlayer.OnBufferingUpdateListener() {
        @Override
        public void onBufferingUpdate(MediaPlayer mp, int percent) {
//            Log.i(TAG,"percent:"+percent);
//            mediaController.updateSeekBarSecondProgress(percent);
        }
    };


    //初始化控件的显示状态
    public void hindVideoView() {
        videoView.setVisibility(View.GONE);
        mediaController.hindVideoView();
    }

    //初始化控件的显示状态
    public void showVideoView() {
        videoView.setVisibility(View.VISIBLE);
        mediaController.showVideoView();
    }

    private String url;

    public void setPlayData(String url) {
        this.url = url;
    }
}
