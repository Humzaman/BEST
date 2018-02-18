package com.best.subtasks.A_RVE;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import com.best.MainActivity;
import com.best.R;
import com.best.subtasks.B_PRE.PREInstructions;

public class RVEInstructions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // hide the status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_rve_instructions);

        Button button = findViewById(R.id.rveInstructionsBeginButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rveBeginClick();
            }
        });

        ImageButton next = findViewById(R.id.rveNext);
        ImageButton previous = findViewById(R.id.rvePrevious);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next();
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                previous();
            }
        });
    }

    private void next() {
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

        Intent intent = new Intent(this, PREInstructions.class);
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

    private void previous() {
        finish();
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

    // send examination data to next activity
    private void rveBeginClick() {
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

        Intent intent = new Intent(this, RVETest.class);
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
