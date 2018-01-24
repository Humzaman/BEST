package com.best;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class AdditionalInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional_info);

        TextView textView = findViewById(R.id.additionalInfoTextView);
        textView.setMovementMethod(new ScrollingMovementMethod());
    }
}
