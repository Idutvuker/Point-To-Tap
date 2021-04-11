package com.example.point_to_tap;

import android.os.Bundle;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    private static final int SLIDER_MAX_PROGRESS = 100;
    private static final int SLIDER_DEFAULT_PROGRESS = 30;

    private static final float MIN_SPEED = 1f;
    private static final float MAX_SPEED = 10f;

    private static final int MILLISECONDS_IN_SECOND = 1000;

    private CanvasView canvasView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        canvasView = findViewById(R.id.canvasView);
        SeekBar seekBar = findViewById(R.id.speedSlider);
        seekBar.setOnSeekBarChangeListener(this);

        seekBar.setMax(SLIDER_MAX_PROGRESS);
        seekBar.setProgress(SLIDER_DEFAULT_PROGRESS);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        float speed = MIN_SPEED + (MAX_SPEED - MIN_SPEED) * progress / seekBar.getMax();
        int duration = (int) (MILLISECONDS_IN_SECOND / speed);
        canvasView.setAnimationDuration(duration);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) { }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) { }
}