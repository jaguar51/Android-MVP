package me.academeg.androidmvp.ui.component;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SimpleDividerItemDecoration extends RecyclerView.ItemDecoration {

    private ShapeDrawable mDivider;

    private int marginLeft = 0;
    private int marginRight = 0;

    public SimpleDividerItemDecoration() {
        mDivider = new ShapeDrawable(new RectShape());
        mDivider.getPaint().setColor(Color.parseColor("#d9d9d9"));
        mDivider.getPaint().setStyle(Paint.Style.FILL_AND_STROKE);
        mDivider.getPaint().setStrokeWidth(1);
    }

    @Override
    public void onDrawOver(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft() + marginLeft;
        int right = parent.getWidth() - parent.getPaddingRight() - marginRight;

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);
        }
    }

    public void setMargins(int left, int right) {
        this.marginLeft = left;
        this.marginRight = right;
    }

    public void setColor(String color) {
        setColor(Color.parseColor(color));
    }

    public void setColor(int color) {
        mDivider.getPaint().setColor(color);
    }

    public void setHeight() {
        mDivider.getPaint().setStrokeWidth(1);
    }
}
