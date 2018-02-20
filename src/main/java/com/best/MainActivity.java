package com.best;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.best.createProfile.CreateProfileActivity;
import com.best.database.DatabaseHelper;
import com.best.database.Profile;
import com.best.database.Results;
import com.best.loadProfile.LoadProfileActivity;
import com.best.settings.SettingsActivity;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void infoClick(MenuItem item) {
        Intent intent = new Intent(this, AdditionalInfo.class);
        startActivity(intent);
    }

    public void settingsClick(MenuItem item) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void createProfileClick(View view) {
        Intent intent = new Intent(this, CreateProfileActivity.class);
        startActivity(intent);
    }

    public void loadProfileClick(View view) {
        Intent intent = new Intent(this, LoadProfileActivity.class);
        startActivity(intent);
    }

    public void instructionsClick(View view) {
        Intent intent = new Intent(this, InstructionsActivity.class);
        startActivity(intent);
    }

    public void exportClick(View view) {

        DatabaseHelper db = DatabaseHelper.getInstance(this);

        if (db.getProfilesCount() == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("No data to export!");

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Export Data?");

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    exportData();
                }
            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // do nothing
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    private void exportData() {
        DatabaseHelper db = DatabaseHelper.getInstance(getApplicationContext());

        File fileDirectory = getFilesDir();

        if (!fileDirectory.exists()){
            fileDirectory.mkdir();
        }

        String date = (new SimpleDateFormat("MM-dd-yyyy_HH.mm.SSS")).format(new Date());
        File file = new File(fileDirectory, "BEST_" + date + ".csv");

        try
        {
            file.createNewFile();
            CSVWriter writer = new CSVWriter(new FileWriter(file));
            List<Profile> profileList = db.getAllProfiles();

            String[] columns = {"ID#", "Last Name", "First Name", "Date of Birth",
                    "Gender", "Handedness", "Education Level", "Notes", "Profile Creation Date",
                    "Examination Date",
                    "RVE Target", "RVE Result",
                    "PRE1 Target", "PRE1 Result", "PRE2 Target", "PRE2 Result",
                    "PVE1 Target", "PVE1 Result", "PVE2 Target", "PVE2 Result",
                    "PPE1 Target", "PPE1 Result", "PPE2 Target", "PPE2 Result",
                    "RBE Target", "RBE Result", "RBE Mean", "RBE SD"};

            writer.writeNext(columns);

            for (Profile profile : profileList) {
                List<Results> resultsList = db.getResults(profile.getIdNumber());

                if (resultsList.size() == 0) {
                    String data[] = {profile.getIdNumber(), profile.getLastName(), profile.getFirstName(),
                            profile.getDob(), profile.getGender(), profile.getHandedness(),
                            profile.getEducationLevel(), profile.getNotes(), profile.getCreationDate(),
                            "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A",
                            "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A", "N/A"};

                            writer.writeNext(data);
                }
                else {
                    for (Results results : resultsList) {
                        String data[] = {profile.getIdNumber(), profile.getLastName(), profile.getFirstName(),
                                profile.getDob(), profile.getGender(), profile.getHandedness(),
                                profile.getEducationLevel(), profile.getNotes(), profile.getCreationDate(),
                                results.getTestDate(), results.getRveTarget(), results.getRveResult(),
                                results.getPre1Target(), results.getPre1Result(),
                                results.getPre2Target(), results.getPre2Result(),
                                results.getPve1Target(), results.getPve1Result(),
                                results.getPve2Target(), results.getPve2Result(),
                                results.getPpe1Target(), results.getPpe1Result(),
                                results.getPpe2Target(), results.getPpe2Result(),
                                results.getRbeTarget(), results.getRbeResult(),
                                results.getRbeMean(), results.getRbeSD()};

                        writer.writeNext(data);
                    }
                }
            }

            writer.close();

            Uri uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID, file);

            Intent intent = new Intent(Intent.ACTION_SEND);
            // the attachment
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            // the mail subject
            intent.putExtra(Intent.EXTRA_SUBJECT, "BEST_" + date);
            // set flag to give temporary permission to external app to use your FileProvider
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            // intent type
            intent.setType("message/rfc822");

            startActivity(Intent.createChooser(intent, "Export Data"));
        }
        catch(Exception sqlEx)
        {
            Log.e("MainActivity", sqlEx.getMessage(), sqlEx);
        }
    }
}
