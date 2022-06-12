package com.udacity.jwdnd.course1.cloudstorage.services.impl;

import com.udacity.jwdnd.course1.cloudstorage.constant.CommonConstants;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Service;

@Service
public class NoteServiceImpl implements NoteService {

    private final UserService userService;
    private final NoteMapper noteMapper;

    public NoteServiceImpl(UserService userService, NoteMapper noteMapper) {
        this.userService = userService;
        this.noteMapper = noteMapper;
    }

    @Override
    public String insertNote(Note note, String userName) {
        Integer userId = userService.getUser(userName).getUserId();

        if (note.getNoteId() != null) {
            int res = noteMapper.updateNote(note);
            if (res < 0) {
                return CommonConstants.UPDATE_NOTE_FAIL;
            }
        } else {
            int res = noteMapper.insertNote(note, userId);
            if (res < 0) {
                return CommonConstants.UPLOAD_NOTE_FAIL;
            }
        }
        return null;
    }

    @Override
    public String deleteNote(Integer noteId) {
        int res = noteMapper.deleteNote(noteId);
        if (res < 0) {
            return CommonConstants.DELETE_NOTE_FAIL;
        }
        return null;
    }
}
