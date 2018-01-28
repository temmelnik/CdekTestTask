package com.cdektesttask.dao;


import com.cdektesttask.model.Note;

import java.util.List;

/**
 * Data Access interface for work with Note entity
 **/
public interface NoteDAO {

    /**
     * Save Note object in database
     *
     * @param note element to be saved in database
     */
    void save(Note note);

    /**
     * Select all Note objects
     *
     * @return List with all Note objects stored in the database
     */
    List<Note> findAll();

    /**
     * Select all Note objects, which record contains string
     *
     * @param q string to search for
     * @return List with all Note objects, which record contains specified string
     */
    List<Note> findAll(String q);
}
