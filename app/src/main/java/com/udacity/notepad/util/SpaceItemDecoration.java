package com.udacity.notepad.util;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private final Context context;
    private final int dimenRes;
    private final int space;

    public SpaceItemDecoration(Context context, int dimenRes) {
        this.context = context;
        this.dimenRes = dimenRes;
        this.space = context.getResources().getDimensionPixelOffset(dimenRes);
    }

    private SpaceItemDecoration() {
        throw new RuntimeException();
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        switch (getOrientation(parent)) {
            case LinearLayoutManager.VERTICAL:
                if (position != 0) outRect.top = space;
                break;
            case LinearLayoutManager.HORIZONTAL:
                if (position != 0) outRect.left = space;
        }
    }

    private int getOrientation(RecyclerView parent) {
        RecyclerView.LayoutManager lm = parent.getLayoutManager();
        if (lm instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) lm).getOrientation();
        }
        return -1;
    }
}