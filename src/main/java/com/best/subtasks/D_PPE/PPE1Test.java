package com.best.subtasks.D_PPE;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import com.best.MainActivity;
import com.best.R;

import java.text.DecimalFormat;

public class PPE1Test extends AppCompatActivity {

    private Button button;
    private SoundPool sound;
    private double time;
    private boolean done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // hide the status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_ppe_test);

        button = findViewById(R.id.ppeTestButton);
        sound = new SoundPool.Builder().build();
        final int toneId = sound.load(this, R.raw.tone2, 1);
        time = 0;
        done = false;

        ImageButton redo = findViewById(R.id.redoPPE);
        ImageButton cancel = findViewById(R.id.cancelPPE);

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

        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    // ensures that test ends after two presses
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
                        ppe1Done();
                    }
                }
                return true;
            }
        });
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

    @Override
    protected void onDestroy() {
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

    private void ppe1Done() {
        String ppe1Result = String.valueOf((new DecimalFormat("#.#")).format(time / 1000.0));

        Bundle bundle = getIntent().getExtras();
        String date = "";
        String id = "";
        String rveResult = "";
        String pre1Result = "";
        String pre2Result = "";
        String pve1Result = "";
        String pve2Result = "";

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

            ppe2Result = (String) bundle.get("ppe2Result");
            rbeResult = (String) bundle.get("rbeResult");
        }

        Intent intent = new Intent(this, PPEInterim.class);
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
