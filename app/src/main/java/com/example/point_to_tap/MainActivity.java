package com.example.point_to_tap;

import android.os.Bundle;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    private static final int SLIDER_MAX_PROGRESS = 100;
    private static final int SLIDER_DEFAULT_PROGRESS = 50;
    private static final int ANIMATION_MAX_DURATION_MS = 3000;

    private CanvasView canvasView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        canvasView = findViewById(R.id.canvasView);
        SeekBar seekBar = findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(this);

        seekBar.setMax(SLIDER_MAX_PROGRESS);
        seekBar.setProgress(SLIDER_DEFAULT_PROGRESS);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        int duration = ANIMATION_MAX_DURATION_MS * progress / seekBar.getMax();
        canvasView.setAnimationDuration(duration);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) { }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) { }
}