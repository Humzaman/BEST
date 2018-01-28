package com.best.loadProfile;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.best.R;
import com.best.database.DatabaseHelper;
import com.best.database.Profile;
import com.best.subtasks.StartBEST;

import java.util.ArrayList;
import java.util.List;

public class ProfileInfoActivity extends AppCompatActivity {

    private String id;
    DatabaseHelper db;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_info_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_info);

        db = DatabaseHelper.getInstance(this);
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            this.id = (String) bundle.get("profileId");
            Profile profile = db.getProfile(id);

            List<Object> items = new ArrayList<>();
            items.add(profile);
            items.addAll(db.getResults(id));

            recyclerView = findViewById(R.id.ProfileInfoRecyclerView);
            recyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new ProfileInfoResultsCardRecyclerAdapter(items);
            recyclerView.setAdapter(adapter);
        }
    }

    public void administerBESTClick(MenuItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Administer BEST?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startBEST();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // do nothing
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void startBEST() {
        Intent intent = new Intent(this, StartBEST.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    public void editProfileInfoClick(MenuItem item) {
        Intent intent = new Intent(this, EditProfileActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    public void deleteProfileInfoClick(MenuItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Profile");
        builder.setMessage("Are you sure you want to delete this profile?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                delete();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // do nothing
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void delete() {
        db.deleteProfile(db.getProfile(id));
        Intent intent = new Intent(this, LoadProfileActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
