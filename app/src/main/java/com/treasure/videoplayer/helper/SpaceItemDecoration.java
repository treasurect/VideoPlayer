package com.treasure.videoplayer.helper;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by treasure on 2017/11/11.
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private int space;
    private int columnCount;
    public static final int TOPIC_DETAIL_HOR_1 = 1;
    public static final int MAIN_TOPIC_HOR_1 = 2;
    public static final int MAIN_BOOK_HOR_1 = 3;

    public SpaceItemDecoration(int space, int columnCount) {
        this.space = space;
        this.columnCount = columnCount;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int position = parent.getChildAdapterPosition(view); // item position
        switch (columnCount) {
            case TOPIC_DETAIL_HOR_1://横向 1行
                outRect.left = space;
                if (position == 0) {
                    outRect.left = space * 2;
                }
                outRect.right = 0;
                break;
            case MAIN_TOPIC_HOR_1:
                outRect.left = space / 2;
                if (position == 0) {
                    outRect.left = space * 2;
                }
                outRect.right = space / 2;
//                if (listsize != 0){
//                    if (position == listsize-1) {
//                        outRect.right = space * 2;
//                    }
//                }

                break;
            case MAIN_BOOK_HOR_1:
                outRect.left = 0;
                if (position == 0) {
                    outRect.left = space * 2;
                }
                outRect.right = 0;

                break;
        }
    }
}
