package com.best.loadProfile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.best.R;
import com.best.database.DatabaseHelper;

public class LoadProfileActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_profile);

        DatabaseHelper db = new DatabaseHelper(this);

        // create and fill the recycler view with profile cards
        recyclerView = findViewById(R.id.loadProfileRecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ProfileCardRecyclerAdapter(db.getAllProfiles());
        recyclerView.setAdapter(adapter);
    }
}
