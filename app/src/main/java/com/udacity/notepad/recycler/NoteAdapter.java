package com.udacity.notepad.recycler;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.notepad.R;
import com.udacity.notepad.data.DataStore;
import com.udacity.notepad.data.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NotesViewHolder> {

    private List<Note> notes = new ArrayList<>();
    private boolean isRefreshing = false;

    // The context is not necessary
    // see onCreateViewHolder
    public NoteAdapter() {
        setHasStableIds(true);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        refresh();
    }

    @Override
    public long getItemId(int position) {
        return notes.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    @Override
    public NotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Here is why the context is not necessary to be pass to the constructor,
        // it can be retrieved from the parent
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotesViewHolder holder, int position) {
        final Note note = notes.get(position);
        holder.text.setText(note.getText());
    }

    public void refresh() {
        if (isRefreshing) {
            return;
        }

        isRefreshing = true;
        DataStore.execute(new Runnable() {
            @Override
            public void run() {
                final List<Note> notes = DataStore.getNoteDao().getAll();
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        NoteAdapter.this.notes = notes;
                        notifyDataSetChanged();
                        isRefreshing = false;
                    }
                });
            }
        });
    }

    static class NotesViewHolder extends RecyclerView.ViewHolder {

        TextView text;

        NotesViewHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
        }
    }
}
