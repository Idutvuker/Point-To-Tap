package com.example.point_to_tap;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayDeque;

public class CanvasView extends View implements View.OnTouchListener, ValueAnimator.AnimatorUpdateListener {
    private static final float CIRCLE_RADIUS = 50;
    private static final float TRACK_STROKE_WIDTH = 3;

    private final Paint circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint trackPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private final PointF position = new PointF();
    private final ArrayDeque<PointF> track = new ArrayDeque<>();

    private final PointF orig = new PointF();
    private final PointF dest = new PointF();

    private final ValueAnimator animation = ValueAnimator.ofFloat(0);

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);

        circlePaint.setColor(Color.BLACK);
        trackPaint.setColor(Color.GRAY);
        trackPaint.setStrokeWidth(TRACK_STROKE_WIDTH);

        animation.addUpdateListener(this);
        animation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                startNewAnimation();
            }
        });

        setOnTouchListener(this);
    }

    private void startNewAnimation() {
        if (track.isEmpty())
            return;

        dest.set(track.poll());
        orig.set(position);

        animation.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(position.x, position.y, CIRCLE_RADIUS, circlePaint);

        if (animation.isRunning()) {
            canvas.drawLine(position.x, position.y, dest.x, dest.y, trackPaint);
        }

        PointF last = dest;
        for (PointF point : track) {
            if (last != null) {
                canvas.drawLine(last.x, last.y, point.x, point.y, trackPaint);
            }
            last = point;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            track.add(new PointF(event.getX(), event.getY()));

            if (!animation.isRunning())
                startNewAnimation();

            return true;
        }

        return false;
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        float f = animation.getAnimatedFraction();

        position.x = orig.x + (dest.x - orig.x) * f;
        position.y = orig.y + (dest.y - orig.y) * f;

        invalidate();
    }

    public void setAnimationDuration(int duration) {
        float fraction = animation.getCurrentPlayTime() / (float) animation.getDuration();
        animation.setDuration(duration);

        if (animation.isRunning()) {
            animation.setCurrentFraction(fraction);
        }
    }
}
