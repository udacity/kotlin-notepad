package com.udacity.notepad

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.udacity.notepad.crud.CreateActivity
import com.udacity.notepad.recycler.NotesAdapter
import com.udacity.notepad.util.SpaceItemDecoration
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        fab.setOnClickListener { startActivity(CreateActivity.get(this@MainActivity)) }

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.addItemDecoration(SpaceItemDecoration(this, R.dimen.margin_small))
        recycler.adapter = NotesAdapter(this)
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    public override fun onDestroy() {
        super.onDestroy()
        recycler!!.adapter = null
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> return super.onOptionsItemSelected(item)
        }
    }


    private fun refresh() {
        (recycler!!.adapter as NotesAdapter).refresh()
    }
}
