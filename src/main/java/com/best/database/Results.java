package com.best.database;

public class Results {

    private String rveTarget;
    private String pre1Target;
    private String pre2Target;
    private String pve1Target;
    private String pve2Target;
    private String ppe1Target;
    private String ppe2Target;
    private String rbeTarget;

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
    private String rbeMax;
    private String rbeMin;
    private String testDate;
    private String resultsId;

    public Results() {}

    public Results(String rveTarget,
                   String pre1Target, String pre2Target,
                   String pve1Target, String pve2Target,
                   String ppe1Target, String ppe2Target,
                   String rbeTarget,
                   String rveResult, String rveDeviation,
                   String pre1Result, String pre2Result,
                   String pve1Result, String pve2Result,
                   String ppe1Result, String ppe2Result,
                   String rbeResult, String rbeMean, String rbeSD,
                   String rbeMax, String rbeMin,
                   String testDate, String resultsId) {
        this.rveTarget = rveTarget;
        this.pre1Target = pre1Target;
        this.pre2Target = pre2Target;
        this.pve1Target = pve1Target;
        this.pve2Target = pve2Target;
        this.ppe1Target = ppe1Target;
        this.ppe2Target = ppe2Target;
        this.rbeTarget = rbeTarget;
        this.rveResult = rveResult;
        this.rveDeviation = rveDeviation;
        this.pre1Result = pre1Result;
        this.pre2Result = pre2Result;
        this.pve1Result = pve1Result;
        this.pve2Result = pve2Result;
        this.ppe1Result = ppe1Result;
        this.ppe2Result = ppe2Result;
        this.rbeResult = rbeResult;
        this.rbeMean = rbeMean;
        this.rbeSD = rbeSD;
        this.rbeMax = rbeMax;
        this.rbeMin = rbeMin;
        this.testDate = testDate;
        this.resultsId = resultsId;
    }

    public String getRveTarget() {
        return rveTarget;
    }

    public void setRveTarget(String rveTarget) {
        this.rveTarget = rveTarget;
    }

    public String getPre1Target() {
        return pre1Target;
    }

    public void setPre1Target(String pre1Target) {
        this.pre1Target = pre1Target;
    }

    public String getPre2Target() {
        return pre2Target;
    }

    public void setPre2Target(String pre2Target) {
        this.pre2Target = pre2Target;
    }

    public String getPve1Target() {
        return pve1Target;
    }

    public void setPve1Target(String pve1Target) {
        this.pve1Target = pve1Target;
    }

    public String getPve2Target() {
        return pve2Target;
    }

    public void setPve2Target(String pve2Target) {
        this.pve2Target = pve2Target;
    }

    public String getPpe1Target() {
        return ppe1Target;
    }

    public void setPpe1Target(String ppe1Target) {
        this.ppe1Target = ppe1Target;
    }

    public String getPpe2Target() {
        return ppe2Target;
    }

    public void setPpe2Target(String ppe2Target) {
        this.ppe2Target = ppe2Target;
    }

    public String getRbeTarget() {
        return rbeTarget;
    }

    public void setRbeTarget(String rbeTarget) {
        this.rbeTarget = rbeTarget;
    }

    public String getRveResult() {
        return rveResult;
    }

    public void setRveResult(String rveResult) {
        this.rveResult = rveResult;
    }

    public String getRveDeviation() {
        return rveDeviation;
    }

    public void setRveDeviation(String rveDeviation) {
        this.rveDeviation = rveDeviation;
    }

    public String getPre1Result() {
        return pre1Result;
    }

    public void setPre1Result(String pre1Result) {
        this.pre1Result = pre1Result;
    }

    public String getPre2Result() {
        return pre2Result;
    }

    public void setPre2Result(String pre2Result) {
        this.pre2Result = pre2Result;
    }

    public String getPve1Result() {
        return pve1Result;
    }

    public void setPve1Result(String pve1Result) {
        this.pve1Result = pve1Result;
    }

    public String getPve2Result() {
        return pve2Result;
    }

    public void setPve2Result(String pve2Result) {
        this.pve2Result = pve2Result;
    }

    public String getPpe1Result() {
        return ppe1Result;
    }

    public void setPpe1Result(String ppe1Result) {
        this.ppe1Result = ppe1Result;
    }

    public String getPpe2Result() {
        return ppe2Result;
    }

    public void setPpe2Result(String ppe2Result) {
        this.ppe2Result = ppe2Result;
    }

    public String getRbeResult() {
        return rbeResult;
    }

    public void setRbeResult(String rbeResult) {
        this.rbeResult = rbeResult;
    }

    public String getTestDate() {
        return testDate;
    }

    public void setTestDate(String testDate) {
        this.testDate = testDate;
    }

    public String getResultsId() {
        return resultsId;
    }

    public void setResultsId(String resultsId) {
        this.resultsId = resultsId;
    }

    public String getRbeMean() {
        return rbeMean;
    }

    public void setRbeMean(String rbeMean) {
        this.rbeMean = rbeMean;
    }

    public String getRbeSD() {
        return rbeSD;
    }

    public void setRbeSD(String rbeSD) {
        this.rbeSD = rbeSD;
    }

    public String getRbeMax() {
        return rbeMax;
    }

    public void setRbeMax(String rbeMax) {
        this.rbeMax = rbeMax;
    }

    public String getRbeMin() {
        return rbeMin;
    }

    public void setRbeMin(String rbeMin) {
        this.rbeMin = rbeMin;
    }
}
