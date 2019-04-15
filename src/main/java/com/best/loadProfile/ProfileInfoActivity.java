package com.best.loadProfile;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.best.R;
import com.best.database.DatabaseHelper;
import com.best.database.Profile;
import com.best.database.Results;
import com.best.loadProfile.tableview.MyTableAdapter;
import com.best.loadProfile.tableview.MyTableViewListener;
import com.best.loadProfile.tableview.model.CellModel;
import com.best.loadProfile.tableview.model.ColumnHeaderModel;
import com.best.loadProfile.tableview.model.RowHeaderModel;
import com.best.subtasks.A_RVE.RVEInstructions;
import com.evrencoskun.tableview.TableView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProfileInfoActivity extends AppCompatActivity {

    private String id;
    DatabaseHelper db;

    private TableView mTableView;
    private MyTableAdapter mTableAdapter;

    // For TableView
    private List<List<CellModel>> mCellList;
    private List<ColumnHeaderModel> mColumnHeaderList;
    private List<RowHeaderModel> mRowHeaderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_info);

        mTableView = findViewById(R.id.my_TableView);

        // Create TableView Adapter
        mTableAdapter = new MyTableAdapter(this);
        mTableView.setAdapter(mTableAdapter);

        // Create listener
        mTableView.setTableViewListener(new MyTableViewListener(mTableView));

        db = DatabaseHelper.getInstance(this);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            this.id = (String) bundle.get("profileId");
        }

        populateInfoCard(db.getProfile(id));
        populatedTableView(db.getResults(id));
    }

    private void populateInfoCard(Profile profile) {
        ((TextView) findViewById(R.id.profileInfoName)).setText(profile.getName());
        ((TextView) findViewById(R.id.profileInfoId)).setText("ID#: " + profile.getIdNumber());
        ((TextView) findViewById(R.id.profileInfoDOB)).setText("Date of Birth: " + profile.getDob());
        ((TextView) findViewById(R.id.profileInfoGender)).setText("Gender: " + profile.getGender());

        if (profile.getHandedness().equals("Ambi")) {
            ((TextView) findViewById(R.id.profileInfoHandedness)).setText("Handedness: " + profile.getHandedness() + "dextrous");
        }
        else {
            ((TextView) findViewById(R.id.profileInfoHandedness)).setText("Handedness: " + profile.getHandedness() + "-handed");
        }

        ((TextView) findViewById(R.id.profileInfoEducation)).setText("Education Level: " + profile.getEducationLevel() + " years");

        if (profile.getNotes().length() == 0) {
            ((TextView) findViewById(R.id.profileInfoNotes)).setText("Notes: N/A");
        }
        else {
            ((TextView) findViewById(R.id.profileInfoNotes)).setText("Notes: " + profile.getNotes());
        }
    }

    public void populatedTableView(List<Results> resultsList) {
        // create Models
        mColumnHeaderList = createColumnHeaderModelList(resultsList);

        if (resultsList.size() == 0) {
            RelativeLayout layout = findViewById(R.id.profileInfoLayout);
            layout.removeView(mTableView);

            TextView tv = new TextView(this);
            tv.setText("No examinations for this profile.");

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);

            tv.setLayoutParams(layoutParams);
            layout.addView(tv);
        }
        else {
            mCellList = db.getAllResultsForProfile(resultsList.get(0).getResultsId());
        }
        mRowHeaderList = createRowHeaderList();

        // Set all items to the TableView
        mTableAdapter.setAllItems(mColumnHeaderList, mRowHeaderList, mCellList);
    }

    private List<ColumnHeaderModel> createColumnHeaderModelList(List<Results> resultsList) {
        List<ColumnHeaderModel> list = new ArrayList<>();

        // Create Column Headers
        for (int i = 0; i < resultsList.size(); i++) {
            // trim old database exam dates to not have the milliseconds show up
            if (resultsList.get(i).getTestDate().length() > 16) {
                list.add(new ColumnHeaderModel(resultsList.get(i).getTestDate().substring(0,16)));
            }
            else {
                list.add(new ColumnHeaderModel(resultsList.get(i).getTestDate()));
            }
        }

        return list;
    }

    private List<RowHeaderModel> createRowHeaderList() {
        List<RowHeaderModel> list = new ArrayList<>();

        list.add(new RowHeaderModel("RVE Target"));
        list.add(new RowHeaderModel("RVE Result"));
        list.add(new RowHeaderModel("RVE Deviation"));
        list.add(new RowHeaderModel("PRE1 Target"));
        list.add(new RowHeaderModel("PRE1 Result"));
        list.add(new RowHeaderModel("PRE2 Target"));
        list.add(new RowHeaderModel("PRE2 Result"));
        list.add(new RowHeaderModel("PVE1 Target"));
        list.add(new RowHeaderModel("PVE1 Result"));
        list.add(new RowHeaderModel("PVE2 Target"));
        list.add(new RowHeaderModel("PVE2 Result"));
        list.add(new RowHeaderModel("PPE1 Target"));
        list.add(new RowHeaderModel("PPE1 Result"));
        list.add(new RowHeaderModel("PPE2 Target"));
        list.add(new RowHeaderModel("PPE2 Result"));
        list.add(new RowHeaderModel("RBE Target"));
        list.add(new RowHeaderModel("RBE Mean"));
        list.add(new RowHeaderModel("RBE SD"));
        list.add(new RowHeaderModel("RBE Max"));
        list.add(new RowHeaderModel("RBE Min"));

        for (int x = 0; x < 60; x++) {
            list.add(new RowHeaderModel("RBE " + (x + 1)));
        }

        return list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_info_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, LoadProfileActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
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
        Intent intent = new Intent(this, RVEInstructions.class);
        intent.putExtra("id", id);
        intent.putExtra("bestDate", (new SimpleDateFormat("MM/dd/yyyy HH:mm")).format(new Date()));
        intent.putExtra("rveResult", "N/A");
        intent.putExtra("pre1Result", "N/A");
        intent.putExtra("pre2Result", "N/A");
        intent.putExtra("pve1Result", "N/A");
        intent.putExtra("pve2Result", "N/A");
        intent.putExtra("ppe1Result", "N/A");
        intent.putExtra("ppe2Result", "N/A");
        intent.putExtra("rbeResult", "N/A");
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
