package com.udacity.notepad.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.provider.BaseColumns._ID
import com.udacity.notepad.data.NotesContract.NoteTable.CREATED_AT
import com.udacity.notepad.data.NotesContract.NoteTable.IS_PINNED
import com.udacity.notepad.data.NotesContract.NoteTable.TEXT
import com.udacity.notepad.data.NotesContract.NoteTable.UPDATED_AT
import com.udacity.notepad.data.NotesContract.NoteTable._TABLE_NAME
import java.util.*

class NoteDatabase(context: Context) {

    private val helper: NotesOpenHelper = NotesOpenHelper(context)

    val all: List<Note>
        get() {
            val cursor = helper.readableDatabase.query(
                    _TABLE_NAME,
                    null,
                    null,
                    null,
                    null,
                    null,
                    CREATED_AT)
            val retval = allFromCursor(cursor)
            cursor.close()
            return retval
        }

    private fun fromCursor(cursor: Cursor): Note {
        var col = 0
        val note = Note()
        note.id = cursor.getInt(col++)
        note.text = cursor.getString(col++)
        note.isPinned = cursor.getInt(col++) != 0
        note.createdAt = Date(cursor.getLong(col++))
        note.updatedAt = Date(cursor.getLong(col))
        return note
    }

    private fun allFromCursor(cursor: Cursor): List<Note> {
        val retval = ArrayList<Note>()
        while (cursor.moveToNext()) {
            retval.add(fromCursor(cursor))
        }
        return retval
    }

    private fun fromNote(note: Note): ContentValues {
        val values = ContentValues()
        val id = note.id
        if (id != -1) {
            values.put(_ID, id)
        }
        values.put(TEXT, note.text)
        values.put(IS_PINNED, note.isPinned)
        values.put(CREATED_AT, note.createdAt.time)
        values.put(UPDATED_AT, note.updatedAt!!.time)
        return values
    }

    private fun fromNotes(notes: Array<out Note>): List<ContentValues> {
        val values = ArrayList<ContentValues>()
        for (note in notes) {
            values.add(fromNote(note))
        }
        return values
    }

    fun loadAllByIds(vararg ids: Int): List<Note> {
        val questionMarks = StringBuilder()
        var i = 0
        while (i++ < ids.size) {
            questionMarks.append("?")
            if (i <= ids.size - 1) {
                questionMarks.append(", ")
            }
        }
        val args = arrayOfNulls<String>(ids.size)
        i = 0
        while (i < ids.size) {
            args[i] = Integer.toString(ids[i])
            ++i
        }
        val selection = "$_ID IN ($questionMarks)"
        val cursor = helper.readableDatabase.query(_TABLE_NAME, null,
                selection,
                args, null, null,
                CREATED_AT)
        val retval = allFromCursor(cursor)
        cursor.close()
        return retval
    }

    fun insert(vararg notes: Note) {
        val values = fromNotes(notes)
        val db = helper.writableDatabase
        db.beginTransaction()
        try {
            for (value in values) {
                db.insert(_TABLE_NAME, null, value)
            }
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }

    fun update(note: Note) {
        val values = fromNote(note)
        helper.writableDatabase.update(_TABLE_NAME,
                values,
                _ID + " = ?",
                arrayOf(Integer.toString(note.id)))
    }

    fun delete(note: Note) {
        helper.writableDatabase.delete(_TABLE_NAME,
                _ID + " = ?",
                arrayOf(Integer.toString(note.id)))
    }
}
