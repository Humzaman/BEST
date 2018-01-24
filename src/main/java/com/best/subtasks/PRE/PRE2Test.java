package com.best.subtasks.PRE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.best.R;
import com.best.subtasks.PVE.PVEInstructions;

import java.util.Timer;
import java.util.TimerTask;

public class PRE2Test extends AppCompatActivity {

    private Timer timer;
    private Button button;
    private ToneGenerator toneGen;
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
        long preTarget = (Long.parseLong(sh.getString("prePref2", "")) * 1000) + 2000;

        timer = new Timer();
        toneGen = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);

        timer.schedule(new TimerTask() {
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        toneGen.startTone(ToneGenerator.TONE_DTMF_1,350);
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
        }, 2350);

        timer.schedule(new TimerTask() {
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        toneGen.startTone(ToneGenerator.TONE_DTMF_1,350);
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
        }, preTarget + 350);
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

    private void activateButton() {
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    if(time == 0) {
                        time = System.currentTimeMillis();
                    }
                    else {
                        time = System.currentTimeMillis() - time;
                        done = true;
                    }

                    toneGen.startTone(ToneGenerator.TONE_DTMF_1);
                    button.setPressed(true);
                }
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    toneGen.stopTone();
                    button.setPressed(false);

                    if(done) {
                        pre2Done();
                    }
                }
                return true;
            }
        });
    }

    private void pre2Done() {
        String pre2Result = String.valueOf(Math.round(time / 1000));

        Bundle bundle = getIntent().getExtras();
        String date = "";
        String id = "";
        String rveResult = "";
        String pre1Result = "";

        if (bundle != null) {
            date = (String) bundle.get("bestDate");
            id = (String) bundle.get("id");
            rveResult = (String) bundle.get("rveResult");
            pre1Result = (String) bundle.get("pre1Result");
        }

        Intent intent = new Intent(this, PVEInstructions.class);
        intent.putExtra("bestDate", date);
        intent.putExtra("id", id);
        intent.putExtra("rveResult", rveResult);
        intent.putExtra("pre1Result", pre1Result);
        intent.putExtra("pre2Result", pre2Result);
        startActivity(intent);
    }

}
