package com.best.loadProfile;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
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
import com.tsongkha.spinnerdatepicker.DatePicker;
import com.tsongkha.spinnerdatepicker.DatePickerDialog;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class EditProfileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener {

    // all the fields in the profile creation form
    private DatabaseHelper db;
    private String id;
    private EditText idNumberEditText;
    private EditText lastNameEditText;
    private EditText firstNameEditText;
    private Spinner genderSpinner;
    private Spinner handednessSpinner;
    private Spinner educationSpinner;
    private EditText dobEditText;
    private EditText notesEditText;
    private String date;
    private String lastExamination;

    SimpleDateFormat simpleDateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // force the date picker to show date in US format
        String languageToLoad  = "en";
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

        setContentView(R.layout.activity_edit_profile);

        setupActionBar();

        this.idNumberEditText = findViewById(R.id.idNumberEditText2);
        this.lastNameEditText = findViewById(R.id.lastNameEditText2);
        this.firstNameEditText = findViewById(R.id.firstNameEditText2);
        this.genderSpinner = findViewById(R.id.genderSpinner2);
        this.handednessSpinner = findViewById(R.id.handednessSpinner2);
        this.educationSpinner = findViewById(R.id.educationSpinner2);
        this.dobEditText = findViewById(R.id.dobEditText2);
        this.notesEditText = findViewById(R.id.notesEditText2);

        // options to be shown in spinners
        String[] genderArray = {"Gender", "Male", "Female"};
        String[] handednessArray = {"Handedness", "Right-handed", "Left-handed", "Ambidextrous"};
        String[] educationArray = {"Education Level", "8 years", "9 years", "10 years",
                "11 years", "12 years", "13 years", "14 years", "15 years",
                "16 years", "17 years", "18 years", "19 years", "20 years"};

        populateSpinner(genderArray, this.genderSpinner);
        populateSpinner(handednessArray, this.handednessSpinner);
        populateSpinner(educationArray, this.educationSpinner);

        simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        this.dobEditText.setInputType(InputType.TYPE_NULL);

        this.dobEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateOfBirth();
            }
        });

        this.dobEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    dateOfBirth();
                }
            }
        });

        fillProfile();
    }

    private void dateOfBirth() {
        Date date1 = new Date();
        String date[] = this.dobEditText.getText().toString().split("/");

        new SpinnerDatePickerDialogBuilder()
                .context(EditProfileActivity.this)
                .callback(EditProfileActivity.this)
                .spinnerTheme(R.style.DatePickerStyle)
                .defaultDate(Integer.parseInt(date[2]),
                        Integer.parseInt(date[0]) - 1,
                        Integer.parseInt(date[1]))
                .maxDate(Integer.parseInt((new SimpleDateFormat("yyyy")).format(date1)),
                        Integer.parseInt((new SimpleDateFormat("M")).format(date1)) - 1,
                        Integer.parseInt((new SimpleDateFormat("dd")).format(date1)))
                .build()
                .show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
        dobEditText.setText(simpleDateFormat.format(calendar.getTime()));
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void fillProfile() {
        db = DatabaseHelper.getInstance(this);
        this.id = "";
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            this.id = (String) bundle.get("id");
        }

        Profile profile = db.getProfile(id);

        this.idNumberEditText.setText(profile.getIdNumber());
        this.lastNameEditText.setText(profile.getLastName());
        this.firstNameEditText.setText(profile.getFirstName());

        this.date = profile.getCreationDate();
        this.lastExamination = profile.getLastExamination();

        switch (profile.getGender()) {
            case "Male":
                this.genderSpinner.setSelection(1);
                break;
            case "Female":
                this.genderSpinner.setSelection(2);
                break;
            default:
                break;
        }

        switch (profile.getHandedness()) {
            case "Right":
                this.handednessSpinner.setSelection(1);
                break;
            case "Left":
                this.handednessSpinner.setSelection(2);
                break;
            case "Ambi":
                this.handednessSpinner.setSelection(3);
                break;
            default:
                break;
        }

        this.educationSpinner.setSelection(Integer.parseInt(profile.getEducationLevel()) - 7);
        this.dobEditText.setText(profile.getDob());
        this.notesEditText.setText(profile.getNotes());
    }

    // add the "Save Profile" button to the menu bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_profile_actions, menu);
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
    public void editProfileSaveClick(MenuItem item) {
        if (!formCompleted()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Incomplete Form");
            builder.setMessage("Please completely fill out the profile.");

            builder.setPositiveButton("OK", null);

            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else if (idExists()) {
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
            builder.setTitle("Save Changes");

            builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    saveChanges();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // do nothing
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    // check if profile id exists in database
    private boolean idExists() {
        // if we're not changing the id, it's fine
        if (this.id.equals(this.idNumberEditText.getText().toString())) {
            return false;
        }
        else return db.profileExists(this.idNumberEditText.getText().toString());
    }

    // checks if the form is completed or not
    private boolean formCompleted() {
        return !(this.idNumberEditText.getText().toString().isEmpty()
                || this.lastNameEditText.getText().toString().trim().isEmpty()
                || this.firstNameEditText.getText().toString().trim().isEmpty()
                || this.genderSpinner.getSelectedItemPosition() == 0
                || this.handednessSpinner.getSelectedItemPosition() == 0
                || this.educationSpinner.getSelectedItemPosition() == 0
                || this.dobEditText.getText().toString().trim().isEmpty());
    }

    private void saveChanges() {
        Profile profile;

        // checks whether to trim "dextrous" or "-handed" from the handedness value
        if (this.handednessSpinner.getSelectedItemPosition() == 3) {
            profile = new Profile(this.idNumberEditText.getText().toString(),
                    this.lastNameEditText.getText().toString().trim(),
                    this.firstNameEditText.getText().toString().trim(),
                    this.genderSpinner.getSelectedItem().toString(),
                    this.handednessSpinner.getSelectedItem().toString().replace("dextrous", ""),
                    this.educationSpinner.getSelectedItem().toString().replace(" years", ""),
                    this.dobEditText.getText().toString(),
                    this.notesEditText.getText().toString().trim(),
                    this.date, this.lastExamination);


        }
        else {
            profile = new Profile(this.idNumberEditText.getText().toString(),
                    this.lastNameEditText.getText().toString().trim(),
                    this.firstNameEditText.getText().toString().trim(),
                    this.genderSpinner.getSelectedItem().toString(),
                    this.handednessSpinner.getSelectedItem().toString().replace("-handed", ""),
                    this.educationSpinner.getSelectedItem().toString().replace(" years", ""),
                    this.dobEditText.getText().toString(),
                    this.notesEditText.getText().toString().trim(),
                    this.date, this.lastExamination);
        }

        db.updateProfile(this.id, profile);

        Intent intent = new Intent(this, ProfileInfoActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("profileId", this.idNumberEditText.getText().toString());
        startActivity(intent);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}