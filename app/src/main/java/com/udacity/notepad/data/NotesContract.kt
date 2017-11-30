package com.udacity.notepad.data

import com.udacity.notepad.data.NotesContract.NoteTable.CREATED_AT
import com.udacity.notepad.data.NotesContract.NoteTable.IS_PINNED
import com.udacity.notepad.data.NotesContract.NoteTable.TEXT
import com.udacity.notepad.data.NotesContract.NoteTable.UPDATED_AT
import com.udacity.notepad.data.NotesContract.NoteTable._ID
import com.udacity.notepad.data.NotesContract.NoteTable._TABLE_NAME


object NotesContract {

    object NoteTable {
        val _ID = "_id"
        val _TABLE_NAME = "notes"
        val TEXT = "text"
        val IS_PINNED = "is_pinned"
        val CREATED_AT = "created_at"
        val UPDATED_AT = "updated_at"
    }

    val SQL_CREATE_ENTRIES = """CREATE TABLE $_TABLE_NAME (
        $_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
        $TEXT TEXT,
        $IS_PINNED INTEGER,
        $CREATED_AT INTEGER,
        $UPDATED_AT INTEGER
    )""".trimMargin()

    val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $_TABLE_NAME"

    val SQL_QUERY_ALL = "SELECT * FROM $_TABLE_NAME ORDER BY $CREATED_AT DESC"
}
