package com.udacity.notepad.data;

import android.provider.BaseColumns;

public final class NoteContract {

    private NoteContract() {
    }

    public static final String SQL_CREATE_NOTES_TABLE =
            "CREATE TABLE " + NoteEntry.TABLE_NAME + " (" +
                    NoteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    NoteEntry.TEXT + " TEXT, " +
                    NoteEntry.IS_PINNED + " INTEGER, " +
                    NoteEntry.CREATED_AT + " INTEGER, " +
                    NoteEntry.UPDATED_AT + " INTEGER" +
                    ")";

    public static final String SQL_DELETE_NOTES_TABLE =
            "DROP TABLE IF EXISTS " + NoteEntry.TABLE_NAME;

    public static final String SQL_QUERY_ALL =
            "SELECT * FROM NOTE ORDER BY " + NoteEntry.CREATED_AT + " DESC";

    public interface NoteEntry extends BaseColumns {

        String TABLE_NAME = "notes";
        String TEXT = "text";
        String IS_PINNED = "is_pinned";
        String CREATED_AT = "created_at";
        String UPDATED_AT = "updated_at";
    }
}
