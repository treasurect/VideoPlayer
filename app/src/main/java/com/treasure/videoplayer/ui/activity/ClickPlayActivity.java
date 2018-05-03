package com.treasure.videoplayer.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.WindowManager;

import com.treasure.videoplayer.R;
import com.treasure.videoplayer.adapter.VideoListAdapter;
import com.treasure.videoplayer.bean.VideoItem;
import com.treasure.videoplayer.helper.MediaHelper;
import com.treasure.videoplayer.helper.SpaceItemDecoration;
import com.treasure.videoplayer.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClickPlayActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private List<VideoItem> videoItemList;
    private LinearLayoutManager layoutManager;
    private VideoListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViews();
        initData();
        initRecyclerView();
    }

    private void initViews() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_click_play);
        ButterKnife.bind(this);
    }

    private void initData() {
        //网络视频路径
        String url = "http://ips.ifeng.com/video19.ifeng.com/video09/2017/05/24/4664192-102-008-1012.mp4";

        //数据的初始化
        videoItemList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            videoItemList.add(new VideoItem(i, url));
        }
    }

    private void initRecyclerView() {
        //初始化RecyclerView
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // 添加分割线
        recyclerView.addItemDecoration(new SpaceItemDecoration(ScreenUtils.dip2px(ClickPlayActivity.this, 8), SpaceItemDecoration.MAIN_TOPIC_HOR_1));
        adapter = new VideoListAdapter(this, videoItemList);
        recyclerView.setAdapter(adapter);
        //设置滑动监听
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            //进行滑动
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //获取屏幕上显示的第一个条目和最后一个条目的下标
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                //获取播放条目的下标
                int currentPosition = adapter.currentPosition;
                if ((firstVisibleItemPosition > currentPosition || lastVisibleItemPosition < currentPosition) && currentPosition > -1) {
                    //让播放隐藏的条目停止
                    MediaHelper.release();
                    adapter.currentPosition = -1;
                    adapter.notifyDataSetChanged();
                }
            }
        });
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MediaHelper.release();
    }
}
