package com.best.subtasks.PVE;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.best.R;

public class PVE1Done extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pve_done);

        Button button = findViewById(R.id.pveDoneNextButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pveDoneNextClick();
            }
        });
    }

    private void pveDoneNextClick() {
        EditText et = findViewById(R.id.pveDoneEditText);
        String pve1Result = et.getText().toString();

        if (pve1Result.equals("")) {
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

            Intent intent = new Intent(this, PVEInterim.class);
            intent.putExtra("bestDate", date);
            intent.putExtra("id", id);
            intent.putExtra("rveResult", rveResult);
            intent.putExtra("pre1Result", pre1Result);
            intent.putExtra("pre2Result", pre2Result);
            intent.putExtra("pve1Result", pve1Result);
            startActivity(intent);
        }
    }
}
