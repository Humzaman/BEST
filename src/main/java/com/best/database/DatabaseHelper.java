package com.best.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.best.loadProfile.tableview.model.CellModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper DBHinstance = null;
    private static final int DATABASE_VERSION = 5;
    private static final String DATABASE_NAME = "BESTDatabase";
    private static final String TABLE_PROFILES = "profiles";
    private static final String TABLE_RESULTS = "results";

    // PROFILES table column names
    private static final String ID_NUMBER = "idNumber";
    private static final String LAST_NAME = "lastName";
    private static final String FIRST_NAME = "firstName";
    private static final String GENDER = "gender";
    private static final String HANDEDNESS = "handedness";
    private static final String EDUCATION_LEVEL = "educationLevel";
    private static final String DOB = "dob";
    private static final String NOTES = "notes";
    private static final String CREATION_DATE = "creationDate";
    private static final String LAST_EXAM_DATE = "lastExamination";

    // RESULTS table column names
    private static final String RVE_TARGET = "rveTarget";
    private static final String PRE1_TARGET = "pre1Target";
    private static final String PRE2_TARGET = "pre2Target";
    private static final String PVE1_TARGET = "pve1Target";
    private static final String PVE2_TARGET = "pve2Target";
    private static final String PPE1_TARGET = "ppe1Target";
    private static final String PPE2_TARGET = "ppe2Target";
    private static final String RBE_TARGET = "rbeTarget";
    private static final String RVE_RESULT = "rveResult";
    private static final String RVE_DEVIATION = "rveDeviation";
    private static final String PRE1_RESULT = "pre1Result";
    private static final String PRE2_RESULT = "pre2Result";
    private static final String PVE1_RESULT = "pve1Result";
    private static final String PVE2_RESULT = "pve2Result";
    private static final String PPE1_RESULT = "ppe1Result";
    private static final String PPE2_RESULT = "ppe2Result";
    private static final String RBE_RESULT = "rbeResult";
    private static final String RBE_MEAN = "rbeMean";
    private static final String RBE_SD = "rbeSD";
    private static final String RBE_MAX = "rbeMax";
    private static final String RBE_MIN = "rbeMin";
    private static final String RESULTS_DATE = "resultsDate";
    private static final String RESULTS_ID = "resultsId";

    public static DatabaseHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        //don't accidentally leak an Activity's context.
        if (DBHinstance == null) {
            DBHinstance = new DatabaseHelper(context.getApplicationContext());
        }
        return DBHinstance;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PROFILES_TABLE = "CREATE TABLE " + TABLE_PROFILES + "("
                + ID_NUMBER + " TEXT PRIMARY KEY," + LAST_NAME + " TEXT,"
                + FIRST_NAME + " TEXT," + GENDER + " TEXT,"
                + HANDEDNESS + " TEXT," + EDUCATION_LEVEL + " TEXT,"
                + DOB + " TEXT," + NOTES + " TEXT," + CREATION_DATE + " TEXT,"
                + LAST_EXAM_DATE + " TEXT" + ")";

        String CREATE_RESULTS_TABLE = "CREATE TABLE " + TABLE_RESULTS + "("
                + RVE_TARGET + " TEXT," + RVE_RESULT + " TEXT," + RVE_DEVIATION + " TEXT,"
                + PRE1_TARGET + " TEXT," + PRE1_RESULT + " TEXT,"
                + PRE2_TARGET + " TEXT," + PRE2_RESULT + " TEXT,"
                + PVE1_TARGET + " TEXT," + PVE1_RESULT + " TEXT,"
                + PVE2_TARGET + " TEXT," + PVE2_RESULT + " TEXT,"
                + PPE1_TARGET + " TEXT," + PPE1_RESULT + " TEXT,"
                + PPE2_TARGET + " TEXT," + PPE2_RESULT + " TEXT,"
                + RBE_TARGET + " TEXT," + RBE_RESULT + " TEXT,"
                + RBE_MEAN + " TEXT," + RBE_SD + " TEXT,"
                + RBE_MAX + " TEXT," + RBE_MIN + " TEXT,"
                + RESULTS_DATE + " TEXT," + RESULTS_ID + " TEXT,"
                + "FOREIGN KEY (" + RESULTS_ID + ") REFERENCES " + TABLE_PROFILES + "(idNumber) " + ")";

        db.execSQL(CREATE_PROFILES_TABLE);
        db.execSQL(CREATE_RESULTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESULTS);

        onCreate(db);
    }
    
    // CRUD operations
    public void addProfile(Profile profile) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ID_NUMBER, profile.getIdNumber());
        values.put(LAST_NAME, profile.getLastName());
        values.put(FIRST_NAME, profile.getFirstName());
        values.put(GENDER, profile.getGender());
        values.put(HANDEDNESS, profile.getHandedness());
        values.put(EDUCATION_LEVEL, profile.getEducationLevel());
        values.put(DOB, profile.getDob());
        values.put(NOTES, profile.getNotes());
        values.put(CREATION_DATE, profile.getCreationDate());
        values.put(LAST_EXAM_DATE, profile.getLastExamination());

        // Inserting Row
        db.insert(TABLE_PROFILES, null, values);
        db.close(); // Closing database connection
    }

    public void addResults(Results results) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(RVE_TARGET, results.getRveTarget());
        values.put(RVE_RESULT, results.getRveResult());
        values.put(RVE_DEVIATION, results.getRveDeviation());
        values.put(PRE1_TARGET, results.getPre1Target());
        values.put(PRE1_RESULT, results.getPre1Result());
        values.put(PRE2_TARGET, results.getPre2Target());
        values.put(PRE2_RESULT, results.getPre2Result());
        values.put(PVE1_TARGET, results.getPve1Target());
        values.put(PVE1_RESULT, results.getPve1Result());
        values.put(PVE2_TARGET, results.getPve2Target());
        values.put(PVE2_RESULT, results.getPve2Result());
        values.put(PPE1_TARGET, results.getPpe1Target());
        values.put(PPE1_RESULT, results.getPpe1Result());
        values.put(PPE2_TARGET, results.getPpe2Target());
        values.put(PPE2_RESULT, results.getPpe2Result());
        values.put(RBE_TARGET, results.getRbeTarget());
        values.put(RBE_RESULT, results.getRbeResult());
        values.put(RBE_MEAN, results.getRbeMean());
        values.put(RBE_SD, results.getRbeSD());
        values.put(RBE_MAX, results.getRbeMax());
        values.put(RBE_MIN, results.getRbeMin());
        values.put(RESULTS_DATE, results.getTestDate());
        values.put(RESULTS_ID, results.getResultsId());

        // Inserting Row
        db.insert(TABLE_RESULTS, null, values);
        db.close(); // Closing database connection
    }

    // Getting single profile
    public Profile getProfile(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PROFILES, new String[] {ID_NUMBER,
                        LAST_NAME, FIRST_NAME, GENDER, HANDEDNESS,
                        EDUCATION_LEVEL, DOB, NOTES, CREATION_DATE, LAST_EXAM_DATE}, ID_NUMBER + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Profile profile = new Profile(cursor.getString(0), cursor.getString(1),
                cursor.getString(2), cursor.getString(3), cursor.getString(4),
                cursor.getString(5), cursor.getString(6), cursor.getString(7),
                cursor.getString(8), cursor.getString(9));

        cursor.close();

        // return profile
        return profile;
    }

    public boolean profileExists(String id) {
        String selectQuery = "SELECT * FROM " + TABLE_PROFILES + " WHERE " + ID_NUMBER + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        }
        else {
            cursor.close();
            return false;
        }
    }

    public List<Results> getResults(String id) {
        List<Results> resultsList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_RESULTS + " WHERE " + RESULTS_ID + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Results results = new Results();
                results.setRveTarget(cursor.getString(0));
                results.setRveResult(cursor.getString(1));
                results.setRveDeviation(cursor.getString(2));
                results.setPre1Target(cursor.getString(3));
                results.setPre1Result(cursor.getString(4));
                results.setPre2Target(cursor.getString(5));
                results.setPre2Result(cursor.getString(6));
                results.setPve1Target(cursor.getString(7));
                results.setPve1Result(cursor.getString(8));
                results.setPve2Target(cursor.getString(9));
                results.setPve2Result(cursor.getString(10));
                results.setPpe1Target(cursor.getString(11));
                results.setPpe1Result(cursor.getString(12));
                results.setPpe2Target(cursor.getString(13));
                results.setPpe2Result(cursor.getString(14));
                results.setRbeTarget(cursor.getString(15));
                results.setRbeResult(cursor.getString(16));
                results.setRbeMean(cursor.getString(17));
                results.setRbeSD(cursor.getString(18));
                results.setRbeMax(cursor.getString(19));
                results.setRbeMin(cursor.getString(20));
                results.setTestDate(cursor.getString(21));
                results.setResultsId(cursor.getString(22));
                // Adding results to list
                resultsList.add(results);
            } while (cursor.moveToNext());
        }

        cursor.close();

        // return results list
        return resultsList;
    }

    public List<List<CellModel>> getAllResultsForProfile(String id) {
        List<Results> resultsList = getResults(id);

        List<CellModel> rveT = new ArrayList<>();
        List<CellModel> rveR = new ArrayList<>();
        List<CellModel> rveD = new ArrayList<>();
        List<CellModel> pre1T = new ArrayList<>();
        List<CellModel> pre1R = new ArrayList<>();
        List<CellModel> pre2T = new ArrayList<>();
        List<CellModel> pre2R = new ArrayList<>();
        List<CellModel> pve1T = new ArrayList<>();
        List<CellModel> pve1R = new ArrayList<>();
        List<CellModel> pve2T = new ArrayList<>();
        List<CellModel> pve2R = new ArrayList<>();
        List<CellModel> ppe1T = new ArrayList<>();
        List<CellModel> ppe1R = new ArrayList<>();
        List<CellModel> ppe2T = new ArrayList<>();
        List<CellModel> ppe2R = new ArrayList<>();
        List<CellModel> rbeT = new ArrayList<>();
        List<CellModel> rbeMean = new ArrayList<>();
        List<CellModel> rbeSD = new ArrayList<>();
        List<CellModel> rbeMax = new ArrayList<>();
        List<CellModel> rbeMin = new ArrayList<>();

        for (int i = 0; i < resultsList.size(); i++) {
            Results results = resultsList.get(i);

            rveT.add(new CellModel("1-" + i, results.getRveTarget()));
            rveR.add(new CellModel("2-" + i, results.getRveResult()));
            rveD.add(new CellModel("3-" + i, results.getRveDeviation()));
            pre1T.add(new CellModel("4-" + i, results.getPre1Target()));
            pre1R.add(new CellModel("5-" + i, results.getPre1Result()));
            pre2T.add(new CellModel("6-" + i, results.getPre2Target()));
            pre2R.add(new CellModel("7-" + i, results.getPre2Result()));
            pve1T.add(new CellModel("8-" + i, results.getPve1Target()));
            pve1R.add(new CellModel("9-" + i, results.getPve1Result()));
            pve2T.add(new CellModel("10-" + i, results.getPve2Target()));
            pve2R.add(new CellModel("11-" + i, results.getPve2Result()));
            ppe1T.add(new CellModel("12-" + i, results.getPpe1Target()));
            ppe1R.add(new CellModel("13-" + i, results.getPpe1Result()));
            ppe2T.add(new CellModel("14-" + i, results.getPpe2Target()));
            ppe2R.add(new CellModel("15-" + i, results.getPpe2Result()));
            rbeT.add(new CellModel("16-" + i, results.getRbeTarget()));
            rbeMean.add(new CellModel("17-" + i, results.getRbeMean()));
            rbeSD.add(new CellModel("18-" + i, results.getRbeSD()));
            rbeMax.add(new CellModel("19-" + i, results.getRbeMax()));
            rbeMin.add(new CellModel("20-" + i, results.getRbeMin()));
        }

        List<List<CellModel>> listOfResultsList = new ArrayList<>();

        listOfResultsList.add(rveT);
        listOfResultsList.add(rveR);
        listOfResultsList.add(rveD);
        listOfResultsList.add(pre1T);
        listOfResultsList.add(pre1R);
        listOfResultsList.add(pre2T);
        listOfResultsList.add(pre2R);
        listOfResultsList.add(pve1T);
        listOfResultsList.add(pve1R);
        listOfResultsList.add(pve2T);
        listOfResultsList.add(pve2R);
        listOfResultsList.add(ppe1T);
        listOfResultsList.add(ppe1R);
        listOfResultsList.add(ppe2T);
        listOfResultsList.add(ppe2R);
        listOfResultsList.add(rbeT);
        listOfResultsList.add(rbeMean);
        listOfResultsList.add(rbeSD);
        listOfResultsList.add(rbeMax);
        listOfResultsList.add(rbeMin);

        // SUPER inefficient way of breaking up the RBE responses into columns.
        // very disappointed in myself for writing this lol.
        for (int a = 0; a < 60; a++) {
            List<CellModel> rbeR = new ArrayList<>();

            for (int i = 0; i < resultsList.size(); i++) {
                Results results = resultsList.get(i);

                if (results.getRbeResult().equals("N/A")) {
                    rbeR.add(new CellModel((a + 21) + "-" + i, "N/A"));
                }
                else {
                    rbeR.add(new CellModel((a + 21) + "-" + i, results.getRbeResult().split(", ")[a]));
                }
            }

            listOfResultsList.add(rbeR);
        }

        return listOfResultsList;
    }

    public List<Profile> searchDOE(String DOE) {
        List<String> resultsIDs = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_RESULTS + " WHERE " + RESULTS_DATE + " LIKE '%"+DOE+"%'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding IDs to list
        if (cursor.moveToFirst()) {
            do {
                if(!resultsIDs.contains(cursor.getString(20))) {
                    resultsIDs.add(cursor.getString(20));
                }
            } while (cursor.moveToNext());
        }

        cursor.close();

        List<Profile> profileList = new ArrayList<>();

        for (String id : resultsIDs) {
            profileList.add(getProfile(id));
        }

        // return results list
        return profileList;
    }

    public List<Results> getAllResults() {
        List<Results> resultsList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_RESULTS;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Results results = new Results();
                results.setRveTarget(cursor.getString(0));
                results.setRveResult(cursor.getString(1));
                results.setRveDeviation(cursor.getString(2));
                results.setPre1Target(cursor.getString(3));
                results.setPre1Result(cursor.getString(4));
                results.setPre2Target(cursor.getString(5));
                results.setPre2Result(cursor.getString(6));
                results.setPve1Target(cursor.getString(7));
                results.setPve1Result(cursor.getString(8));
                results.setPve2Target(cursor.getString(9));
                results.setPve2Result(cursor.getString(10));
                results.setPpe1Target(cursor.getString(11));
                results.setPpe1Result(cursor.getString(12));
                results.setPpe2Target(cursor.getString(13));
                results.setPpe2Result(cursor.getString(14));
                results.setRbeTarget(cursor.getString(15));
                results.setRbeResult(cursor.getString(16));
                results.setRbeMean(cursor.getString(17));
                results.setRbeSD(cursor.getString(18));
                results.setRbeMax(cursor.getString(19));
                results.setRbeMin(cursor.getString(20));
                results.setTestDate(cursor.getString(21));
                results.setResultsId(cursor.getString(22));
                // Adding results to list
                resultsList.add(results);
            } while (cursor.moveToNext());
        }

        cursor.close();

        // return results list
        return resultsList;
    }


    // Getting All Profiles
    public List<Profile> getAllProfiles() {
        List<Profile> profileList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_PROFILES
                + " ORDER BY " + LAST_NAME + ", " + FIRST_NAME;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Profile profile = new Profile();
                profile.setIdNumber(cursor.getString(0));
                profile.setLastName(cursor.getString(1));
                profile.setFirstName(cursor.getString(2));
                profile.setGender(cursor.getString(3));
                profile.setHandedness(cursor.getString(4));
                profile.setEducationLevel(cursor.getString(5));
                profile.setDob(cursor.getString(6));
                profile.setNotes(cursor.getString(7));
                profile.setCreationDate(cursor.getString(8));
                profile.setLastExamination(cursor.getString(9));
                // Adding profile to list
                profileList.add(profile);
            } while (cursor.moveToNext());
        }

        cursor.close();

        // return profile list
        return profileList;
    }

    public List<List<String>> getAllData() {
        List<List<String>> allData = new ArrayList<>();

        List<String> id = new ArrayList<>();
        List<String> lastName = new ArrayList<>();
        List<String> firstName = new ArrayList<>();
        List<String> dob = new ArrayList<>();
        List<String> gender = new ArrayList<>();
        List<String> handedness = new ArrayList<>();
        List<String> edu = new ArrayList<>();
        List<String> notes = new ArrayList<>();
        List<String> doc = new ArrayList<>();
        List<String> doe = new ArrayList<>();
        List<String> rveT = new ArrayList<>();
        List<String> rveR = new ArrayList<>();
        List<String> rveD = new ArrayList<>();
        List<String> pre1T = new ArrayList<>();
        List<String> pre1R = new ArrayList<>();
        List<String> pre2T = new ArrayList<>();
        List<String> pre2R = new ArrayList<>();
        List<String> pve1T = new ArrayList<>();
        List<String> pve1R = new ArrayList<>();
        List<String> pve2T = new ArrayList<>();
        List<String> pve2R = new ArrayList<>();
        List<String> ppe1T = new ArrayList<>();
        List<String> ppe1R = new ArrayList<>();
        List<String> ppe2T = new ArrayList<>();
        List<String> ppe2R = new ArrayList<>();
        List<String> rbeT = new ArrayList<>();
        List<String> rbeMean = new ArrayList<>();
        List<String> rbeSD = new ArrayList<>();
        List<String> rbeMax = new ArrayList<>();
        List<String> rbeMin = new ArrayList<>();
        List<String> rbeR = new ArrayList<>();

        id.add("ID#");
        lastName.add("Last Name");
        firstName.add("First Name");
        dob.add("Date of Birth");
        gender.add("Gender");
        handedness.add("Handedness");
        edu.add("Education Level");
        notes.add("Notes");
        doc.add("Profile Creation Date");
        doe.add("Exam Date");
        rveT.add("RVE Target");
        rveR.add("RVE Result");
        rveD.add("RVE Deviation");
        pre1T.add("PRE1 Target");
        pre1R.add("PRE1 Result");
        pre2T.add("PRE2 Target");
        pre2R.add("PRE2 Result");
        pve1T.add("PVE1 Target");
        pve1R.add("PVE1 Result");
        pve2T.add("PVE2 Target");
        pve2R.add("PVE2 Result");
        ppe1T.add("PPE1 Target");
        ppe1R.add("PPE1 Result");
        ppe2T.add("PPE2 Target");
        ppe2R.add("PPE2 Result");
        rbeT.add("RBE Target");
        rbeMean.add("RBE Mean");
        rbeSD.add("RBE SD");
        rbeMax.add("RBE Max");
        rbeMin.add("RBE Min");
        rbeR.add("RBE Result");

        List<Profile> profiles = getAllProfiles();

        for (Profile profile : profiles) {
            List<Results> resultsList = getResults(profile.getIdNumber());

            if (resultsList.size() == 0) {
                id.add(profile.getIdNumber());
                lastName.add(profile.getLastName());
                firstName.add(profile.getFirstName());
                dob.add(profile.getDob());
                gender.add(profile.getGender());
                handedness.add(profile.getHandedness());
                edu.add(profile.getEducationLevel());
                notes.add(profile.getNotes());
                doc.add(profile.getCreationDate());
                doe.add("N/A");
                rveT.add("N/A");
                rveR.add("N/A");
                rveD.add("N/A");
                pre1T.add("N/A");
                pre1R.add("N/A");
                pre2T.add("N/A");
                pre2R.add("N/A");
                pve1T.add("N/A");
                pve1R.add("N/A");
                pve2T.add("N/A");
                pve2R.add("N/A");
                ppe1T.add("N/A");
                ppe1R.add("N/A");
                ppe2T.add("N/A");
                ppe2R.add("N/A");
                rbeT.add("N/A");
                rbeMean.add("N/A");
                rbeSD.add("N/A");
                rbeMax.add("N/A");
                rbeMin.add("N/A");
                rbeR.add("N/A");
            }
            else {
                for (Results results : resultsList) {
                    id.add(profile.getIdNumber());
                    lastName.add(profile.getLastName());
                    firstName.add(profile.getFirstName());
                    dob.add(profile.getDob());
                    gender.add(profile.getGender());
                    handedness.add(profile.getHandedness());
                    edu.add(profile.getEducationLevel());
                    notes.add(profile.getNotes());
                    doc.add(profile.getCreationDate());
                    doe.add(results.getTestDate());
                    rveT.add(results.getRveTarget());
                    rveR.add(results.getRveResult());
                    rveD.add(results.getRveDeviation());
                    pre1T.add(results.getPre1Target());
                    pre1R.add(results.getPre1Result());
                    pre2T.add(results.getPre2Target());
                    pre2R.add(results.getPre2Result());
                    pve1T.add(results.getPve1Target());
                    pve1R.add(results.getPve1Result());
                    pve2T.add(results.getPve2Target());
                    pve2R.add(results.getPve2Result());
                    ppe1T.add(results.getPpe1Target());
                    ppe1R.add(results.getPpe1Result());
                    ppe2T.add(results.getPpe2Target());
                    ppe2R.add(results.getPpe2Result());
                    rbeT.add(results.getRbeTarget());
                    rbeMean.add(results.getRbeMean());
                    rbeSD.add(results.getRbeSD());
                    rbeMax.add(results.getRbeMax());
                    rbeMin.add(results.getRbeMin());
                    rbeR.add(results.getRbeResult());
                }
            }
        }

        allData.add(id);
        allData.add(lastName);
        allData.add(firstName);
        allData.add(dob);
        allData.add(gender);
        allData.add(handedness);
        allData.add(edu);
        allData.add(notes);
        allData.add(doc);
        allData.add(doe);
        allData.add(rveT);
        allData.add(rveR);
        allData.add(rveD);
        allData.add(pre1T);
        allData.add(pre1R);
        allData.add(pre2T);
        allData.add(pre2R);
        allData.add(pve1T);
        allData.add(pve1R);
        allData.add(pve2T);
        allData.add(pve2R);
        allData.add(ppe1T);
        allData.add(ppe1R);
        allData.add(ppe2T);
        allData.add(ppe2R);
        allData.add(rbeT);
        allData.add(rbeMean);
        allData.add(rbeSD);
        allData.add(rbeMax);
        allData.add(rbeMin);
        allData.add(rbeR);

        return allData;
    }

    // Getting profiles Count
    public int getProfilesCount() {
        String countQuery = "SELECT * FROM " + TABLE_PROFILES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        return count;
    }

    // Updating single profile
    public void updateProfile(String id, Profile profile) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ID_NUMBER, profile.getIdNumber());
        values.put(LAST_NAME, profile.getLastName());
        values.put(FIRST_NAME, profile.getFirstName());
        values.put(GENDER, profile.getGender());
        values.put(HANDEDNESS, profile.getHandedness());
        values.put(EDUCATION_LEVEL, profile.getEducationLevel());
        values.put(DOB, profile.getDob());
        values.put(NOTES, profile.getNotes());
        values.put(CREATION_DATE, profile.getCreationDate());
        values.put(LAST_EXAM_DATE, profile.getLastExamination());

        ContentValues valuesR = new ContentValues();
        valuesR.put(RESULTS_ID, profile.getIdNumber());

        // updating rows
        db.update(TABLE_RESULTS, valuesR, RESULTS_ID + " = ?",
                new String[] { String.valueOf(id) });
        db.update(TABLE_PROFILES, values, ID_NUMBER + " = ?",
                new String[] { String.valueOf(id) });
    }

    // Deleting single profile
    public void deleteProfile(Profile profile) {
        //List<Results> resultsList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_PROFILES, ID_NUMBER + " = ?",
                new String[] { String.valueOf(profile.getIdNumber()) });
        db.delete(TABLE_RESULTS, RESULTS_ID + " = ?",
                new String[] { String.valueOf(profile.getIdNumber()) });

        db.close();
    }

    public List<Profile> searchDB(String searchTerm, int searchBy) {
        List<Profile> profileList = new ArrayList<>();
        String column;

        if (searchBy == 0) {
            column = ID_NUMBER;
        }
        else {
            column = DOB;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_PROFILES + " WHERE "
                + column + " LIKE '%"+searchTerm+"%'"
                + "ORDER BY " + LAST_NAME;

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Profile profile = new Profile();
                profile.setIdNumber(cursor.getString(0));
                profile.setLastName(cursor.getString(1));
                profile.setFirstName(cursor.getString(2));
                profile.setGender(cursor.getString(3));
                profile.setHandedness(cursor.getString(4));
                profile.setEducationLevel(cursor.getString(5));
                profile.setDob(cursor.getString(6));
                profile.setNotes(cursor.getString(7));
                profile.setCreationDate(cursor.getString(8));
                profile.setLastExamination(cursor.getString(9));
                // Adding profile to list
                profileList.add(profile);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return profileList;
    }

    public List<Profile> searchName(String searchLast, String searchFirst) {
        List<Profile> profileList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_PROFILES + " WHERE "
                + LAST_NAME + " LIKE '%"+searchLast+"%' AND "
                + FIRST_NAME + " LIKE '%"+searchFirst+"%'"
                + "ORDER BY " + LAST_NAME;

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Profile profile = new Profile();
                profile.setIdNumber(cursor.getString(0));
                profile.setLastName(cursor.getString(1));
                profile.setFirstName(cursor.getString(2));
                profile.setGender(cursor.getString(3));
                profile.setHandedness(cursor.getString(4));
                profile.setEducationLevel(cursor.getString(5));
                profile.setDob(cursor.getString(6));
                profile.setNotes(cursor.getString(7));
                profile.setCreationDate(cursor.getString(8));
                profile.setLastExamination(cursor.getString(9));
                // Adding profile to list
                profileList.add(profile);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return profileList;
    }
}
