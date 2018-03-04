package com.best.loadProfile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.best.MainActivity;
import com.best.R;
import com.best.database.DatabaseHelper;

public class LoadProfileActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_profile);

        db = DatabaseHelper.getInstance(this);

        // create and fill the recycler view with profile cards
        recyclerView = findViewById(R.id.loadProfileRecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ProfileCardRecyclerAdapter(db.getAllProfiles());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_search, menu);

        MenuItem item = menu.findItem(R.id.loadProfile_search);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchProfiles(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void searchProfiles(String searchTerm) {
        adapter = new ProfileCardRecyclerAdapter(db.searchDB(searchTerm), searchTerm);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        this.recreate();
        super.onRestart();
    }
}
