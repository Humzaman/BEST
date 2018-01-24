package com.best.subtasks.RVE;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.best.R;
import com.best.subtasks.PRE.PREInstructions;

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
    }

    private void rveDoneNextClick() {
        EditText et = findViewById(R.id.rveDoneEditText);
        String rveResult = et.getText().toString();

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

            if (bundle != null) {
                date = (String) bundle.get("bestDate");
                id = (String) bundle.get("id");
            }

            Intent intent = new Intent(this, PREInstructions.class);
            intent.putExtra("bestDate", date);
            intent.putExtra("id", id);
            intent.putExtra("rveResult", rveResult);
            startActivity(intent);
        }
    }
}
