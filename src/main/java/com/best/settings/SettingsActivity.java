package com.best.settings;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;

import com.best.R;

public class SettingsActivity extends AppCompatPreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupActionBar();
        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // allowing the Up button in the action bar to function correctly
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_defaultAll:
                defaultsDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void defaultsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Reset to defaults?")
                .setPositiveButton("RESET", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                restoreDefaults();
                dialog.dismiss();
            }
        })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void restoreDefaults() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPref.edit().clear().apply();

        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
    }

    public static class SettingsFragment extends PreferenceFragment
            implements SharedPreferences.OnSharedPreferenceChangeListener {
        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);

            final SharedPreferences sh = getPreferenceManager().getSharedPreferences();
            String[] prefKeys = {"rvePref", "prePref1", "prePref2", "pvePref1", "pvePref2", "ppePref1", "ppePref2", "rbePref"};
            setSummaries(sh, prefKeys);
        }

        @Override
        public void onResume() {
            final SharedPreferences sh = getPreferenceManager().getSharedPreferences();
            super.onResume();
            sh.registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onPause() {
            final SharedPreferences sh = getPreferenceManager().getSharedPreferences();
            super.onPause();
            sh.unregisterOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sh, String key) {
            if (key.equals("rvePref")
                    || key.equals("prePref1") || key.equals("prePref2")
                    || key.equals("pvePref1") || key.equals("pvePref2")
                    || key.equals("ppePref1") || key.equals("ppePref2")
                    || key.equals("rbePref")) {
                if (sh.getString(key, "").replaceFirst("^0+(?!$)", "").equals("1")) {
                    findPreference(key).setSummary("Target Time: " +
                            sh.getString(key, "").replaceFirst("^0+(?!$)", "") + " second");
                }
                else {
                    findPreference(key).setSummary("Target Time: " +
                            sh.getString(key, "").replaceFirst("^0+(?!$)", "") + " seconds");
                }

                EditTextPreference edt = (EditTextPreference) findPreference(key);
                edt.setText(sh.getString(key, "").replaceFirst("^0+(?!$)", ""));
            }
        }

        public void setSummaries(SharedPreferences sh, String[] prefKeys) {
            for (String key: prefKeys) {
                if (sh.getString(key, "").replaceFirst("^0+(?!$)", "").equals("1")) {
                    findPreference(key).setSummary("Target Time: " +
                            sh.getString(key, "").replaceFirst("^0+(?!$)", "") + " second");
                }
                else {
                    findPreference(key).setSummary("Target Time: " +
                            sh.getString(key, "").replaceFirst("^0+(?!$)", "") + " seconds");
                }

                EditTextPreference edt = (EditTextPreference) findPreference(key);
                edt.setText(sh.getString(key, "").replaceFirst("^0+(?!$)", ""));
            }
        }
    }

}