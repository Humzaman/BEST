package com.best.loadProfile;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.best.MainActivity;
import com.best.R;
import com.best.database.DatabaseHelper;
import com.best.database.Profile;
import com.tsongkha.spinnerdatepicker.DatePicker;
import com.tsongkha.spinnerdatepicker.DatePickerDialog;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class LoadProfileActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private DatabaseHelper db;
    private int checked;
    private EditText editText;
    private SimpleDateFormat simpleDateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_profile);

        db = DatabaseHelper.getInstance(this);
        List<Profile> profiles = db.getAllProfiles();

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_search, null);

        editText = dialogView.findViewById(R.id.searchEditText);
        checked = R.id.searchRadioName;
        simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        recyclerView = findViewById(R.id.loadProfileRecyclerView);

        if (profiles.size() != 0) {
            // create and fill the recycler view with profile cards
            recyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new ProfileCardRecyclerAdapter(profiles);
            recyclerView.setAdapter(adapter);
        }
        else {
            LinearLayout layout = findViewById(R.id.loadProfileLinearLayout);
            layout.setBackgroundColor(Color.WHITE);
            layout.removeView(recyclerView);

            TextView tv = new TextView(this);
            tv.setText("No profiles to display.");
            tv.setGravity(Gravity.CENTER);

            LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            tv.setLayoutParams(layoutParams);

            layout.addView(tv);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_search, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public void searchClick(MenuItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View dialogView = (this.getLayoutInflater()).inflate(R.layout.dialog_search, null);
        final EditText editText = dialogView.findViewById(R.id.searchEditText);
        final RadioGroup radioGroup = dialogView.findViewById(R.id.searchRadioGroup);
        final LinearLayout layout = dialogView.findViewById(R.id.searchLayout);
        final EditText fnameET = dialogView.findViewById(R.id.searchEditText2);

        if (checked != R.id.searchRadioName) {
            editText.setHint("Search term");
            layout.removeView(fnameET);
        }

        radioGroup.check(checked);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                checked = i;
                editText.setText("");

                switch (i) {
                    case R.id.searchRadioID:
                        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                        editText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(9)});
                        editTextOnClickDate(false, editText);
                        if (layout.getChildCount() > 2) {
                            editText.setHint("Search term");
                            fnameET.setText("");
                            layout.removeView(fnameET);
                        }
                        break;
                    case R.id.searchRadioName:
                        editText.setInputType(InputType.TYPE_CLASS_TEXT);
                        editText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(35)});
                        editTextOnClickDate(false, editText);
                        editText.setHint("Last Name");
                        layout.addView(fnameET);
                        break;
                    case R.id.searchRadioDOB:
                        editText.setFilters(new InputFilter[] {});
                        InputMethodManager imm = (InputMethodManager) dialogView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        if (imm != null) {
                            imm.hideSoftInputFromWindow(dialogView.getWindowToken(), 0);
                        }
                        editTextOnClickDate(true, editText);
                        if (layout.getChildCount() > 2) {
                            editText.setHint("Search term");
                            fnameET.setText("");
                            layout.removeView(fnameET);
                        }
                        break;
                    case R.id.searchRadioDOE:
                        editText.setFilters(new InputFilter[] {});
                        InputMethodManager imm2 = (InputMethodManager) dialogView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        if (imm2 != null) {
                            imm2.hideSoftInputFromWindow(dialogView.getWindowToken(), 0);
                        }
                        editTextOnClickDate(true, editText);
                        if (layout.getChildCount() > 2) {
                            editText.setHint("Search term");
                            fnameET.setText("");
                            layout.removeView(fnameET);
                        }
                        break;
                    default:
                        break;
                }
            }
        });

        builder.setView(dialogView);
        builder.setTitle("Search by");

        builder.setPositiveButton("SEARCH", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (checked != R.id.searchRadioName) {
                    if (!editText.getText().toString().trim().equals("")) {
                        searchProfiles(editText.getText().toString().trim(), radioGroup.getCheckedRadioButtonId());
                    }
                }
                else {
                    searchName(editText.getText().toString().trim(), fnameET.getText().toString().trim());
                }
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //layout.removeView(fnameET);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

        this.editText = editText;
    }

    private void searchProfiles(String searchTerm, int checked) {
        switch (checked) {
            case R.id.searchRadioID:
                adapter = new ProfileCardRecyclerAdapter(db.searchDB(searchTerm, 0));
                break;
            case R.id.searchRadioDOB:
                adapter = new ProfileCardRecyclerAdapter(db.searchDB(searchTerm, 1));
                break;
            case R.id.searchRadioDOE:
                adapter = new ProfileCardRecyclerAdapter(db.searchDOE(searchTerm));
                break;
            default:
                break;
        }

        recyclerView.setAdapter(adapter);
    }

    private void searchName(String lastName, String firstName) {
        adapter = new ProfileCardRecyclerAdapter(db.searchName(lastName, firstName));
        recyclerView.setAdapter(adapter);
    }

    private void date(EditText editText) {
        Date date = new Date();

        if (editText.getText().toString().equals("")) {

            new SpinnerDatePickerDialogBuilder()
                    .context(LoadProfileActivity.this)
                    .callback(LoadProfileActivity.this)
                    .spinnerTheme(R.style.DatePickerStyle)
                    .defaultDate(Integer.parseInt((new SimpleDateFormat("yyyy")).format(date)), Integer.parseInt((new SimpleDateFormat("M")).format(date)) - 1, Integer.parseInt((new SimpleDateFormat("dd")).format(date)))
                    .build()
                    .show();
        }
        else {
            String inputtedDate[] = editText.getText().toString().split("/");

            new SpinnerDatePickerDialogBuilder()
                    .context(LoadProfileActivity.this)
                    .callback(LoadProfileActivity.this)
                    .spinnerTheme(R.style.DatePickerStyle)
                    .defaultDate(Integer.parseInt(inputtedDate[2]),
                            Integer.parseInt(inputtedDate[0]) - 1,
                            Integer.parseInt(inputtedDate[1]))
                    .build()
                    .show();
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
        editText.setText(simpleDateFormat.format(calendar.getTime()));
    }

    private void editTextOnClickDate(boolean tf, final EditText editText) {
        if (tf) {
            editText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    date(editText);
                }
            });
        }
        else {
            editText.setOnClickListener(null);
        }
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