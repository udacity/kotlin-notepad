package com.udacity.notepad.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NotesOpenHelper extends SQLiteOpenHelper {

    public NotesOpenHelper(Context context) {
        super(context, "notes.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(INSTANCE.getSQL_CREATE_ENTRIES());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(INSTANCE.getSQL_DELETE_ENTRIES());
        onCreate(db);
    }
}
