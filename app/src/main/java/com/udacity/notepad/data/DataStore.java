package com.udacity.notepad.data;

import android.content.Context;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public final class DataStore {

    private DataStore() {}

    public static final Executor EXEC = Executors.newSingleThreadExecutor();

    private static NoteDao noteDao;

    public static void init(Context context) {
        noteDao = new NoteDao(context);
    }

    public static NoteDao getNoteDao() {
        return noteDao;
    }

    public static void execute(Runnable runnable) {
        EXEC.execute(runnable);
    }
}
