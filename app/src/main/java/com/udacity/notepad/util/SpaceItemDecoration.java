package com.udacity.notepad.util;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private final int space;

    // This class is to verbose and performs unnecessary operations
    // The 'instanceof' is a clear indicator of something wrong
    // The return -1 in getOrientation is not handled and therefore ignored on the switch
    // (Note that non of the cases matches the value -1)
    // Instead the following constructor can be used:

    // public SpaceItemDecoration(Context context, int dimenRes, int orientation)

    // adding the orientation in the constructor, which is known by the caller
    // no need to calculate that at all (Notice that it is being calculated in every element draw!!)
    // the switch would be cleaner and the code much simpler
    public SpaceItemDecoration(Context context, int dimenRes) {
        space = context.getResources().getDimensionPixelOffset(dimenRes);
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
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).getOrientation();
        }
        return -1;
    }
}