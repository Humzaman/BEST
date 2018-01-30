package com.best.database;

public class Profile {

    private String idNumber;
    private String lastName;
    private String firstName;
    private String gender;
    private String handedness;
    private String educationLevel;
    private String dob;
    private String notes;
    private String creationDate;
    private String lastExamination;

    // empty constructor
    public Profile() {}

    public Profile(String idNumber, String lastName, String firstName,
                   String gender, String handedness, String educationLevel,
                   String dob, String notes, String creationDate, String lastExamination) {
        this.idNumber = idNumber;
        this.lastName = lastName;
        this.firstName = firstName;
        this.gender = gender;
        this.handedness = handedness;
        this.educationLevel = educationLevel;
        this.dob = dob;
        this.notes = notes;
        this.creationDate = creationDate;
        this.lastExamination = lastExamination;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getName() {
        return this.lastName + ", " + this.firstName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHandedness() {
        return handedness;
    }

    public void setHandedness(String handedness) {
        this.handedness = handedness;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getLastExamination() {
        return lastExamination;
    }

    public void setLastExamination(String lastExamination) {
        this.lastExamination = lastExamination;
    }
}
