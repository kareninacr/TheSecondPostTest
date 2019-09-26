package com.example.thesecondposttest;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

    public class NotesListActivity extends ArrayAdapter<Notes> {
    private Activity context;
    private List<Notes> notes;

    public NotesListActivity(Activity context, List<Notes> notes) {
        super(context, R.layout.activity_list, notes);
        this.context = context;
        this.notes   = notes;
        }

        @Override
        public View getView(int position,View convertView, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();

            View listViewItem = inflater.inflate(R.layout.activity_list, null, true);
            TextView tx_Judul = (TextView) listViewItem.findViewById(R.id.tx_Judul);
            TextView tx_Desk  = (TextView) listViewItem.findViewById(R.id.tx_Desk);

            Notes note = notes.get(position);
            tx_Judul.setText(note.getJudul());
            tx_Desk.setText(note.getDeskripsi());

            return listViewItem;
        }
    }
