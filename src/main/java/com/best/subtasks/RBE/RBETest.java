package com.best.subtasks.RBE;

import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.best.R;

import java.util.Timer;
import java.util.TimerTask;

public class RBETest extends AppCompatActivity {

    private Button button;
    private ToneGenerator toneGen;
    private Timer timer;
    private long beat;
    private int tap;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // hide the status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_rbe_test);

        toneGen = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
        button = findViewById(R.id.rbeTestButton);
        timer = new Timer();
        beat = 0;
        tap = 0;

        tv = findViewById(R.id.textViewRBE);

        timer.schedule(new TimerTask() {
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        beat = System.currentTimeMillis();

                        if (tap < 10) {
                            toneGen.startTone(ToneGenerator.TONE_DTMF_1,100);
                            button.setPressed(true);

                            timer.schedule(new TimerTask() {
                                public void run() {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            button.setPressed(false);
                                        }
                                    });
                                }
                            }, 100);
                        }
                    }
                });
            }
        }, 2000, 667);

        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    if ((System.currentTimeMillis() - beat) <= 500
                            && (beat - System.currentTimeMillis()) >= -500) {
                        tap++;
                    }
                    else {
                        tap = 0;
                    }

                    if (tap >= 10) {
                        measureTaps();
                    }
                }
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    tv.setText(String.valueOf(tap));
                }
                return true;
            }
        });
    }

    // go back to instructions if we return to this activity
    @Override
    protected void onRestart() {
        finish();
        super.onRestart();
    }

    // cancel running timers if activity is destroyed
    @Override
    protected void onDestroy() {
        timer.cancel();
        toneGen.release();
        super.onDestroy();
    }

    private void measureTaps() {
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    button.setPressed(true);
                }
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    button.setPressed(false);
                }
                return true;
            }
        });
    }
}
