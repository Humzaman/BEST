package com.best.subtasks.C_PVE;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.SoundPool;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.best.MainActivity;
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

        ImageButton redo = findViewById(R.id.redoPVE);
        ImageButton cancel = findViewById(R.id.cancelPVE);

        redo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redo();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRestart();
            }
        });

        sound = new SoundPool.Builder().build();
        final int toneId = sound.load(this, R.raw.tone2, 1);
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

    private void redo() {
        this.recreate();
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

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Stop BEST");
        builder.setMessage("Are you sure you want to stop administering this BEST?\n\nResults will not be saved!");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                stopBEST();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // do nothing
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();


    }

    private void stopBEST() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void pveTestDone() {
        Bundle bundle = getIntent().getExtras();
        String date = "";
        String id = "";
        String rveResult = "";
        String pre1Result = "";
        String pre2Result = "";
        String pve1Result = "";
        String pve2Result = "";
        String ppe1Result = "";
        String ppe2Result = "";
        String rbeResult = "";

        if (bundle != null) {
            date = (String) bundle.get("bestDate");
            id = (String) bundle.get("id");
            rveResult = (String) bundle.get("rveResult");
            pre1Result = (String) bundle.get("pre1Result");
            pre2Result = (String) bundle.get("pre2Result");
            pve1Result = (String) bundle.get("pve1Result");
            pve2Result = (String) bundle.get("pve2Result");
            ppe1Result = (String) bundle.get("ppe1Result");
            ppe2Result = (String) bundle.get("ppe2Result");
            rbeResult = (String) bundle.get("rbeResult");
        }

        Intent intent = new Intent(this, PVE1Done.class);
        intent.putExtra("bestDate", date);
        intent.putExtra("id", id);
        intent.putExtra("rveResult", rveResult);
        intent.putExtra("pre1Result", pre1Result);
        intent.putExtra("pre2Result", pre2Result);
        intent.putExtra("pve1Result", pve1Result);
        intent.putExtra("pve2Result", pve2Result);
        intent.putExtra("ppe1Result", ppe1Result);
        intent.putExtra("ppe2Result", ppe2Result);
        intent.putExtra("rbeResult", rbeResult);
        startActivity(intent);
    }
}
