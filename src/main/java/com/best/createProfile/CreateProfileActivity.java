package com.best.createProfile;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.best.R;
import com.best.database.DatabaseHelper;
import com.best.database.Profile;
import com.best.subtasks.StartBESTfromCreate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CreateProfileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private DatabaseHelper db;
    // all the fields in the profile creation form
    private EditText idNumberEditText;
    private EditText lastNameEditText;
    private EditText firstNameEditText;
    private Spinner genderSpinner;
    private Spinner handednessSpinner;
    private Spinner educationSpinner;
    private EditText dobEditText;
    private EditText notesEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        db = DatabaseHelper.getInstance(this);
        this.idNumberEditText = findViewById(R.id.idNumberEditText);
        this.lastNameEditText = findViewById(R.id.lastNameEditText);
        this.firstNameEditText = findViewById(R.id.firstNameEditText);
        this.genderSpinner = findViewById(R.id.genderSpinner);
        this.handednessSpinner = findViewById(R.id.handednessSpinner);
        this.educationSpinner = findViewById(R.id.educationSpinner);
        this.dobEditText = findViewById(R.id.dobEditText);
        this.notesEditText = findViewById(R.id.notesEditText);

        // options to be shown in spinners
        String[] genderArray = {"Gender", "Male", "Female"};
        String[] handednessArray = {"Handedness", "Right-handed", "Left-handed", "Ambidextrous"};
        String[] educationArray = {"Education Level", "8 years", "9 years", "10 years",
                "11 years", "12 years", "13 years", "14 years", "15 years",
                "16 years", "17 years", "18 years", "19 years", "20 years"};

        populateSpinner(genderArray, this.genderSpinner);
        populateSpinner(handednessArray, this.handednessSpinner);
        populateSpinner(educationArray, this.educationSpinner);
        new DateInputMask(this.dobEditText);
    }

    // add the "Save Profile" button to the menu bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.create_profile_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // populates spinner items with values in passed array
    private void populateSpinner(String[] array, Spinner spinner) {
        spinner.setOnItemSelectedListener(this);
        final List<String> list = new ArrayList<>(Arrays.asList(array));

        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, R.layout.item_spinner, list) {
            @Override
            public boolean isEnabled(int position) {
                // Disable the first item from Spinner (position 0)
                // First item will be used for hint
                return position != 0;
            }
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                // Set the hint text color gray
                tv.setTextColor(position == 0 ? Color.GRAY : Color.BLACK);
                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(R.layout.item_spinner);
        spinner.setAdapter(spinnerArrayAdapter);
    }

    // for spinner
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // set spinner text to BLACK when an item is chosen
        TextView selectedText = (TextView) adapterView.getChildAt(0);
        if (i != 0) {
            selectedText.setTextColor(Color.BLACK);
        }
    }

    // also for spinner
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // do nothing
    }

    // when the "Save Profile" button (in the menu bar) is clicked
    public void createProfileSaveClick(MenuItem item) {
        if (!formCompleted()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Incomplete Form");
            builder.setMessage("Please completely fill out the profile.");

            builder.setPositiveButton("OK", null);

            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else if (db.profileExists(idNumberEditText.getText().toString())) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("ID Taken");
            builder.setMessage("There is already a profile with that ID. Please enter a different ID.");

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // do nothing
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Save Profile");

            builder.setPositiveButton("Save and Administer BEST", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    saveProfile();
                    startBEST();
                }
            });
            builder.setNegativeButton("Save Only", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    saveProfile();
                    finish();
                }
            });
            builder.setNeutralButton("Cancel", null);

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    // checks if the form is completed or not
    private boolean formCompleted() {
        return !(this.idNumberEditText.getText().toString().isEmpty()
                || this.lastNameEditText.getText().toString().trim().isEmpty()
                || this.firstNameEditText.getText().toString().trim().isEmpty()
                || this.genderSpinner.getSelectedItemPosition() == 0
                || this.handednessSpinner.getSelectedItemPosition() == 0
                || this.educationSpinner.getSelectedItemPosition() == 0
                || this.dobEditText.getText().toString().trim().isEmpty()
                || this.dobEditText.getText().toString().trim().contains("M")
                || this.dobEditText.getText().toString().trim().contains("D")
                || this.dobEditText.getText().toString().trim().contains("Y"));
    }

    private void saveProfile() {
        // set profile creation date and time
        String date = (new SimpleDateFormat("MM/dd/yyyy HH:mm:SSS")).format(new Date());

        // checks whether to trim "dextrous" or "-handed" from the handedness value
        if (this.handednessSpinner.getSelectedItemPosition() == 3) {
            db.addProfile(new Profile(this.idNumberEditText.getText().toString(),
                    this.lastNameEditText.getText().toString().trim(),
                    this.firstNameEditText.getText().toString().trim(),
                    this.genderSpinner.getSelectedItem().toString(),
                    this.handednessSpinner.getSelectedItem().toString().replace("dextrous", ""),
                    this.educationSpinner.getSelectedItem().toString().replace(" years", ""),
                    this.dobEditText.getText().toString(),
                    this.notesEditText.getText().toString().trim(),
                    date, "N/A"));
        }
        else {
            db.addProfile(new Profile(this.idNumberEditText.getText().toString(),
                    this.lastNameEditText.getText().toString().trim(),
                    this.firstNameEditText.getText().toString().trim(),
                    this.genderSpinner.getSelectedItem().toString(),
                    this.handednessSpinner.getSelectedItem().toString().replace("-handed", ""),
                    this.educationSpinner.getSelectedItem().toString().replace(" years", ""),
                    this.dobEditText.getText().toString(),
                    this.notesEditText.getText().toString().trim(),
                    date, "N/A"));
        }
    }

    private void startBEST() {
        Intent intent = new Intent(this, StartBESTfromCreate.class);
        intent.putExtra("id", this.idNumberEditText.getText().toString());
        startActivity(intent);
    }
}