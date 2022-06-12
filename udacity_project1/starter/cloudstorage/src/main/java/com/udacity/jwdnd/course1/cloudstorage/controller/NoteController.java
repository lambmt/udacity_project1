package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class NoteController {
    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }


    @PostMapping("/note")
    public String insertNote(@ModelAttribute Note note, Principal principal, Model model) {

        String userName = principal.getName();
        String error = noteService.insertNote(note, userName);
        if (error != null) {
            model.addAttribute("error", error);
        } else {
            model.addAttribute("success", true);
        }
        return "res";
    }

    @GetMapping("/note/delete")
    public String deleteNote(@RequestParam Integer noteId, Model model) {
        String error = noteService.deleteNote(noteId);
        if (error != null) {
            model.addAttribute("error", error);
        } else {
            model.addAttribute("success", true);
        }
        return "res";
    }

}
