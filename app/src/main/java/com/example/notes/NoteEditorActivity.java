package com.example.notes;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NoteEditorActivity extends AppCompatActivity {
    private EditText noteEditText;
    private NoteDatabaseHelper databaseHelper;
    private long noteId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        noteEditText = findViewById(R.id.noteEditText);
        databaseHelper = new NoteDatabaseHelper(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("note_id")) {
            noteId = extras.getLong("note_id");
            Note note = databaseHelper.getNoteById(noteId);
            if (note != null) {
                noteEditText.setText(note.getContent());
            }
        }

        FloatingActionButton saveNoteButton = findViewById(R.id.saveNoteButton);
        saveNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });
    }

    private void saveNote() {
        String content = noteEditText.getText().toString();
        if (noteId != -1) {
            Note updatedNote = new Note(noteId, content);
            databaseHelper.updateNote(updatedNote);
        } else {
            databaseHelper.addNote(content);
        }
        finish();
    }
}