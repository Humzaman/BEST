package com.best.subtasks;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.best.MainActivity;
import com.best.R;
import com.best.database.DatabaseHelper;
import com.best.database.Profile;
import com.best.database.Results;
import com.best.loadProfile.ProfileInfoActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EndBEST extends AppCompatActivity {

    TextView textView;

    // target times from sharedprefs
    private String rveTarget;
    private String preTarget1;
    private String preTarget2;
    private String pveTarget1;
    private String pveTarget2;
    private String ppeTarget1;
    private String ppeTarget2;
    private String rbeTarget;

    // results from bundle
    private String date;
    private String id;
    private String rveResult;
    private String rveDeviation;
    private String pre1Result;
    private String pre2Result;
    private String pve1Result;
    private String pve2Result;
    private String ppe1Result;
    private String ppe2Result;
    private String rbeResult;

    private String rbeMean;
    private String rbeSD;
    private String rbeMin;
    private String rbeMax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_best);

        setupActionBar();

        this.textView = findViewById(R.id.EndResultsTextView);
        this.textView.setMovementMethod(new ScrollingMovementMethod());

        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(this);
        this.rveTarget = sh.getString("rvePref", "53");
        this.preTarget1 = sh.getString("prePref1", "23");
        this.preTarget2 = sh.getString("prePref2", "53");
        this.pveTarget1 = sh.getString("pvePref1", "23");
        this.pveTarget2 = sh.getString("pvePref2", "53");
        this.ppeTarget1 = sh.getString("ppePref1", "23");
        this.ppeTarget2 = sh.getString("ppePref2", "53");
        this.rbeTarget = sh.getString("rbePref", "750");

        Bundle bundle = getIntent().getExtras();
        this.date = "";
        this.id = "";
        this.rveResult = "";
        this.pre1Result = "";
        this.pre2Result = "";
        this.pve1Result = "";
        this.pve2Result = "";
        this.ppe1Result = "";
        this.ppe2Result = "";
        this.rbeResult = "";

        if (bundle != null) {
            this.date = (String) bundle.get("bestDate");
            this.id = (String) bundle.get("id");
            this.rveResult = (String) bundle.get("rveResult");

            if (this.rveResult.equals("N/A")) {
                this.rveDeviation = "N/A";
            }
            else {
                this.rveDeviation = Integer.toString(Integer.parseInt(this.rveResult) - Integer.parseInt(this.rveTarget));
            }

            this.pre1Result = (String) bundle.get("pre1Result");
            this.pre2Result = (String) bundle.get("pre2Result");
            this.pve1Result = (String) bundle.get("pve1Result");
            this.pve2Result = (String) bundle.get("pve2Result");
            this.ppe1Result = (String) bundle.get("ppe1Result");
            this.ppe2Result = (String) bundle.get("ppe2Result");
            this.rbeResult = (String) bundle.get("rbeResult");

            if (this.rbeResult.equals("N/A")) {
                this.rbeMean = "N/A";
                this.rbeSD = "N/A";
            }
            else {
                calculateMeanAndSDAndRange();
            }
        }

        showResults();
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
                previous();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void previous() {
        Bundle bundle = getIntent().getExtras();
        String date = "";
        String id = "";
        String rveResult = "";
        String pre1Result = "";
        String pre2Result = "";
        String pve1Result = "";
        String pve2Result = "";
        String ppe1Result = "";
        String ppe2Result = "";
        String rbeResult = "";

        if (bundle != null) {
            date = (String) bundle.get("bestDate");
            id = (String) bundle.get("id");
            rveResult = (String) bundle.get("rveResult");
            pre1Result = (String) bundle.get("pre1Result");
            pre2Result = (String) bundle.get("pre2Result");
            pve1Result = (String) bundle.get("pve1Result");
            pve2Result = (String) bundle.get("pve2Result");
            ppe1Result = (String) bundle.get("ppe1Result");
            ppe2Result = (String) bundle.get("ppe2Result");
            rbeResult = (String) bundle.get("rbeResult");
        }

        Intent intent = new Intent(this, BESTComplete.class);
        intent.putExtra("bestDate", date);
        intent.putExtra("id", id);
        intent.putExtra("rveResult", rveResult);
        intent.putExtra("pre1Result", pre1Result);
        intent.putExtra("pre2Result", pre2Result);
        intent.putExtra("pve1Result", pve1Result);
        intent.putExtra("pve2Result", pve2Result);
        intent.putExtra("ppe1Result", ppe1Result);
        intent.putExtra("ppe2Result", ppe2Result);
        intent.putExtra("rbeResult", rbeResult);
        startActivity(intent);
    }

    // add save and delete icons
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_results_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Stop BEST");
        builder.setMessage("Are you sure you want to stop administering this BEST?\n\nResults will not be saved!");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                stopBEST();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // do nothing
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void stopBEST() {
        Bundle bundle = getIntent().getExtras();
        String id = "";

        if (bundle != null) {
            id = (String) bundle.get("id");
        }

        Intent intent = new Intent(this, ProfileInfoActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("profileId", id);
        startActivity(intent);
    }

    private void calculateMeanAndSDAndRange() {
        List<String> rbeItems = Arrays.asList(this.rbeResult.split(","));
        List<Integer> rbeItemsInt = new ArrayList<>();

        for (String s : rbeItems) {
            rbeItemsInt.add(Integer.valueOf(s.trim()));
        }

        this.rbeMax = String.valueOf(Collections.max(rbeItemsInt));
        this.rbeMin = String.valueOf(Collections.min(rbeItemsInt));

        // calculate mean
        int meanSum = 0;

        for (Integer i : rbeItemsInt) {
            meanSum += i;
        }

        double mean = meanSum / rbeItems.size();
        this.rbeMean = String.valueOf((new DecimalFormat("#.#")).format(mean));

        // calculate standard deviation
        double sdSum = 0.0;

        for (Integer j : rbeItemsInt) {
            sdSum += ((j - mean) * (j - mean));
        }

        this.rbeSD = String.valueOf((new DecimalFormat("#.#")).format(Math.sqrt(sdSum / rbeItems.size())));
    }

    private void showResults() {
        String id = "<b>Examinee ID#: </b>" + this.id;
        String date = "<b>Examination Date: </b>" + this.date;

        String rveTitle = "<b><big>Retrospective Verbal Estimate</big></b>";
        String rveTarget;
        if (this.rveTarget.equals("1"))
            rveTarget = "<b>RVE Target Time: </b>" + this.rveTarget + " second";
        else
            rveTarget = "<b>RVE Target Time: </b>" + this.rveTarget + " seconds";

        String rveResult;
        if (this.rveResult.equals("1"))
            rveResult = "<b>RVE Result: </b>" + this.rveResult + " second";
        else if (this.rveResult.equals("N/A"))
            rveResult = "<b>RVE Result: </b>" + this.rveResult;
        else
            rveResult = "<b>RVE Result: </b>" + this.rveResult + " seconds";

        String rveDeviation;
        if (this.rveDeviation.equals("1"))
            rveDeviation = "<b>RVE Deviation: </b>" + this.rveDeviation + " second";
        else if (this.rveDeviation.equals("N/A"))
            rveDeviation = "<b>RVE Deviation: </b>" + this.rveDeviation;
        else
            rveDeviation = "<b>RVE Deviation: </b>" + this.rveDeviation + " seconds";

        String preTitle = "<b><big>Prospective Reproduction Estimate</big></b>";
        String preTarget1;
        if (this.preTarget1.equals("1"))
            preTarget1 = "<b>PRE 1 Target Time: </b>" + this.preTarget1 + " second";
        else
            preTarget1 = "<b>PRE 1 Target Time: </b>" + this.preTarget1 + " seconds";

        String pre1Result;
        if (this.pre1Result.equals("1"))
            pre1Result = "<b>PRE 1 Result: </b>" + this.pre1Result + " second";
        else if (this.pre1Result.equals("N/A"))
            pre1Result = "<b>PRE 1 Result: </b>" + this.pre1Result;
        else
            pre1Result = "<b>PRE 1 Result: </b>" + this.pre1Result + " seconds";

        String preTarget2;
        if (this.preTarget2.equals("1"))
            preTarget2 = "<b>PRE 2 Target Time: </b>" + this.preTarget2 + " second";
        else
            preTarget2 = "<b>PRE 2 Target Time: </b>" + this.preTarget2 + " seconds";

        String pre2Result;
        if (this.pre2Result.equals("1"))
            pre2Result = "<b>PRE 2 Result: </b>" + this.pre2Result + " second";
        else if (this.pre2Result.equals("N/A"))
            pre2Result = "<b>PRE 2 Result: </b>" + this.pre2Result;
        else
            pre2Result = "<b>PRE 2 Result: </b>" + this.pre2Result + " seconds";

        String pveTitle = "<b><big>Prospective Verbal Estimate</big></b>";
        String pveTarget1;
        if (this.pveTarget1.equals("1"))
            pveTarget1 = "<b>PVE 1 Target Time: </b>" + this.pveTarget1 + " second";
        else
            pveTarget1 = "<b>PVE 1 Target Time: </b>" + this.pveTarget1 + " seconds";

        String pve1Result;
        if (this.pve1Result.equals("1"))
            pve1Result = "<b>PVE 1 Result: </b>" + this.pve1Result + " second";
        else if (this.pve1Result.equals("N/A"))
            pve1Result = "<b>PVE 1 Result: </b>" + this.pve1Result;
        else
            pve1Result = "<b>PVE 1 Result: </b>" + this.pve1Result + " seconds";

        String pveTarget2;
        if (this.pveTarget2.equals("1"))
            pveTarget2 = "<b>PVE 2 Target Time: </b>" + this.pveTarget2 + " second";
        else
            pveTarget2 = "<b>PVE 2 Target Time: </b>" + this.pveTarget2 + " seconds";

        String pve2Result;
        if (this.pve2Result.equals("1"))
            pve2Result = "<b>PVE 2 Result: </b>" + this.pve2Result + " second";
        else if (this.pve2Result.equals("N/A"))
            pve2Result = "<b>PVE 2 Result: </b>" + this.pve2Result;
        else
            pve2Result = "<b>PVE 2 Result: </b>" + this.pve2Result + " seconds";

        String ppeTitle = "<b><big>Prospective Production Estimate</big></b>";
        String ppeTarget1;
        if (this.ppeTarget1.equals("1"))
            ppeTarget1 = "<b>PPE 1 Target Time: </b>" + this.ppeTarget1 + " second";
        else
            ppeTarget1 = "<b>PPE 1 Target Time: </b>" + this.ppeTarget1 + " seconds";

        String ppe1Result;
        if (this.ppe1Result.equals("1"))
            ppe1Result = "<b>PPE 1 Result: </b>" + this.ppe1Result + " second";
        else if (this.ppe1Result.equals("N/A"))
            ppe1Result = "<b>PPE 1 Result: </b>" + this.ppe1Result;
        else
            ppe1Result = "<b>PPE 1 Result: </b>" + this.ppe1Result + " seconds";

        String ppeTarget2;
        if (this.ppeTarget2.equals("1"))
            ppeTarget2 = "<b>PPE 2 Target Time: </b>" + this.ppeTarget2 + " second";
        else
            ppeTarget2 = "<b>PPE 2 Target Time: </b>" + this.ppeTarget2 + " seconds";

        String ppe2Result;
        if (this.ppe2Result.equals("1"))
            ppe2Result = "<b>PPE 2 Result: </b>" + this.ppe2Result + " second";
        else if (this.ppe2Result.equals("N/A"))
            ppe2Result = "<b>PPE 2 Result: </b>" + this.ppe2Result;
        else
            ppe2Result = "<b>PPE 2 Result: </b>" + this.ppe2Result + " seconds";

        String rbeTitle = "<b><big>Rhythmic Beats Estimate</big></b>";
        String rbeTarget;
        if (this.rbeTarget.equals("1"))
            rbeTarget = "<b>RBE Beat Interval: </b>" + this.rbeTarget + " millisecond";
        else
            rbeTarget = "<b>RBE Beat Interval: </b>" + this.rbeTarget + " milliseconds";
        String rbeResult = "<b>RBE Results: </b>";
        String rbeMax = "<b>RBE Maximum Response: </b>" + this.rbeMax;
        String rbeMin = "<b>RBE Minimum Response: </b>" + this.rbeMin;
        String rbeMean = "<b>RBE Mean: </b>" + this.rbeMean;
        String rbeSD = "<b>RBE Standard Deviation: </b>" + this.rbeSD;

        this.textView.setText("\n");
        this.textView.append(Html.fromHtml(id));
        this.textView.append("\n");
        this.textView.append(Html.fromHtml(date));
        this.textView.append("\n\n");
        this.textView.append(Html.fromHtml(rveTitle));
        this.textView.append("\n\n");
        this.textView.append(Html.fromHtml(rveTarget));
        this.textView.append("\n");
        this.textView.append(Html.fromHtml(rveResult));
        this.textView.append("\n");
        this.textView.append(Html.fromHtml(rveDeviation));
        this.textView.append("\n\n");
        this.textView.append(Html.fromHtml(preTitle));
        this.textView.append("\n\n");
        this.textView.append(Html.fromHtml(preTarget1));
        this.textView.append("\n");
        this.textView.append(Html.fromHtml(pre1Result));
        this.textView.append("\n\n");
        this.textView.append(Html.fromHtml(preTarget2));
        this.textView.append("\n");
        this.textView.append(Html.fromHtml(pre2Result));
        this.textView.append("\n\n");
        this.textView.append(Html.fromHtml(pveTitle));
        this.textView.append("\n\n");
        this.textView.append(Html.fromHtml(pveTarget1));
        this.textView.append("\n");
        this.textView.append(Html.fromHtml(pve1Result));
        this.textView.append("\n\n");
        this.textView.append(Html.fromHtml(pveTarget2));
        this.textView.append("\n");
        this.textView.append(Html.fromHtml(pve2Result));
        this.textView.append("\n\n");
        this.textView.append(Html.fromHtml(ppeTitle));
        this.textView.append("\n\n");
        this.textView.append(Html.fromHtml(ppeTarget1));
        this.textView.append("\n");
        this.textView.append(Html.fromHtml(ppe1Result));
        this.textView.append("\n\n");
        this.textView.append(Html.fromHtml(ppeTarget2));
        this.textView.append("\n");
        this.textView.append(Html.fromHtml(ppe2Result));
        this.textView.append("\n\n");
        this.textView.append(Html.fromHtml(rbeTitle));
        this.textView.append("\n\n");
        this.textView.append(Html.fromHtml(rbeTarget));
        this.textView.append("\n\n");
        this.textView.append(Html.fromHtml(rbeMean));
        this.textView.append("\n");
        this.textView.append(Html.fromHtml(rbeSD));
        this.textView.append("\n");
        this.textView.append(Html.fromHtml(rbeMax));
        this.textView.append("\n");
        this.textView.append(Html.fromHtml(rbeMin));
        this.textView.append("\n\n");
        this.textView.append(Html.fromHtml(rbeResult));

        if (this.rbeResult.equals("N/A"))
            this.textView.append(this.rbeResult);
        else
            this.textView.append("\n" + this.rbeResult);
    }

    public void saveResultsDialog(MenuItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Save Results");

        builder.setPositiveButton("Save and return to menu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveResults();
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

    private void saveResults() {
        DatabaseHelper db = DatabaseHelper.getInstance(this);
        Results results = new Results(rveTarget,
                preTarget1, preTarget2,
                pveTarget1, pveTarget2,
                ppeTarget1, ppeTarget2,
                rbeTarget,
                rveResult, rveDeviation,
                pre1Result, pre2Result,
                pve1Result, pve2Result,
                ppe1Result, ppe2Result,
                rbeResult, rbeMean, rbeSD,
                rbeMax, rbeMin,
                date, id);

        Profile profile = db.getProfile(id);
        profile.setLastExamination(date);

        db.addResults(results);
        db.updateProfile(id, profile);

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void deleteResultsDialog(MenuItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Discard Results");
        builder.setMessage("Are you sure you want to discard these results? This is not reversible!");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                stopBEST();
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
}
