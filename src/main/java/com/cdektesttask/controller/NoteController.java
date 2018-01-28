package com.cdektesttask.controller;


import com.cdektesttask.dao.NoteDAO;
import com.cdektesttask.model.Note;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;


@Controller
public class NoteController {

    private final NoteDAO noteDAO;

    public NoteController(NoteDAO noteDAO) {
        this.noteDAO = noteDAO;
    }

    @GetMapping(value = "/")
    public String listNote(Model model) {
        List<Note> listNote = noteDAO.findAll();
        model.addAttribute("allNotes", listNote);
        return "pages/home";
    }

    @GetMapping(value = "/search")
    public String searchListNote(Model model, @RequestParam String q) {
        List<Note> listNote = noteDAO.findAll(q);
        model.addAttribute("allNotes", listNote);
        return "fragments/notesTable :: noteTable";
    }

    @GetMapping(value = "/addNote")
    public String getViewAddNote(@ModelAttribute Note note) {
        return "pages/addNote";
    }

    @PostMapping(value = "/addNote")
    public ModelAndView addNote(@Valid Note note) {
        noteDAO.save(note);
        return new ModelAndView("redirect:/");
    }
}
