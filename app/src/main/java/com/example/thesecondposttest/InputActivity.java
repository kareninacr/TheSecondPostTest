package com.example.thesecondposttest;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class InputActivity extends AppCompatActivity {
    Button btn_Simpan;
    EditText edt_Judul, edt_Desk;
    DatabaseReference databaseNotes;
    List<Notes> notes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        Intent intent = getIntent();

        databaseNotes = FirebaseDatabase.getInstance().getReference("notes").child(intent.getStringExtra(MainActivity.NOTES_ID));

        btn_Simpan = (Button) findViewById(R.id.btn_Simpan);
        edt_Judul  = (EditText) findViewById(R.id.edt_Judul);
        edt_Desk   = (EditText) findViewById(R.id.edt_Desk);

        notes = new ArrayList<>();



    }
}
