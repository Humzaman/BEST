package com.best.loadProfile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.best.R;
import com.best.database.DatabaseHelper;
import com.best.database.Profile;
import com.best.subtasks.StartBEST;

public class ProfileInfoActivity extends AppCompatActivity {

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_info);

        DatabaseHelper db = new DatabaseHelper(this);

        TextView textView = findViewById(R.id.profileInfoTextView);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            this.id = (String) bundle.get("profileId");
            textView.setText("");

            Profile profile = db.getProfile(id);

            textView.append("Name: " + profile.getName() + "\n");
            textView.append("ID#: " + profile.getIdNumber() + "\n");
            textView.append("Gender: " + profile.getGender() + "\n");

            if (profile.getHandedness().equals("Ambi")) {
                textView.append("Handedness: " + profile.getHandedness() + "dextrous\n");
            }
            else {
                textView.append("Handedness: " + profile.getHandedness() + "-handed\n");
            }

            textView.append("Education Level: " + profile.getEducationLevel() + " years\n");
            textView.append("DOB: " + profile.getDob() + "\n");
            textView.append("Notes: " + profile.getNotes() + "\n");
            textView.append("Profile Creation Date: " + profile.getCreationDate() + "\n");
        }
    }

    public void administerBESTClick(View view) {
        Intent intent = new Intent(this, StartBEST.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

}
