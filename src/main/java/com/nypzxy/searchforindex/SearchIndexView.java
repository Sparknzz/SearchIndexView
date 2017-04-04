package com.nypzxy.searchforindex;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class SearchIndexView extends View {

    private String[] letterArr = {"A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
            "V", "W", "X", "Y", "Z", "#"};
    private Paint paint;
    private float cellHeight;

    public SearchIndexView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }


    //记录index位置 给用户友好提示
    int currentIndex = -1;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                int index = (int) (event.getY() / cellHeight);
                //对eventAction进行健壮性判断
                if (currentIndex != index) {
                    //获取点击letter位置的index 防止滑出屏幕报错
                    if (index >= 0 && index < letterArr.length) {
                        if (listener != null) {
                            listener.onLetterChange(letterArr[index]);
                        }
                        currentIndex = index;
                    }
                }

                break;
            case MotionEvent.ACTION_UP:
                currentIndex = -1;
                if (listener != null) {
                    //抬手后告诉外界
                    listener.onRelease();
                }
                break;
        }

        invalidate();
        return true;
    }

    /**
     * 由于要绘制Text  所以先需要初始化画笔
     * Note: 只有在draw 图片的时候不需要画笔对象  画线 画图形  都需要
     */
    private void initPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextAlign(Paint.Align.CENTER);
        int textSize = getResources().getDimensionPixelSize(R.dimen.siv_text_size);
        paint.setTextSize(textSize);
    }


    /**
     * 获取到每一个格子的高度
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        cellHeight = getMeasuredHeight() * 1f / letterArr.length;
    }

    /**
     * 当我们画drawText的时候  默认是当前view的左下角 开始绘制的(0,0)点  将画笔的中心 设置成center 即从text的中心位置开始绘制(0,width/2)
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {

        for (int i = 0; i < letterArr.length; i++) {

            int x = getMeasuredWidth() / 2;//获取x轴坐标

            float y = cellHeight * i + cellHeight / 2 + getTextHeight(letterArr[i]) / 2;

            if (i == currentIndex) {
                paint.setColor(Color.RED);
            } else {
                paint.setColor(Color.BLACK);
            }

            canvas.drawText(letterArr[i], x, y, paint);
        }
    }

    /**
     * 获取每个字母的高度值 由于每一个字母其实都是由一个矩形框来决定的  即我们平常使用的拼音4线图
     * 画笔可以得到字母的轮廓  所以通过获取textBounds我们可以得到每个字母的宽高
     *
     * @param text
     * @return
     */
    private int getTextHeight(String text) {

        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);

        return bounds.height();
    }

    OnLetterChangeListener listener;

    public void setOnLetterChangeListener(OnLetterChangeListener listener) {
        this.listener = listener;
    }


    public interface OnLetterChangeListener {
        void onLetterChange(String letter);

        void onRelease();
    }

}
