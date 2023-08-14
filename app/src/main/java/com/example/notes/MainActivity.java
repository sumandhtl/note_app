package com.example.notes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView noteListView;
    private ArrayAdapter<Note> noteAdapter;
    private NoteDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noteListView = findViewById(R.id.noteListView);
        databaseHelper = new NoteDatabaseHelper(this);

        List<Note> notes = databaseHelper.getAllNotes();
        noteAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notes);
        noteListView.setAdapter(noteAdapter);

        noteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Note selectedNote = noteAdapter.getItem(position);
                openNoteEditor(selectedNote);
            }
        });

        FloatingActionButton addNoteButton = findViewById(R.id.addNoteButton);
        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNoteEditor(null);
            }
        });
    }

    private void openNoteEditor(Note note) {
        Intent intent = new Intent(this, NoteEditorActivity.class);
        if (note != null) {
            intent.putExtra("note_id", note.getId());
        }
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Note> notes = databaseHelper.getAllNotes();
        noteAdapter.clear();
        noteAdapter.addAll(notes);
        noteAdapter.notifyDataSetChanged();
    }
}