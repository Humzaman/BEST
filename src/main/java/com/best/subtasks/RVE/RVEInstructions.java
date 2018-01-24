package com.best.subtasks.RVE;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.best.R;

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
    }

    private void rveBeginClick() {
        Bundle bundle = getIntent().getExtras();
        String date = "";
        String id = "";

        if (bundle != null) {
            date = (String) bundle.get("bestDate");
            id = (String) bundle.get("id");
        }

        Intent intent = new Intent(this, RVETest.class);
        intent.putExtra("bestDate", date);
        intent.putExtra("id", id);
        startActivity(intent);
    }
}
