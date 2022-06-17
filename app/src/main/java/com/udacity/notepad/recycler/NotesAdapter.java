package com.udacity.notepad.recycler;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.notepad.R;
import com.udacity.notepad.data.DataStore;
import com.udacity.notepad.data.Note;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private final Context context;
    private List<Note> notes = new ArrayList<>();
    private boolean isRefreshing = false;

    public NotesAdapter(Context context) {
        this.context = context;
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_note, parent, false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotesViewHolder holder, int position) {
        final Note note = notes.get(position);
        holder.text.setText(note.getText());
    }

    public void refresh() {
        if (isRefreshing) return;
        isRefreshing = true;
        DataStore.execute(new Runnable() {
            @Override
            public void run() {
                final List<Note> notes = DataStore.getNotes().getAll();
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        NotesAdapter.this.notes = notes;
                        notifyDataSetChanged();
                        isRefreshing = false;
                    }
                });
            }
        });
    }

    public static class NotesViewHolder extends RecyclerView.ViewHolder {

        TextView text;

        NotesViewHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
        }
    }
}
