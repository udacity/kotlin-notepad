package com.udacity.notepad.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.udacity.notepad.data.NoteContract.NoteEntry;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.provider.BaseColumns._ID;

// Is this a good name for this class? Any better idea?
public class NoteDao {

    private final NoteOpenHelper helper;

    // For consistency all should be singular (Note instead of Notes)
    public NoteDao(Context context) {
        helper = new NoteOpenHelper(context);
    }

    public List<Note> getAll() {
        // With the imports this way this becomes much more readable
        Cursor cursor = helper.getReadableDatabase().query(NoteEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                NoteEntry.CREATED_AT);
        List<Note> notes = new ArrayList<>();
        while (cursor.moveToNext()) {
            notes.add(fromCursor(cursor));
        }
        cursor.close();
        return notes;
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

        // Here it is adding "?" to the selection that would be then replace by the args
        // In this case it does not seem a good approach because:
        // - You do 2 loops and the query method would need to put everything in place
        // - SQL cannot handle an infinite number of "?" without an AND
        //   so the query will crash if it is big enough
        // I would suggest to add the ids directly into the selection and do not use args
        // in this case. This will solve the problem above looping once only
        // Still, this method is unused so probably not necessary

        String selection = _ID + " IN (" + questionMarks.toString() + ")";
        Cursor cursor = helper.getReadableDatabase().query(NoteEntry.TABLE_NAME,
                null,
                selection,
                args,
                null,
                null,
                NoteEntry.CREATED_AT);
        List<Note> notes = allFromCursor(cursor);
        cursor.close();
        return notes;
    }

    public void insert(Note... notes) {
        List<ContentValues> values = fromNotes(notes);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.beginTransaction();
        try {
            for (ContentValues value : values) {
                db.insert(NoteEntry.TABLE_NAME, null, value);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public void update(Note note) {
        ContentValues values = fromNote(note);
        helper.getWritableDatabase().update(NoteEntry.TABLE_NAME,
                values,
                _ID + " = ?",
                // This is super ugly code. it should be extracted to a local variable
                // or even a method as it could be reused
                new String[]{ Integer.toString(note.getId()) });
    }

    public void delete(Note note) {
        helper.getWritableDatabase().delete(NoteEntry.TABLE_NAME,
                _ID + " = ?",
                new String[]{ Integer.toString(note.getId()) });
    }

    // The names of the methods below do not seem good to me
    private static Note fromCursor(Cursor cursor) {
        int col = 0;
        Note note = new Note();
        // This is terrible!
        // using col++ is a source of future problems
        // We have a contract for a reason
        // example:
        // note.setId(cursor.getInt(cursor.getColumnIndex(NoteEntry._ID)));
        note.setId(cursor.getInt(col++));
        note.setText(cursor.getString(col++));
        note.setPinned(cursor.getInt(col++) != 0);
        note.setCreatedAt(new Date(cursor.getLong(col++)));
        note.setUpdatedAt(new Date(cursor.getLong(col)));
        return note;
    }

    private static List<Note> allFromCursor(Cursor cursor) {
        List<Note> notes = new ArrayList<>();
        while (cursor.moveToNext()) {
            notes.add(fromCursor(cursor));
        }
        return notes;
    }

    private static ContentValues fromNote(Note note) {
        ContentValues values = new ContentValues();
        int id = note.getId();
        if (id != -1) {
            values.put(_ID, id);
        }
        values.put(NoteEntry.TEXT, note.getText());
        values.put(NoteEntry.IS_PINNED, note.isPinned());
        values.put(NoteEntry.CREATED_AT, note.getCreatedAt().getTime());
        values.put(NoteEntry.UPDATED_AT, note.getUpdatedAt().getTime());
        return values;
    }

    private static List<ContentValues> fromNotes(Note[] notes) {
        List<ContentValues> values = new ArrayList<>();
        for (Note note : notes) {
            values.add(fromNote(note));
        }
        return values;
    }
}
