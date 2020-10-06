package com.udacity.notepad

import android.app.Application
import com.udacity.notepad.data.DataStore.init

class NotesApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        init(this)
    }
}