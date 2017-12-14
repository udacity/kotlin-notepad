package com.udacity.notepad

import android.app.Application

import com.udacity.notepad.data.DataStore

class NotesApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        DataStore.init(this)
    }
}
