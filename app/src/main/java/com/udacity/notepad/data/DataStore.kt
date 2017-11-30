package com.udacity.notepad.data

import android.content.Context
import org.jetbrains.anko.doAsync

object DataStore {

    @JvmStatic
    lateinit var notes: NoteDatabase

    fun init(context: Context) {
        notes = NoteDatabase(context)
    }

    fun execute(runnable: Runnable) {
        execute { runnable.run() }
    }

    fun execute(fn: () -> Unit) {
        doAsync { fn() }
    }
}
