package com.best.subtasks.RVE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.best.R;

import java.util.Timer;
import java.util.TimerTask;

public class RVETest extends AppCompatActivity {

    private Timer timer;
    private ToneGenerator toneGen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // hide the status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_rve_test);

        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(this);
        long rveTarget = (Long.parseLong(sh.getString("rvePref", "")) * 1000) + 2000;

        timer = new Timer();
        toneGen = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                toneGen.startTone(ToneGenerator.TONE_DTMF_1,350);
            }
        }, 2000);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                toneGen.startTone(ToneGenerator.TONE_DTMF_1,350);
                rveTestDone();
            }
        }, rveTarget);
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

    private void rveTestDone() {
        Bundle bundle = getIntent().getExtras();
        String date = "";
        String id = "";

        if (bundle != null) {
            date = (String) bundle.get("bestDate");
            id = (String) bundle.get("id");
        }

        Intent intent = new Intent(this, RVEDone.class);
        intent.putExtra("bestDate", date);
        intent.putExtra("id", id);
        startActivity(intent);
    }
}
