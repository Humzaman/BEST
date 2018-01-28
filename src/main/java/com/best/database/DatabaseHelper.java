package com.best.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
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
    private static final String PRE1_RESULT = "pre1Result";
    private static final String PRE2_RESULT = "pre2Result";
    private static final String PVE1_RESULT = "pve1Result";
    private static final String PVE2_RESULT = "pve2Result";
    private static final String PPE1_RESULT = "ppe1Result";
    private static final String PPE2_RESULT = "ppe2Result";
    private static final String RBE_RESULT = "rbeResult";
    private static final String RESULTS_DATE = "resultsDate";
    private static final String RESULTS_ID = "resultsId";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PROFILES_TABLE = "CREATE TABLE " + TABLE_PROFILES + "("
                + ID_NUMBER + " TEXT PRIMARY KEY," + LAST_NAME + " TEXT,"
                + FIRST_NAME + " TEXT," + GENDER + " TEXT,"
                + HANDEDNESS + " TEXT," + EDUCATION_LEVEL + " TEXT,"
                + DOB + " TEXT," + NOTES + " TEXT," + CREATION_DATE + " TEXT" + ")";

        String CREATE_RESULTS_TABLE = "CREATE TABLE " + TABLE_RESULTS + "("
                + RVE_TARGET + " TEXT," + RVE_RESULT + " TEXT,"
                + PRE1_TARGET + " TEXT," + PRE1_RESULT + " TEXT,"
                + PRE2_TARGET + " TEXT," + PRE2_RESULT + " TEXT,"
                + PVE1_TARGET + " TEXT," + PVE1_RESULT + " TEXT,"
                + PVE2_TARGET + " TEXT," + PVE2_RESULT + " TEXT,"
                + PPE1_TARGET + " TEXT," + PPE1_RESULT + " TEXT,"
                + PPE2_TARGET + " TEXT," + PPE2_RESULT + " TEXT,"
                + RBE_TARGET + " TEXT," + RBE_RESULT + " TEXT,"
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

        // Inserting Row
        db.insert(TABLE_PROFILES, null, values);
        db.close(); // Closing database connection
    }

    public void addResults(Results results) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(RVE_TARGET, results.getRveTarget());
        values.put(RVE_RESULT, results.getRveResult());
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
                        EDUCATION_LEVEL, DOB, NOTES, CREATION_DATE}, ID_NUMBER + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Profile profile = new Profile(cursor.getString(0), cursor.getString(1),
                cursor.getString(2), cursor.getString(3), cursor.getString(4),
                cursor.getString(5), cursor.getString(6), cursor.getString(7),
                cursor.getString(8));

        cursor.close();

        // return profile
        return profile;
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
                results.setPre1Target(cursor.getString(2));
                results.setPre1Result(cursor.getString(3));
                results.setPre2Target(cursor.getString(4));
                results.setPre2Result(cursor.getString(5));
                results.setPve1Target(cursor.getString(6));
                results.setPve1Result(cursor.getString(7));
                results.setPve2Target(cursor.getString(8));
                results.setPve2Result(cursor.getString(9));
                results.setPpe1Target(cursor.getString(10));
                results.setPpe1Result(cursor.getString(11));
                results.setPpe2Target(cursor.getString(12));
                results.setPpe2Result(cursor.getString(13));
                results.setRbeTarget(cursor.getString(14));
                results.setRbeResult(cursor.getString(15));
                results.setTestDate(cursor.getString(16));
                results.setResultsId(cursor.getString(17));
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
        String selectQuery = "SELECT * FROM " + TABLE_PROFILES;
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
                // Adding profile to list
                profileList.add(profile);
            } while (cursor.moveToNext());
        }

        cursor.close();

        // return profile list
        return profileList;
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
    public int updateProfile(Profile profile) {
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

        // updating row
        return db.update(TABLE_PROFILES, values, ID_NUMBER + " = ?",
                new String[] { String.valueOf(profile.getIdNumber()) });
    }

    // Deleting single profile
    public void deleteProfile(Profile profile) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_PROFILES, ID_NUMBER + " = ?",
                new String[] { String.valueOf(profile.getIdNumber()) });
        db.delete(TABLE_RESULTS, RESULTS_ID + " = ?",
                new String[] { String.valueOf(profile.getIdNumber()) });

        db.close();
    }
}
