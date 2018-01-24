package com.best.subtasks.RBE;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.best.R;

public class RBEInstructions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // hide the status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_rbe_instructions);

        Button button = findViewById(R.id.rbeInstructionsBeginButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rbeBeginClick();
            }
        });
    }

    private void rbeBeginClick() {
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

        Intent intent = new Intent(this, RBETest.class);
        intent.putExtra("bestDate", date);
        intent.putExtra("id", id);
        intent.putExtra("rveResult", rveResult);
        intent.putExtra("pre1Result", pre1Result);
        intent.putExtra("pre2Result", pre2Result);
        intent.putExtra("pve1Result", pve1Result);
        intent.putExtra("pve2Result", pve2Result);
        intent.putExtra("ppe1result", ppe1Result);
        intent.putExtra("ppe2result", ppe2Result);
        startActivity(intent);
    }
}
