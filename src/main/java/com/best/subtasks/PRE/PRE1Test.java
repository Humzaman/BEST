package com.best.subtasks.PRE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.SoundPool;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.best.R;

import java.util.Timer;
import java.util.TimerTask;

public class PRE1Test extends AppCompatActivity {

    private Timer timer;
    private Button button;
    private SoundPool sound;
    private int toneId;
    private double time;
    private boolean done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // hide the status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pre_test);

        time = 0;
        done = false;

        button = findViewById(R.id.preTestButton);
        button.setClickable(false);

        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(this);
        long preTarget = (Long.parseLong(sh.getString("prePref1", "23")) * 1000) + 2000;

        sound = new SoundPool.Builder().build();
        toneId = sound.load(this, R.raw.tone1, 1);
        timer = new Timer();

        timer.schedule(new TimerTask() {
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        sound.play(toneId, 1, 1, 1, 0, 1);
                        button.setPressed(true);
                    }
                });
            }
        }, 2000);

        timer.schedule(new TimerTask() {
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        button.setPressed(false);
                    }
                });
            }
        }, 2150);

        timer.schedule(new TimerTask() {
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        sound.play(toneId, 1, 1, 1, 0, 1);
                        button.setPressed(true);
                    }
                });
            }
        }, preTarget);

        timer.schedule(new TimerTask() {
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        button.setPressed(false);
                        activateButton();
                    }
                });
            }
        }, preTarget + 150);
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
        sound.release();
        super.onDestroy();
    }

    // get data from button presses
    private void activateButton() {
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    // ensure that test ends after two presses
                    if(time == 0) {
                        time = System.currentTimeMillis();
                    }
                    else {
                        time = System.currentTimeMillis() - time;
                        done = true;
                    }

                    sound.play(toneId, 1, 1, 1, 0, 1);
                    button.setPressed(true);
                }
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    button.setPressed(false);

                    if(done) {
                        pre1Done();
                    }
                }
                return true;
            }
        });
    }

    private void pre1Done() {
        String pre1Result = String.valueOf(Math.round(time / 1000));

        Bundle bundle = getIntent().getExtras();
        String date = "";
        String id = "";
        String rveResult = "";

        if (bundle != null) {
            date = (String) bundle.get("bestDate");
            id = (String) bundle.get("id");
            rveResult = (String) bundle.get("rveResult");
        }

        Intent intent = new Intent(this, PREInterim.class);
        intent.putExtra("bestDate", date);
        intent.putExtra("id", id);
        intent.putExtra("rveResult", rveResult);
        intent.putExtra("pre1Result", pre1Result);
        startActivity(intent);
    }

}
