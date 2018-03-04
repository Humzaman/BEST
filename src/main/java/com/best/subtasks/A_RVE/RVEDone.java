package com.best.subtasks.A_RVE;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.best.R;
import com.best.loadProfile.ProfileInfoActivity;
import com.best.subtasks.B_PRE.PREInstructions;

public class RVEDone extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // hide the status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_rve_done);

        Button button = findViewById(R.id.rveDoneNextButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rveDoneNextClick();
            }
        });

        ImageButton previous = findViewById(R.id.rveDonePrevious);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                previous();
            }
        });
    }

    private void previous() {
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

        Intent intent = new Intent(this, RVEInstructions.class);
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
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
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

    private void rveDoneNextClick() {
        EditText et = findViewById(R.id.rveDoneEditText);
        String rveResult = et.getText().toString();

        // check if user entered results
        if (rveResult.equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Enter time passed");
            builder.setMessage("Please enter the amount of time passed between the two tones.");
            builder.setPositiveButton("OK", null);

            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else {
            Bundle bundle = getIntent().getExtras();
            String date = "";
            String id = "";

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
    }
}
