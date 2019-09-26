package com.example.thesecondposttest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String NOTES_JUDUL = "notesjudul";
    public static final String NOTES_ID = "notesid";
    public static final String NOTES_DESKRIPSI = "notesdeskripsi";

    EditText edt_Judul, edt_Desk;
    Button btn_Simpan;
    ListView listViewNotes;
    List<Notes> notes;
    DatabaseReference databaseNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseNotes = FirebaseDatabase.getInstance().getReference("notes");

        edt_Judul       = (EditText) findViewById(R.id.edt_Judul);
        edt_Desk        = (EditText) findViewById(R.id.edt_Desk);
        notes           = new ArrayList<>();

//        btn_Simpan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                addNotes();
//            }
//        });

        listViewNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            }
        });

        listViewNotes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Notes note = notes.get(i);
                showUpdateDeleteDialog(note.getId(), note.getJudul(), note.getDeskripsi());
                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseNotes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                notes.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Notes note = postSnapshot.getValue(Notes.class);
                    notes.add(note);
                }
                NotesListActivity notesList = new NotesListActivity(MainActivity.this, notes);
                listViewNotes.setAdapter(notesList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                }
        });
    }

    private void addNotes() {
        String judul = edt_Judul.getText().toString().trim();
        String deskripsi  = edt_Desk.getText().toString().trim();

        if(!TextUtils.isEmpty(judul)) {
            String id = databaseNotes.push().getKey();
            Notes note = new Notes(id, judul, deskripsi);
            databaseNotes.child(id).setValue(note);
            edt_Judul.setText("");
            edt_Desk.setText("");
            Toast.makeText(this, "Notes tersimpan", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Silahkan masukkan judul!", Toast.LENGTH_LONG).show();
        }
    }

    private boolean updateNotes(String id, String judul, String deskripsi) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("artist").child(id);

        Notes note = new Notes(id, judul, deskripsi);
        dR.setValue(note);
        Toast.makeText(getApplicationContext(), "Notes diperbarui", Toast.LENGTH_LONG).show();
        return true;
    }

    private void showUpdateDeleteDialog(final String id, String judul, String deskripsi) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_update, null);
        dialogBuilder.setView(dialogView);

        final EditText edt_judul  = (EditText) dialogView.findViewById(R.id.edt_Judul);
        final EditText edt_desk   = (EditText) dialogView.findViewById(R.id.edt_Desk);
        final Button btn_Update   = (Button) dialogView.findViewById(R.id.btn_Update);
        final ImageView btn_hapus = (ImageView) dialogView.findViewById(R.id.btn_hapus);

        dialogBuilder.setTitle(judul);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        btn_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String judul     = edt_judul.getText().toString().trim();
                String deskripsi = edt_desk.getTransitionName().toString().trim();
                if (!TextUtils.isEmpty(judul)) {
                    updateNotes(id, judul, deskripsi);
                    b.dismiss();
                }
            }
        });

        btn_hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteNotes(id);
                b.dismiss();
            }
        });
    }

    private boolean deleteNotes(String id) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("notes").child(id);
        dR.removeValue();

        DatabaseReference drTracks = FirebaseDatabase.getInstance().getReference("notes").child(id);
        drTracks.removeValue();
        Toast.makeText(getApplicationContext(), "Notes dihapus", Toast.LENGTH_LONG).show();
        return true;
    }

}
