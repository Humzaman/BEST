package com.best.subtasks.E_RBE;

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
import android.widget.Button;
import android.widget.ImageButton;

import com.best.R;
import com.best.loadProfile.ProfileInfoActivity;
import com.best.subtasks.BESTComplete;

import java.util.Timer;
import java.util.TimerTask;

public class RBETest extends AppCompatActivity {

    private Button button;
    private SoundPool sound;
    private Timer timer;
    private long beat;
    private int beatCount;
    private int beatCount2; // used for synchronizing beat and taps
    private long rbeTarget;
    private String rbeResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // hide the status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_rbe_test);

        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(this);
        rbeTarget = (Long.parseLong(sh.getString("rbePref", "750")));

        button = findViewById(R.id.rbeTestButton);
        button.setClickable(false);
        timer = new Timer();
        sound = new SoundPool.Builder().build();
        final int toneId = sound.load(this, R.raw.tone2, 1);
        beat = 0;
        beatCount = 0;
        beatCount2 = 0;
        rbeResult = "";

        ImageButton redo = findViewById(R.id.redoRBE);
        ImageButton cancel = findViewById(R.id.cancelRBE);

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

        // start a timer that presses the button and beeps 10 times
        timer.schedule(new TimerTask() {
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // when a tap is registered, get the next target beat when needed
                        if (beatCount > beatCount2) {
                            beat = System.currentTimeMillis();
                            beatCount2 = beatCount;
                        }

                        // beep 20 times only
                        if(beatCount < 20) {
                            beatCount++;
                            button.setPressed(true);
                            sound.play(toneId, 1, 1, 1, 0, 1);

                            timer.schedule(new TimerTask() {
                                public void run() {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            button.setPressed(false);
                                        }
                                    });
                                }
                            }, 300);
                        }

                        // once 20 beeps have completed, start measuring taps
                        if(beatCount == 20) {
                            measureTaps();
                        }

                        // complete test after 60 taps measured
                        if (beatCount >= 80) {
                            rbeTestDone();
                        }
                    }
                });
            }
        }, 2000, rbeTarget);
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
        Bundle bundle = getIntent().getExtras();
        String id = "";

        if (bundle != null) {
            id = (String) bundle.get("id");
        }

        Intent intent = new Intent(this, ProfileInfoActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("profileId", id);
        startActivity(intent);
    }

    private void measureTaps() {
        button.setClickable(true);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // if user taps more than once between target beats,
                //add target time to beat to test for "next target beat" (that probably doesn't make sense lol)
                // (beatCount > 20) is there so this doesn't run on the very first tap.
                if ((beatCount > 20) && (beatCount > beatCount2)) {
                    beat += rbeTarget;
                }

                rbeResult += (beat - System.currentTimeMillis());
                rbeResult += ", ";
                beatCount++;
            }
        });
    }

    private void rbeTestDone() {
        timer.cancel();
        // remove last comma
        rbeResult = rbeResult.substring(0, rbeResult.length() - 2);

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
        }

        Intent intent = new Intent(this, BESTComplete.class);
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
