package com.best.subtasks.PVE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.SoundPool;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.best.R;

import java.util.Timer;
import java.util.TimerTask;

public class PVE1Test extends AppCompatActivity {

    private Timer timer;
    private SoundPool sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // hide the status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pve_test);

        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(this);
        long pveTarget = (Long.parseLong(sh.getString("pvePref1", "23")) * 1000) + 2000;

        sound = new SoundPool.Builder().build();
        final int toneId = sound.load(this, R.raw.tone1, 1);
        timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                sound.play(toneId, 1, 1, 1, 0, 1);
            }
        }, 2000);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                sound.play(toneId, 1, 1, 1, 0, 1);
                pveTestDone();
            }
        }, pveTarget);
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

    private void pveTestDone() {
        Bundle bundle = getIntent().getExtras();
        String date = "";
        String id = "";
        String rveResult = "";
        String pre1Result = "";
        String pre2Result = "";

        if (bundle != null) {
            date = (String) bundle.get("bestDate");
            id = (String) bundle.get("id");
            rveResult = (String) bundle.get("rveResult");
            pre1Result = (String) bundle.get("pre1Result");
            pre2Result = (String) bundle.get("pre2Result");
        }

        Intent intent = new Intent(this, PVE1Done.class);
        intent.putExtra("bestDate", date);
        intent.putExtra("id", id);
        intent.putExtra("rveResult", rveResult);
        intent.putExtra("pre1Result", pre1Result);
        intent.putExtra("pre2Result", pre2Result);
        startActivity(intent);
    }
}
