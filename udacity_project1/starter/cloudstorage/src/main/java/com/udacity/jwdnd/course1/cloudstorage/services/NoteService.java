package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;

public interface NoteService {
    String insertNote(Note note, String userName);

    String deleteNote(Integer noteId);
}
