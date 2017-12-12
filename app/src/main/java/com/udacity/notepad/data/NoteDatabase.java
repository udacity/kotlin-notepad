package com.udacity.notepad.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.provider.BaseColumns._ID;

public class NoteDatabase {

    private final NotesOpenHelper helper;

    public NoteDatabase(Context context) {
        helper = new NotesOpenHelper(context);
    }

    private static Note fromCursor(Cursor cursor) {
        int col = 0;
        Note note = new Note();
        note.setId(cursor.getInt(col++));
        note.setText(cursor.getString(col++));
        note.setPinned(cursor.getInt(col++) != 0);
        note.setCreatedAt(new Date(cursor.getLong(col++)));
        note.setUpdatedAt(new Date(cursor.getLong(col)));
        return note;
    }

    private static List<Note> allFromCursor(Cursor cursor) {
        List<Note> retval = new ArrayList<>();
        while (cursor.moveToNext()) {
            retval.add(fromCursor(cursor));
        }
        return retval;
    }

    private static ContentValues fromNote(Note note) {
        ContentValues values = new ContentValues();
        int id = note.getId();
        if (id != -1) {
            values.put(_ID, id);
        }
        values.put(Companion.getTEXT(), note.getText());
        values.put(Companion.getIS_PINNED(), note.isPinned());
        values.put(Companion.getCREATED_AT(), note.getCreatedAt().getTime());
        values.put(Companion.getUPDATED_AT(), note.getUpdatedAt().getTime());
        return values;
    }

    private static List<ContentValues> fromNotes(Note[] notes) {
        List<ContentValues> values = new ArrayList<>();
        for (Note note : notes) {
            values.add(fromNote(note));
        }
        return values;
    }

    public List<Note> getAll() {
        Cursor cursor = helper.getReadableDatabase().query(Companion.get_TABLE_NAME(),
                null,
                null,
                null,
                null,
                null,
                Companion.getCREATED_AT());
        List<Note> retval = allFromCursor(cursor);
        cursor.close();
        return retval;
    }

    public List<Note> loadAllByIds(int... ids) {
        StringBuilder questionMarks = new StringBuilder();
        int i = 0;
        while (i++ < ids.length) {
            questionMarks.append("?");
            if (i <= ids.length - 1) {
                questionMarks.append(", ");
            }
        }
        String[] args = new String[ids.length];
        for (i = 0; i < ids.length; ++i) {
            args[i] = Integer.toString(ids[i]);
        }
        String selection = _ID + " IN (" + questionMarks + ")";
        Cursor cursor = helper.getReadableDatabase().query(Companion.get_TABLE_NAME(),
                null,
                selection,
                args,
                null,
                null,
                Companion.getCREATED_AT());
        List<Note> retval = allFromCursor(cursor);
        cursor.close();
        return retval;
    }

    public void insert(Note... notes) {
        List<ContentValues> values = fromNotes(notes);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.beginTransaction();
        try {
            for (ContentValues value : values) {
                db.insert(Companion.get_TABLE_NAME(), null, value);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public void update(Note note) {
        ContentValues values = fromNote(note);
        helper.getWritableDatabase().update(Companion.get_TABLE_NAME(),
                values,
                _ID + " = ?",
                new String[]{ Integer.toString(note.getId()) });
    }

    public void delete(Note note) {
        helper.getWritableDatabase().delete(Companion.get_TABLE_NAME(),
                _ID + " = ?",
                new String[]{ Integer.toString(note.getId()) });
    }
}
