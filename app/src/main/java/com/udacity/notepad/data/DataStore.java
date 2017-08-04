package com.udacity.notepad.data;

import android.content.Context;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public final class DataStore {

    private DataStore() {}

    public static final Executor EXEC = Executors.newSingleThreadExecutor();

    private static NoteDatabase notes;

    public static void init(Context context) {
        notes = new NoteDatabase(context);
    }

    public static NoteDatabase getNotes() {
        return notes;
    }

    public static void execute(Runnable runnable) {
        EXEC.execute(runnable);
    }
}
