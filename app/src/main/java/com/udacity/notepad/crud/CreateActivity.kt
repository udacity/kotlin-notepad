package com.udacity.notepad.crud

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.udacity.notepad.R
import com.udacity.notepad.data.DataStore.execute
import com.udacity.notepad.data.DataStore.notes
import com.udacity.notepad.data.Note
import kotlinx.android.synthetic.main.activity_create.*
import java.util.*

class CreateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_accept, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_accept -> {
                save()
                finish()
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun save() {
        execute(Runnable {
            val note = updateNote()
            notes.insert(note)
        })
    }

    private fun updateNote(): Note {
        val note = Note()
        note.text = edit_text.text.toString()
        note.updatedAt = Date()
        return note
    }

    companion object {
        operator fun get(context: Context?): Intent {
            return Intent(context, CreateActivity::class.java)
        }
    }
}