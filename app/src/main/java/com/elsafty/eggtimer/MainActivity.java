package com.elsafty.eggtimer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button startButton, pauseButton;
    SeekBar seekBar;
    CountDownTimer downTimer;
    private long timeRemaining = 0;
    TextView textView;
    boolean counterIsActive, pauseIsActive,timerFinished,timerStarted;

    public void updateTimer(int Second) {
        int minutes = (int) (Second / 60);
        int seconds = Second - minutes * 60;
        String zero = String.valueOf(seconds);
        if (zero == "0") {
            zero = "00";
        }
        textView.setText(String.valueOf(minutes) + ":" + zero);
    }

    public void timer(int limit) {
        timerStarted=true;
        downTimer = new CountDownTimer(limit, 1000) {
            @Override
            public void onTick(long millisUnitFinished) {
                updateTimer((int) millisUnitFinished / 1000);
                timeRemaining=millisUnitFinished;
            }

            @Override
            public void onFinish() {
                timerFinished=true;
                textView.setText("0:00");
                MediaPlayer player = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                player.start();
            }
        }.start();
    }

    public void controlTimer(View view) {
        if (counterIsActive == false) {
            counterIsActive = true;
            seekBar.setEnabled(false);
            pauseButton.setVisibility(View.VISIBLE);
            startButton.setText("STOP");
            timer(seekBar.getProgress() * 1000 + 100);
            pauseIsActive=false;
        } else {
            pauseButton.setText("PAUSE");
            timeRemaining=0;
            pauseButton.setVisibility(View.GONE);
            updateTimer(seekBar.getProgress());
            startButton.setText("GO!");
            seekBar.setEnabled(true);
            downTimer.cancel();
            counterIsActive = false;
        }
    }

    public void pause(View view) {
        if (timerFinished==true||timerStarted==false){
            return;
        }
        if (pauseIsActive == false) {
            pauseIsActive = true;
            downTimer.cancel();
            pauseButton.setText("CONTINUE");
        } else {
            timer((int) timeRemaining);
            pauseButton.setText("PAUSE");
            pauseIsActive = false;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        startButton = findViewById(R.id.start_button);
        pauseButton = findViewById(R.id.pause);
        pauseButton.setVisibility(View.GONE);
        textView = (TextView) findViewById(R.id.textView);
        seekBar.setProgress(30);
        seekBar.setMax(600);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }


}
