package com.example.sqlitefilterable;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "mydb";
    private static final int DB_VERSION = 1;

    protected static final String TABLE_NAME = "planets";
    protected static final String COL_ID = "_id";
    protected static final String COL_PLANET_NAME = "planet_name";
    protected static final String COL_PLANET_DIAMETER = "planet_diameter";

    private static final String STRING_CREATE = "CREATE TABLE " + TABLE_NAME
            + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_PLANET_NAME + " TEXT, " + COL_PLANET_DIAMETER + " REAL);";

    private static final String[] PLANET_NAMES = new String[] { "Mercury",
            "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune" };

    private static final double[] PLANET_DIAMETERS = new double[] { 4880,
            12103.6, 12756.3, 6794, 142984, 120536, 51118, 49532 };

    public MyDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(STRING_CREATE);

        for (int i = 0; i < PLANET_NAMES.length; i++) {
            ContentValues cv = new ContentValues();
            cv.put(COL_PLANET_NAME, PLANET_NAMES[i]);
            cv.put(COL_PLANET_DIAMETER, PLANET_DIAMETERS[i]);

            db.insert(TABLE_NAME, null, cv);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
    }
}
