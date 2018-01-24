package com.best.subtasks.PPE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.best.R;

public class PPEInstructions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ppe_instructions);

        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(this);
        String ppeTarget = sh.getString("ppePref1", "");

        TextView tv = findViewById(R.id.ppeTargetTextView);
        if (ppeTarget.equals("1")) {
            tv.setText("Target Time: " + ppeTarget + " second");
        }
        else {
            tv.setText("Target Time: " + ppeTarget + " seconds");
        }

        Button button = findViewById(R.id.ppeInstructionsBeginButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ppe1BeginClick();
            }
        });
    }

    private void ppe1BeginClick() {
        Bundle bundle = getIntent().getExtras();
        String date = "";
        String id = "";
        String rveResult = "";
        String pre1Result = "";
        String pre2Result = "";
        String pve1Result = "";
        String pve2Result = "";

        if (bundle != null) {
            date = (String) bundle.get("bestDate");
            id = (String) bundle.get("id");
            rveResult = (String) bundle.get("rveResult");
            pre1Result = (String) bundle.get("pre1Result");
            pre2Result = (String) bundle.get("pre2Result");
            pve1Result = (String) bundle.get("pve1Result");
            pve2Result = (String) bundle.get("pve2Result");
        }

        Intent intent = new Intent(this, PPE1Test.class);
        intent.putExtra("bestDate", date);
        intent.putExtra("id", id);
        intent.putExtra("rveResult", rveResult);
        intent.putExtra("pre1Result", pre1Result);
        intent.putExtra("pre2Result", pre2Result);
        intent.putExtra("pve1Result", pve1Result);
        intent.putExtra("pve2Result", pve2Result);
        startActivity(intent);
    }
}
