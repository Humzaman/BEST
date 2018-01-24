package com.best.subtasks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.best.R;
import com.best.subtasks.RVE.RVEInstructions;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StartBEST extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // hide the status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start_best);

        Bundle bundle = getIntent().getExtras();
        String date = (new SimpleDateFormat("MM/dd/yyyy HH:mm:SSS")).format(new Date());
        String id = "";

        if (bundle != null) {
            id = (String) bundle.get("id");
        }

        Intent intent = new Intent(this, RVEInstructions.class);
        intent.putExtra("bestDate", date);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    // this activity should never display on screen
    @Override
    protected void onResume() {
        finish();
        super.onResume();
    }
}
