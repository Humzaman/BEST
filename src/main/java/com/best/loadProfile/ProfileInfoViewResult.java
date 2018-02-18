package com.best.loadProfile;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;

import com.best.R;

public class ProfileInfoViewResult extends AppCompatActivity {

    TextView textView;

    private String rveTarget;
    private String preTarget1;
    private String preTarget2;
    private String pveTarget1;
    private String pveTarget2;
    private String ppeTarget1;
    private String ppeTarget2;
    private String rbeTarget;
    private String date;
    private String id;
    private String rveResult;
    private String pre1Result;
    private String pre2Result;
    private String pve1Result;
    private String pve2Result;
    private String ppe1Result;
    private String ppe2Result;
    private String rbeResult;
    private String rbeMean;
    private String rbeSD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_best);

        setupActionBar();

        this.textView = findViewById(R.id.EndResultsTextView);
        this.textView.setMovementMethod(new ScrollingMovementMethod());

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
        this.rveTarget = "";
        this.preTarget1 = "";
        this.preTarget2 = "";
        this.pveTarget1 = "";
        this.pveTarget2 = "";
        this.ppeTarget1 = "";
        this.ppeTarget2 = "";
        this.rbeTarget = "";
        this.rbeMean = "";
        this.rbeSD = "";

        if (bundle != null) {
            this.date = (String) bundle.get("bestDate");
            this.id = (String) bundle.get("id");
            this.rveResult = (String) bundle.get("rveResult");
            this.pre1Result = (String) bundle.get("pre1Result");
            this.pre2Result = (String) bundle.get("pre2Result");
            this.pve1Result = (String) bundle.get("pve1Result");
            this.pve2Result = (String) bundle.get("pve2Result");
            this.ppe1Result = (String) bundle.get("ppe1Result");
            this.ppe2Result = (String) bundle.get("ppe2Result");
            this.rbeResult = (String) bundle.get("rbeResult");
            this.rveTarget = (String) bundle.get("rveTarget");
            this.preTarget1 = (String) bundle.get("pre1Target");
            this.preTarget2 = (String) bundle.get("pre2Target");
            this.pveTarget1 = (String) bundle.get("pve1Target");
            this.pveTarget2 = (String) bundle.get("pve2Target");
            this.ppeTarget1 = (String) bundle.get("ppe1Target");
            this.ppeTarget2 = (String) bundle.get("ppe2Target");
            this.rbeTarget = (String) bundle.get("rbeTarget");
            this.rbeMean = (String) bundle.get("rbeMean");
            this.rbeSD = (String) bundle.get("rbeSD");
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
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
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
        String rbeResult = "<b>RBE Result: </b>";
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
        this.textView.append("\n");
        this.textView.append(Html.fromHtml(rbeResult));

        if (this.rbeResult.equals("N/A"))
            this.textView.append(this.rbeResult);
        else
            this.textView.append("\n" + this.rbeResult);

        this.textView.append("\n");
        this.textView.append(Html.fromHtml(rbeMean));
        this.textView.append("\n");
        this.textView.append(Html.fromHtml(rbeSD));
        this.textView.append("\n\n");
    }
}
