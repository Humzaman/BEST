package com.best.loadProfile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.best.R;

public class ProfileInfoViewResult extends AppCompatActivity {

    TextView textView;

    private String rveTarget;
    private String pre1Target;
    private String pre2Target;
    private String pve1Target;
    private String pve2Target;
    private String ppe1Target;
    private String ppe2Target;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_best);

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
        this.pre1Target = "";
        this.pre2Target = "";
        this.pve1Target = "";
        this.pve2Target = "";
        this.ppe1Target = "";
        this.ppe2Target = "";
        this.rbeTarget = "";

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
            this.pre1Target = (String) bundle.get("pre1Target");
            this.pre2Target = (String) bundle.get("pre2Target");
            this.pve1Target = (String) bundle.get("pve1Target");
            this.pve2Target = (String) bundle.get("pve2Target");
            this.ppe1Target = (String) bundle.get("ppe1Target");
            this.ppe2Target = (String) bundle.get("ppe2Target");
            this.rbeTarget = (String) bundle.get("rbeTarget");
        }

        showResults();
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
        else
            rveResult = "<b>RVE Result: </b>" + this.rveResult + " seconds";


        String preTitle = "<b><big>Prospective Reproduction Estimate</big></b>";
        String preTarget1;
        if (this.pre1Target.equals("1"))
            preTarget1 = "<b>PRE 1 Target Time: </b>" + this.pre1Target + " second";
        else
            preTarget1 = "<b>PRE 1 Target Time: </b>" + this.pre1Target + " seconds";

        String pre1Result;
        if (this.pre1Result.equals("1"))
            pre1Result = "<b>PRE 1 Result: </b>" + this.pre1Result + " second";
        else
            pre1Result = "<b>PRE 1 Result: </b>" + this.pre1Result + " seconds";

        String preTarget2;
        if (this.pre2Target.equals("1"))
            preTarget2 = "<b>PRE 2 Target Time: </b>" + this.pre2Target + " second";
        else
            preTarget2 = "<b>PRE 2 Target Time: </b>" + this.pre2Target + " seconds";

        String pre2Result;
        if (this.pre2Result.equals("1"))
            pre2Result = "<b>PRE 2 Result: </b>" + this.pre2Result + " second";
        else
            pre2Result = "<b>PRE 2 Result: </b>" + this.pre2Result + " seconds";

        String pveTitle = "<b><big>Prospective Verbal Estimate</big></b>";
        String pveTarget1;
        if (this.pve1Target.equals("1"))
            pveTarget1 = "<b>PVE 1 Target Time: </b>" + this.pve1Target + " second";
        else
            pveTarget1 = "<b>PVE 1 Target Time: </b>" + this.pve1Target + " seconds";

        String pve1Result;
        if (this.pve1Result.equals("1"))
            pve1Result = "<b>PVE 1 Result: </b>" + this.pve1Result + " second";
        else
            pve1Result = "<b>PVE 1 Result: </b>" + this.pve1Result + " seconds";

        String pveTarget2;
        if (this.pve2Target.equals("1"))
            pveTarget2 = "<b>PVE 2 Target Time: </b>" + this.pve2Target + " second";
        else
            pveTarget2 = "<b>PVE 2 Target Time: </b>" + this.pve2Target + " seconds";

        String pve2Result;
        if (this.pve2Result.equals("1"))
            pve2Result = "<b>PVE 2 Result: </b>" + this.pve2Result + " second";
        else
            pve2Result = "<b>PVE 2 Result: </b>" + this.pve2Result + " seconds";

        String ppeTitle = "<b><big>Prospective Production Estimate</big></b>";
        String ppeTarget1;
        if (this.ppe1Target.equals("1"))
            ppeTarget1 = "<b>PPE 1 Target Time: </b>" + this.ppe1Target + " second";
        else
            ppeTarget1 = "<b>PPE 1 Target Time: </b>" + this.ppe1Target + " seconds";

        String ppe1Result;
        if (this.ppe1Result.equals("1"))
            ppe1Result = "<b>PPE 1 Result: </b>" + this.ppe1Result + " second";
        else
            ppe1Result = "<b>PPE 1 Result: </b>" + this.ppe1Result + " seconds";

        String ppeTarget2;
        if (this.ppe2Target.equals("1"))
            ppeTarget2 = "<b>PPE 2 Target Time: </b>" + this.ppe2Target + " second";
        else
            ppeTarget2 = "<b>PPE 2 Target Time: </b>" + this.ppe2Target + " seconds";

        String ppe2Result;
        if (this.ppe2Result.equals("1"))
            ppe2Result = "<b>PPE 2 Result: </b>" + this.ppe2Result + " second";
        else
            ppe2Result = "<b>PPE 2 Result: </b>" + this.ppe2Result + " seconds";

        String rbeTitle = "<b><big>Rhythmic Beats Estimate</big></b>";
        String rbeTarget;
        if (this.rbeTarget.equals("1"))
            rbeTarget = "<b>RBE Beat Interval: </b>" + this.rbeTarget + " millisecond";
        else
            rbeTarget = "<b>RBE Beat Interval: </b>" + this.rbeTarget + " milliseconds";
        String rbeResult = "<b>RBE Result: </b>";

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
        this.textView.append("\n");
        this.textView.append(this.rbeResult);
        this.textView.append("\n\n");
    }
}
