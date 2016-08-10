package com.augmentis.ayp.contact_project.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.augmentis.ayp.contact_project.Database.ContactBaseHelper;
import com.augmentis.ayp.contact_project.Database.ContactCursorWrapper;
import com.augmentis.ayp.contact_project.Database.ContactDbSchema.ContactTable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Theerawuth on 8/9/2016.
 */
public class ContactLab {

    private static ContactLab instance;
    private Context context;
    private SQLiteDatabase database;

    public static ContactLab getInstance(Context context){
        if (instance == null){
            instance = new ContactLab(context);
        }
        return instance;
    }

    public ContactLab(Context context) {
        this.context = context.getApplicationContext();
        ContactBaseHelper contactBaseHelper = new ContactBaseHelper(context);
        database = contactBaseHelper.getWritableDatabase();

    }

    private static ContentValues getContentValues(Contact contact){

        ContentValues contentValues = new ContentValues();
        contentValues.put(ContactTable.Cols.UUID, contact.getId().toString());
        contentValues.put(ContactTable.Cols.NAME, contact.getName());
        contentValues.put(ContactTable.Cols.TEL, contact.getPhoneNumber());
        contentValues.put(ContactTable.Cols.EMAIL, contact.getEmail());

        return contentValues;
    }

    public void addContact(Contact contact) {
        ContentValues contentValues = getContentValues(contact);
        database.insert(ContactTable.NAMETABLE, null, contentValues);
    }

    public void updateContact(Contact contact){
        String uuidStr = contact.getId().toString();
        ContentValues contentValues = getContentValues(contact);

        database.update(ContactTable.NAMETABLE, contentValues,
                ContactTable.Cols.UUID + " = ? ", new String[] {uuidStr} );

    }

        public void deleteContact (UUID uuid){
        database.delete(ContactTable.NAMETABLE,
                ContactTable.Cols.UUID + " = ? ",
                new String[] { uuid.toString() });
    }

    // Cursor คือ ตัวชี้ข้อมูลเพื่อจัดการกับข้อมูล
    public ContactCursorWrapper queryContacts (String whereCause, String[] whereArgs){
        Cursor cursor =  database.query(ContactTable.NAMETABLE,
                null,
                whereCause,
                whereArgs,
                null,
                null,
                null);

        return new ContactCursorWrapper(cursor);
    }

    public List<Contact> getContact() {
        List<Contact> contactList = new ArrayList<>();

        ContactCursorWrapper cursor = queryContacts(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                contactList.add(cursor.getContact());
                cursor.moveToNext();

            }
        }
        finally
        {
            cursor.close();
        }
        return contactList;
    }

    public Contact getContactById(UUID uuid){
        ContactCursorWrapper cursor = queryContacts(ContactTable.Cols.UUID + " = ? ",
                new String[] { uuid.toString() } );
        try {
            if(cursor.getCount() == 0){
                return null;
            }

            cursor.moveToFirst();
            return cursor.getContact();
        }
        finally
        {
            cursor.close();
        }

    }

    public File getPhotoFile(Contact contact){
        File externalFilesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        if(externalFilesDir == null){
            return null;
        }

        return new File (externalFilesDir, contact.getPhotoFilename());
    }

}



