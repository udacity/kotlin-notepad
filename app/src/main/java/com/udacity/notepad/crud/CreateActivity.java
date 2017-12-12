package com.udacity.notepad.crud;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.udacity.notepad.R;
import com.udacity.notepad.data.DataStore;
import com.udacity.notepad.data.Note;

import java.util.Date;

public class CreateActivity extends AppCompatActivity {

    private TextView editText;

    public static Intent get(Context context) {
        return new Intent(context, CreateActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        editText = findViewById(R.id.edit_text);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_accept, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_accept:
                save();
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    private void save() {
        DataStore.INSTANCE.execute(new Runnable() {
            @Override
            public void run() {
                Note note = updateNote();
                DataStore.getNotes().insert(note);
            }
        });
    }

    private Note updateNote() {
        Note note = new Note();
        note.setText(editText.getText().toString());
        note.setUpdatedAt(new Date());
        return note;
    }
}
