package com.augmentis.ayp.contact_project;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.augmentis.ayp.contact_project.ContactDbSchema.ContactTable;

/**
 * Created by Theerawuth on 8/9/2016.
 */
public class ContactBaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "Contact";
    private static final String DATABASE_NAME = "contactBase.db";
    private static final int VERSION = 1;


    public ContactBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "Create Database");
        db.execSQL("CREATE table " + ContactTable.NAMETABLE
                + "("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ContactTable.Cols.UUID + ","
                + ContactTable.Cols.NAME + ","
                + ContactTable.Cols.TEL + ","
                + ContactTable.Cols.EMAIL + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
