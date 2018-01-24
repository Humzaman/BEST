package com.best.database;


public class Results {

    private String rveResult;
    private String pre1Result;
    private String pre2Result;
    private String pve1Result;
    private String pve2Result;
    private String ppe1Result;
    private String ppe2Result;
    private String rbeResult;
    private String testDate;
    private String resultsId;

    public Results() {}

    public Results(String rveResult,
                   String pre1Result, String pre2Result,
                   String pve1Result, String pve2Result,
                   String ppe1Result, String ppe2Result,
                   String rbeResult,
                   String testDate, String resultsId) {
        this.rveResult = rveResult;
        this.pre1Result = pre1Result;
        this.pre2Result = pre2Result;
        this.pve1Result = pve1Result;
        this.pve2Result = pve2Result;
        this.ppe1Result = ppe1Result;
        this.ppe2Result = ppe2Result;
        this.rbeResult = rbeResult;
        this.testDate = testDate;
        this.resultsId = resultsId;
    }

    public String getRveResult() {
        return rveResult;
    }

    public void setRveResult(String rveResult) {
        this.rveResult = rveResult;
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
}
